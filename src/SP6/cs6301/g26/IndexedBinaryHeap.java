package cs6301.g26;

/**
 * IndexedBinaryHeap:
 *
 * @author : Sharath
 * 05/10/2017
 */

import java.util.Comparator;

public class IndexedBinaryHeap<T extends IndexedNode> extends BinaryHeap<T> {

    /**
     * Build a priority queue with a given array q, using q[0..n-1].
     * It is not necessary that n == q.length.
     * Extra space available can be used to add new elements.
     *
     * @param q
     * @param comp
     * @param n
     */
    public IndexedBinaryHeap(T[] q, Comparator<T> comp, int n) {
        super(q, comp, n);
    }

    void move(int i, T element){
            pq[i] = element;
            element.setIndex(i);
    }
}
