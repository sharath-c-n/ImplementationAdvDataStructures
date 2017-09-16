package cs6301.g26;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * This class contains all the common functions that will be used by all the projects
 * @author Sharath
 */
public class Util {
    /**
     * This function is get a scanner of console or a file based on the user input.
     * @return Scanner
     * @throws FileNotFoundException
     */
    public static Scanner readInput() throws FileNotFoundException {
        Scanner console = new Scanner(System.in);
        Scanner input;
        InputLoop:
        while (true) {
            System.out.println("Select one of the below option \n" +
                    "1) Enter the path of the file which contains the graph details\n" +
                    "2) Enter the graph details manually");
            System.out.print("Choice : ");
            switch(console.nextInt()){
                case 1:
                    System.out.print("Enter the file path : ");
                    //Flush the newline character that was not consumed by nextInt
                    console.nextLine();
                    //Read the file path
                    String fileName = console.nextLine();
                    File file = new File(fileName);
                    input = new Scanner(file);
                    console.close();
                    break InputLoop;
                case 2:
                    System.out.println("Enter the graph details below : ");
                    input = console;
                    break InputLoop;
                default:
                    System.out.println("Invalid input try again");
                    break;
            }
        }
        return input;
    }
}
