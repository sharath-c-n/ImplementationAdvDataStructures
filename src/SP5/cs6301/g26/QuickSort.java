package cs6301.g26;

import java.util.Random;

/**
 * Created by Ankitha on 9/30/2017.
 */
public class QuickSort {
    static class Pivot {
        int left;
        int right;

        public Pivot(int left, int right) {
            this.left = left;
            this.right = right;
        }

        public Pivot() {
        }
    }

    public static <T extends Comparable<? super T>> void quickSort(T[] arr) {
        quickSort(arr, 0, arr.length - 1);
    }

    public static <T extends Comparable<? super T>> void dualPivotQuickSort(T[] arr) {
        dualPivotQuickSort(arr, 0, arr.length - 1);
    }


    private static <T extends Comparable<? super T>> void quickSort(T[] arr, int p, int r) {
        if (p <= r) {
            int q = partitionType2(arr, p, r);
            quickSort(arr, p, q - 1);
            quickSort(arr, q + 1, r);
        }
    }

    private static <T extends Comparable<? super T>> int partitionType1(T[] arr, int p, int r) {
        Random rand = new Random();
        int x = p + rand.nextInt(r - p);
        swap(arr, x, r);
        T pivot = arr[r];
        int i = p - 1;
        for (int j = p; j < r; j++) {
            if (arr[j].compareTo(pivot) <= 0) {
                i++;
                swap(arr, i, j);
            }
        }

        swap(arr, i + 1, r);
        return i + 1;

    }

    private static <T extends Comparable<? super T>> int partitionType2(T[] arr, int p, int r) {
        Random rand = new Random();
        int x = p + rand.nextInt(r - p);
        swap(arr, x, r);
        T pivot = arr[r];
        int i = p;
        int j = r;
        while (true) {
            while (arr[i].compareTo(pivot) < 0) {
                i++;
            }
            while (arr[j].compareTo(pivot) > 0) {
                j--;
            }
            if (i >= j) return j;
            swap(arr, i, j);
        }

    }

    private static <T extends Comparable<? super T>> void dualPivotQuickSort(T[] arr, int p, int r) {
        if (p < r) {
            int[] pivotes = new int[2];
            Pivot pivot = dualPartition(arr, p, r, pivotes);

            dualPivotQuickSort(arr, p, pivot.left - 1);
            dualPivotQuickSort(arr, pivot.right + 1, r);
            if (arr[pivot.left].compareTo(arr[pivot.right]) != 0)
                dualPivotQuickSort(arr, pivot.left + 1, pivot.right - 1);
        }
    }

    private static <T extends Comparable<? super T>> Pivot dualPartition(T[] arr, int p, int r, int[] pivotes) {
        Random rand = new Random();
        int x = p + rand.nextInt(r - p + 1);
        int y = p + rand.nextInt(r - p + 1);
        while (y == x) {
            y = p + rand.nextInt(r - p + 1);
        }

        swap(arr, p, x);
        swap(arr, r, y);
        if (arr[p].compareTo(arr[r]) > 0) {
            swap(arr, p, r);
        }
        T x1 = arr[p];
        T x2 = arr[r];
        int k, i;
        k = i = p + 1;
        int j = r - 1;

        while (i <= j) {
            //a[j] > x2
            if (x2.compareTo(arr[j]) < 0) {
                j--;
            }
            //a[i] < x1
            else if (x1.compareTo(arr[i]) > 0) {
                swap(arr, i, k);
                i++;
                k++;
            }
            //x1<=arr[i]<=x2
            else if (x1.compareTo(arr[i]) <= 0 && x2.compareTo(arr[i]) >= 0) {
                i++;
            }
            //a[i] > x2
            else if (x2.compareTo(arr[i]) < 0) {
                //a[j] < x1
                if (x1.compareTo(arr[j]) > 0) {
                    swap(arr, k, i);
                    swap(arr, k, j);
                    k++;
                    i++;
                    j--;
                }
                //a[j] > x1
                else {
                    swap(arr, i, j);
                    i++;
                    j--;
                }
            }
        }
        swap(arr, p, k - 1);
        swap(arr, j + 1, r);
        return new Pivot(k - 1, j + 1);
    }

    private static void swap(Object[] arr, int x, int r) {
        Object temp = arr[x];
        arr[x] = arr[r];
        arr[r] = temp;
    }
}
