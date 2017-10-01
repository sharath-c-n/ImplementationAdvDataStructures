package cs6301.g26;

import cs6301.g00.Shuffle;

import java.util.Scanner;

import cs6301.g00.Timer;

/**
 * Created by Ankitha on 9/30/2017.
 */
public class SP5Q1Driver {
    public static void main(String args[]) {
        System.out.print("Enter the Array Size to be sorted : ");
        Scanner scanner = new Scanner(System.in);
        int arraySize = scanner.nextInt();
        Integer genericArray1[] = new Integer[arraySize];
        Integer genericArray2[] = new Integer[arraySize];
        //uncomment for shuffled order and comment descending order code
        /*for (int i = 0; i < arraySize; i++) {
            genericArray1[i] = i;
        }
        //Shuffle the array
        Shuffle.shuffle(genericArray1);*/

        //For arrays sorted in descending order
        for (int i = 0; i < arraySize; i++) {
            genericArray1[i] = arraySize-i;
        }

        System.arraycopy(genericArray1,0,genericArray2,0,arraySize);
        Timer timer = new Timer();
        /*Sort integers using generic quick sort 1*/
        timer.start();
        QuickSort.quickSort1(genericArray1);
        System.out.println(timer.end());

        /*Sort integers using generic quick sort 2*/
        timer.start();
        QuickSort.quickSort2(genericArray2);
        System.out.println(timer.end());

    }
}
