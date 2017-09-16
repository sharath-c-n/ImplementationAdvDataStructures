package cs6301.g26.graph;

import common.g00.Graph;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Scanner;

/**
 * Created by Ankitha on 9/13/2017.
 */

/**
 * Created by Ankitha on 8/29/2017.
 * This class contains the SSCCount which returns the count of the
 * number of strongly connected components
 */
public class SCC {
    /**
     * This function will run the topological sort on all vertices of the input graph.
     * @param graph      : graph whose topological order has to be found
     * @param vertexCount : number of vertex in the input graph
    been explored so far
     * @return : The number of Strongly connected components in the graph
     */
    public static int SSCCount(Graph graph, int vertexCount) {
        boolean[] visited = new boolean[vertexCount];
        int count=0;
        //Step 1 : find the topological oder of the vertices of the given graph
        List<Graph.Vertex> path = TopologicalSort.topologicalSort1(graph,graph.size());
        //Step 2 : do a DFS on the reverse graph by picking each vertex from the path in the topological order
        //Step 3: Increment the count of Strongly Connected Components when a vertex which is
        // not explored previously is found indicating the discovery of a new component
        for (Graph.Vertex vertex : path) {
            if(!visited[vertex.getName()]){
                count++;
                DFS(vertex,visited);

            }
        }
        return count;
    }

    private static void DFS(Graph.Vertex vertex, boolean visited[])
    {
        visited[vertex.getName()] = true;
        //reverse graph is got by traversing over the reverse adjacency list which is maintained
        for(Graph.Edge e : vertex.getRevAdj()){
            if(!visited[e.otherEnd(vertex).getName()]){
                DFS(e.otherEnd(vertex), visited);
            }
        }
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
          /*Check if the graph has cycles*/
            List<Graph.Vertex> path = TopologicalSort.topologicalSort1(g,g.size());
            /*Print the returned path  list*/
            for (Graph.Vertex vertex : path) {
                System.out.print(vertex.getName() + " ");
            }
            System.out.println("Number of Connected Components "+SCC.SSCCount(g,g.size()));
    }
}
