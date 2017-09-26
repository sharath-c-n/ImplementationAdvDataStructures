package cs6301.g26;

import java.util.Arrays;

/**
 * Rearrange: this class has a function rearrange which rearranges the input array such that
 * all the negative elements are in the first part followed by positive numbers and the ordering of the
 * numbers are maintained.
 *
 * @author : Sharath
 */
public class Rearrange {
    public static void rearrangeMinusPlus(int[] arr) {
        rearrangeMinusPlus(arr,0, arr.length-1);
    }

    /**
     * Reverses the sub-array bounded by start and end
     * @param arr : input array
     * @param start : sub-array start index
     * @param end : sub-array end index
     */
    private static void reverse(int arr[], int start, int end) {
        while (start < end) {
            int tmp = arr[start];
            arr[start++] = arr[end];
            arr[end--] = tmp;
        }
    }

    /**
     * Merge the subarrays in a way that all the negative elements come first.
     * @param arr : input array
     * @param left : sub-array start index
     * @param mid : sub-array middle index
     * @param right : sub-array end index
     */
    private static void merge(int arr[], int left, int mid, int right) {
        int i = left;
        int j = mid + 1;
        //find the last index of negative element
        while (i <= mid && arr[i] < 0)
            i++;
        //find the last index of negative element
        while (j <= right && arr[j] < 0)
            j++;

        // reverse positive part
        reverse(arr, i, mid);
        // reverse negative part
        reverse(arr, mid + 1, j - 1);
        // reverse the middle part
        reverse(arr, i, j - 1);
    }

    //Recursive function like merge sort
    private static void rearrangeMinusPlus(int arr[], int left, int right) {
        if (left < right) {
            // avoid overflow
            int mid = left + (right - left) / 2;
            rearrangeMinusPlus(arr, left, mid);
            rearrangeMinusPlus(arr, mid + 1, right);
            merge(arr, left, mid, right);
        }
    }

    public static void main(String[] args) {
        int arr[] = {0,-3,23,7,-7,90,-35};
        rearrangeMinusPlus(arr);

        for (int element : arr) {
            System.out.print(element + " ");
        }
    }

}
