package cs6301.g26;


public class SortableList<T extends Comparable<? super T>> extends SinglyLinkedList<T> {

    void mergeSort() {
        mergeSorter(1, size);
        tail = getEntryAt(size);
    }

    public Entry<T> getEntryAt(int i) {
        Entry<T> entry = head;
        int cur = 0;
        while (cur < i) {
            entry = entry.next;
            cur++;
        }
        return entry;
    }

    /**
     * This function will merge two parts of the input array and sorts them while merging it.
     */
    private void merge(int start, int end, int mid) {
        int i = start, j = mid + 1;
        Entry<T> tmp = getEntryAt(start - 1);
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


    private void mergeSorter(int start, int end) {
        if (start < end) {
            int mid = (start + end) / 2;
            mergeSorter(start, mid);
            mergeSorter(mid + 1, end);
            merge(start, end, mid);
        }
    }

    public static <T extends Comparable<? super T>> void mergeSort(SortableList<T> list) {
        list.mergeSort();
    }

    public static void main(String[] args) {
        SortableList<Integer> sortableList = new SortableList<>();
        int size = 1000000;
        Integer[] intArray = new Integer[size];
        for (int i = 0; i < size; i++) {
            intArray[i] = size - i;
        }
        //Shuffle the integer array
        for (int i = 0; i < size; i++) {
            sortableList.add(intArray[i]);
        }
       // sortableList.printList();
        sortableList.mergeSort();
        sortableList.printList();
        int count=0;
        for(Integer item : sortableList){
            if(item != count++){
                System.out.println("Not sorted");
                break;
            }
        }
        if(count == size)
            System.out.println("sorted!!");

    }
}
