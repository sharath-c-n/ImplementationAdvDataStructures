package cs6301.g26;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.*;

/**
 * @author Sandeep on 10/1/2017
 */

public class ExternalQuickSort {
    private static int tempId = 1; //Counter to Keep track of unused FileIds

    ExternalQuickSort(int size){
        writeInput(size);
        quickSort(1);
    }
/*
 * quickSort Reads the data from fileVal.txt and Sorts into fileVal.txt
 * This Function creates two more Files with Values less than min of Buffer in one File and greater max of Buffer into another File
 *  Size of (pivot)Buffer is <= 1M( 1000000)
 * Final Sorted data is Stored in 1.txt
 *
 */
    public void quickSort(int fileVal) {
        try {
            Scanner scanner = new Scanner(new File("LocalSortedInput1\\" + fileVal + ".txt"));
            int size = 0;
            PriorityQueue < Integer >  prq = new PriorityQueue< Integer >();
            int mx=Integer.MIN_VALUE;
            while (scanner.hasNext() && size < 1000000) {
                int val = scanner.nextInt();
                prq.offer(val);
                mx=Math.max(mx,val);
                size++;
            }
            //System.out.println("fINE HERE111");
            BufferedWriter outputWriter1 = null;
            BufferedWriter outputWriter2 = null;
            int ss1 = 0, ss2 = 0;
            if (scanner.hasNextInt()) {
                tempId++;
                ss1 = tempId;
                outputWriter1 = new BufferedWriter(new FileWriter("LocalSortedInput1\\" + tempId + ".txt", false));
                tempId++;
                ss2 = tempId;
                outputWriter2 = new BufferedWriter(new FileWriter("LocalSortedInput1\\" + tempId + ".txt", false));
            }
            while (scanner.hasNextInt()) {
                size++;
                int firstLeast=prq.peek();
                int val = scanner.nextInt();
                if (val <= firstLeast)
                    outputWriter1.write(val + " ");
                else if (val >= mx)
                    outputWriter2.write(val + " ");
                else {
                    int headMin=prq.poll();
                   outputWriter1.write(headMin+" ");
                   prq.add(val);
                }
            }
            scanner.close();
            int tempSize=size;
            if(size>1000000)
                tempSize=1000000;

            Integer[] arr1 = new Integer[tempSize];
            Integer ar [] =prq.toArray(arr1);
           QuickSort.quickSort1(ar);
            int arr[] =convertToIntArray(ar);
            //If the size is less than 1M do not Recurse
            if (size > 1000000) {
                quickSort(ss1);
                quickSort(ss2);
                combineFiles(fileVal,arr, ss1, ss2);
                outputWriter1.close();
                outputWriter2.close();
            } else
                writeToFile(fileVal, arr);
        } catch (Exception ex) {
            System.out.println("Exception Found in quickSort  " + ex.getStackTrace());
        }
    }
/*
 * Helper Function to convert Integer List to int[]
 */
    private int[] convertToIntArray(Integer [] ar) {

        int[] ret = new int[ar.length];
        for (int i = 0; i < ret.length; i++)
            ret[i] = ar[i];
        return ret;
    }
/*
 * Combine Files leftFile and RightFile and Pivot(buffer) and write back the output in fileId.txt
 *
 */
    private void combineFiles(int fileId, int arr[], int ss1, int ss2) {

        try {
            Scanner scanner1 = new Scanner(new File("LocalSortedInput1\\" + ss1 + ".txt"));
            Scanner scanner2 = new Scanner(new File("LocalSortedInput1\\" + ss2 + ".txt"));
            BufferedWriter outputWriter = new BufferedWriter(new FileWriter("LocalSortedInput1\\" + fileId + ".txt", false));
            while (scanner1.hasNextInt())
                outputWriter.write(scanner1.nextInt() + " ");
            for (int val : arr)
                outputWriter.write(val + " ");
            while (scanner2.hasNextInt())
                outputWriter.write(scanner2.nextInt() + " ");
            scanner1.close();
            scanner2.close();
            outputWriter.close();
        } catch (Exception ex) {
            System.out.println("Exception Found in CombineFiles  " + ex.getStackTrace());
        }
    }
/*
 * Write sorted Pivot(Buffer)to fileId.txt
 */
    private void writeToFile(int fileId, int[] arr) {
        try {
            BufferedWriter outputWriter = new BufferedWriter(new FileWriter("LocalSortedInput1\\" + fileId + ".txt", false));
            for (int val : arr)
                outputWriter.write(val + " ");
            outputWriter.close();
        } catch (Exception ex) {
            System.out.println(" Exception Found in  WriteToFiles " + ex.getStackTrace());
        }
    }
/*
 * Helper Function to write the Input File as 1.txt in LocalSortedInput1 directory
 */
    private static void writeInput(int size) {
        try {
            Scanner scanner = new Scanner(new File("Input\\Input" + size + "MB.txt"));
            File dir = new File("LocalSortedInput1");
            dir.mkdir();
            BufferedWriter outputWriter = new BufferedWriter(new FileWriter("LocalSortedInput1\\" + 1 + ".txt", false));
            while(scanner.hasNextInt())
                outputWriter.write(scanner.nextInt()+" ");
        }
        catch (Exception ex){
            System.out.println("Exception Found in writeInput "+ ex.getStackTrace());
        }
    }
}
