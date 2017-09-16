package cs6301.g26;

import java.io.File;
import java.io.FileNotFoundException;
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
        Scanner input = Util.readInput();
        Graph g = Graph.readGraph(input, true);
        if(isEulerian(g,g.size()))
            System.out.println("The graph is Eulerian");
        else
            System.out.println("The graph is not Eulerian");

    }

}
