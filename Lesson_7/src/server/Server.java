package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Server {
    public static void main(String[] args) {
        ServerSocket server = null;
        Socket socket = null;

        DataInputStream inputStream;
        DataOutputStream outputStream;

        try {
            server = new ServerSocket(8189);
            System.out.println("Сервер запустился");

            socket = server.accept();
            System.out.println("Клиент подключился");

            inputStream = new DataInputStream(socket.getInputStream());
            outputStream = new DataOutputStream(socket.getOutputStream());
//            PrintWriter msg = new PrintWriter(new DataOutputStream(socket.getOutputStream()), true);

//            Thread input = new Thread(() -> {
            try {
                while (true) {

                    String str = inputStream.readUTF();

                    /**
                     * Ловим сообщение Клиента и отправляем всем в чат.
                     * если от клиента пришел /end - он отправляется обратно и обрабатывается на стороне клиента,
                     * завершая только работу клиента и сообщая остальным о выходе.
                     */
                    System.out.println(str);
                    outputStream.writeUTF(str);
                    outputStream.flush();
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    socket.close();
                    System.out.println("Сокет закрылся");
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    server.close();
                    System.out.println("Сервер отключен");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

