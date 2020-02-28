package client;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuBar;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;
import java.net.URL;
import java.util.ResourceBundle;


public class Controller implements Initializable {
    final String IP_ADDRESS = "localhost";
    final int PORT = 8189;
    @FXML
    public TextField textMsg;
    @FXML
    public TextArea sentText;
    @FXML
    public HBox authPanel;
    @FXML
    public TextField loginField;
    @FXML
    public PasswordField passwordField;
    @FXML
    public MenuBar menuBar;
    @FXML
    public HBox msgPanel;
    Socket socket;
    DataInputStream inputStream;
    DataOutputStream outputStream;
    private boolean authenticated;
    private String nickname;
    private boolean exit = false;

    public void setAuthenticated(boolean authenticated) {
        this.authenticated = authenticated;
        authPanel.setVisible(!authenticated);
        authPanel.setManaged(!authenticated);
        msgPanel.setVisible(authenticated);
        msgPanel.setManaged(authenticated);
        if (!authenticated) {
            nickname = "";

        }
        sentText.clear();
        setTitle("chat");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        authenticated = false;
        Platform.runLater(() -> {
            Stage stage = (Stage) sentText.getScene().getWindow();
            stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                @Override
                public void handle(WindowEvent event) {
                    if (socket != null && !socket.isClosed()) {
                        try {
                            outputStream.writeUTF("/end");
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
        });
    }

    public void connect() {
        try {
            socket = new Socket(IP_ADDRESS, PORT);
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

                        if (str.startsWith("/authok ")) {
                            setAuthenticated(true);
                            nickname = str.split(" ")[1];
                            break;
                        }
                        sentText.appendText(str + "\n");
                    }



                    // цикл работы
                    while (true) {
                        String str = inputStream.readUTF();

                        if(exit) break;

                        setTitle("chat : " + nickname);
                        // если отключение
                        if (str.equals("/end")) {
                            setAuthenticated(false);
                            break;
                        }

                        sentText.appendText(str + "\n");

                    }
                } catch (SocketException e) {
                    setAuthenticated(false);
                    sentText.appendText("Сервер отключился");
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    try {
                        socket.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }).start();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sentMsg() {
        if (!textMsg.getText().trim().equals("")) {
            if (!(textMsg.getText()).equals("")) {
                try {
                    outputStream.writeUTF(textMsg.getText().trim());
                    sentText.positionCaret(sentText.getText().length());
                    textMsg.clear();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        } else {
            textMsg.clear();
        }
        textMsg.requestFocus();
    }

    public void tryToAuth(ActionEvent actionEvent) {
        if (socket == null || socket.isClosed()) {
            connect();
        }

        try {
            outputStream.writeUTF("/auth " + loginField.getText() + " " + passwordField.getText());
            passwordField.clear();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void setTitle(String title) {
        Platform.runLater(() -> {
            ((Stage) textMsg.getScene().getWindow()).setTitle(title);
        });

    }
}
