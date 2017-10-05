package cs6301.g26;

import cs6301.g00.Timer;
import cs6301.g00.Shuffle;

import java.util.Scanner;

/**
 * @author Sharath
 * MergeSort driver class
 */
public class MergeSort {
    public static void mergeSort(int[] arr) {
        mergeSort(arr, 0, arr.length - 1);
    }

    public static void mergeSort(int[] arr, int[] tmp) {
        mergeSortWithTemp(arr, tmp, 0, arr.length - 1);
    }

    public static void mergeSortWithInserionSort(int[] arr, int[] tmp) {
        mergeSortUsingInsertionSort(arr, tmp, 0, arr.length - 1);
    }

    public static void mergeSortWithCopy(int[] arr, int[] tmp) {
        mergeSortWithCopy(arr, tmp, 0, arr.length - 1);
    }


    private static void mergeSort(int[] arr, int left, int right) {
        if (left < right) {
            /*Avoiding over flow*/
            int mid = left + (right - left) / 2;

            mergeSort(arr, left, mid);
            mergeSort(arr, mid + 1, right);
            merge(arr, left, mid, right);
        }
    }

    private static void merge(int arr[], int l, int m, int r) {
        // Find sizes of two subarrays to be merged
        int n1 = m - l + 1;
        int n2 = r - m;

        /* Create temp arrays */
        int L[] = new int[n1];
        int R[] = new int[n2];

        /*Copy data to temp arrays*/
        for (int i = 0; i < n1; ++i)
            L[i] = arr[l + i];
        for (int j = 0; j < n2; ++j)
            R[j] = arr[m + 1 + j];


        /* Merge the temp arrays */

        // Initial indexes of first and second subarrays
        int i = 0, j = 0;

        // Initial index of merged subarry array
        int k = l;
        while (i < n1 && j < n2) {
            if (L[i] <= R[j]) {
                arr[k] = L[i];
                i++;
            } else {
                arr[k] = R[j];
                j++;
            }
            k++;
        }

        /* Copy remaining elements of L[] if any */
        while (i < n1) {
            arr[k] = L[i];
            i++;
            k++;
        }

        /* Copy remaining elements of R[] if any */
        while (j < n2) {
            arr[k] = R[j];
            j++;
            k++;
        }
    }

    /**
     * This function will mergeWithTempArray two parts of the input array and sorts them while merging it.
     * @param arr   input array
     * @param left  the current window starting index
     * @param right the current window ending index
     * @param tmp   temporary array
     */
    private static void mergeSortWithTemp(int[] arr, int[] tmp, int left, int right) {
        if (left < right) {
            /*Avoiding over flow*/
            int mid = left + (right - left) / 2;

            mergeSortWithTemp(arr, tmp, left, mid);
            mergeSortWithTemp(arr, tmp, mid + 1, right);
            mergeWithTempArray(arr, left, mid, right, tmp);
        }
    }

    /**
     * This function sorts the input array using insertion sort.
     * @param arr : input array
     */
    private static void insertionSort(int[] arr, int left, int right) {
        for (int i = left + 1; i <= right; ++i) {
            int key = arr[i];
            int j = i - 1;
            /* Move elements of arr[0..i-1], that are
               greater than key, to one position ahead
               of their current position */
            while (j >= left && arr[j] > key) {
                arr[j + 1] = arr[j];
                j = j - 1;
            }
            arr[j + 1] = key;
        }
    }

    /**
     * normal merge sort with one exception, when the size is less than a threshold using insertion
     * to merge that part.
     */
    private static void mergeSortUsingInsertionSort(int[] arr, int[] tmp, int left, int right) {
        if (left < right) {
            if (right - left <= 30) {
                insertionSort(arr, left, right);
                return;
            }
            /*Avoiding over flow*/
            int mid = left + (right - left) / 2;
            mergeSortWithTemp(arr, tmp, left, mid);
            mergeSortWithTemp(arr, tmp, mid + 1, right);
            mergeWithTempArray(arr, left, mid, right, tmp);
        }
    }

    /**
     * merge sort with having a copy of input array in temporary array
     */
    private static void mergeSortWithCopy(int[] arr, int[] tmp, int left, int right) {
        if (left < right) {
            if (right - left <= 30) {
                insertionSort(arr, left, right);
            } else {
                 /*Avoiding over flow*/
                int mid = left + (right - left) / 2;
                mergeSortWithCopy(tmp, arr, left, mid);
                mergeSortWithCopy(tmp, arr, mid + 1, right);
                mergeWithCopy(tmp, left, mid, right, arr);
            }

        }
    }

    /**
     * This function will mergeWithTempArray two parts of the input array and sorts them while merging it.
     *
     * @param arr   input array
     * @param left  the current window starting index
     * @param right the current window ending index
     * @param tmp   temporary array
     */
    private static void mergeWithTempArray(int[] arr, int left, int mid, int right, int[] tmp) {
        System.arraycopy(arr, left, tmp, left, right + 1 - left);
        int i = left, j = mid + 1;
        for (int k = left; k <= right; k++) {
            if (j > right || (i <= mid && tmp[i] <= tmp[j]))
                arr[k] = tmp[i++];
            else
                arr[k] = tmp[j++];
        }
    }

    /**
     * Merge the arr into temp instead of copying arr into tmp and them merging back to arr.
     */
    private static void mergeWithCopy(int[] arr, int left, int mid, int right, int[] tmp) {
        int i = left, j = mid + 1;
        for (int k = left; k <= right; k++) {
            if (j > right || (i <= mid && arr[i] <= arr[j]))
                tmp[k] = arr[i++];
            else
                tmp[k] = arr[j++];
        }
    }

    public static void main(String args[]) {
        Scanner scanner = new Scanner(System.in);
        int arraySize = scanner.nextInt();
        int array1[] = new int[arraySize];
        int array2[] = new int[arraySize];
        int array3[] = new int[arraySize];
        int array4[] = new int[arraySize];
        int tmp[] = new int[arraySize];
        for (int i = 0; i < arraySize; i++) {
            array1[i] = i;
        }
        //Shuffle the integer array
        Shuffle.shuffle(array1);

        /*Replicate the same integer array input so that we will give the same
        input to all the sort methods. */
        for (int i = 0; i < arraySize; i++) {
            array2[i] = array3[i] = array4[i] = array1[i];
        }

        Timer timer = new Timer();
        mergeSort(array1);
        System.out.println(timer.end());

        timer.start();
        mergeSort(array2, tmp);
        System.out.println(timer.end());

        timer.start();
        mergeSortWithInserionSort(array3, tmp);
        System.out.println(timer.end());

        for (int i = 0; i < array4.length; i++)
            tmp[i] = array4[i];
        timer.start();
        mergeSortWithCopy(array4, tmp);
        System.out.println(timer.end());
    }
}
