
// Starter code for LP4
// Do not rename this file or move it away from cs6301/g??

// change following line to your group number

import cs6301.g00.Graph;
import cs6301.g00.Graph.Vertex;
import cs6301.g00.Graph.Edge;
import cs6301.g00.GraphAlgorithm;

import java.util.*;

public class LP4 extends GraphAlgorithm<LP4.Vertexutil> {
    public static final long INFINITY = Integer.MAX_VALUE;
    Graph g;
    Vertex s;

    static class Vertexutil {
        boolean seen;
        List<Graph.Edge> nxtAdj;
        long distance;  // distance of vertex from source
        int count;
        long spCount = 0;

        Vertexutil(Graph.Vertex u) {
            seen = false;
            nxtAdj = new ArrayList<>();
            distance = INFINITY;
            count = 0;
            spCount = 0;
        }
    }

    // common constructor for all parts of LP4: g is graph, s is source vertex
    public LP4(Graph g, Vertex s) {
        super(g);
        this.g = g;
        this.s = s;
        node = new Vertexutil[g.size()];
        // Create array for storing vertex properties
        for (Graph.Vertex u : g) {
            node[u.getName()] = new Vertexutil(u);
        }
        // Set source to be at distance 0
        getVertex(s).distance = 0;
    }

    int getCount(Graph.Vertex u) {
        return getVertex(u).count;
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

    public boolean bellmanFordTake3() {
        for (Graph.Vertex u : g) {
            Vertexutil bu = getVertex(u);
            bu.seen = false;
            bu.distance = INFINITY;
            bu.count = 0;
        }
        getVertex(s).distance = 0;
        getVertex(s).count = 0;
        Queue<Vertex> q = new LinkedList<>();
        q.add(s);
        while (!q.isEmpty()) {
            Graph.Vertex u = q.poll();
            getVertex(u).seen = false;
            getVertex(u).count = getVertex(u).count + 1;
            if (getCount(u) >= g.size()) return false;
            for (Graph.Edge e : u.adj) {
                Vertexutil bu = getVertex(u);
                Vertexutil bv = getVertex(e.otherEnd(u));
                if (bv.distance > (bu.distance + (long) e.weight)) {
                    bv.distance = bu.distance + (long) e.weight;
                    if (!bv.seen) {
                        q.add(e.otherEnd(u));
                        bv.seen = true;
                    }
                }
            }
        }
        return true;
    }


    // Part c. Return the number of shortest paths from s to t
    // 	Return -1 if the graph has a negative or zero cycle
    public long countShortestPaths(Vertex t) {
        long ans = -1L;
        boolean res= bellmanFordTake3();
        if (res)
        ans = RecursiveCount(t);

        return ans;

    }

    public long RecursiveCount(Graph.Vertex t) {
        for (Graph.Vertex v : g) {
            getVertex(v).seen = false;
            getVertex(v).spCount = 0;
        }
        getVertex(s).spCount = 1;
        Queue<Vertex> q = new LinkedList<>();
        q.add(t);
        getVertex(t).seen = true;
        while (!q.isEmpty()) {
            Graph.Vertex u = q.poll();
            for (Graph.Edge e : u.revAdj) {
                Graph.Vertex v = e.otherEnd(u);
                if ((getVertex(v).distance + e.weight) == getVertex(u).distance) {
                    getVertex(v).nxtAdj.add(e);
                    if (!getVertex(v).seen) {
                        getVertex(v).seen = true;
                        q.add(v);
                    }
                }
            }
        }
        for (Graph.Vertex v : g)
            getVertex(v).seen = false;
        Deque<Graph.Edge> Que = new ArrayDeque<>();
        finishedOrder(s, Que);
        for (Graph.Edge e : Que) {
            getVertex(e.toVertex()).spCount += getVertex(e.fromVertex()).spCount;
        }
        return getVertex(t).spCount;
    }

    //Find the topological order
    public void finishedOrder(Graph.Vertex v, Deque<Graph.Edge> q) {
        getVertex(v).seen = true;
        for (Graph.Edge t : getVertex(v).nxtAdj) {
            if (!getVertex(t.otherEnd(v)).seen)
                finishedOrder(t.otherEnd(v), q);
        }
        for (Graph.Edge e : getVertex(v).nxtAdj)
            q.addFirst(e);
    }

    // Part d. Print all shortest paths from s to t, one per line, and 
    //	return number of shortest paths from s to t.
    //	Return -1 if the graph has a negative or zero cycle.
    public long enumerateShortestPaths(Vertex t) {
        long ans = countShortestPaths(t);
        if (ans < 0)
            return ans;
        Graph.Vertex [] arr = new Graph.Vertex[g.size()];
        arr[0]=s;
        printRecursively(s,t,arr,1 );
        return ans;
    }
    void printRecursively( Graph.Vertex s, Graph.Vertex t, Graph.Vertex [] arr, int index){
        if( s.equals(t)){
            for(int i=0;i<index;i++){
                System.out.print(arr[i]+" ");
            }
            System.out.println();
        }
        else{
            for(Graph.Edge e:getVertex(s).nxtAdj) {
                arr[index]=e.toVertex();
                printRecursively(e.toVertex(), t, arr,index+1);
            }
        }
    }

    public boolean bellmanFordTake1(int k){
       long  dist[][] = new long[g.size()+1][k+1];
        for (Graph.Vertex u : g) {
            Vertexutil bu = getVertex(u);
            bu.seen = false;
            bu.distance = INFINITY;
            dist[u.getName()][0]=INFINITY;
            bu.count = 0;
        }
        getVertex(s).distance = 0;
        dist[s.getName()][0]=0;
        getVertex(s).count = 0;
        for( int i=1;i<=k;i++){
            boolean nochange =true;
            for(Graph.Vertex v:g){
                dist[v.getName()][i]=dist[v.getName()][i-1];
                for( Graph.Edge e:v.revAdj){
                  if(dist[v.getName()][i] > (dist[e.fromVertex().getName()][i-1]+e.weight)){
                      dist[v.getName()][i]= (dist[e.fromVertex().getName()][i-1]+e.weight);
                      nochange =false;
                  }
                }
            }
            if( nochange){
             for(Graph.Vertex v:g){
                 getVertex(v).distance= dist[v.getName()][k];
             }
             return  true;
            }
        }
        for( Graph.Vertex v:g){
            getVertex(v).distance= dist[v.getName()][k];
        }
        return true;
    }

    // Part e. Return weight of shortest path from s to t using at most k edges
    public int constrainedShortestPath(Vertex t, int k) {
           boolean res= bellmanFordTake1(k);
           if(!res)
               return -1;
        return (int)getVertex(t).distance;
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
