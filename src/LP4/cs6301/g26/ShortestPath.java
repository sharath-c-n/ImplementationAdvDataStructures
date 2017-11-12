
package cs6301.g26;

import cs6301.g00.Graph;

import java.util.*;

/**
 * @author Sandeep on 11/11/2017
 */

public class ShortestPath extends GraphAlgorithm<ShortestPath.VertexUtil> {
    public static final int INFINITY = Integer.MAX_VALUE;
    private Graph.Vertex src;

    ShortestPath(Graph g, Graph.Vertex s) {
        super(g);
        this.src = s;
        node = new VertexUtil[g.size()];
        for (Graph.Vertex v : g) {
            node[v.getName()] = new VertexUtil();
        }
    }

    class VertexUtil {
        boolean seen;
        List<Graph.Edge> nxtAdj;
        int distance;  // distance of vertex from source
        int count;
        long spCount = 0;

        VertexUtil() {
            seen = false;
            nxtAdj = new ArrayList<>();
            distance = INFINITY;
            count = 0;
            spCount = 0;
        }
    }

    int getCount(Graph.Vertex u) {
        return getVertex(u).count;
    }

    public void reset() {
        for (VertexUtil u : node) {
            u.seen = false;
        }

    }

    private boolean bellmanFordTake3() {
        reset();
        getVertex(src).distance = 0;
        getVertex(src).count = 0;
        Queue<Graph.Vertex> q = new LinkedList<>();
        q.add(src);
        while (!q.isEmpty()) {
            Graph.Vertex u = q.poll();
            VertexUtil bu = getVertex(u);
            bu.seen = false;
            bu.count = getVertex(u).count + 1;
            if (getCount(u) >= g.size()) return false;
            for (Graph.Edge e : u.adj) {
                VertexUtil bv = getVertex(e.toVertex());
                if ((bu.distance != INFINITY) && (bv.distance > (bu.distance + e.getWeight()))) {
                    bv.distance = bu.distance + e.getWeight();
                    if (!bv.seen) {
                        q.add(e.toVertex()); //?
                        bv.seen = true;
                    }
                }
            }
        }
        return true;
    }

    public long getSPCount(Graph.Vertex t) {
        reset();
        getVertex(src).spCount = 1;
        Queue<Graph.Vertex> q = new LinkedList<>();
        q.add(t);
        getVertex(t).seen = true;
        while (!q.isEmpty()) {
            Graph.Vertex u = q.poll();
            for (Graph.Edge e : u.revAdj) {
                Graph.Vertex v = e.fromVertex();
                if ((getVertex(v).distance + e.getWeight()) == getVertex(u).distance) {
                    getVertex(v).nxtAdj.add(e);
                    if (!getVertex(v).seen) {
                        getVertex(v).seen = true;
                        q.add(v);
                    }
                }
            }
        }
        reset();
        Deque<Graph.Edge> Que = new ArrayDeque<>();
        finishedOrder(src, Que);
        for (Graph.Edge e : Que) {
            getVertex(e.toVertex()).spCount += getVertex(e.fromVertex()).spCount;
        }
        return getVertex(t).spCount;
    }

    //Find the topological order
    private void finishedOrder(Graph.Vertex v, Deque<Graph.Edge> q) {
        getVertex(v).seen = true;
        for (Graph.Edge t : getVertex(v).nxtAdj) {
            if (!getVertex(t.otherEnd(v)).seen)
                finishedOrder(t.otherEnd(v), q);
        }
        for (Graph.Edge e : getVertex(v).nxtAdj)
            q.addFirst(e);
    }

    private void printRecursively(Graph.Vertex s, Graph.Vertex t, Graph.Vertex[] arr, int index) {
        if (s.equals(t)) {
            for (int i = 0; i < index; i++) {
                System.out.print(arr[i] + " ");
            }
            System.out.println();
        } else {
            for (Graph.Edge e : getVertex(s).nxtAdj) {
                arr[index] = e.toVertex();
                printRecursively(e.toVertex(), t, arr, index + 1);
            }
        }
    }

    public long countSPs(Graph.Vertex t) {
        long ans = -1L;
        boolean res = bellmanFordTake3();
        if (!res)
            return ans;
        ans = getSPCount(t);
        return ans;
    }

    public long FindAllSPs(Graph.Vertex t) {
        long ans = countSPs(t);
        if (ans < 0)
            return ans;
        Graph.Vertex[] arr = new Graph.Vertex[g.size()];
        arr[0] = src;
        printRecursively(src, t, arr, 1);
        return ans;
    }


    public int FindConstraintShortestPaths(Graph.Vertex t, int k) {
        bellmanFordTake1(k);
        return getVertex(t).distance;
    }

    private void bellmanFordTake1(int k) {
        int dist[][] = new int[g.size() + 1][k + 1];
        reset();
        for (Graph.Vertex u : g) {
            dist[u.getName()][0] = INFINITY;
        }
        getVertex(src).distance = 0;
        dist[src.getName()][0] = 0;
        for (int i = 1; i <= k; i++) {
            boolean noChange = true;
            for (Graph.Vertex v : g) {
                dist[v.getName()][i] = dist[v.getName()][i - 1];
                for (Graph.Edge e : v.revAdj) {
                    if ((dist[e.fromVertex().getName()][i - 1] != INFINITY) && (dist[v.getName()][i] > (dist[e.fromVertex().getName()][i - 1] + e.getWeight()))) {
                        dist[v.getName()][i] = (dist[e.fromVertex().getName()][i - 1] + e.getWeight());
                        noChange = false;
                    }
                }
            }
            if (noChange) {
                for (Graph.Vertex v : g)
                    getVertex(v).distance = dist[v.getName()][i];
                return;
            }
        }
        for (Graph.Vertex v : g)
            getVertex(v).distance = dist[v.getName()][k];
    }
}