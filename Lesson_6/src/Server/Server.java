package Server;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Server {
    public static void main(String[] args) {
        ServerSocket server = null;
        Socket socket = null;

        try {
            server = new ServerSocket(8189);
            System.out.println("Сервер запустился");

            socket = server.accept();
            System.out.println("Клиент подключился");

            Scanner output = new Scanner(System.in);
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

            while (true) {
                    String str = null;
                    str = output.nextLine();

                    out.println(str);

                    if (str.equals("/end")) {
                        System.out.println("Сервер отключен");
                        break;
                    }
            }


        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                server.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
