package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ClientHandler {
    Socket socket = null;
    DataInputStream inputStream;
    DataOutputStream outputStream;
    Server server;
    private String nick;
    private String login;

    public ClientHandler(Socket socket, Server server) {
        try {
            this.socket = socket;
            this.server = server;
            inputStream = new DataInputStream(socket.getInputStream());
            outputStream = new DataOutputStream(socket.getOutputStream());

            new Thread(() -> {
                try {
                    // цикл аутентификации
                    while (true) {
                        String str = inputStream.readUTF();
                        if (str.startsWith("/auth ")) {
                            String[] token = str.split(" ");
                            String newNick = server
                                    .getAuthService()
                                    .getNicknameByLoginAndPassword(token[1], token[2]);
                            if (server.clientOnline(newNick)) {
                                sendMsg("Этот клиент уже подключен");
                            } else if (newNick != null) {
                                sendMsg("/authok " + newNick);
                                nick = newNick;
                                login = token[1];
                                server.subscribe(this);
                                System.out.println("Клиент " + nick + " подключился");
                                break;
                            } else {
                                sendMsg("Неверный логин или пароль");
                            }
                        }
                    }

                    // цикл работы
                    while (true) {

                        String str = inputStream.readUTF();

                        if (str.equals("/end")) {
                            sendMsg("/end");
                            break;
                        }

                        // если приватное сообщение
                        if (str.startsWith("/w ")) {
                            String[] privateMsgInfo = str.split(" ", 3);
                            String recipient = privateMsgInfo[1];
                            String msg = privateMsgInfo[2];
                            if (server.clientOnline(recipient)) {
                                server.privateMsg(nick, recipient, msg);
                            } else {
                                sendMsg("!Получатель " + recipient + " не доступен или данные указаны не верно!");
                            }
                            break;
                        }

                        server.broadcastMsg(str);

                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    try {
                        inputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    try {
                        outputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    try {
                        socket.close();
                        System.out.println("Сокет закрылся");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    server.unsubscribe(this);
                    System.out.println("Клиент " + nick + " отключился");
                }
            }).start();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    //отправляем сообщение клиенту
    public void sendMsg(String msg) {
        try {
            outputStream.writeUTF(msg);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getNick() {
        return nick;
    }
}
