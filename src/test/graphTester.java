package test;

import Utility.Graph;

import java.io.IOException;
import java.util.*;

/**
 * Created by darki on 4/18/2017.
 */
public class graphTester {
    public static void main(String[] args)throws IOException{
        Graph g = new Graph("https://en.wikipedia.org/wiki/Computer_scientist");

        g.displayGraph();

        Scanner kb = new Scanner(System.in);
        String start = kb.nextLine();
        String end = kb.nextLine();

        ArrayList<String> path = g.shortPath(start, end);
        if (path != null){
            System.out.println("Path found");
        }
    }
}
