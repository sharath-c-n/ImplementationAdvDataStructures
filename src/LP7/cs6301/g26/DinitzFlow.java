/**
 * @authors Ankitha, Sharath, Sandeep on 11/23/2017
 */

package cs6301.g26;

import cs6301.g00.Graph;
import cs6301.g00.Graph.*;
import cs6301.g26.FlowGraph.FlowEdge;
import cs6301.g26.FlowGraph.FlowVertex;

import java.util.*;

public class DinitzFlow {
    private FlowGraph flowGraph;
    private FlowVertex src;
    private FlowVertex t;

    DinitzFlow(FlowGraph flowGraph, FlowVertex s, FlowVertex t) {
        this.flowGraph = flowGraph;
        this.src = s;
        this.t = t;
    }

    /* This Function does BFS of FlowGraph  by iterating through nonZero flow edges
     * This computes the distance from the src to each vertex(i.e which is the label of the Layer)
     * @return boolean: true if target vertex is reachable, false otherwise.
     */

    private boolean BFS() {
        Queue<FlowVertex> qu = new LinkedList<>();
        qu.add(src);
        src.seen = true;
        src.parent = null; //parent of src is null
        src.distance = 0; //distance from src to itself is Zero
        while (!qu.isEmpty()) {
            FlowVertex u = qu.poll();
            for (Graph.Edge e : u) {
                FlowVertex v = (FlowVertex) (e.otherEnd(u));
                if (!v.seen) {
                    v.parent = u;
                    qu.add(v);
                    v.distance = u.distance + 1;
                    v.seen = true;
                }
            }
        }
        return (t.seen);
    }

    /*
     * This Function pushes flow along the Layered Network from the src to target.
     * @param u: Vertex to iterate
     * @param minFlow: Minimum flow to push from source to u.
     *
     * @returns -1 when can't push any flow from source to target
     */
    private int pushFlowAlongPath(FlowVertex u, int minFlow) {
        if (u.getName() == t.getName())
            return minFlow;
        for (Graph.Edge e : u) {
            FlowEdge edge = (FlowEdge) e;
            FlowVertex v = (FlowVertex) (e.otherEnd(u));
            if (v.distance == (u.distance + 1) && (edge.getAvailableFlow()>0)) { //If the vertex u connects to vertex  in the next layer continue .
                int minflowtoPush = pushFlowAlongPath(v, Math.min(minFlow, edge.getAvailableFlow()));
                //If there is no available flow to push from u to target this function returns -1.
                //Otherwise reduce the edge flow from u to v with the Minimum flow that can be pushed along the path from source to target.
                if (minflowtoPush > 0) {
                    edge.pushFlow(minflowtoPush);
                    return minflowtoPush;
                }
            }
        }
        return -1;
    }

    /*
     * This function does the Bfs in Stages and until there is no available flow to push from source to target in Residual Graph.
     *
     */
    public int ComputeDinitzMaxFlow() {
        flowGraph.resetAll(-1); //Reset all the parameters
        while (BFS()) { // If there is atleast one path from source to target in the Residual Graph continue.
            //Push Flow from src to Target in the layered Graph until there is no Flow to push from source to Target.
            while (pushFlowAlongPath(src, Integer.MAX_VALUE) != -1){
            }
            flowGraph.resetAll(-1);
        }
        return src.getOutFlow();
    }

    /*
     * This Function computes the min cut, S side vertices
     *
     *
     */
    public void minCutfromS(Set<Vertex> Sv) {
        flowGraph.resetSeen();
        BFS();
        for (Vertex v : flowGraph) {
            FlowVertex fv = (FlowVertex) v;
            if (fv.seen)
                Sv.add(v);
        }
    }

    /*
     * This Function computes the min cut, T side vertices
     *
     */
    public void minCutfromT(Set<Vertex> Tv) {
        flowGraph.resetSeen();
        BFS();
        for (Vertex v : flowGraph) {
            FlowVertex fv = (FlowVertex) v;
            if (!fv.seen)
                Tv.add(v);
        }
    }
}