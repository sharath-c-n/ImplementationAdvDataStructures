package cs6301.g26;

import cs6301.g00.Graph;
import cs6301.g00.Graph.*;
import java.util.HashMap;
import java.util.Set;

public class Flow {
    public Flow(Graph g, Vertex s, Vertex t, HashMap<Edge, Integer> capacity) {
    }

    // Return max flow found by Dinitz's algorithm
    public int dinitzMaxFlow() {
        return 0;
    }

    // Return max flow found by relabelToFront algorithm
    public int relabelToFront() {
        return 0;
    }

    // flow going through edge e
    public int flow(Edge e) {
        return 0;
    }

    // capacity of edge e
    public int capacity(Edge e) {
        return 0;
    }

    /* After maxflow has been computed, this method can be called to
       get the "S"-side of the min-cut found by the algorithm
    */
    public Set<Vertex> minCutS() {
        return null;
    }

    /* After maxflow has been computed, this method can be called to
       get the "T"-side of the min-cut found by the algorithm
    */
    public Set<Vertex> minCutT() {
        return null;
    }
}