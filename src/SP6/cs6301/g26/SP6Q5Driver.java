package cs6301.g26;

import cs6301.g00.Shuffle;
import cs6301.g00.Timer;

import java.util.Scanner;

import static cs6301.g26.BinaryHeap.heapSort;

/**
 * SP6Q5Driver:
 *
 * @author : Sharath
 * 06/10/2017
 */
public class SP6Q5Driver {
    public static void main(String[] args) {
        System.out.print("Enter the Array Size to be sorted : ");
        Scanner scanner = new Scanner(System.in);
        int arraySize = scanner.nextInt();
        Integer genericArray1[] = new Integer[arraySize];
        Integer genericArray2[] = new Integer[arraySize];

        //For arrays sorted in descending order
        for (int i = 0; i < arraySize; i++) {
            genericArray1[i] = arraySize - i;
        }
        Shuffle.shuffle(genericArray1);
        System.arraycopy(genericArray1, 0, genericArray2, 0, genericArray1.length);
        Timer timer = new Timer();
        timer.start();
        heapSort(genericArray1, Integer::compareTo);
        System.out.println("Binary heap ascending order" + timer.end());

        timer.start();
        heapSort(genericArray2, (x, y) -> y - x);
        System.out.println("Binary heap descending order" + timer.end());
    }
}
