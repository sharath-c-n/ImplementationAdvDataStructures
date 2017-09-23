package cs6301.g26;

/**
 * Finding the K missing elements in a sorted array.
 * @author Sharath
 */
public class KMissing {
    /**
     * Public function to find the K missing elements
     * @param a : sorted array which has elements missing
     */
    public static void findKMissing(int a[]) {
        findKMissing(a, 0, a.length - 1);
    }

    /**
     * The main recursive function which prints the missing elements
     * @param a : input sorted array
     * @param low : Starting index of the sub-array
     * @param high : last index of the sub-array
     */
    static void findKMissing(int a[], int low, int high) {
        if (high >= low) {
            //finally check if any elements are missing between 2 elements
            if (high - low == 1) {
                for (int i = a[low] + 1; i < a[high]; i++) {
                    System.out.print(i + " ");
                }
                return;
            }
            int mid = low + (high - low) / 2;
            //Check if any elements are missing in the right half
            if ((a[mid] + high - mid) != a[high]) {
                findKMissing(a, mid, high);
            }
            //Check if any elements are missing in the left half
            if ((a[low] + mid - low) != a[mid]) {
                findKMissing(a, low, mid);
            }
        }
    }

    /**
     * Tester method
     * @param args : command line arguments
     */
    public static void main(String[] args) {
        int[] a = {1, 2, 4, 5, 6, 10};
        findKMissing(a);
    }

}
