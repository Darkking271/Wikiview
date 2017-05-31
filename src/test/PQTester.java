package test;
import Utility.*;

/**
 * Created by Alex on 4/30/2017.
 */
public class PQTester {
    public static void main(String[] args){
        PriorityQueue pq = new PriorityQueue(5);
        Edge a = new Edge(4, .47382);
        pq.add(a);
        Edge b = new Edge(3, .2356);
        pq.add(b);
        Edge c = new Edge(5, .95748);
        pq.add(c);
        Edge d = new Edge(1, .483729);
        pq.add(d);
        Edge e = new Edge(2, .894937);
        pq.add(e);

        for (int i = 0; i < 5; i++){
            Edge z = pq.pull();
            System.out.println(z.getEdge() + " " + z.getWeight());
        }
    }
}
