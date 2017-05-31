package Utility;

/**
 * Created by Alex on 4/26/2017.
 */
public class Edge {
    int edge;
    double weight;
    public Edge(int edge, double weight){
        this.edge = edge;
        this.weight = weight;
    }

    public int getEdge(){
        return edge;
    }

    public double getWeight(){
        return weight;
    }
}
