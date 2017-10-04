package cs6301.g26;

import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * KWaySort:
 *
 * @author : Sharath
 * 03/10/2017
 */


public class KWaySort {
    /**
     * This class Used to keep track of individual sub-arrays of the main array in the heap
     */
    static class HeapNode {
        int val; // The element to be stored
        int statIdx; // index of the array from which the element is taken
        int endIdx; // index of the last element to be picked from array

        public HeapNode(int val, int statIdx, int endIdx) {
            this.val = val;
            this.statIdx = statIdx;
            this.endIdx = endIdx;
        }
    }

    /**
     * Public function to do k-way sort
     * @param array : input array
     * @param k : number of partitions
     */
    public static void sort(int[] array, int k) {
        int[] temp = new int[array.length];
        kWayMergeSort(array, 0, array.length - 1, k, temp);
    }

    /**
     * This function sorts the input array using insertion sort.
     * @param arr : input array
     */
    public static void insertionSort(int[] arr, int left, int right) {
        for (int i = left + 1; i <= right; ++i) {
            int key = arr[i];
            int j = i - 1;
            /* Move elements of arr[0..i-1], that are
               greater than key, to one position ahead
               of their current position */
            while (j >= left && arr[j] > key) {
                arr[j + 1] = arr[j];
                j = j - 1;
            }
            arr[j + 1] = key;
        }
    }

    /**
     * Main recursive loop that will sort the input array by dividing it k-part in each recursive call
     * @param array : input array
     * @param start : start index
     * @param end : end index
     * @param k : number of partitions needed
     * @param temp : used for merging
     */
    public static void kWayMergeSort(int[] array, int start, int end, int k, int[] temp) {
       //Apply normal sorting if the size of the array is less then the partition size
        if (end - start <= k) {
            insertionSort(array, start, end);
            return;
        }
        if (start < end) {
            int totalElements = end - start;
            //number items in each split
            int splitSize = totalElements / k,
                    i = start, idx = 0;
            //stores the splits for merge call
            HeapNode[] splits = new HeapNode[k];
            while (idx < k - 1) {
                int endIdx = i + splitSize;
                //call merge on each partition
                kWayMergeSort(array, i, endIdx - 1, k, temp);
                splits[idx++] = new HeapNode(array[i], i, endIdx - 1);
                i = endIdx;
            }
            //call merge on last partition, which will have all the remaining items
            if (i <= end) {
                kWayMergeSort(array, i, end, k, temp);
                splits[idx] = new HeapNode(array[i], i, end);
            }
            //merge all the sorted splits
            merge(array, splits, temp);
        }
    }

    /**
     * Merges the splits
     * @param array : input array
     * @param splits : all the splits of the array
     * @param temp : temporary array used for merging
     */
    private static void merge(int[] array, HeapNode[] splits, int[] temp) {
        PriorityQueue<HeapNode> pq = new PriorityQueue<>(splits.length, Comparator.comparingInt(a -> a.val));
        int startIdx = splits[0].statIdx;
        for (int i = 0; i < splits.length; i++) {
            pq.add(splits[i]);
        }
        int count;
        for (count = 0; pq.size() > 0; count++) {
            HeapNode root = pq.poll();
            temp[count] = root.val;
            if (root.statIdx < root.endIdx) {
                root.val = array[++root.statIdx];
                pq.add(root);
            }
        }
        System.arraycopy(temp, 0, array, startIdx, count);
    }
}
