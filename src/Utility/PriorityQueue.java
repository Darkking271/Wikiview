package Utility;

/**
 * Created by Alex on 4/25/2017.
 */
public class PriorityQueue {

    private Edge[] array;
    private int count;

    public PriorityQueue(int cap){
        array = new Edge[cap];
        count = 0;
    }

    public void add(Edge x){
        int k = count++;
        array[k] = x;
        while(k > 0){
            int p = (k - 1) >>> 1;
            if (array[k].weight < array[p].weight){
                Edge t = array[k];
                array[k] = array[p];
                array[p] = t;
                k = p;
            }
            else return;
        }
    }

    public Edge pull() {
        if (this.count == 0) return null;
        Edge x = array[0];
        array[0] = array[count-- - 1];
        array[count] = null;
        int k = 0;
        for (; ; ) {
            int left = k * 2 + 1;
            int right = left + 1;
            if (left >= count)
                break;
            int least;
            if (right >= count)
                least = left;
            else if (array[left].weight < array[right].weight)
                least = left;
            else
                least = right;
            Edge t = array[k];
            array[k] = array[least];
            array[least] = t;
            k = least;
        }
        return x;
    }

    public void update(Edge x){
        if (exists(x.edge)){
            for (int i = 0; i < array.length; i++){
                if (array[i] == null)
                    break;
                else if (array[i].edge == x.edge) {
                    array[i].weight = x.weight;

                    if (array[i + 1] != null){
                        if (array[i].weight > array[i + 1].weight)
                            siftUp(i);
                    }
                    else if (i != 0) {
                        if (array[i].weight < array[i - 1].weight)
                            siftDown(i);
                    }

                    return;
                }
            }
        }
    }

    public void siftUp(int x){
        Edge e = array[x];
        array[x] = array[x + 1];
        array[x + 1] = e;

        if (array[x + 1] != null && array[x + 2] != null) {
            if (array[x + 1].weight > array[x + 2].weight)
                siftUp(x + 1);
            else return;
        }
    }

    public void siftDown(int x){
        Edge e = array[x];
        array[x] = array[x - 1];
        array[x - 1] = e;

        if (array[x - 1].weight < array[x - 2].weight)
            siftDown(x - 1);
        else return;
    }

    public boolean exists(int x){
        for (int i = 0; i < array.length; i++){
            if (array[i] != null) {
                if (array[i].edge == x)
                    return true;
            }
        }
        return false;
    }

    public boolean isEmpty(){
        if (count == 0){
            return true;
        }
        return false;
    }
}
