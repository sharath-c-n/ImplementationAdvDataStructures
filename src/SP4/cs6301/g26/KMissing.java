package cs6301.g26;

public class KMissing {
    public static void findKMissing(int a[]) {
        findKMissing(a, 0, a.length - 1);
    }

    static void findKMissing(int a[], int low, int high) {
        if (high >= low) {
            int mid = low + (high - low) / 2;
            if (high - low == 1) {
                for (int i = a[low] + 1; i < a[high]; i++) {
                    System.out.print(i + " ");
                }
                return;
            }
            if ((a[mid] + high - mid) != a[high]) {
                findKMissing(a, mid, high);
            }
            if ((a[low] + mid - low) != a[mid]) {
                findKMissing(a, low, mid);
            }
        }
    }

    public static void main(String[] args) {
        int[] a = {1, 2, 4, 5, 6, 10};
        findKMissing(a);
    }

}
