package cs6301.g26;

import java.io.*;

/**
 * ExternalSorting:
 * @author : Sharath
 * 25/09/2017
 */
public class ExternalSorting {
    public static final int MAX_IN_MEMORY_SIZE = 1000000;
    public static final String SPLIT_BY = ";";

    public static void externalSort(String inputFile,String outputFile) throws IOException {
        if(inputFile == null || outputFile == null){
            System.out.println("Input and output files cannot be null");
            return;
        }
        BufferedReader inputBr = new BufferedReader(new FileReader(inputFile));
        BufferedWriter outBw = new BufferedWriter(new FileWriter(outputFile,true));
        sortFromFile(inputBr,outBw);
    }

    private static void sortFromFile(BufferedReader inputBr, BufferedWriter outBw) throws IOException {
        String line;
        String temp[];
        int count=0;
        int inMemoryArr[] = new int[MAX_IN_MEMORY_SIZE];
        while((line = inputBr.readLine())!=null){
            count = 0;
            while(count!= MAX_IN_MEMORY_SIZE-1){
                temp = line.split(SPLIT_BY);
                for (String i : temp) {
                    inMemoryArr[count++] = Integer.parseInt(i);
                }
            }
            sort(inMemoryArr);
        }
        if(count!=0){
            sort(inMemoryArr);
        }
    }

    private static void sort(int[] arr){

    }
}
