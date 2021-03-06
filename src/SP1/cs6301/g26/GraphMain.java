package cs6301.g26;

import cs6301.g26.graph.BFS;
import cs6301.g26.graph.Cyclic;
import cs6301.g26.graph.Graph;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Scanner;

/**
 * Created by Ankitha on 8/29/2017.
 * This class is the driver class for Longest diameter finder implementation
 */
public class GraphMain {

    public static void main(String[] args) throws FileNotFoundException {
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
        Graph g = Graph.readGraph(input, false);
        /*Check if the graph has cycles*/
        if (!Cyclic.isCyclic(g)) {
            List<Graph.Vertex> path = BFS.diameter(g);
            /*Print the returned path  list*/
            for (Graph.Vertex vertex : path) {
                System.out.print(vertex + " ");
            }
        } else {
            System.out.println("Not a valid undirected graph");
        }
    }

}
