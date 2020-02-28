package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.net.Socket;

public class ClientHandler {
    Socket socket = null;
    DataInputStream inputStream;
    DataOutputStream outputStream;
    Server server;
    private String nick;
    private String login;
    private boolean exit = false;

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
                        if (str.equals("/end")) {
                            exit = true;
                            break;
                        }
                        if (str.startsWith("/auth ")) {
                            String[] token = str.split(" ");
                            if (token.length < 3) {
                                continue;
                            }
                            String newNick = server
                                    .getAuthService()
                                    .getNicknameByLoginAndPassword(token[1], token[2]);
                            if (newNick != null) {
                                login = token[1];
                                if (!server.isLoginAuthorized(login)) {
                                    sendMsg("/authok " + newNick);
                                    nick = newNick;
                                    server.subscribe(this);
                                    System.out.println("Клиент " + nick + " подключился");
                                    break;
                                } else {
                                    sendMsg("С этим логином уже авторизовались");
                                }
                            } else {
                                sendMsg("Неверный логин или пароль");
                            }
                        }
                    }

                    // цикл работы
                    while (true) {

                        String str = inputStream.readUTF();

                        if(exit) {
                            sendMsg("/end");
                            break;
                        }
                        // все сообщения начинающиеся с "/"
                        if (str.startsWith("/")) {
                            // сообщение о завершении работы клиента
                            if (str.equals("/end")) {
                                sendMsg("/end");
                                break;
                            }
                            // приватное сообщение
                            if (str.startsWith("/w ")) {
                                String[] privateMsgInfo = str.split(" ", 3);
                                if(privateMsgInfo.length == 3) {
                                    server.privateMsg(this, privateMsgInfo[1], privateMsgInfo[2]);
                                }
                            }
                        } else {
                            server.broadcastMsg(nick,   str);
                        }
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

    public String getLogin() {
        return login;
    }
}
