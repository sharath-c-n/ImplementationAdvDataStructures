package cs6301.g26.sorter;

/**
 * @author Sharath
 * This class containes all the generic sorting fnctions namely
 * 1) MergeSort
 * 2) Insertion Sort
 */

public class GenericSorter {

    /**
     * This is the public function which will be called to sort the array
     * @param arr : this is a mutable generic array which needs to be sorted
     * @param tmp : An array of size equal to arr, this is used while merging the sorted portions
     * @param <T> : The generic class must implement the Comparable interface
     */
    public static <T extends Comparable<? super T>> void mergeSort(T[] arr, T[] tmp) {
        mergeSorter(arr,tmp,0,arr.length-1);
    }

    /**
     * This function divides the array into small pieces and then sorts then while merging
     * @param arr input array
     * @param tmp temporary array
     * @param left the current window starting index
     * @param right the current window ending index
     * @param <T> : The generic class
     */
    private static <T extends Comparable<? super T>> void mergeSorter(T[] arr, T[] tmp, int left, int right) {
        if (left < right)
        {
            int mid = left+ (right-left)/2;

            mergeSorter(arr, tmp,left, mid);
            mergeSorter(arr , tmp,mid+1, right);

            merge(arr, left, mid, right,tmp);
        }
    }

    /**
     * This function will merge two parts of the input array and sorts them while merging it.
     * @param arr input array
     * @param left the current window starting index
     * @param right the current window ending index
     * @param tmp temporary array
     * @param <T> The generic class
     */
    private static <T extends Comparable<? super T>> void merge(T[] arr,int left,int mid,int right, T[] tmp){
        int i = left,
            j = mid+1,
            k = 0;
        while (i <= mid && j <= right)
        {
            if (arr[i].compareTo(arr[j]) <= 0)
            {
                tmp[k] = arr[i];
                i++;
            }
            else
            {
                tmp[k] = arr[j];
                j++;
            }
            k++;
        }
        //copy the remaining elements
        while (i <= mid)
        {
            tmp[k] = arr[i];
            i++;
            k++;
        }

        while (j <= right)
        {
            tmp[k] = arr[j];
            j++;
            k++;
        }

        j=0;
        for(i=left;j<k;i++){
            arr[i] = tmp[j++];
        }
    }

    /**
     * This function sorts the input array using insertion sort.
     * @param arr : input array
     * @param <T> : Generic class which implements comparable interface
     */
    public static <T extends Comparable<? super T>> void nSquareSort(T[] arr) {
        int n = arr.length;
        for (int i=1; i<n; ++i)
        {
            T key = arr[i];
            int j = i-1;

            /* Move elements of arr[0..i-1], that are
               greater than key, to one position ahead
               of their current position */
            while (j>=0 && arr[j].compareTo(key) > 0)
            {
                arr[j+1] = arr[j];
                j = j-1;
            }
            arr[j+1] = key;
        }
    }
}
