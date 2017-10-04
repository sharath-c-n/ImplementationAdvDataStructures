// Ver 1.0:  Starter code for bounded size Binary Heap implementation

package cs6301.g26;

import cs6301.g00.Shuffle;
import cs6301.g00.Timer;
import sun.plugin.dom.exception.InvalidStateException;

import java.util.Comparator;
import java.util.Scanner;

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
        pq[heapSize] = x;
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
    /* TO DO.  Replaces root of binary heap by x if x has higher priority
	     (smaller) than root, and restore heap order.  Otherwise do nothing. 
	   This operation is used in finding largest k elements in a stream.
	 */
    }

    /**
     * pq[i] may violate heap order with parent
     */
    void percolateUp(int i) {
        T x = pq[i];
        while (i > 0 && c.compare(pq[parent(i)], x) > 0) {
            pq[i] = pq[parent(i)];
            i = parent(i);
        }
        pq[i] = x;
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
            pq[i] = pq[child];
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
    private boolean isEmpty() {
        return heapSize ==0;
    }

    /* sort array A[].
       Sorted order depends on comparator used to buid heap.
       min heap ==> descending order
       max heap ==> ascending order
     */
    public static <T> void heapSort(T[] A, Comparator<T> comp) {
        T [] temp = (T[]) new Object[A.length];
        BinaryHeap heap = new BinaryHeap(temp, comp,A.length);
        for(T item:A){
            heap.add(item);
        }
       // heap.buildHeap();
        int count = 0;
        while(!heap.isEmpty())
        {
            A[count++] = (T)heap.remove();
        }
    }

    public static void main(String []args){
        System.out.print("Enter the Array Size to be sorted : ");
        Scanner scanner = new Scanner(System.in);
        int arraySize = scanner.nextInt();
        Integer genericArray1[] = new Integer[arraySize];
        Integer genericArray2[] = new Integer[arraySize];

        //For arrays sorted in descending order
        for (int i = 0; i < arraySize; i++) {
            genericArray1[i] = arraySize-i;
        }
        Shuffle.shuffle(genericArray1);
        System.arraycopy(genericArray1,0,genericArray2,0,genericArray1.length);
        Timer timer = new Timer();
        timer.start();
        heapSort(genericArray1, Integer::compareTo);
        System.out.println("Binary heap ascending order" + timer.end());

        timer.start();
        heapSort(genericArray2, (x,y)->y-x);
        System.out.println("Binary heap descending order" + timer.end());
    }
}
