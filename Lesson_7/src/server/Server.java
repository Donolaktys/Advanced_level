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
    public void broadcastMsg(String nick, String msg) {
        for (ClientHandler c : clients) {
            c.sendMsg(nick + " : " + msg);
        }
    }

    // Отправить приватное
    public void privateMsg(ClientHandler sender, String recipient, String msg) {
        String privateMsg = String.format("%s для %s : %s", sender.getNick(), recipient, msg);
        for (ClientHandler c : clients) {
            if (c.getNick().equals(recipient)) {
                c.sendMsg(privateMsg);
                if (!sender.getNick().equals(recipient)) {
                sender.sendMsg(privateMsg);
                }
                return;
            }
        }
        sender.sendMsg("Not found user " + recipient);
    }

    public void subscribe(ClientHandler clientHandler) {
        clients.add(clientHandler);
    }

    public void unsubscribe(ClientHandler clientHandler) {
        clients.remove(clientHandler);
    }

    public boolean isLoginAuthorized (String login) {
        for (ClientHandler c : clients) {
            if (c.getLogin().equals(login)) {
                return true;
            }
        }
        return false;
    }
}

