package Utility;

import java.util.ArrayList;

/**
 * Created by Alex on 12/1/2017.
 */
public class SiteEdges {
    String name;
    ArrayList<String> edges;

    public SiteEdges(String name){
        this.name = name;
        edges = new ArrayList<>();
    }

    public void addEdges(String name){
        this.edges.add(name);
    }

    public String getName(){return name;}

    public ArrayList<String> getEdges(){return edges;}
}
