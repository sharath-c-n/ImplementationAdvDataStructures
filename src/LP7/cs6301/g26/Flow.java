package cs6301.g26;

import cs6301.g00.Graph;
import cs6301.g00.Graph.*;

import java.util.HashMap;
import java.util.Set;

/**
 * @author Sharath Chalya Nagaraju, Ankitha karunakar Shetty, Sandeep
 */
public class Flow {
    private FlowGraph graph;
    private FlowGraph.FlowVertex source;
    private FlowGraph.FlowVertex target;
    private HashMap<Edge, Integer> capacity;

    public Flow(Graph g, Vertex s, Vertex t, HashMap<Edge, Integer> capacity) {
        graph = new FlowGraph(g, capacity);
        source = graph.getVertex(s);
        target = graph.getVertex(t);
        this.capacity = capacity;
    }

    // Return max flow found by Dinitz's algorithm
    public int dinitzMaxFlow() {
        return 0;
    }

    // Return max flow found by relabelToFront algorithm
    public int relabelToFront() {
        RelabelMaxFlow relabelMaxFlow = new RelabelMaxFlow(graph,source,target);
        return relabelMaxFlow.getMaxFlow();
    }


    // flow going through edge e
    //not using this function
    public int flow(Edge e) {
        return e.getWeight();
    }

    // capacity of edge e
    //not using this function
    public int capacity(Edge e) {
        return capacity.get(e);
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