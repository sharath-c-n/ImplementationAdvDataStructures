package cs6301.g26;

import java.util.List;
import java.util.LinkedList;

public class Euler extends GraphAlgorithm<Euler.Vertex>{
    int VERBOSE;
    List<Graph.Edge> tour;
    // Constructor
    Euler(Graph g, Graph.Vertex start) {
        super(g);
        VERBOSE = 1;
	tour = new LinkedList<>();
    }

    // To do: function to find an Euler tour
    public List<Graph.Edge> findEulerTour() {
	findTours();
	if(VERBOSE > 9) { printTours(); }
	stitchTours();
	return tour;
    }

    /* To do: test if the graph is Eulerian.
     * If the graph is not Eulerian, it prints the message:
     * "Graph is not Eulerian" and one reason why, such as
     * "inDegree = 5, outDegree = 3 at Vertex 37" or
     * "Graph is not strongly connected"
     */
    boolean isEulerian() {

	System.out.println("Graph is not Eulerian");
	System.out.println("Reason: Graph is not strongly connected");
	return false;
    }

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

    // Find tours starting at vertices with unexplored edges
    void findTours() {
    }

    /* Print tours found by findTours() using following format:
     * Start vertex of tour: list of edges with no separators
     * Example: lp2-in1.txt, with start vertex 3, following tours may be found.
     * 3: (3,1)(1,2)(2,3)(3,4)(4,5)(5,6)(6,3)
     * 4: (4,7)(7,8)(8,4)
     * 5: (5,7)(7,9)(9,5)
     *
     * Just use System.out.print(u) and System.out.print(e)
     */
    void printTours() {
    }

    // Stitch tours into a single tour using the algorithm discussed in class
    void stitchTours() {
    }

    void setVerbose(int v) {
	VERBOSE = v;
    }

    public class Vertex {
        //Add all the needed attributes i.e parallel array stuff
    }
}
