package client;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import javax.xml.crypto.Data;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;


public class Controller implements Initializable {
    @FXML
    public TextField textMsg;
    @FXML
    public TextArea sentText;

    Socket socket;
    DataInputStream inputStream;
    DataOutputStream outputStream;

    final String IP_ADDRESS = "localhost";
    final int PORT = 8189;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            socket = new Socket(IP_ADDRESS, PORT);
            inputStream = new DataInputStream(socket.getInputStream());
            outputStream = new DataOutputStream(socket.getOutputStream());

            Thread input = new Thread(() -> {
                try {
                    while (true) {
                        String str = inputStream.readUTF();

                        if (str.equals("/end")) {
                            break;
                        }

                        sentText.appendText(str + "\n");

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
            });
            input.start();

//            Thread outputStream = new Thread(() -> {
//                while (true) {
//                    String str = output.nextLine();
//
//                    msg.println(str);
//                }
//            });
//            outputStream.setDaemon(true);
//            outputStream.start();
//
//            try {
//                inputStream.join();
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void refresh(ActionEvent actionEvent) {
        sentText.clear();
        textMsg.requestFocus();
    }

    public void close(ActionEvent actionEvent) {
        Platform.exit();
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


}
