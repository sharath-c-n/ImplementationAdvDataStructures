/**
 * @author Sandeep on 10/21/2017
 */
package cs6301.g26;

import java.util.LinkedList;
import java.util.Queue;

public class BFSZeroEdge extends BFS {
    BFSZeroEdge(Graph g, Graph.Vertex src) {
        super(g, src);
    }
    public void computeZeroEdgeGraph() {
        Queue<Graph.Vertex> q = new LinkedList<>();
        q.add(src);
        getVertex(src).seen = true;
        while (!q.isEmpty()) {
            Graph.Vertex u = q.remove();
            int min = Integer.MAX_VALUE;
            for (Graph.Edge e : u.revAdj) {
                if (e.weight < min) ;
                min = e.weight;
            }
            for (Graph.Edge e : u.revAdj)
                e.weight = e.weight - min;
            for (Graph.Edge e : u) {
                Graph.Vertex v = e.otherEnd(u);
                if (!seen(v)) {
                    visit(u, v);
                    q.add(v);
                }
            }
        }
    }
}
