package cs6301.g26;

import java.util.Arrays;
import java.util.Random;

/**
 * SelectAlgorithm:
 *
 * @author : Sharath
 * 30/09/2017
 */
public class SelectAlgorithm {
    public int threshold=6;
    public  <T extends Comparable<? super T>> T[] select(T[] arr, int k) {
        int n = arr.length;
        if(k<=0)  return null;
        if(k > n) return arr;
        select(arr, 0, n, k);
        return Arrays.copyOfRange(arr, n-k, n);
    }

    private  <T extends Comparable<? super T>> T select(T[] arr, int p, int n, int k) {
      int r = p+n-1;
      if(n < threshold){
          insertionSort(arr);
          return arr[p+n-k];
      }
      else {
          int q = QuickSort.partitionType2(arr,p,r);
          int left = q-p;
          int right = r-q;
          if(right>=k){
              return select(arr,q+1,right,k);
          }
          else if(right+1 == k){
               return arr[q];
          }
          else {
              return select(arr, p, left, k-(right+1));
          }
      }
    }

    /**
     * This function sorts the input array using insertion sort.
     * @param arr : input array
     * @param <T> : Generic class which implements comparable interface
     */
    public static <T extends Comparable<? super T>> void insertionSort(T[] arr) {
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
