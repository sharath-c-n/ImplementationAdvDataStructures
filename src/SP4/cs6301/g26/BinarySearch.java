package cs6301.g26;

public class BinarySearch {
    public static<T extends Comparable<? super T>> int binarySearch(T[] arr, T x){
        return customBinarySearch(arr,0,arr.length,x);
    }

    public static<T extends Comparable<? super T>> int customBinarySearch(T[] arr,int low,int high ,T x) {
        {
            if (high >= low) {
                int mid = low + (high - low) / 2;

                // If the element is present at the middle itself
                if (x.compareTo(arr[mid]) >= 0 && x.compareTo(arr[mid + 1]) < 0) return mid;

                // If element is smaller than mid, then it can only be present
                // in left subarray
                if (x.compareTo(arr[mid]) < 0) return customBinarySearch(arr, low, mid - 1, x);

                // Else the element can only be present in right subarray
                return customBinarySearch(arr, mid + 1, high, x);
            }

            // We reach here when element is not present in array
            return -1;
        }
    }

    public static void main(String args[]){
        int size = 100;
        int j=0;
        Integer[] a = new Integer[size];
        for(int i=0;i<size;i++){
            a[i] = j++;
            if(j == 45){
                j++;
            }
        }
        System.out.println(binarySearch(a,60));
    }
}


