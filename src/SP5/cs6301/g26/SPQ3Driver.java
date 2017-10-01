package cs6301.g26;

import cs6301.g00.Shuffle;
import cs6301.g00.Timer;

import java.util.Scanner;

/**
 * SPQ3Driver:
 *
 * @author : Sharath
 * 30/09/2017
 */
public class SPQ3Driver {

    public static void minHeap(Integer genericArray[], int k) {
        int arr[] = new int[k];
        MinHeap<Integer> minHeap = new MinHeap<>(genericArray.length);
        for (int i = 0; i < k; i++) {
            minHeap.add(genericArray[i]);
        }
        for (int i = k; i < genericArray.length; i++) {
            if (genericArray[i].compareTo(minHeap.peek()) > 0) {
                minHeap.remove();
                minHeap.add(genericArray[i]);
            }
        }
        for(int i=0;i<k;i++){
            arr[i] = minHeap.remove();
        }
    }


    public static void maxHeap(Integer genericArray[], int k) {
        MaxHeap<Integer> maxHeap = new MaxHeap<>(genericArray.length);
        for (int i : genericArray) {
            maxHeap.add(genericArray[i]);
        }
    }

    public static void main(String args[]) {
        System.out.println("Enter the Array Size to be sorted : ");
        Scanner scanner = new Scanner(System.in);
        SelectAlgorithm s = new SelectAlgorithm();
        int arraySize = scanner.nextInt();
        System.out.println("Enter K : ");
        int k = scanner.nextInt();
        Integer genericArray[] = new Integer[arraySize];
        for (int i = 0; i < arraySize; i++) {
            genericArray[i] = i;
        }
        Shuffle.shuffle(genericArray);
        Timer timer = new Timer();
        timer.start();
        maxHeap(genericArray,k);
        System.out.println("K largest elements using MaxHeap: "+timer.end());

        timer.start();
        minHeap(genericArray,k);
        System.out.println("K largest elements using MinHeap: " + timer.end());


        timer.start();
        Integer [] output = s.select(genericArray, k);
        System.out.println("K largest elements using select algorithm: "+ timer.end());
    }
}
