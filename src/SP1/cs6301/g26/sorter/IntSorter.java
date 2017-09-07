package cs6301.g26.sorter;

/**
 * @author Ankitha
 * This class containes all the integer sorting functions
 * code src : http://www.geeksforgeeks.org/
 */

public class IntSorter {

    /**
     * This is the public function which will be called to sort the array
     * @param arr : this is a mutable integer array which needs to be sorted
     * @param tmp : An array of size equal to arr, this is used while merging the sorted portions
     */
    public static void mergeSort(int[] arr, int[] tmp) {
        mergeSort(arr, tmp, 0, arr.length - 1);
    }

    /**
     * This function will merge two parts of the input array and sorts them while merging it.
     * @param arr input array
     * @param left the current window starting index
     * @param right the current window ending index
     * @param tmp temporary array
     */
    private static void mergeSort(int[] arr, int[] tmp, int left, int right) {
        if (left < right) {
            /*Avoiding over flow*/
            int mid = left + (right - left) / 2;

            mergeSort(arr, tmp, left, mid);
            mergeSort(arr, tmp, mid + 1, right);
            merge(arr, left, mid, right, tmp);
        }
    }

    /**
     * This function will merge two parts of the input array and sorts them while merging it.
     * @param arr input array
     * @param left the current window starting index
     * @param right the current window ending index
     * @param tmp temporary array
     */
    private static void merge(int[] arr, int left, int mid, int right, int[] tmp) {
        int i = left,
            j = mid + 1,
            k = 0;

        while (i <= mid && j <= right) {
            if (arr[i] <= arr[j]) {
                tmp[k] = arr[i];
                i++;
            } else {
                tmp[k] = arr[j];
                j++;
            }
            k++;
        }
        //copy the remaining elements
        while (i <= mid) {
            tmp[k] = arr[i];
            i++;
            k++;
        }

        while (j <= right) {
            tmp[k] = arr[j];
            j++;
            k++;
        }

        j = 0;
        for (i = left; j < k; i++) {
            arr[i] = tmp[j++];
        }
    }

}
