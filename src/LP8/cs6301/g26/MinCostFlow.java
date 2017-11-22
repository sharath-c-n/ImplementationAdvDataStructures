package cs6301.g26;

import cs6301.g00.Graph;
import cs6301.g00.Graph.*;
import cs6301.g26.FlowGraph.*;

import java.util.HashMap;
import java.util.List;

import static cs6301.g26.GraphUtil.INFINITY;

public class MinCostFlow {
    private FlowGraph graph;
    private FlowVertex source;
    private FlowVertex target;
    private HashMap<Edge, Integer> capacity;
    private HashMap<Edge, Integer> cost;

    public MinCostFlow(Graph g, Vertex s, Vertex t, HashMap<Edge, Integer> capacity, HashMap<Edge, Integer> cost) {
        graph = new FlowGraph(g, capacity, cost);
        source = graph.getVertex(s);
        target = graph.getVertex(t);
        this.capacity = capacity;
        this.cost = cost;
    }

    // Return cost of d units of availableFlow found by cycle cancellation algorithm
    int cycleCancellingMinCostFlow(int d) {
        int cost = findFlow(d);
        List<FlowGraph.FlowEdge> negativeCycle = GraphUtil.identifyNegCycle(graph, source);
        //While has negative cycle
        while (negativeCycle != null) {
            int adjustment = findPathCapacity(negativeCycle);
            cost += pushFlow(adjustment, negativeCycle);
            negativeCycle = GraphUtil.identifyNegCycle(graph, source);
        }
        return cost;
    }

    // Return cost of d units of availableFlow found by successive shortest paths
    int successiveSPMinCostFlow(int d) {
        int cost = 0, flow = 0;
        List<FlowGraph.FlowEdge> shortestPath;
        //While flow not equal to d
        while (flow != d) {
            //Find shortest path from source to destination i.e w.r.t cost, and send flow along that path.
            shortestPath = GraphUtil.getShortestPath(graph, source, target);
            if (shortestPath == null) {
                System.out.println("Cannot transfer the flow "+ d +" from "+ source +" to "+ target);
                return INFINITY;
            }
            int minFlow = findPathCapacity(shortestPath);
            minFlow = minFlow < d - flow ? minFlow : d - flow;
            flow += minFlow;
            cost += pushFlow(minFlow, shortestPath);
        }
        return cost;
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

    private int findPathCapacity(List<FlowGraph.FlowEdge> edges) {
        int min = INFINITY;
        for (FlowGraph.FlowEdge e : edges) {
            if (min > e.getAvailableFlow()) {
                min = e.getAvailableFlow();
            }
        }
        return min;
    }

    private int findFlow(int d) {
        int cost = 0;
        int flow = 0;
        while (flow != d) {
            List<FlowEdge> path = GraphUtil.runBfs(graph, source, target);
            if (path == null) {
                System.out.println("Cannot transfer the flow "+ d +" from "+ source +" to "+ target);
                return INFINITY;
            }
            int minFlow = findPathCapacity(path);
            minFlow = minFlow < d - flow ? minFlow : d - flow;
            flow += minFlow;
            cost += pushFlow(minFlow, path);
        }
        return cost;
    }

    private int pushFlow(int flow, List<FlowEdge> path) {
        int cost = 0;
        for (FlowGraph.FlowEdge e : path) {
            if (e != null) {
                e.pushFlow(flow);
                cost += e.getWeight() * flow;
            }
        }
        return cost;
    }
}