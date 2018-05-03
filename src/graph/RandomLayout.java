package graph;

import graph.cells.Cell;

import java.util.List;
import java.util.Random;

public class RandomLayout extends Layout {

    Graph graph;


    Random rnd = new Random();

    public RandomLayout(Graph graph) {

        this.graph = graph;

    }

    public void execute() {

        List<Cell> cells = graph.getModel().getAllCells();

        for (Cell cell : cells) {


            double x = rnd.nextDouble() * 1000;
            double y = rnd.nextDouble() * 1000;

            cell.relocate(x, y);

        }

    }

}