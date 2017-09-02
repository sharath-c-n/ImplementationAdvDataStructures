package cs6301.g26;

import cs6301.g26.sorter.GenericSorter;
import cs6301.g26.sorter.IntSorter;
import cs6301.g26.util.Shuffle;
import cs6301.g26.util.Timer;

/**
 * @author Sharath, Ankitha, Sandeep
 * Main driver class
 */
public class Main {

    private static final int ARR_SIZE = 5000000;

    public static void main(String args[]) {
        int intArray[] = new int[ARR_SIZE];
        int tmp[] = new int[ARR_SIZE];

        Integer genericArray1[] = new Integer[ARR_SIZE];
        Integer genericArray2[] = new Integer[ARR_SIZE];
        Integer tmp2[] = new Integer[ARR_SIZE];
        for (int i = 0; i < ARR_SIZE; i++) {
            intArray[i] = i;
        }
        //Shuffle the integer array
        Shuffle.shuffle(intArray);

        /*Replicate the same integer array input so that we will give the same
        input to all the sort methods. */
        for (int i = 0; i < ARR_SIZE; i++) {
            genericArray1[i] = intArray[i];
            genericArray2[i] = intArray[i];
        }

        Timer timer = new Timer();

        /*Sort integers using generic merge sort*/
        timer.start();
        GenericSorter.mergeSort(genericArray1, tmp2);
        timer.stop();
        System.out.println("Total execution time to sort " + ARR_SIZE + " objects for Generic merge sort " +
                "in Java in millis: " + timer.getTime());


        /*Sort integers using normal mergeSort*/
        timer.start();
        IntSorter.mergeSort(intArray, tmp);
        timer.stop();
        System.out.println("Total execution time to sort " + ARR_SIZE + " objects for integer merge sort " +
                "in Java in millis: " + timer.getTime());


        /*Sort integers using generic n-squared sort*/
        timer.start();
       // GenericSorter.nSquareSort(genericArray2);
        timer.stop();
        System.out.println("Total execution time to sort " + ARR_SIZE + " objects for Generic nSquared sort " +
                "in Java in millis: " + timer.getTime());

    }
}
