package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;

public class Server {
    private Vector<ClientHandler> clients;
    private AuthService authService;

    public Server() {
        clients = new Vector<>();
        authService = new SimpleAuthService();
        ServerSocket server = null;
        Socket socket = null;

        try {
            server = new ServerSocket(8189);
            System.out.println("Сервер запустился");
            while (true) {
                socket = server.accept();
                System.out.println("Клиент подключился");
                new ClientHandler(socket, this);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                server.close();
                System.out.println("Сервер отключен");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public AuthService getAuthService() {
        return authService;
    }

    // Отправляем всем клиентам
    public void broadcastMsg(String msg) {
        for (ClientHandler c : clients) {
            c.sendMsg(msg);
        }
    }

    // Отправить приватное
    public void privateMsg(String sender, String recipient, String msg) {
        for (ClientHandler c : clients) {
            if (c.getNick().equals(recipient)) {
                c.sendMsg("Личное от " + sender + ": " + msg);
            }
            if (c.getNick().equals(sender)) {
                c.sendMsg("Личное для " + recipient + ": " + msg);
            }
        }
    }

    public boolean clientOnline(String nick) {
        boolean result = false;
        for (ClientHandler c : clients) {
            if (c.getNick().equals(nick)) {
                result = true;
            }
        }
        return result;
    }

    public void subscribe(ClientHandler clientHandler) {
        clients.add(clientHandler);
    }

    public void unsubscribe(ClientHandler clientHandler) {
        clients.remove(clientHandler);
    }
}

