package graph.cells;

import com.jfoenix.controls.JFXButton;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

public class ButtonCell extends Cell {

    public ButtonCell(String id) {
        super(id);

        BorderPane pane = new BorderPane();

        pane.setPrefSize(100, 40);
        JFXButton view = new JFXButton(id);

        pane.setCenter(view);
        pane.setBorder(new Border(new BorderStroke(Color.BLUE,
                BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
        pane.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));

        setView(pane);

    }

}