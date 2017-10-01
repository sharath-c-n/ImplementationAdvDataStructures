package cs6301.g26;

import java.util.Arrays;

/**
 * SelectAlgorithm: implements select algorithm to find the largest K elements
 * @author : Sharath
 * 30/09/2017
 */
public class SelectAlgorithm {
    public int threshold = 5;

    public <T extends Comparable<? super T>> T[] select(T[] arr, int k) {
        int n = arr.length;
        if (k <= 0) return null;
        if (k > n) return arr;
        int idx = select(arr, 0, n, k);
        return Arrays.copyOfRange(arr, idx, idx+k);
    }

    /**
     *
     * @param arr implements select algorithm to find the largest K elements
     * @param p : start index of array
     * @param n : end array index
     * @param k : number elements to find
     * @return : index from which the largest elements can be found
      */
    private <T extends Comparable<? super T>> int select(T[] arr, int p, int n, int k) {
        int r = p + n - 1;
        if (n < threshold) {
            //Using insertion sort if the size of array is less than a threshold
            QuickSort.insertionSort(arr,p,r);
            return p + n - k;
        } else {
            //Using partition algorithm from quick sort
            int q = QuickSort.partitionType2(arr, p, r);
            int left = q - p;
            int right = r - q;
            if (right >= k) {
                return select(arr, q + 1, right, k);
            } else if (right + 1 == k) {
                return q;
            } else {
                return select(arr, p, left, k - (right + 1));
            }
        }
    }


}
