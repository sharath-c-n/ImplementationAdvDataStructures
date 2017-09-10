package cs6301.g26;
/**
 * This class provides a static method to sort the singly linked list using merge sort
 * @author Sharath
 */

public class SortableList<T extends Comparable<? super T>> extends SinglyLinkedList<T> {

    /**
     * Merge sort function which will sort teh current list
     */
    void mergeSort() {
        if (size < 2) {
            return;
        }
        //Save the first entry
        Entry next = head.next;
        //Make the list empty
        head.next = null;
        mergeSort(next);
    }

    /**
     * This function will merge the given list with the current list.
     * @param otherList : the list to be merged
     */
    void merge(SortableList<T> otherList) {
        Entry<T> tailx = head;
        Entry<T> tc = this.head.next;
        Entry<T> oc = otherList.head.next;
        while (tc != null && oc != null) {
            if (tc.element.compareTo(oc.element) <= 0) {
                tailx.next = tc;
                tailx = tc;
                tc = tc.next;
            } else {
                tailx.next = oc;
                tailx = oc;
                oc = oc.next;
            }
        }
        //Copy the remaining elements and update tail
        if (tc == null) {
            tailx.next = oc;
            tail = otherList.tail;
        } else {
            tailx.next = tc;
        }

    }

    /**
     * Recursive program to divide the list and merge  them in sorted order
     */
    private Entry mergeSort(Entry entry) {
        if (entry == null || entry.next == null) {
            return entry;
        }
        Entry mid = getMid(entry);
        Entry afterMid = mid.next;
        mid.next = null;
        Entry left = mergeSort(entry);
        Entry right = mergeSort(afterMid);
        SortableList temp = new SortableList();
        temp.head.next = left;
        temp.tail = mid;
        merge(temp);

        temp.head.next = right;
        temp.tail = tail;
        merge(temp);
        return null;
    }

    /**
     * This method will get the middle element in the given list
     * @param entry : entry from which the list starts.
     * @return middle entry in the list
     */
    private Entry<T> getMid(Entry<T> entry) {
        Entry<T> slowPtr = entry;
        Entry<T> fastPtr = entry.next;
        while (fastPtr != null && fastPtr.next != null) {
            fastPtr = fastPtr.next.next;
            slowPtr = slowPtr.next;
        }
        return slowPtr;
    }

    /**
     * Public static function to call mergeSort on a list.
     * @param list : list to be sorted
     * @param <T>  : generic type which needs to implement comparable
     */
    public static <T extends Comparable<? super T>> void mergeSort(SortableList<T> list) {
        list.mergeSort();
    }

    public static void main(String[] args) {
        SortableList<Integer> sortableList = new SortableList<>();
        int size = 100;
        Integer[] intArray = new Integer[size];
        for (int i = 0; i < size; i++) {
            intArray[i] = size - i;
        }
        for (int i = 0; i < size; i++) {
            sortableList.add(intArray[i]);
        }
        sortableList.printList();
        SortableList.mergeSort(sortableList);
        sortableList.printList();
    }
}
