package cs6301.g26;

import cs6301.g00.Shuffle;
import cs6301.g00.Timer;

import java.util.Scanner;

/**
 * SP5Q2Driver:
 *
 * @author : Sharath
 * 30/09/2017
 */
public class SP5Q2Driver {
    public static void main(String args[]) {
        System.out.print("Enter the Array Size to be sorted : ");
        Scanner scanner = new Scanner(System.in);
        int arraySize = scanner.nextInt();
        Integer genericArray1[] = new Integer[arraySize];
        Integer genericArray2[] = new Integer[arraySize];

        /*//uncomment for shuffled order and comment descending order code
        for (int i = 0; i < arraySize; i++) {
            genericArray1[i] = i;
        }*/

        //For arrays sorting with duplicates order
        for (int i = 0; i < arraySize; i++) {
            int j =0;
            for(;arraySize > j+i && j<12;j++)
                genericArray1[j+i] = i;
            i += j-1;
        }

        //Shuffle the array
        Shuffle.shuffle(genericArray1);
        System.arraycopy(genericArray1,0,genericArray2,0,arraySize);

        Timer timer = new Timer();
        /*Sort integers using regular generic quick sort*/
        timer.start();
        QuickSort.quickSort1(genericArray1);
        System.out.println(timer.end());


        /*Sort integers using generic dual pivot quick sort*/
        timer.start();
        QuickSort.dualPivotQuickSort(genericArray2);
        System.out.println(timer.end());

    }

}
