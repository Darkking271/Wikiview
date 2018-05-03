package FX;

import Utility.*;
import com.jfoenix.controls.*;
import graph.cells.CellType;
import graph.Layout;
import graph.Model;
import graph.RandomLayout;
import javafx.collections.ObservableList;
import javafx.concurrent.*;
import javafx.fxml.*;
import javafx.scene.control.*;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import java.io.IOException;
import java.util.ArrayList;

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
    @FXML private JFXButton graphButton;
    @FXML private Label title;
    @FXML private StackPane stack;
    @FXML private Label spanCount;
    private graph.Graph window;

    private JFXDialog loadDialog;
    private static JFXDialog errorSiteDialog;
    private Task<Graph> buildTask;
    private Task<ArrayList> pathTask;
    private Graph graph;

    @FXML public void initialize() throws IOException{
        fixComponents();
        createDialog();
        buildGraphWindow();
        startBox.appendText("wikipedia.org/");
    }

    @FXML public void GeneratePressed() throws IOException{
        createTask();
        String name = startBox.getText();
        if(!name.equals("")) {
            if(Loader.linkValid(name)) {
                new Thread(buildTask).start();
                loadDialog.show();
            }else{
                errorSiteDialog.show();
                startBox.clear();
            }
        }
    }

    @FXML public void FindPathPressed(){
        window.getScrollPane().toFront();
    }

    @FXML public void GraphPressed(){

        window.getScrollPane().toFront();
        Model model = window.getModel();
        model.clear();
        model.clearAddedLists();
        window.getCellLayer().getChildren().clear();

        ArrayList<SiteEdges> names = graph.getEdges();

        window.beginUpdate();
            for(SiteEdges edge: names){
                model.addCell(edge.getName(), CellType.BUTTON);
            }
            for(SiteEdges edge: names){
                System.out.println(edge.getName() + " :");
                for (String s: edge.getEdges())
                    System.out.println("\t" + s);
            }

            for(SiteEdges edge: names){
                for(String s: edge.getEdges()) {
                    System.out.print("Finding from : " + edge.getName() + " to " + s + ": ");
                    if(model.exists(s)) {
                        model.addEdge(edge.getName(), s);
                        System.out.println("Found");
                    }else System.out.println("Not Found");
                }
            }
        window.endUpdate();

        Layout layout = new RandomLayout(window);
        layout.execute();

    }

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
        loadDialog.setOverlayClose(false);
        AnchorPane errorSite = FXMLLoader.load(getClass().getResource("ErrorDialog.fxml"));
        errorSiteDialog = new JFXDialog(stack, errorSite, JFXDialog.DialogTransition.CENTER, true);
    }


    private void fixComponents() {
        genButton.setStyle("    -fx-padding: 0.7em 0.75em;\n   " +
                "    -fx-font-size: 12px;\n    " +
                "    -fx-button-type: RAISED;\n      " +
                "    -fx-background-color: rgb(77,102,204);\n" +
                "    -fx-pref-width: 94;\n   " +
                "    -fx-pref-height: 6;\n" +
                "    -fx-text-fill: WHITE;");
        pathButton.setStyle("    -fx-padding: 0.7em 0.75em;\n   " +
                "    -fx-font-size: 12px;\n    " +
                "    -fx-button-type: RAISED;\n      " +
                "    -fx-background-color: rgb(77,102,204);\n" +
                "    -fx-pref-width: 94;\n   " +
                "    -fx-pref-height: 6;\n" +
                "    -fx-text-fill: WHITE;");
        graphButton.setStyle("    -fx-padding: 0.7em 0.75em;\n   " +
                "    -fx-font-size: 12px;\n    " +
                "    -fx-button-type: RAISED;\n      " +
                "    -fx-background-color: rgb(77,102,204);\n" +
                "    -fx-pref-width: 94;\n   " +
                "    -fx-pref-height: 6;\n" +
                "    -fx-text-fill: WHITE;");
        startBox.setFocusTraversable(false);
    }

    private void createTask(){
        buildTask = new Task<Graph>() {
            @Override
            protected Graph call() throws Exception {
                graph = new Graph(startBox.getText());
                return graph;
            }
        };

        buildTask.addEventHandler(WorkerStateEvent.WORKER_STATE_SUCCEEDED,
            t -> {
                graph = buildTask.getValue();
                ObservableList<String> list = graph.getNamesList();
                startPage.getItems().addAll(list);
                endPage.getItems().addAll(list);
                startBox.clear();
                pane.requestFocus();
                title.setText(graph.graphTitle() + " graph");
//                spanCount.setText("# of spans: " + graph.getSpanCount());

                loadDialog.close();
            });

        pathTask = new Task<ArrayList>() {
            @Override
            protected ArrayList call() throws Exception {
                ArrayList<String> path = graph.shortPath(startPage.getPromptText(), endPage.getPromptText());
                return path;
            }
        };

        pathTask.addEventHandler(WorkerStateEvent.WORKER_STATE_SUCCEEDED,
            t -> {
                loadDialog.close();
            });
    }

    private void buildGraphWindow(){
        window = new graph.Graph();
        AnchorPane.setTopAnchor(window.getScrollPane(), 70.0);
        AnchorPane.setLeftAnchor(window.getScrollPane(), 7.0);
        AnchorPane.setBottomAnchor(window.getScrollPane(), 50.0);
        AnchorPane.setRightAnchor(window.getScrollPane(), 7.0);
        pane.getChildren().add(window.getScrollPane());
        window.getScrollPane().toBack();
    }
}
