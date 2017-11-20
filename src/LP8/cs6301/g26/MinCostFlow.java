// Starter code for LP8
package cs6301.g26;

import cs6301.g00.Graph;
import cs6301.g00.Graph.*;

import java.util.HashMap;
import java.util.List;

public class MinCostFlow {
    private FlowGraph graph;
    private Vertex source;
    private Vertex target;
    HashMap<Edge, Integer> capacity;
    HashMap<Edge, Integer> cost;

    public MinCostFlow(Graph g, Vertex s, Vertex t, HashMap<Edge, Integer> capacity, HashMap<Edge, Integer> cost) {
        graph = new FlowGraph(g, capacity, cost);
        source = s;
        target = t;
        this.capacity = capacity;
        this.cost = cost;
    }

    // Return cost of d units of availableFlow found by cycle cancellation algorithm
    int cycleCancellingMinCostFlow(int d) {
        int cost = 16;
        FlowGraph.FlowVertex v = graph.getVertex(1);
        v.FAdj.get(0).pushflow(2);
        v.FAdj.get(1).pushflow(4);
        v = graph.getVertex(2);
        v.FAdj.get(1).pushflow(1);
        v.FAdj.get(2).pushflow(1);
        v = graph.getVertex(3);
        v.FAdj.get(2).pushflow(5);
        graph.printGraph();
        System.out.println();
        List<FlowGraph.FlowEdge> negativeCycle = GraphUtil.identifyNegCycle(graph, graph.getVertex(source));
        //While has negative cycle
        while (negativeCycle != null) {
            int adjustment = findMin(negativeCycle);
            for (FlowGraph.FlowEdge e : negativeCycle) {
                if (e != null) {
                    e.pushflow(adjustment);
                    cost += e.getWeight() * adjustment;
                }
            }
            graph.printGraph();
            System.out.println();
            negativeCycle = GraphUtil.identifyNegCycle(graph, graph.getVertex(source));
        }
        return cost;
    }

    // Return cost of d units of availableFlow found by successive shortest paths
    int successiveSPMinCostFlow(int d) {
        return 0;
    }

    // Return cost of d units of availableFlow found by cost scaling algorithm
    int costScalingMinCostFlow(int d) {
        return 0;
    }

    // availableFlow going through edge e
    public int flow(Edge e) {
        return 0;
    }

    // capacity of edge e
    public int capacity(Edge e) {
        return capacity.get(e);
    }

    // cost of edge e
    public int cost(Edge e) {
        return cost.get(e);
    }

    private int findMin(List<FlowGraph.FlowEdge> edges) {
        int min = Integer.MAX_VALUE;
        for (FlowGraph.FlowEdge e : edges) {
            if (min > e.availableFlow) {
                min = e.availableFlow;
            }
        }
        return min;
    }
}