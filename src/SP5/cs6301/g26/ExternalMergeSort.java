package cs6301.g26;

import java.io.*;
import java.util.Scanner;

public class ExternalMergeSort {
    public static class PassValueByReference {
        int val;

        PassValueByReference(int val) {
            this.val = val;
        }

        int getVal() {
            return this.val;
        }

        void setVal(int x) {
            this.val = x;
        }

    }

    ExternalMergeSort(int size) {
        sortLocalMemory(size);
        kwayMerge(1, size);
    }
 /*
  * Read the data from file, create files of sorted 1M(1000000)data and store in LocalSortedInput
  *
  */
    public void sortLocalMemory(int size) {
        try {
            Scanner scanner = new Scanner(new File("Input\\Input" + size + "MB.txt"));
            File dir = new File("LocalSortedInput");
            dir.mkdir();
            for (int j = 0; j < size; j++) {
                BufferedWriter outputWriter = new BufferedWriter(new FileWriter("LocalSortedInput\\" + (j + 1) + ".txt", false));
                int[] ar = new int[1000000];
                for (int i = 0; i < 1000000; i++) {
                    ar[i] = scanner.nextInt();
                }
                MergeSort.mergeSort(ar);
                for (int i = 0; i < 1000000; i++)
                    outputWriter.write(ar[i] + " ");
            }
        } catch (Exception ex) {
            System.out.println("Exception Found  " + ex.getStackTrace());
        }
    }
    /*
     * kwayMerge Function uses divide and conquer approach to merge k-sorted files each of size 1M.
     * Final Sorted Data is Stored in 1.txt in LocalSortedInput directory.
    */
    public int kwayMerge(int low, int high) {
        if (low > high) {
            System.out.println("Low Index and High Index set are Incorrect");
            return -1;
        } else if (low == high)
            return low;
        else {
            int mid = (low + high) / 2;
            int leftFileId = kwayMerge(low, mid);
            int righFileId = kwayMerge(mid + 1, high);
            mergeFiles(leftFileId, righFileId);
            return leftFileId;
        }
    }
    /*
     * This function performs Merging two sorted Files  leftFileId.txt and RightFileId.txt and stores the result in leftFileId.txt
     */
    private void mergeFiles(int leftFileId, int rightFileId) {
        try {
            Scanner leftFilePtr = new Scanner(new File("LocalSortedInput\\" + leftFileId + ".txt"));
            Scanner rightFilePtr = new Scanner(new File("LocalSortedInput\\" + rightFileId + ".txt"));
            BufferedWriter outputWriter = new BufferedWriter(new FileWriter("LocalSortedInput\\temp.txt", false));
            PassValueByReference leftVal = new PassValueByReference(-1), rightVal = new PassValueByReference(-1);
            boolean left = curFileElement(leftFilePtr, leftVal);
            boolean right = curFileElement(rightFilePtr, rightVal);
            while (left && right) {
                if (leftVal.getVal() <= rightVal.getVal()) {
                    outputWriter.write(leftVal.getVal() + " ");
                    left = curFileElement(leftFilePtr, leftVal);
                } else {
                    outputWriter.write(rightVal.getVal() + " ");
                    right = curFileElement(rightFilePtr, rightVal);
                }
            }
        /* Copy remaining elements of LeftFile if any */
            while (left) {
                outputWriter.write(leftVal.getVal() + " ");
                left = curFileElement(leftFilePtr, leftVal);
            }

        /* Copy remaining elements of RightFile if any */
            while (right) {
                outputWriter.write(rightVal.getVal() + " ");
                right = curFileElement(rightFilePtr, rightVal);
            }
            leftFilePtr.close();
            rightFilePtr.close();
            outputWriter.close();
            copyTempToSrc(leftFileId);

        } catch (Exception ex) {
            System.out.println("Exception Found " + ex.getStackTrace());
        }
    }
  /*
   * Copy Contents From temp.txt to leftId.txt
   *
   */
    private void copyTempToSrc(int leftId) {

        try {
            File infile = new File("LocalSortedInput\\temp.txt");
            File outfile = new File("LocalSortedInput\\" + leftId + ".txt");
            FileInputStream instream = new FileInputStream(infile);
            FileOutputStream outstream = new FileOutputStream(outfile);

            byte[] buffer = new byte[1024];

            int length;
            /*copying the contents from input stream to
             * output stream using read and write methods
    	     */
            while ((length = instream.read(buffer)) > 0) {
                outstream.write(buffer, 0, length);
            }

            //Closing the input/output file streams
            instream.close();
            outstream.close();

           // System.out.println("File copied successfully!!");
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }

    }
/*
 * This is a helper Function to return CurrentElement of File FileId.txt
 */
    private boolean curFileElement(Scanner FileId, PassValueByReference val) {
        if (FileId.hasNextInt()) {
            val.setVal(FileId.nextInt());
            return true;
        } else {
            val.setVal(-1);
            return false;
        }
    }
}