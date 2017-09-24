package cs6301.g26;

import java.util.ArrayList;
import java.util.List;

/**
 * Finding the K missing elements in a sorted array.
 * @author Sharath
 */
public class KMissing {
    /**
     * Public function to find the K missing elements
     * @param a : sorted array which has elements missing
     */
    public static void findKMissing(int a[], List<Integer> missing) {
        findKMissing(a, 0, a.length - 1,missing);
    }

    /**
     * The main recursive function which prints the missing elements
     * @param a : input sorted array
     * @param low : Starting index of the sub-array
     * @param high : last index of the sub-array
     */
    static void findKMissing(int a[], int low, int high,List<Integer> missing) {
        if (high >= low) {
            //finally check if any elements are missing between 2 elements
            if (high - low == 1) {
                for (int i = a[low] + 1; i < a[high]; i++) {
                    missing.add(i);
                }
                return;
            }
            int mid = low + (high - low) / 2;
            //Check if any elements are missing in the right half
            if ((a[mid] + high - mid) != a[high]) {
                findKMissing(a, mid, high,missing);
            }
            //Check if any elements are missing in the left half
            if ((a[low] + mid - low) != a[mid]) {
                findKMissing(a, low, mid,missing);
            }
        }
    }

    /**
     * Tester method
     * @param args : command line arguments
     */
    public static void main(String[] args) {
        int[] a = {1, 2, 4, 8, 12};
        ArrayList<Integer> missingElements = new ArrayList<>();
        findKMissing(a,missingElements);
        missingElements.forEach(x->System.out.print(x+ " "));
    }

}
