package edu.g26;
/**
 * Group 26
 * @authors Sharath,Ankitha
 **/


import edu.g26.sorter.GenericSorter;
import edu.g26.util.Timer;

/**
 * Main driver class
 */
public class Main {

    public static void main(String args[])
    {
        Integer arr[] = {12, 11, 13, 5, 6, 7};
        Integer tmp [] = new Integer[arr.length];
        Timer timer = new Timer();
        timer.start();
        GenericSorter.mergeSort(arr,tmp);
        timer.stop();

        System.out.println("Total execution time to create 1000K objects in Java in millis: "
                + timer.getTime());

        GenericSorter.nSquareSort(arr);
        for(int i : arr){ System.out.println(i+" ");}
    }
}
