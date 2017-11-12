
// Starter code for LP4
// Do not rename this file or move it away from cs6301/g??

// change following line to your group number
package Lp4.cs6301.g26;
import cs6301.g00.Graph;
import cs6301.g00.Graph.Vertex;
import cs6301.g00.Graph.Edge;
import  Lp4.cs6301.g26.ShortestPathCount;

import java.util.*;

public class LP4 {
    Graph g;
    Vertex s;


    // common constructor for all parts of LP4: g is graph, s is source vertex
    public LP4(Graph g, Vertex s) {
        this.g = g;
        this.s = s;
    }



    // Part a. Return number of topological orders of g
    public long countTopologicalOrders() {
        // To do
        return 0;
    }


    // Part b. Print all topological orders of g, one per line, and 
    //	return number of topological orders of g
    public long enumerateTopologicalOrders() {
        // To do
        return 0;
    }



    // Part c. Return the number of shortest paths from s to t
    // 	Return -1 if the graph has a negative or zero cycle
    public long countShortestPaths(Vertex t) {
        ShortestPathCount scp= new ShortestPathCount(g,s );
        return scp.countsps(t);
    }


    // Part d. Print all shortest paths from s to t, one per line, and 
    //	return number of shortest paths from s to t.
    //	Return -1 if the graph has a negative or zero cycle.
    public long enumerateShortestPaths(Vertex t) {
        ShortestPathCount scp= new ShortestPathCount(g,s);
        return  scp.FindallShortestPaths(t);
    }


    // Part e. Return weight of shortest path from s to t using at most k edges
    public int constrainedShortestPath(Vertex t, int k) {
        ShortestPathCount scp2 =new ShortestPathCount(g,s);
        return scp2.FindConstraintShortestPaths(t, k);
    }


    // Part f. Reward collection problem
    // Reward for vertices is passed as a parameter in a hash map
    // tour is empty list passed as a parameter, for output tour
    // Return total reward for tour
    public int reward(HashMap<Vertex, Integer> vertexRewardMap, List<Vertex> tour) {
        // To do
        return 0;
    }

    // Do not modify this function
    static void printGraph(Graph g, HashMap<Vertex, Integer> map, Vertex s, Vertex t, int limit) {
        System.out.println("Input graph:");
        for (Vertex u : g) {
            if (map != null) {
                System.out.print(u + "($" + map.get(u) + ")\t: ");
            } else {
                System.out.print(u + "\t: ");
            }
            for (Edge e : u) {
                System.out.print(e + "[" + e.weight + "] ");
            }
            System.out.println();
        }
        if (s != null) {
            System.out.println("Source: " + s);
        }
        if (t != null) {
            System.out.println("Target: " + t);
        }
        if (limit > 0) {
            System.out.println("Limit: " + limit + " edges");
        }
        System.out.println("___________________________________");
    }
}