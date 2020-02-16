package sample;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;


public class Controller {
    @FXML public TextField textToSand;
    @FXML public TextArea sentText;

    public void refresh(ActionEvent actionEvent) {
        sentText.clear();
        textToSand.requestFocus();
    }

    public void close(ActionEvent actionEvent) {
        Platform.exit();
    }

    public void sentMsg() {
        if(!textToSand.getText().trim().equals("")) {
            if (!(textToSand.getText()).equals("")) {
                sentText.appendText(textToSand.getText().trim() + "\n");
                sentText.positionCaret(sentText.getText().length());
                textToSand.clear();
            }
        } else { textToSand.clear(); }
        textToSand.requestFocus();
    }
}
