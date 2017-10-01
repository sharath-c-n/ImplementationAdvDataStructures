package cs6301.g26;

import sun.plugin.dom.exception.InvalidStateException;

import java.util.Comparator;

/**
 * BinaryHeap:
 *
 * @author : Sharath
 * 30/09/2017
 */
public abstract class  BinaryHeap <T extends Comparable<? super T>> implements Comparator<T> {
    int heapSize;
    int capacity;
    Object[] heap;

    public BinaryHeap(int capacity) {
        this.heapSize = 0;
        this.capacity = capacity;
        this.heap = new Object[capacity];
    }

    public void add(T x){
        if(capacity == heapSize){
            throw new InvalidStateException("Heap Capacity reached");
        }
        heap[heapSize]= x;
        percolateUp(heapSize);
        heapSize++;
    }

    private void percolateUp(int i) {
        Object x = heap[i];
        while(i>0 && compare((T)heap[parent(i)],(T)x)>0){
            heap[i] = heap[parent(i)];
            i= parent(i);
        }
        heap[i] = x;
    }

    private int parent(int i) {
        return (i-1)/2;
    }

    public T remove(){
        if(capacity == 0){
            throw new InvalidStateException("Heap Size 0 ");
        }
      Object min = heap[0];
      heap[0] = heap[--heapSize];
      percolateDown(0);
      return (T)min;
    }

    public T peek(){
        return (T)heap[0];
    }

    private void percolateDown(int i) {
        Object x = heap[i];
        int child = 2*i +1;
        while(child <= heapSize-1){
            if(child < heapSize-1 && compare((T)heap[child],(T)heap[child+1])>0){
                child++;
            }
            if(compare((T)x,(T)heap[child])<=0) break;
        heap[i] = heap[child];
        i=child;
        child = 2*i+1;
        }
     heap[i] = x;
    }
}
