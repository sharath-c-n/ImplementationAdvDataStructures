package edu.g26;
/**
 * Group 26
 *
 * @authors Sharath, Ankitha
 **/


import edu.g26.sorter.GenericSorter;
import edu.g26.sorter.IntSorter;
import edu.g26.util.Timer;

import java.util.Arrays;
import java.util.Collections;

/**
 * Main driver class
 */
public class Main {

    private static final int ARR_SIZE = 10000000;

    public static void main(String args[]) {
        int arr[] = new int[ARR_SIZE];
        int tmp[] = new int[ARR_SIZE];

        Integer arr2[] = new Integer[ARR_SIZE];
        Integer arr3[] = new Integer[ARR_SIZE];
        Integer tmp2[] = new Integer[ARR_SIZE];
        for (int i = 0; i < ARR_SIZE; i++) {
            arr[i] = ARR_SIZE-i;
        }
        for (int j = 0; j < 1000; j++) {
            Collections.shuffle(Arrays.asList(arr));
        }
        for (int i = 0; i < ARR_SIZE; i++) {
            arr2[i] = arr[i];
            arr3[i] = arr[i];
        }

        Timer timer = new Timer();

        timer.start();
        IntSorter.mergeSort(arr, tmp);
        timer.stop();
        System.out.println("Total execution time to sort 10000K objects for integer merge sort " +
                "in Java in millis: " + timer.getTime());

        timer.clear();


        timer.start();
        GenericSorter.mergeSort(arr2, tmp2);
        timer.stop();
        System.out.println("Total execution time to sort 10000K objects for Generic merge sort " +
                "in Java in millis: " + timer.getTime());


        timer.clear();
        timer.start();
        GenericSorter.nSquareSort(arr3);
        timer.stop();

        System.out.println("Total execution time to sort 10000K objects for Generic nSquared sort " +
                "in Java in millis: " + timer.getTime());
        for (int i = 0; i < ARR_SIZE; i++) {
            if (arr3[i] != i) {
                System.out.println(i + " ");
            }
        }

    }
}
