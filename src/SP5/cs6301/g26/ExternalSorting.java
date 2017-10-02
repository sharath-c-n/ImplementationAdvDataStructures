package cs6301.g26;

import cs6301.g00.Timer;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.Random;
import java.util.Scanner;


/**
 * ExternalSorting:
 *
 * @author : Sandeep
 * 25/09/2017
 */
public class ExternalSorting {
    /*
     * This Function creates a file with Random Input in the range [1, size*1000000] of FileSize= size*1M in the Input Directory
     * e.g If size=5 then Random Input creates a file with 5M Integer Values
     * @param size : size of File in terms of 1M(1000000)
     *
     */
    public static void inputGenerator(int size) {
        try {
            File dir = new File("Input");
            dir.mkdir();
            BufferedWriter outputWriter = new BufferedWriter(new FileWriter("Input\\Input" + size + "MB.txt"));
            for (int counter = 0; counter < size; counter++) {
                System.out.println("In counter " + counter);
                int n = 1000000; //Intermediate Array Storage
                for (int i = 0; i < n; i++) {
                    Random rand = new Random();
                    int val = rand.nextInt(size * n) + 1;
                    outputWriter.write(val + " ");
                }
                outputWriter.newLine();
                System.out.println("Completed Stage ");
            }
            outputWriter.close();
        } catch (Exception ex) {
            System.out.println("Exception found in inputGenerator " + ex.getStackTrace());
        }
    }

    public static void main(String args[]) {
        Scanner in = new Scanner( System.in);
        System.out.println("Enter the File Size in terms of 1M");
        int size;
        size=in.nextInt();
        int option=2;
        System.out.println("Enter 1 to create Input File \n \t 2.Read From Existing File " );
        option=in.nextInt();
        if(option==1)
        inputGenerator(size);
        Timer timer = new Timer();
        System.out.println("External MergeSort: ");
        timer.start();
        ExternalMergeSort mergeSort = new ExternalMergeSort(size);
        System.out.println(timer.end());
        System.out.println("External QuickSort: ");
        timer.start();
        ExternalQuickSort quickSort = new ExternalQuickSort(size);
        System.out.println(timer.end());
    }
}
