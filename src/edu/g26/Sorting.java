package edu.g26;


public class Sorting {

    static <T extends Comparable<? super T>> void merge(T[] arr,int left,int mid,int right, T[] tmp){
        int n1 = mid - left + 1;
        int n2 = right - mid;


        int i = 0, j = 0;

        int k = 0;
        while (i < n1 && j < n2)
        {
            if (arr[left+i].compareTo(arr[right+j]) <=0 )
            {
                tmp[k] = arr[left+i];
                i++;
            }
            else
            {
                tmp[k] = arr[right+j];
                j++;
            }
            k++;
        }

        while (i < n1)
        {
            tmp[k] = arr[left+i];
            i++;
            k++;
        }

        while (j < n2)
        {
            tmp[k] = arr[right+j];
            j++;
            k++;
        }

        j=0;
        for(i=left;i<=right;i++){
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
        // Find sizes of two subarrays to be merged
        int n1 = mid - left + 1;
        int n2 = right - mid;


        // Initial indexes of first and second subarrays
        int i = left, j = mid + 1;

        // Initial index of merged subarry array
        int k = 0;
        while (i < n1 && j < n2)
        {
            if (arr[i] <=arr[ j])
            {
                tmp[k] = arr[i];
                i++;
            }
            else
            {
                tmp[k] =arr[j];
                j++;
            }
            k++;
        }

        /* Copy remaining elements of L[] if any */
        while (i < n1)
        {
            tmp[k] = arr[i];
            i++;
            k++;
        }

        /* Copy remaining elements of R[] if any */
        while (j < n2)
        {
            tmp[k] =arr[j];
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
