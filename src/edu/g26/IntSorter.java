package edu.g26;

/**
 * @author Ankitha
 * This class containes all the integer sorting functions
 * code src : http://www.geeksforgeeks.org/
 */

public class IntSorter {

    private static  void merge(int[] arr,int left,int mid,int right, int[] tmp){
        // Initial indexes of first and second subarrays in the main array
        int i = left, j = mid+1;

        int k = 0;
        while (i <= mid && j <= right)
        {
            if (arr[i] <= arr[j])
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
        for(i=left;i<k;i++){
            arr[i] = tmp[j++];
        }
    }

    private static void mergeSorter(int[] arr, int[] tmp, int left, int right) {
        if (left < right)
        {
            /*Avoiding over flow*/
            int mid = left+ (right-left)/2;

            mergeSorter(arr, tmp,left, mid);
            mergeSorter(arr , tmp,mid+1, right);
            merge(arr, left, mid, right,tmp);
        }
    }



    public static void mergeSort(int[] arr, int[] tmp) {
        mergeSorter(arr, tmp,0,arr.length-1);
    }
}
