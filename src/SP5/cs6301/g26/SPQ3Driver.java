package cs6301.g26;

import cs6301.g00.Shuffle;

import java.util.Scanner;

/**
 * SPQ3Driver:
 *
 * @author : Sharath
 * 30/09/2017
 */
public class SPQ3Driver {
    public static void main(String args[]) {
        System.out.println("Enter the Array Size to be sorted : ");
        Scanner scanner = new Scanner(System.in);
        int arraySize = scanner.nextInt();
        System.out.println("Enter K : ");
        int k = scanner.nextInt();
        Integer genericArray[] = new Integer[arraySize];
        for (int i = 0; i < arraySize; i++) {
            genericArray[i] = i;
        }
        //Shuffle the integer array
        Shuffle.shuffle(genericArray);
        SelectAlgorithm s = new SelectAlgorithm();
        Integer output[] = new Integer[k];
        output = s.select(genericArray,k);
        for(int i =0;i<k; i++){
            System.out.print(output[i]+" ");
        }
    }
}
