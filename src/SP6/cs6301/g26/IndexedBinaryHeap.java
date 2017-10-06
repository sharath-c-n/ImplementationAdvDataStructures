package cs6301.g26;

/**
 * IndexedBinaryHeap: extendsss the BinaryHeap to have indexes of the elements in the heap
 * to be stored in the element itself.
 * Elements Need to implement IndexedNode interface
 * @author : Sharath
 * 05/10/2017
 */

import java.util.Comparator;

public class IndexedBinaryHeap<T extends IndexedNode> extends BinaryHeap<T> {

    /**
     * Build a priority queue with a given array q, using q[0..n-1].
     * It is not necessary that n == q.length.
     * Extra space available can be used to add new elements.
     */
    public IndexedBinaryHeap(T[] q, Comparator<T> comp, int n) {
        super(q, comp, n);
    }

    /**
     * overloaded function to store indexs
     * @param i : new index of the element
     * @param element : element to be moved to the index
     */
    void move(int i, T element){
            pq[i] = element;
            element.setIndex(i);
    }

    /** restore heap order property after the priority of x has decreased */
    public void decreaseKey(T x) {
        percolateUp(x.getIndex());
    }
}
