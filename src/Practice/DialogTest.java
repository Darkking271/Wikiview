package Practice;

import com.jfoenix.controls.JFXButton;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * Created by Alex on 8/2/2017.
 */
public class DialogTest extends Application{
    private Stage mainWindow;
    private Scene mainScene;

    public void start(Stage primaryStage) throws Exception{
        try {
            AnchorPane pane = FXMLLoader.load(getClass().getResource("DialogTest.fxml"));
            mainWindow = primaryStage;

            mainScene = new Scene(pane);
            mainWindow.setTitle("Dialog");
            mainWindow.setResizable(true);
            mainWindow.setScene(mainScene);
            mainWindow.setOnCloseRequest(e -> Platform.exit());
            mainWindow.show();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
