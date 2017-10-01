package cs6301.g26;

import cs6301.g00.Shuffle;
import cs6301.g00.Timer;

import java.util.Scanner;

/**
 * SP5Q4Driver:
 *
 * @author : Sharath
 * 01/10/2017
 */
public class SP5Q4Driver {
    public static  <T extends Comparable<? super T>> void mergeSort(T[] arr,T[] temp){
        mergeSort(arr,temp,0,arr.length-1);
    }
    private static  <T extends Comparable<? super T>> void merge(T[] arr, int left, int mid, int right, T[] tmp) {
        int i = left, j = mid + 1;
        for (int k = left; k <= right; k++) {
            if (j > right || (i <= mid && arr[i].compareTo(arr[j]) <= 0))
                tmp[k] = arr[i++];
            else
                tmp[k] = arr[j++];
        }
    }

    /**
     * merge sort with having a copy of input array in temporary array
     */
    private static  <T extends Comparable<? super T>> void mergeSort(T[] arr, T[] tmp, int left, int right) {
        if (left < right) {
            if (right - left <= 30) {
                QuickSort.insertionSort(arr, left, right);
            } else {
                 /*Avoiding over flow*/
                int mid = left + (right - left) / 2;
                mergeSort(tmp, arr, left, mid);
                mergeSort(tmp, arr, mid + 1, right);
                merge(tmp, left, mid, right, arr);
            }

        }
    }

    public static void main(String args[]) {
        System.out.println("Enter the Array Size to be sorted : ");
        Scanner scanner = new Scanner(System.in);
        int arraySize = scanner.nextInt();
        Integer genericArray1[] = new Integer[arraySize];
        Integer genericArray2[] = new Integer[arraySize];
        Integer temp[] = new Integer[arraySize];

        //uncomment for shuffled order and comment descending order code
        for (int i = 0; i < arraySize; i++) {
            genericArray1[i] = i;
        }

        /*//For arrays sorting with duplicates order
        for (
                int i = 0;
                i < arraySize; i++)

        {
            int j = 0;
            for (; arraySize > j + i && j < 12; j++)
                genericArray1[j + i] = i;
            i += j - 1;
        }*/

        //Shuffle the array
        Shuffle.shuffle(genericArray1);
        System.arraycopy(genericArray1, 0, genericArray2, 0, arraySize);
        System.arraycopy(genericArray1, 0, temp, 0, arraySize);

        Timer timer = new Timer();
        /*Sort integers using regular generic quick sort*/
        timer.start();
        mergeSort(genericArray1,temp);
        System.out.println(timer.end());


        /*Sort integers using generic dual pivot quick sort*/
        timer.start();
        QuickSort.dualPivotQuickSort(genericArray2);
        System.out.println(timer.end());
    }
}