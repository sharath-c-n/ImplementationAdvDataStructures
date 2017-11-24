package cs6301.g26;

import cs6301.g00.Graph;
import cs6301.g26.FlowGraph.*;

import java.util.LinkedList;

/**
 * RelabelMaxFlow:
 * @author : Sharath Chalya Nagaraju, Ankitha Karunakar shetty, Sandeep
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

    /**
     * Increases the height of the current vertex which is the min{neighours.height}
     * @param u : vertex whose height needs to be increased.
     */
    private void increaseHeight(FlowVertex u) {
        int maxHeight = Integer.MAX_VALUE;
        for (Graph.Edge e : u) {
            FlowVertex v = graph.getVertex(e.toVertex());
            if (v.height < maxHeight) {
                maxHeight = v.height;
            }
        }
        u.height = maxHeight + 1;
    }

    public int getMaxFlow() {
        LinkedList<FlowVertex> q = floodFromSource();
        //While vertices needs to be processed
        while (!q.isEmpty()) {
            FlowGraph.FlowVertex u = q.poll();
            int excess = u.getExcess();
            //If the element has excess flow
            if (excess > 0) {
                for (Graph.Edge e : u) {
                    FlowEdge edge = (FlowEdge) e;
                    FlowVertex v = (FlowVertex) edge.toVertex();
                    if (u.height > v.height) {
                        //note that the flow should be less than or equal to excess flow
                        int flowValue = Math.min(edge.getAvailableFlow(), excess);
                        excess -= flowValue;
                        edge.pushFlow(flowValue);
                        //Don't add target vertex or source to queue
                        if (!v.seen) {
                            //Add vertices to queue only if you push flow into them
                            q.add(v);
                        }
                    }
                    if (excess == 0)
                        break;
                }
            }
            //If the element has excess flow and cannot push flow to neighbours
            if (excess > 0) {
                //if vertex still has excess flow move to front
                increaseHeight(u);
                q.addFirst(u);
            }
        }
        return source.getOutFlow();
    }

    private LinkedList<FlowVertex> floodFromSource() {
        //set source height to |V|
        source.height = graph.size();
        LinkedList<FlowVertex> q = new LinkedList<>();
        FlowEdge edge;
        //Source never gets back into the queue
        source.seen = true;
        target.seen = true;
        //While we can still push flow
        for (Graph.Edge e : source) {
            edge = (FlowEdge) e;
            int flowValue = edge.getAvailableFlow();
            FlowVertex vertex = (FlowVertex) e.toVertex();
            edge.pushFlow(flowValue);
            //check if the vertex is not target
            if (!vertex.seen)
                q.add(vertex);
        }
        return q;
    }
}
