package cs6301.g26;

import cs6301.g00.Graph;

import java.util.ArrayDeque;
import java.util.LinkedList;
import java.util.List;

import cs6301.g26.Flow;
import cs6301.g26.FlowGraph.*;

/**
 * GraphUtil:
 *
 * @author : Sharath
 * 19/11/2017
 */
public class GraphUtil {
    public static final int INFINITY = Integer.MAX_VALUE;

    public static boolean bellmanFord(FlowGraph graph, FlowGraph.FlowVertex source) {
        graph.resetAll();
        source.parent = null;
        source.distance = 0;
        boolean noChange = true;
        for (int k = 0; k < graph.size() - 1; k++) {
            for (Graph.Vertex from : graph) {
                FlowGraph.FlowVertex u = graph.getVertex(from);
                for (FlowGraph.Edge e : graph.getVertex(u)) {
                    FlowGraph.FlowVertex v = graph.getVertex(e.toVertex());
                    if(v.distance > u.distance + e.getWeight()){
                        v.parent = u;
                        v.distance = u.distance + e.getWeight();
                        noChange = false;
                    }
                }
            }
            if(noChange){
                return true;
            }
        }
        return false; // G has a negative cycle
    }

    public static FlowVertex findVertex(FlowGraph graph){
        for (Graph.Vertex from : graph) {
            FlowVertex u = graph.getVertex(from);
            for (FlowGraph.Edge e : graph.getVertex(u)) {
                FlowVertex v = graph.getVertex(e.toVertex());
                if(u.distance!=INFINITY && v.distance > u.distance + e.getWeight()){
                    return v;
                }
            }
        }
        return null;
    }
    public static FlowEdge findParentEdge(FlowVertex parent, FlowVertex child){
        for(Graph.Edge e : parent){
            if(e.toVertex() == child)
                return (FlowEdge) e;
        }
        return null;
    }
    public static List<FlowEdge> identifyNegCycle(FlowGraph graph, FlowVertex source) {
        if(bellmanFord(graph, source)){
            return null;
        }
        graph.resetSeen();
        FlowVertex v = findVertex(graph);
        if(v==null)
            return null;
        LinkedList<FlowVertex> q = new LinkedList<>();
        LinkedList<FlowEdge> edges = new LinkedList<>();
        while (!v.seen) {
            v.seen = true;
            q.add(v);
            v = v.parent;
        }
        while (q.peekFirst() != v) {
            q.removeFirst();
        }

        for (FlowGraph.FlowVertex u : q) {
            edges.add(GraphUtil.findParentEdge(u.parent, u));
        }
        return edges;
    }
}
