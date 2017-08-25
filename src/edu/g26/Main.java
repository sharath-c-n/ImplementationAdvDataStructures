package edu.g26;
/**
 * Group 26
 * @authors Sharath,Ankitha
 **/


/**
 * Main driver class
 */
public class Main {

    public static void main(String args[])
    {
        Integer arr[] = {12, 11, 13, 5, 6, 7};
        Integer tmp [] = new Integer[arr.length];
        GenericSorter.mergeSort(arr,tmp);
        GenericSorter.nSquareSort(arr);
        for(int i : arr){ System.out.println(i+" ");}
    }
}
