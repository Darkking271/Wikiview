package FX;

import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;

/**
 * Created by Alex on 8/3/2017.
 */
public class ErrorDialogController {
    @FXML JFXButton errorSiteButton;

    @FXML public void initialize(){

        errorSiteButton.setStyle("    -fx-padding: 0.7em 0.75em;\n   " +
                                 "    -fx-font-size: 12px;\n    " +
                                 "    -fx-button-type: RAISED;\n      " +
                                 "    -fx-background-color: rgb(77,102,204);\n" +
                                 "    -fx-pref-width: 94;\n   " +
                                 "    -fx-pref-height: 10;\n" +
                                 "    -fx-text-fill: WHITE;");

    }

    @FXML public void errorSiteButtonPressed(){
        WindowController.errorSiteButtonPressed();
    }
}
