package Utility;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.*;
import java.util.*;
import java.lang.*;

/**
 * Created by Alex 4/12/2017.
 */
public class Graph{

    static class Node{
        String name;
        ArrayList<Edge> edges;
        String contents;
        String url;
        List<String> links;
        boolean visited;
        double dist;
        Integer prev;
        public Node(String name, String contents, String url, List<String> links){
            this.name = name;
            edges = new ArrayList<>();
            this.visited = false;
            this.contents = contents;
            this.url = url;
            this.links = links;
        }
    }

    static class Pair implements Comparable<Pair>{
        String name;
        double weight;
        public Pair(String name, double weight){
            this.name = name;
            this.weight = weight;
        }

        public double getWeight(){
            return weight;
        }
        public int compareTo(Pair item){
            return Double.compare(item.getWeight(), this.weight);
        }
    }

    private ArrayList<Node> graph;
    private HashMap<String, Integer> map;
    private Loader load;
    private int count = 0;
    private final int maxDepth = 3;
    private final int maxBreadth = 4;
    private int recursed = 0;
    private String start;

    private int counted = 0;

    public Graph(String url) throws IOException{
        graph = new ArrayList<>();
        map = new HashMap<>();
        load = new Loader(maxBreadth);
        start = load.getName(url);
        System.out.println("Building : " + url);

        Node n = span(url);
        if (map.containsKey(n.name)){
            graph.get(map.get(n.name)).edges = n.edges;
        }else{
            graph.add(n);
            map.put(n.name, count);
        }
        addConnections();
        //newSpan(url);

        //load.save();
    }

    public String graphTitle(){
        return start;
    }

    public String[] getNames(){
        String[] names = new String[graph.size()];
        for (int i = 0; i < graph.size(); i++){
            names[i] = graph.get(i).name;
        }
        return names;
    }

    public ObservableList<String> getNamesList(){
        ObservableList<String> list = FXCollections.observableArrayList();
        for (Node n : graph){
            list.addAll(n.name);
        }
        return list;
    }

    public String similar(String name){
        int pos = map.get(name);
        ArrayList<Pair> sorted = new ArrayList<>();
        for (Node n : graph){
            Pair p = new Pair(n.name, Cosine.findSimilarity(graph.get(pos).contents, n.contents));
            sorted.add(p);
        }
        Collections.sort(sorted);
        return sorted.get(1).name;
    }

    public void displayGraph(){

        for (int i = 0; i < graph.size(); i++){
            System.out.println(i + " " + graph.get(i).name + " : " + graph.get(i).url);
            for (Edge e : graph.get(i).edges){
                System.out.println("\t" + e.edge + " " + graph.get(e.edge).name + " " + e.weight);
            }
        }
    }

    public ArrayList<String> shortPath(String a, String b){
        ArrayList<String> path = new ArrayList<>();
        Stack<String> rev = new Stack<>();
        Integer start = map.get(a);
        Integer end = map.get(b);

        if (start == null || end == null) {
            return null;
        }
        else if(newPath(start, end)){
            while (end != null) {
                rev.add(graph.get(end).name);
                end = graph.get(end).prev;
            }
            while(!rev.empty()) {
                path.add(rev.pop());
            }

            clearMarkers();
            return path;
        }else{
            clearMarkers();
            return null;
        }
    }

    public boolean checkSpan(){
        traverse(map.get(this.start));

        for (Node n : graph){
            if (!n.visited){
                System.out.println(n.name + " is not visited : " + map.get(n.name));
            }
        }

        clearMarkers();
        if (counted == graph.size()) {
            counted = 0;
            return true;
        }
        else{
            counted = 0;
            return false;
        }
    }

    private void traverse(Integer i){
        graph.get(i).visited = true;
        counted++;
        for (Edge e : graph.get(i).edges){
            if (!graph.get(e.edge).visited){
                traverse(e.edge);
            }
        }
    }

    public int getSpanCount(){
        int count = 1;
        traverse(map.get(this.start));

        while(!allVisited()){
            count++;
            traverse(nonVisited());
        }
        clearMarkers();
        return count;
    }

    private boolean allVisited(){
        for (Node n : graph){
            if (!n.visited)
                return false;
        }
        return true;
    }

    private Integer nonVisited(){
        for (Node n : graph){
            if (!n.visited){
                return map.get(n.name);
            }
        }
        return null;
    }

    private boolean oldPath(int a, int b){
        PriorityQueue pq = new PriorityQueue(graph.size());
        graph.get(a).prev = null;
        graph.get(a).dist = 0;
        graph.get(a).visited = true;

        for (Edge e : graph.get(a).edges){
            graph.get(e.edge).prev = a;
            graph.get(e.edge).dist += e.weight;
            graph.get(e.edge).visited = true;
            pq.add(e);
        }

        while(!pq.isEmpty()){
            Edge n = pq.pull();

            if (n.edge == b)
                return true;

            if (n != null) {
                for (Edge e : graph.get(n.edge).edges) {
                    graph.get(e.edge).prev = n.edge;
                    graph.get(e.edge).dist = e.weight + graph.get(n.edge).dist;
                    graph.get(e.edge).visited = true;
                    pq.add(e);
                }
            }

        }
        return false;
    }

    private boolean newPath(int a, int b){
        PriorityQueue pq = new PriorityQueue(graph.size());
        graph.get(a).prev = null;
        graph.get(a).dist = 0;

        for (int i = 0; i < graph.size(); i++){
            if (i != a){
                graph.get(i).prev = null;
                graph.get(i).dist = Integer.MAX_VALUE;
            }
            Edge e = new Edge(i, graph.get(i).dist);
            pq.add(e);
        }

        double alt;
        while (!pq.isEmpty()){
            Edge newE = pq.pull();

            for (Edge e : graph.get(newE.edge).edges){
                alt = graph.get(newE.edge).dist + e.edge;
                if (alt < graph.get(e.edge).dist){
                    graph.get(e.edge).dist = alt;
                    graph.get(e.edge).prev = newE.edge;
                    Edge newF = new Edge(e.edge, graph.get(e.edge).dist);
                    pq.update(newF);
                }
            }
        }

        if (graph.get(b).prev != null)
            return true;
        else return false;
    }

    private void clearMarkers(){
        for(Node n : graph){
            n.dist = 0.0;
            n.prev = null;
            n.visited = false;
        }
    }

    private Node span(String url){
        Site site = load.load(url);
        if (site == null) {
            return null;
        }
        Node n = new Node(site.name, site.contents, site.url, site.links);
        if (recursed < maxDepth) {
            for (String link : n.links) {
                this.recursed++;
                Node s = span(link);
                this.recursed--;
                if (s != null) {
                    if (map.containsKey(s.name)) {
                        Edge e = new Edge(map.get(s.name), Cosine.findSimilarity(n.contents, s.contents));
                        n.edges.add(e);
                    } else{
                        graph.add(s);
                        map.put(s.name, count++);
                        Edge e = new Edge(map.get(s.name), Cosine.findSimilarity(n.contents, s.contents));
                        n.edges.add(e);
                    }
                }
            }
        }
        if (map.containsKey(n.name)) {
            graph.get(map.get(n.name)).edges = n.edges;
        }
        return n;
    }

    private void addConnections(){
        for (Node n : graph){
            if (n.edges.size() == 0){
                for (String link : n.links){
                    String name = load.getName(link);
                    if (map.containsKey(name)){
                        Edge e = new Edge(map.get(name), Cosine.findSimilarity(n.contents, graph.get(map.get(name)).contents));
                        n.edges.add(e);
                    }
                }
            }
        }
    }
}
