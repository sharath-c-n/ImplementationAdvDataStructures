package cs6301.g26;

import cs6301.g26.sorter.GenericSorter;
import cs6301.g26.sorter.IntSorter;
import cs6301.g26.util.Shuffle;
import cs6301.g26.util.Timer;

import java.util.Scanner;

/**
 * @author Sharath, Ankitha, Sandeep
 * SortAlgorithmComparator driver class
 */
public class SortAlgorithmComparator {


    public static void main(String args[]) {

        System.out.print("Enter the Array Size to be sorted : ");
        Scanner scanner = new Scanner(System.in);
        int arraySize = scanner.nextInt();
        int intArray[] = new int[arraySize];
        int tmp[] = new int[arraySize];
        Integer genericArray1[] = new Integer[arraySize];
        Integer genericArray2[] = new Integer[arraySize];
        Integer tmp2[] = new Integer[arraySize];
        for (int i = 0; i < arraySize; i++) {
            intArray[i] = i;
        }
        //Shuffle the integer array
        Shuffle.shuffle(intArray);

        /*Replicate the same integer array input so that we will give the same
        input to all the sort methods. */
        for (int i = 0; i < arraySize; i++) {
            genericArray1[i] = intArray[i];
            genericArray2[i] = intArray[i];
        }

        Timer timer = new Timer();

        /*Sort integers using generic merge sort*/
        timer.start();
        GenericSorter.mergeSort(genericArray1, tmp2);
        timer.stop();
        System.out.println("Total execution time to sort " + arraySize + " objects for Generic merge sort " +
                "in Java in milliSeconds: " + timer.getTime());


        /*Sort integers using normal mergeSort*/
        timer.start();
        IntSorter.mergeSort(intArray, tmp);
        timer.stop();
        System.out.println("Total execution time to sort " + arraySize + " objects for integer merge sort " +
                "in Java in milliSeconds: " + timer.getTime());


        /*Sort integers using generic n-squared sort*/
        timer.start();
        GenericSorter.nSquareSort(genericArray2);
        timer.stop();
        System.out.println("Total execution time to sort " + arraySize + " objects for Generic nSquared sort " +
                "in Java in milliSeconds: " + timer.getTime());

    }
}
