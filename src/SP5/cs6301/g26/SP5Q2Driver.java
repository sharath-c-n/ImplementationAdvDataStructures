package cs6301.g26;

import cs6301.g00.Shuffle;

import java.util.Scanner;

/**
 * SP5Q2Driver:
 *
 * @author : Sharath
 * 30/09/2017
 */
public class SP5Q2Driver {
    public static void main(String args[]) {
        System.out.println("Enter the Array Size to be sorted : ");
        Scanner scanner = new Scanner(System.in);
        int arraySize = scanner.nextInt();
        System.out.println("Enter K : ");
        int k = scanner.nextInt();
        Integer genericArray[] = new Integer[arraySize];
        for (int i = 0; i < arraySize; i++) {
            genericArray[i] = i;
        }
        //Shuffle the integer array
        Shuffle.shuffle(genericArray);

        MaxHeap<Integer> maxHeap = new MaxHeap<>(arraySize);
        for (int i = 0; i < arraySize; i++) {
            maxHeap.add(genericArray[i]);
        }
        System.out.println("K largest elements using MaxHeap: ");
        for(int i=0; i < k;i++){
            System.out.print(maxHeap.remove()+" ");
        }

        MinHeap<Integer> minHeap = new MinHeap<>(arraySize);
        for (int i = 0; i < k; i++) {
            minHeap.add(genericArray[i]);
        }
        for (int i = k; i < arraySize; i++) {
            if(genericArray[i].compareTo(minHeap.peek())>0){
              minHeap.remove();
              minHeap.add(genericArray[i]);
            }
        }
        System.out.println("K largest elements using MaxHeap: ");
        for(int i=0; i < k;i++){
            System.out.print(minHeap.remove()+" ");
        }

    }

}
