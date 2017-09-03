/**
 * Class to represent a graph
 *  @author rbk
 *  Ver 1.1: 2017/08/28.  Updated some methods to public.  Added getName() to Vertex
 *
 */
package cs6301.g26.graph;
import cs6301.g26.util.ArrayIterator;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;



public class Graph implements Iterable<Vertex> {
    Vertex[] v; // vertices of graph
    int n; // number of verices in the graph
    boolean directed;  // true if graph is directed, false otherwise

    /**
     * Nested class to represent a vertex of a graph
     */
    /**
     * Constructor for graph
     *
     * @param n
     *            : int - number of vertices
     */
    public Graph(int n) {
        this.n = n;
        this.v = new Vertex[n];
        this.directed = false;  // default is undirected graph
        // create an array of Vertex objects
        for (int i = 0; i < n; i++)
            v[i] = new Vertex(i);
    }

    /**
     * Find vertex no. n
     * @param n
     *           : int
     */
    public Vertex getVertex(int n) {
        return v[n-1];
    }

    /**
     * Method to add an edge to the graph
     *
     * @param from
     *            : int - one end of edge
     * @param to
     *            : int - other end of edge
     * @param weight
     *            : int - the weight of the edge
     */
    public void addEdge(Vertex from, Vertex to, int weight) {
        Edge e = new Edge(from, to, weight);
        if(this.directed) {
            from.adj.add(e);
            to.revAdj.add(e);
        } else {
            from.adj.add(e);
            to.adj.add(e);
        }
    }

    public int size() {
        return n;
    }

    /**
     * Method to create iterator for vertices of graph
     */
    public Iterator<Vertex> iterator() {
        return new ArrayIterator<Vertex>(v);
    }

    // read a directed graph using the Scanner interface
    public static Graph readDirectedGraph(Scanner in) {
        return readGraph(in, true);
    }

    // read an undirected graph using the Scanner interface
    public static Graph readGraph(Scanner in) {
        return readGraph(in, false);
    }

    public static Graph readGraph(Scanner in, boolean directed) {
        // read the graph related parameters
        int n = in.nextInt(); // number of vertices in the graph
        int m = in.nextInt(); // number of edges in the graph

        // create a graph instance
        Graph g = new Graph(n);
        g.directed = directed;
        for (int i = 0; i < m; i++) {
            int u = in.nextInt();
            int v = in.nextInt();
            int w = in.nextInt();
            g.addEdge(g.getVertex(u), g.getVertex(v), w);
        }
        return g;
    }

    public void printGraph() {
        for (Vertex edges : this) {
            for (Edge edge : edges) {
                System.out.println(edge);
            }
        }
       /* Iterator<Vertex> itr = this.iterator();
        while(itr.hasNext()){
            Iterator<Edge> edgesItr = itr.next().iterator();
            while(edgesItr.hasNext()){
                Edge edge = edgesItr.next();
                System.out.println(edge);
            }
        }*/
    }

}
