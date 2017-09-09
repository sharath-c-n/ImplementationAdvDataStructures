package cs6301.g26;


public class SortableList<T extends Comparable<? super T>> extends SinglyLinkedList<T> {

    void mergeSort() {
        mergeSorter(1, size);
        tail = getEntryAt(size);
    }

    /**
     * This function returns the item at the index i.
     * @param i : the index from which the item is to be retrieved
     * @return : entry Object at the index I.
     */
    public Entry<T> getEntryAt(int i) {
        Entry<T> entry = head;
        int cur = 0;
        while (cur < i && entry !=null ) {
            entry = entry.next;
            cur++;
        }
        return entry;
    }

    /**
     * This function will merge two parts of the input array and sorts them while merging it.
     * @param start : start index of the list
     * @param end : End index of the list
     * @param mid : middle index of the list
     */
    private void merge(int start, int end, int mid) {
        int i = start, j = mid + 1;
        //The new sorted list will be attached to this node, the node before the start index.
        Entry<T> tmp = getEntryAt(start - 1);

        //the entries/nodes from which the merging should start.
        Entry<T> left = tmp.next;
        Entry<T> right = getEntryAt(mid + 1);

        while (i <= mid && j <= end && left != null && right != null) {
            if (left.element.compareTo(right.element) <= 0) {
                tmp.next = left;
                tmp = left;
                left = left.next;
                i++;
            } else {
                tmp.next = right;
                tmp = right;
                right = right.next;
                j++;
            }
        }
        //copy the remaining elements
        while (i <= mid && left != null) {
            tmp.next = left;
            tmp = left;
            left = left.next;
            i++;
        }

        while (j <= end && right != null) {
            tmp.next = right;
            tmp = right;
            right = right.next;
            j++;
        }

        tmp.next = right;
    }

    /**
     * Recursive program to divide the list and merge  them in sorted order
     * @param start : start index of the list
     * @param end :end index of the list
     */
    private void mergeSorter(int start, int end) {
        if (start < end) {
            int mid = (start + end) / 2;
            mergeSorter(start, mid);
            mergeSorter(mid + 1, end);
            merge(start, end, mid);
        }
    }

    /**
     * Public static function to call mergeSort on a list.
     * @param list : list to be sorted
     * @param <T> : generic type which needs to implement comparable
     */
    public static <T extends Comparable<? super T>> void mergeSort(SortableList<T> list) {
        list.mergeSort();
    }

    public static void main(String[] args) {
        SortableList<Integer> sortableList = new SortableList<>();
        int size = 1000;
        Integer[] intArray = new Integer[size];
        for (int i = 0; i < size; i++) {
            intArray[i] = size - i;
        }
        //Shuffle the integer array
        for (int i = 0; i < size; i++) {
            sortableList.add(intArray[i]);
        }
        sortableList.printList();
        sortableList.mergeSort();
        sortableList.printList();
    }
}
