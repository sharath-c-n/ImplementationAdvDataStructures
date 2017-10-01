package cs6301.g26;

import cs6301.g00.Shuffle;
import java.util.Scanner;
import  cs6301.g00.Timer;

/**
 * Created by Ankitha on 9/30/2017.
 */
public class SP5Q1Driver {
    public static void main(String args[]) {
        System.out.print("Enter the Array Size to be sorted : ");
        Scanner scanner = new Scanner(System.in);
        int arraySize = scanner.nextInt();
        Integer genericArray1[] = new Integer[arraySize];
        for (int i = 0; i < arraySize; i++) {
            genericArray1[i] = i;
        }
        //Shuffle the integer array
        Shuffle.shuffle(genericArray1);
        Timer timer = new Timer();
        /*Sort integers using generic merge sort*/
        timer.start();
        QuickSort.dualPivotQuickSort(genericArray1);
        System.out.println(timer.end());
        for (int i = 0; i < arraySize-1; i++) {
            if(genericArray1[i]>genericArray1[i+1]){
               // System.out.print(genericArray1[i]);
                System.out.print("not sorted");
                break;
           // }
        }
    }
}}
