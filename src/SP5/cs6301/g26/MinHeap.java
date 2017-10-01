package cs6301.g26;

/**
 * MinHeap:Extends the Binary heap and implements min heap by implementing the
 * comparator function
 * @author : Sharath
 * 30/09/2017
 */
public class MinHeap<T extends Comparable<? super T>> extends BinaryHeap<T> {
    public MinHeap(int capacity) {
        super(capacity);
    }

    @Override
    public int compare(T o1, T o2) {
        return o1.compareTo(o2);
    }
}
