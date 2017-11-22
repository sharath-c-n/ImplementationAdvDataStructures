package cs6301.g26;

import cs6301.g00.Graph;
import cs6301.g00.Graph.*;
import cs6301.g26.FlowGraph.*;

import java.util.HashMap;
import java.util.LinkedList;
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
        return 0;
    }

    // Return max flow found by relabelToFront algorithm
    public int relabelToFront() {
        source.height = graph.size();
        LinkedList<FlowVertex> q = new LinkedList<>();
        q.add(source);
        source.seen = true;
        //While no vertices needs to be processed
        while (!q.isEmpty()) {
            FlowGraph.FlowVertex u = q.poll();
            u.seen = false;
            int excess = u.getExcess();
            //If the element has excess flow and can push to one or more neighbour
            if (GraphUtil.canPush(graph, u)) {
                FlowEdge edge = GraphUtil.getMinEdge(graph, u);
                //Push flow out of vertex until all the excess flow is gone or until you can no longer push.
                while (edge != null && (excess > 0 || u == source)) {
                    FlowVertex v = graph.getVertex(edge.toVertex());
                    //get the flow that needs to be sent to neighbour, note that the flow should be
                    //less than or equal to excess flow for non source vertices
                    int flowValue = u == source ? edge.getAvailableFlow() : Math.min(edge.getAvailableFlow(), excess);
                    excess -= flowValue;
                    edge.pushFlow(flowValue);
                    //Don't add target vertex to queue
                    if (!v.seen && v != target) {
                        v.seen = true;
                        q.add(v);
                    }
                    edge = GraphUtil.getMinEdge(graph, u);
                }
                //if vertex still has excess flow move to front
                if (excess > 0)
                    q.addFirst(u);
            } else if (u != source && excess > 0) {
                GraphUtil.increaseHeight(graph, u);
                q.addFirst(u);
            }
        }
        return source.getOutFlow();
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