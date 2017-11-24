package cs6301.g26;

import cs6301.g00.Graph;
import cs6301.g26.FlowGraph.*;

import java.util.LinkedList;

/**
 * RelabelMaxFlow:
 *
 * @author : Sharath
 * 22/11/2017
 */
public class RelabelMaxFlow {
    private FlowGraph graph;
    private FlowGraph.FlowVertex source;
    private FlowGraph.FlowVertex target;

    public RelabelMaxFlow(FlowGraph graph, FlowVertex source, FlowVertex target) {
        this.graph = graph;
        this.source = source;
        this.target = target;
    }


    private FlowEdge getMinEdge(FlowVertex u) {
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


    private void increaseHeight(FlowVertex u) {
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

    public int getMaxFlow() {
        source.height = graph.size();
        LinkedList<FlowVertex> q = new LinkedList<>();
        q.add(source);
        source.seen = true;
        //While no vertices needs to be processed
        while (!q.isEmpty()) {
            FlowGraph.FlowVertex u = q.poll();
            u.seen = false;
            int excess = u.getExcess();
            //If the element has excess flow and can push to one or more units to neighbour
            FlowEdge edge = getMinEdge(u);
            if (edge != null) {
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
                    edge = getMinEdge( u);
                }
                //if vertex still has excess flow move to front
                if (excess > 0)
                    q.addFirst(u);
            } else if (u != source && excess > 0) {
                increaseHeight(u);
                q.addFirst(u);
            }
        }
        return source.getOutFlow();
    }
}
