package FX;

import Utility.*;
import com.jfoenix.controls.*;
import javafx.collections.ObservableList;
import javafx.concurrent.*;
import javafx.fxml.*;
import javafx.scene.control.*;
import javafx.scene.input.*;
import javafx.scene.layout.*;

import java.io.IOException;

/**
 * Created by Alex on 5/31/2017.
 */
public class WindowController{

    @FXML private AnchorPane pane;
    @FXML private JFXTextField startBox;
    @FXML private JFXButton genButton;
    @FXML private JFXComboBox<String> startPage;
    @FXML private JFXComboBox<String> endPage;
    @FXML private JFXButton pathButton;
    @FXML private Label title;
    @FXML private StackPane stack;
    @FXML private Label spanCount;

    private JFXDialog loadDialog;
    private static JFXDialog errorSiteDialog;
    private Task<Graph> task;
    private Graph graph;

    @FXML public void initialize() throws IOException{
        fixComponents();
        createDialog();
    }

    @FXML public void GeneratePressed() throws IOException{
        createTask();
        String name = startBox.getText();
        if(!name.equals("")) {
            if(Loader.linkValid(name)) {
                new Thread(task).start();
                loadDialog.show();
            }else{
                errorSiteDialog.show();
                startBox.clear();
            }
        }
    }

    @FXML public void FindPathPressed(){}

    @FXML public void startEnterPressed(KeyEvent event)throws IOException{
        if (event.getCode() == KeyCode.ENTER){
            GeneratePressed();
        }
    }

    static void errorSiteButtonPressed(){
        errorSiteDialog.close();
    }

    private void createDialog() throws IOException {
        StackPane loader = FXMLLoader.load(getClass().getResource("LoadingScreen.fxml"));
        loadDialog = new JFXDialog(stack, loader, JFXDialog.DialogTransition.CENTER);
        AnchorPane errorSite = FXMLLoader.load(getClass().getResource("ErrorDialog.fxml"));
        errorSiteDialog = new JFXDialog(stack, errorSite, JFXDialog.DialogTransition.CENTER, true);
    }


    private void fixComponents() {
        genButton.setStyle("    -fx-padding: 0.7em 0.75em;\n   " +
                "    -fx-font-size: 12px;\n    " +
                "    -fx-button-type: RAISED;\n      " +
                "    -fx-background-color: rgb(77,102,204);\n" +
                "    -fx-pref-width: 94;\n   " +
                "    -fx-pref-height: 10;\n" +
                "    -fx-text-fill: WHITE;");
        pathButton.setStyle("    -fx-padding: 0.7em 0.75em;\n   " +
                "    -fx-font-size: 12px;\n    " +
                "    -fx-button-type: RAISED;\n      " +
                "    -fx-background-color: rgb(77,102,204);\n" +
                "    -fx-pref-width: 94;\n   " +
                "    -fx-pref-height: 10;\n" +
                "    -fx-text-fill: WHITE;");
        startBox.setFocusTraversable(false);
    }

    private void createTask(){
        task = new Task<Graph>() {
            @Override
            protected Graph call() throws Exception {
                graph = new Graph(startBox.getText());
                return graph;
            }
        };

        task.addEventHandler(WorkerStateEvent.WORKER_STATE_SUCCEEDED,
                t -> {
                    graph = task.getValue();
                    ObservableList<String> list = graph.getNamesList();
                    startPage.getItems().addAll(list);
                    endPage.getItems().addAll(list);
                    startBox.clear();
                    pane.requestFocus();
                    title.setText(graph.graphTitle() + " graph");
                    spanCount.setText("# of spans: " + graph.getSpanCount());

                    loadDialog.close();
                });
    }
}
