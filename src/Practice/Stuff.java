package Practice;

import java.util.*;

/**
 * Created by Alex on 4/26/2017.
 */
public class Stuff {

    public static void main(String[] args){
        Queue<Integer> pq = new LinkedList<>();
        for (int i = 2; i < 20; i += 2){
            pq.add(i);
        }
        pq.add(21);
        pq.add(1);
        while(!pq.isEmpty()){
            System.out.println(pq.poll());
        }
    }
}
