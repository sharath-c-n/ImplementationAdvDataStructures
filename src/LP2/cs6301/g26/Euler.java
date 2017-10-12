/**
 * Group 26
 *
 * @author Ankitha, Sharath, Sandeep
 */
package cs6301.g26;

import java.util.*;

public class Euler extends GraphAlgorithm<Euler.Vertex> {
    private int VERBOSE;
    private List<Graph.Edge> tour;
    private Graph.Vertex start;

    // Constructor
    Euler(Graph g, Graph.Vertex start) {
        super(g);
        VERBOSE = 1;
        tour = new LinkedList<>();
        node = new Vertex[g.size()];
        // Create array for storing vertex properties
        for (Graph.Vertex u : g) {
            setVertex(u, new Vertex(u));
        }
        this.start = start == null ? g.getVertex(1) : start;
    }

    // To do: function to find an Euler tour
    public List<Graph.Edge> findEulerTour() {
        findTours();
        if (VERBOSE > 9) {
            printTours();
        }
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
        GraphUtil util = new GraphUtil(g);
        if (!util.isStronglyConnected()) {
            System.out.println("Graph is not strongly connected\nHence graph is not Eulerian");
            return false;
        }
        Graph.Vertex vertex = util.inEqlOutEdges();
        if (vertex != null) {
            System.out.println("inDegree = " + vertex.adj.size() + ", outDegree =  " + vertex.revAdj.size() + " at Vertex " + vertex);
            System.out.println("hence graph is not Eulerian");
            return false;
        }
        return true;
    }

    // Find tours starting at vertices with unexplored edges
    void findTour(Graph.Vertex u, List<Graph.Edge> tour) {
        Iterator<Graph.Edge> itr = getVertex(u).itr;
        while (itr.hasNext()) {
            Graph.Edge e = itr.next();
            tour.add(e);
            u = e.otherEnd(u);
            itr = getVertex(u).itr;
        }
    }

    void findTours() {
        //Start the tour from the given vertex
        Vertex eulerVertex = getVertex(start);
        eulerVertex.tour = new LinkedList<Graph.Edge>();
        findTour(start, eulerVertex.tour);

        for (Graph.Vertex vertex : g) {
            eulerVertex = getVertex(vertex);
            if (eulerVertex.itr.hasNext()) {
                eulerVertex.tour = new LinkedList<Graph.Edge>();
                findTour(vertex, eulerVertex.tour);
            }
        }
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
        for (Graph.Vertex vertex : g) {
            List<Graph.Edge> currentTour = getVertex(vertex).tour;
            if (currentTour != null) {
                System.out.print(vertex + " : ");
                for (Graph.Edge e : currentTour) {
                    System.out.print(e);
                }
                System.out.print("\n");
            }
        }
    }

    // Stitch tours into a single tour using the algorithm discussed in class
    void stitchTours() {
        explore(start);
    }

    private void explore(Graph.Vertex vertex) {
        Vertex temp = getVertex(vertex);
        temp.isExplored = true;
        for (Graph.Edge e : temp.tour) {
            tour.add(e);
            Vertex otherEulerVertex = getVertex(e.to);
            if (otherEulerVertex.tour != null && !otherEulerVertex.isExplored) {
                explore(e.to);
            }
        }
    }

    void setVerbose(int v) {
        VERBOSE = v;
    }

    public class Vertex {
        //Add all the needed attributes i.e parallel array stuff
        boolean isExplored;
        List<Graph.Edge> tour;
        Iterator<Graph.Edge> itr;

        public Vertex(Graph.Vertex v) {
            this.isExplored = false;
            this.tour = null;
            this.itr = v.iterator();
        }
    }
}
