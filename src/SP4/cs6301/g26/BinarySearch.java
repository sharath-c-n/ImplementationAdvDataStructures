package cs6301.g26;

/**
 * This class will do a recursive binary search for any generic elements in a sorted array
 * @author Ankitha
 */
public class BinarySearch {
    /**
     * This is the public function to perform a binary search on a sorted generic array.
     * @param arr : Sorted generic array
     * @param x : Generic element to be searched in the array
     * @param <T> : the generic element should implement comparable
     * @return : the element index in the provided array
     */
    public static<T extends Comparable<? super T>> int binarySearch(T[] arr, T x){
        return customBinarySearch(arr,0,arr.length,x);
    }

    /**
     * This is the public function to perform a binary search on a sorted generic array.
     * @param arr : Sorted generic array
     * @param low : Starting index of the generic sub-array
     * @param high : Ending index of the generic sub-array
     * @param x : Element to find
     * @param <T> : Generic type
     * @return : The element index in the provided array if found else -1.
     */
    public static<T extends Comparable<? super T>> int customBinarySearch(T[] arr,int low,int high ,T x) {
        {
            if (high >= low) {
                int mid = low + (high - low) / 2;

                // If the element is present at the middle itself
                if (x.compareTo(arr[mid]) >= 0 && x.compareTo(arr[mid + 1]) < 0) return mid;

                // If element is smaller than mid
                if (x.compareTo(arr[mid]) < 0) return customBinarySearch(arr, low, mid - 1, x);

                //At his point element can only be present in right sub-array
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


