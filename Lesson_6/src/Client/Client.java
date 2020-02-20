package Client;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        Socket socket = null;

        final String IP_ADDRESS = "localhost";
        final int PORT = 8189;

        try {
            socket = new Socket(IP_ADDRESS, PORT);
            Scanner in = new Scanner(socket.getInputStream());
            Scanner output = new Scanner(System.in);
            PrintWriter msg = new PrintWriter(socket.getOutputStream(), true);

            Thread inputStream = new Thread(() -> {
                while (true) {
                    String str = in.nextLine();

                    if(str.equals("/end")) {
                        System.out.println("Работа завершена");
                        msg.println("Клиент отключен");
                        break;
                    }
                    System.out.println(str);
                }
            });

            Thread outputStream = new Thread(() -> {
                while (true) {
                    String str = output.nextLine();

                    msg.println(str);
                }
            });

            inputStream.start();
            outputStream.setDaemon(true);
            outputStream.start();

            try {
                inputStream.join();
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
        }
    }
}

