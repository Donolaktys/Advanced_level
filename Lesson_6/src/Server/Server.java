package Server;

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

        try {
            server = new ServerSocket(8189);
            System.out.println("Сервер запустился");

            socket = server.accept();
            System.out.println("Клиент подключился");

            Scanner in = new Scanner(socket.getInputStream());
            Scanner output = new Scanner(System.in);
            PrintWriter msg = new PrintWriter(socket.getOutputStream(), true);

            Thread inputStream = new Thread(() -> {
                while (true) {
                    try {
                        String str = in.nextLine();

                        /**
                         * если от клиента пришел /end - он отправляется обратно и обрабатывается на стороне клиента,
                         * завершая только работу клиента
                         */
                        if(str.equals("/end")) {
                            msg.println(str);
                        } else {
                            System.out.println(str); }
                    } catch (NoSuchElementException e) {
                        break;
                    }
                }

            });

            Thread outputStream = new Thread(() -> {
                try {
                    while (true) {
                        String str = output.nextLine();

                        msg.println(str);
                        /**
                         * /end на стороне сервера завершает работу и клиента и сервера
                         */
                    if (str.equals("/end")) {
                        break;
                    }}
                } catch (NoSuchElementException e){
                    return;
                }
            });

            inputStream.setDaemon(true);
            inputStream.start();
            outputStream.start();

            try {
                outputStream.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
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
    }
}
