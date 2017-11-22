package cs6301.g26;

import cs6301.g00.Graph;

import java.util.HashMap;
import java.util.Scanner;

/**
 * LP8Driver:
 * @author : Sharath
 * 19/11/2017
 */
public class LP8Driver {
    static int VERBOSE = 0;

    public static void main(String[] args) {
        if (args.length > 0) {
            VERBOSE = Integer.parseInt(args[0]);
        }
        Scanner scanner = new java.util.Scanner(System.in);
        HashMap<Graph.Edge, Integer> capacity = new HashMap<>();
        HashMap<Graph.Edge, Integer> cost = new HashMap<>();
        Graph g = Graph.readDirectedGraph(scanner);
        cs6301.g00.Timer t = new cs6301.g00.Timer();
        for (Graph.Vertex v : g) {
            for (Graph.Edge e : v) {
                capacity.put(e, scanner.nextInt());
            }
        }
        for (Graph.Vertex v : g) {
            for (Graph.Edge e : v) {
                cost.put(e, scanner.nextInt());
            }
        }
        MinCostFlow handle = new MinCostFlow(g, g.getVertex(1), g.getVertex(4), capacity, cost);
        long result = handle.cycleCancellingMinCostFlow(7);
        //long result = handle.successiveSPMinCostFlow(7);
        System.out.println(result + "\n" + t.end());
    }
}
