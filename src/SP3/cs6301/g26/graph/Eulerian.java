package cs6301.g26.graph;

import common.g00.Graph;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Scanner;

/**
 * Created by Ankitha on 9/14/2017.
 */
/**
 * Created by Ankitha on 8/29/2017.
 * This class contains the isEulerian method which checks if the given graph is
 * a euleian or not
 */
public class Eulerian {
    /**
     * This function will run the topological sort on all vertices of the input graph.
     * @param graph      : graph whose topological order has to be found
     * @param vertexCount : number of vertex in the input graph
    been explored so far
     * @return : returns true if the given grpah is eulerian
     */
    public static boolean isEulerian(Graph graph, int vertexCount) {
    //Returns true if the directed graph G is called Eulerian that is
    // if it is strongly connected and the in-degree of every vertex is equal to its out-degree
            return (isEqlEdgeCount(graph) && SCC.SSCCount(graph,vertexCount)==1);
    }

    //checka if th number of incoming and outgoing edges are the same of all vertices in the graph
    public static boolean isEqlEdgeCount(Graph graph) {
        boolean isEqual = true;
        for(Graph.Vertex v : graph){
            if(v.getRevAdj().size()!=v.getAdj().size()){
                isEqual = false;
                break;
            }
        }
        return isEqual;
    }


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
        Graph g = Graph.readGraph(input, true);
        System.out.println("is Eulerian"+isEulerian(g,g.size()));
    }

}
