package cs6301.g26;

/**
 * @author Ankitha
 */

import sun.plugin.dom.exception.InvalidStateException;

import java.util.Comparator;

public class BinaryHeap<T> {
    T[] pq;
    int heapSize;
    int capacity;
    Comparator<T> c;

    /**
     * Build a priority queue with a given array q, using q[0..n-1].
     * It is not necessary that n == q.length.
     * Extra space available can be used to add new elements.
     */
    public BinaryHeap(T[] q, Comparator<T> comp, int n) {
        pq = q;
        c = comp;
        heapSize = 0;
        capacity = n;
    }

    public void insert(T x) {
        add(x);
    }

    public T deleteMin() {
        return remove();
    }

    public T min() {
        return peek();
    }

    public void add(T x) {
        if (pq.length == heapSize) {
            throw new InvalidStateException("Heap Capacity reached");
        }
        move(heapSize,x);
        percolateUp(heapSize);
        heapSize++;
    }

    public T remove() {
        if (heapSize == 0) {
            throw new InvalidStateException("Heap Size 0 ");
        }
        T min = pq[0];
        pq[0] = pq[--heapSize];
        percolateDown(0);
        return min;
    }

    public T peek() {
        if (heapSize == 0) {
            throw new InvalidStateException("Heap Size 0 ");
        }
        return pq[0];
    }

    public void replace(T x) {
        if(c.compare(peek(),x) > 0){
            pq[0] = x;
            percolateDown(0);
        }
    }

    /**
     * pq[i] may violate heap order with parent
     */
    void percolateUp(int i) {
        T x = pq[i];
        while (i > 0 && c.compare(pq[parent(i)], x) > 0) {
            move(i, pq[parent(i)]);
            i = parent(i);
        }
        move(i, x);
    }

    void move(int i, T element) {
        pq[i] = element;
    }

    private int parent(int i) {
        return (i - 1) / 2;
    }

    /**
     * pq[i] may violate heap order with children
     */
    void percolateDown(int i) {
        T x = pq[i];
        int child = 2 * i + 1;
        while (child <= heapSize - 1) {
            if (child < heapSize - 1 && c.compare(pq[child], pq[child + 1]) > 0) {
                child++;
            }
            if (c.compare(x, pq[child]) <= 0) break;
            move(i, pq[child]);
            i = child;
            child = 2 * i + 1;
        }
        pq[i] = x;
    }

    /**
     * Create a heap.  Precondition: none.
     */
    void buildHeap() {
        for (int i = (pq.length - 1) / 2; i >= 0; i--) {
            percolateDown(i);
        }
    }

    public boolean isEmpty() {
        return heapSize == 0;
    }

    /* sort array A[].
       Sorted order depends on comparator used to buid heap.
       min heap ==> descending order
       max heap ==> ascending order
     */
    public static <T> void heapSort(T[] A, Comparator<T> comp) {
        T[] temp = (T[]) new Object[A.length];
        BinaryHeap heap = new BinaryHeap(temp, comp, A.length);
        for (T item : A) {
            heap.add(item);
        }
        // heap.buildHeap();
        int count = 0;
        while (!heap.isEmpty()) {
            A[count++] = (T) heap.remove();
        }
    }
}
