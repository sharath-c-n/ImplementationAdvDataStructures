package cs6301.g26;

import cs6301.g00.Graph;
import cs6301.g26.FlowGraph.*;

/**
 * GraphUtil:
 *
 * @author : Sharath
 * 22/11/2017
 */
public class GraphUtil {
    public static  FlowEdge getMinEdge(FlowGraph graph,FlowVertex u) {
        FlowEdge minEdge = null;
        int maxHeight = Integer.MAX_VALUE;
        for (Graph.Edge e : u) {
            FlowEdge edge = (FlowEdge) e;
            FlowVertex v = graph.getVertex(edge.toVertex());
            if (v.height < maxHeight && v.height < u.height) {
                maxHeight = v.height;
                minEdge = edge;
            }
        }
        return minEdge;
    }

    public static boolean canPush(FlowGraph graph,FlowVertex v) {
        for (Graph.Edge e : v) {
            FlowEdge edge = (FlowEdge) e;
            if (graph.getVertex(edge.toVertex()).height < v.height) {
                return true;
            }
        }
        return false;
    }

    public static void increaseHeight(FlowGraph graph,FlowVertex u) {
        int maxHeight = Integer.MAX_VALUE;
        for (Graph.Edge e : u) {
            FlowEdge edge = (FlowEdge) e;
            FlowVertex v = graph.getVertex(edge.toVertex());
            if (v.height < maxHeight ) {
                maxHeight = v.height;
            }
        }
        u.height = maxHeight + 1;
    }
}
