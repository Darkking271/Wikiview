package Practice;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;

/**
 * Created by Alex on 8/2/2017.
 */
public class DialogTestController {
    @FXML JFXButton button;
    @FXML AnchorPane pane;
    @FXML StackPane stack;
    JFXDialog window;

    @FXML
    public void initialize(){
        button.setStyle("    -fx-padding: 0.7em 0.75em;\n   " +
                "    -fx-font-size: 15px;\n    " +
                "    -fx-button-type: RAISED;\n      " +
                "    -fx-background-color: rgb(77,102,204);\n" +
                "    -fx-pref-width: 94;\n   " +
                "    -fx-pref-height: 10;\n" +
                "    -fx-text-fill: WHITE;");

        JFXDialogLayout content = new JFXDialogLayout();
        content.setMaxSize(280, 120);
        content.setBody(new Label("Loading"));
        window = new JFXDialog(stack, content, JFXDialog.DialogTransition.CENTER);
    }

    @FXML
    public void display(){
        window.show();
    }
}
