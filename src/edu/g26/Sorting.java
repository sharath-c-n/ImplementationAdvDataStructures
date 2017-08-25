package edu.g26;


public class Sorting {

    static <T extends Comparable<? super T>> void merge(T[] arr,int left,int mid,int right, T[] tmp){
        int i = left, j = mid+1;

        int k = 0;
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
        for(i=left;i<k;i++){
            arr[i] = tmp[j++];
        }
    }

    static <T extends Comparable<? super T>> void mergeSorter(T[] arr, T[] tmp, int left, int right) {
        if (left < right)
        {
            int mid = left+ (right-left)/2;

            mergeSorter(arr, tmp,left, mid);
            mergeSorter(arr , tmp,mid+1, right);

            merge(arr, left, mid, right,tmp);
        }
    }


    static  void merge(int[] arr,int left,int mid,int right, int[] tmp){
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

    static void mergeSorter(int[] arr, int[] tmp, int left, int right) {
        if (left < right)
        {
            /*Avoiding over flow*/
            int mid = left+ (right-left)/2;

            mergeSorter(arr, tmp,left, mid);
            mergeSorter(arr , tmp,mid+1, right);
            merge(arr, left, mid, right,tmp);
        }
    }

    static <T extends Comparable<? super T>> void mergeSort(T[] arr, T[] tmp) {
        mergeSorter(arr,tmp,0,arr.length-1);
    }

    static void mergeSort(int[] arr, int[] tmp) {
        mergeSorter(arr, tmp,0,arr.length-1);
    }

    static <T extends Comparable<? super T>> void nSquareSort(T[] arr) {

    }

    public static void main(String args[])
    {
        int arr[] = {12, 11, 13, 5, 6, 7};
        int tmp [] = new int[arr.length];
        mergeSort(arr,tmp);
        for(int i : arr){ System.out.println(i+" ");}
    }
}
