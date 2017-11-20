// Starter code for LP8
package cs6301.g26;
import cs6301.g00.Graph;
import cs6301.g00.Graph.*;
import java.util.HashMap;

public class MinCostFlow {
    private FlowGraph graph;
    private Vertex source;
    private Vertex target;

    public MinCostFlow(Graph g, Vertex s, Vertex t, HashMap<Edge, Integer> capacity, HashMap<Edge, Integer> cost) {
        graph = new FlowGraph(g,capacity,cost);
        source = s;
        target = t;
    }

    // Return cost of d units of flow found by cycle cancellation algorithm
    int cycleCancellingMinCostFlow(int d) {
        return 0;
    }

    // Return cost of d units of flow found by successive shortest paths
    int successiveSPMinCostFlow(int d) {
        return 0;
    }

    // Return cost of d units of flow found by cost scaling algorithm
    int costScalingMinCostFlow(int d) {
        return 0;
    }

    // flow going through edge e
    public int flow(Edge e) {
        return 0;
    }

    // capacity of edge e
    public int capacity(Edge e) {
        return 0;
    }

    // cost of edge e
    public int cost(Edge e) {
        return 0;
    }
}