package cs6301.g26;

/**
 * MaxHeap: Extends the Binary heap and implements max heap by implementing the
 * comparator function
 * @author : Sharath
 * 30/09/2017
 */
public class MaxHeap <T extends Comparable<? super T>> extends BinaryHeap<T>  {
    public MaxHeap(int capacity) {
        super(capacity);
    }

    @Override
    public int compare(T o1, T o2) {
        return Integer.compare(0, o1.compareTo(o2));
    }
}
