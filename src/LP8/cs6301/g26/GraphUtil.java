package cs6301.g26;

import cs6301.g00.Graph;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
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
        source.parent = null;
        source.distance = 0;
        boolean noChange = true;
        for (int k = 0; k < graph.size(); k++) {
            for (Graph.Vertex from : graph) {
                FlowGraph.FlowVertex u = graph.getVertex(from);
                noChange = true;
                for (FlowGraph.Edge e : graph.getVertex(u)) {
                    FlowGraph.FlowVertex v = graph.getVertex(e.toVertex());
                    if (v.distance > u.distance + e.getWeight()) {
                        v.parent = u;
                        v.distance = u.distance + e.getWeight();
                        noChange = false;
                    }
                }
            }
            if (noChange) {
                return true;
            }
        }
        return false; // G has a negative cycle
    }

    public static FlowVertex findVertex(FlowGraph graph) {
        for (Graph.Vertex from : graph) {
            FlowVertex u = graph.getVertex(from);
            for (FlowGraph.Edge e : graph.getVertex(u)) {
                FlowVertex v = graph.getVertex(e.toVertex());
                if (u.distance != INFINITY && v.distance > u.distance + e.getWeight()) {
                    return v;
                }
            }
        }
        return null;
    }

    public static FlowEdge findParentEdge(FlowVertex parent, FlowVertex child) {
        if (parent == null || child == null)
            return null;
        for (Graph.Edge e : parent) {
            if (e.toVertex() == child)
                return (FlowEdge) e;
        }
        return null;
    }

    public static List<FlowEdge> identifyNegCycle(FlowGraph graph, FlowVertex source) {
        graph.resetAll(1);
        if (bellmanFord(graph, source)) {
            return null;
        }
        graph.resetSeen();
        FlowVertex v = findVertex(graph);
        if (v == null)
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

    public static List<FlowEdge> getShortestPath(FlowGraph graph, FlowVertex source, FlowVertex dest) {
        graph.resetAll(INFINITY);
        if (!bellmanFord(graph, source)) {
            return null;
        }
        return getPath(source, dest);
    }

    public static List<FlowEdge> getPath(FlowVertex source, FlowVertex dest) {
        LinkedList<FlowEdge> path = new LinkedList<>();
        FlowVertex currentVertex = dest;
        while (currentVertex != source) {
            FlowEdge edge = findParentEdge(currentVertex.parent, currentVertex);
            if (edge == null)
                return null;
            path.addFirst(edge);
            currentVertex = currentVertex.parent;
        }
        return path;
    }

    public static List<FlowEdge> runBfs(FlowGraph graph, FlowVertex source, FlowVertex target) {
        graph.resetAll(INFINITY);
        Queue<FlowVertex> q = new LinkedList<>();
        q.add(graph.getVertex(source));
        graph.getVertex(source).seen = true;
        while (!q.isEmpty()) {
            FlowVertex v = q.poll();
            if (v == target)
                break;
            for (Graph.Edge e : v) {
                FlowEdge edge = (FlowEdge) e;
                if (!graph.getVertex(edge.toVertex()).seen) {
                    graph.getVertex(edge.toVertex()).parent = v;
                    graph.getVertex(edge.toVertex()).seen = true;
                    q.add(graph.getVertex(edge.toVertex()));
                }
            }
        }
        return getPath(source, target);
    }

}
