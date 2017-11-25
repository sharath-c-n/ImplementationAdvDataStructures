/**
 * @author Sharath Chalya Nagaraju, Ankitha karunakar Shetty, Sandeep
 */

package cs6301.g26;

import cs6301.g00.Graph;
import cs6301.g00.Graph.*;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

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
        DinitzFlow dinitzFlow = new DinitzFlow(graph,source,target);
        return dinitzFlow.ComputeDinitzMaxFlow();
    }

    // Return max flow found by relabelToFront algorithm
    public int relabelToFront() {
        RelabelMaxFlow relabelMaxFlow = new RelabelMaxFlow(graph,source,target);
        return relabelMaxFlow.getMaxFlow();
    }


    // flow going through edge e
    //not using this function, use originalGraphItr instead of normal iterator.
    public int flow(Edge e) {
        FlowGraph.FlowVertex u = graph.getVertex(e.fromVertex());
        return u.getFlow(graph.getVertex(e.toVertex()));
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
        Set<Vertex> Sv= new HashSet<>();
        DinitzFlow dinitzFlow = new DinitzFlow(graph,source,target);
        dinitzFlow.minCutfromS(Sv);
        return Sv;
    }

    /* After maxflow has been computed, this method can be called to
       get the "T"-side of the min-cut found by the algorithm
    */
    public Set<Vertex> minCutT() {
        Set<Vertex> Tv= new HashSet<>();
        DinitzFlow dinitzFlow = new DinitzFlow(graph,source,target);
        dinitzFlow.minCutfromT(Tv);
        return Tv;
    }
}