package cs6301.g26;

import cs6301.g00.Shuffle;

import java.util.Scanner;

import cs6301.g00.Timer;

/**
 * Created by Ankitha on 9/30/2017.
 */
public class SP6Q1Driver {
    public static void main(String args[]) {
        System.out.print("Enter the Array Size to be sorted : ");
        Scanner scanner = new Scanner(System.in);
        int arraySize = scanner.nextInt();
        System.out.print("Enter the K endIdx : ");
        int k = scanner.nextInt();
        int array[] = new int[arraySize];

        for (int i = 0; i < arraySize; i++) {
            array[i] = i;
        }
        //Shuffle the array
        Shuffle.shuffle(array);


        Timer timer = new Timer();
        /*Sort integers using generic quick sort 1*/
        timer.start();
        KWaySort.sort(array, k);
        System.out.println(timer.end());
        for (int i = 0; i < arraySize - 1; i++) {
            if (array[i] > array[i + 1]) {
                System.out.println("Not sorted!!!");
                break;
            }
        }

    }
}
