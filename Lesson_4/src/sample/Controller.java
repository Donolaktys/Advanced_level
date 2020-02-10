package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;

import java.awt.*;

public class Controller {
    @FXML
    //текст для отправки
    public TextField textToSand;
    //отправленный текст
    public TextArea sentText;

    //нажатие мышкой кнопки "отправить"
    public void onMouse(ActionEvent actionEvent) {
        sentText.setText(sentText.getText() + "\n" + textToSand.getText());
        sentText.positionCaret(sentText.getText().length());
        textToSand.setText("");
        textToSand.requestFocus();
    }

    //нажатие клавиши Enter в строке сообщения
    public void onEnter(KeyEvent keyEvent) {
        if (keyEvent.getCode().equals(KeyCode.ENTER)) {
            sentText.setText(sentText.getText() + "\n" + textToSand.getText());
            sentText.positionCaret(sentText.getText().length());
            textToSand.setText("");
            textToSand.requestFocus();
        }
    }
}
