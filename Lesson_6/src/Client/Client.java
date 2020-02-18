package Client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
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

                while (true) {
                        String str = in.nextLine();

                        if(str.equals("/end")) {
                            System.out.println("Сервер отключен");
                            break;
                        }

                        System.out.println(str);
                }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

