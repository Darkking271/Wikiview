package FX;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class Main extends Application {
    private Stage mainWindow;
    private Scene mainScene;

    public void start(Stage primaryStage) throws Exception{
        try {
            AnchorPane pane = FXMLLoader.load(getClass().getResource("Window.fxml"));
            mainWindow = primaryStage;

            mainScene = new Scene(pane);
            mainWindow.setTitle("Wikiview");
            mainWindow.setResizable(true);
            mainWindow.setScene(mainScene);
            mainWindow.show();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void stop() throws Exception
    {
        Platform.exit();
        System.exit(0);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
