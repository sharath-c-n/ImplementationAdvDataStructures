package cs6301.g26;

import cs6301.g00.Graph;
import cs6301.g00.Timer;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Scanner;

/**
 * Created by Ankitha on 11/4/2017.
 */
public class RewardCollection extends GraphAlgorithm<RewardCollection.Vertex> {
    public static final int INFINITY = Integer.MAX_VALUE;
    Graph.Vertex src;
    Graph.Vertex graphVertex;

    public RewardCollection(Graph g, int[] rewards, Graph.Vertex source) {
        super(g);
        node = new Vertex[g.size()];
        this.src = source;
        for (Graph.Vertex vertex : g) {
            node[vertex.getName()] = new Vertex(vertex);
            node[vertex.getName()].reward = rewards[vertex.getName()];
        }
    }

    class Vertex {
        boolean seen;
        int reward;
        int distance;
        Graph.Vertex graphVertex;
        Vertex(Graph.Vertex u) {
            seen = false;
            graphVertex = u;
            distance = INFINITY;
        }
    }


    // reinitialize allows running graph algorithms  many times, with different sources and reinitialises the graph parameters
    void reinitialize() {
        for (Vertex u : node) {
            u.seen = false;
            u.distance = INFINITY;
        }
        getVertex(src).distance = 0;
    }


    private boolean relax(Graph.Edge e, Graph.Vertex u) {
        Graph.Vertex v = e.otherEnd(u);
        if (getVertex(v).distance > getVertex(u).distance + e.getWeight()) {
            getVertex(v).distance = getVertex(u).distance + e.getWeight();
            return true;
        }
        return false;
    }

    /* Dijkstra is performed on the input graph
    * Vertices are inserted in to the priority Queue and distance is the priority metric */
    public void dijkstraSP​() {
        boolean changed;
        reinitialize();
        PriorityQueue<Vertex> pq = new PriorityQueue<>(Comparator.comparingInt(x -> x.distance));
        pq.add(getVertex(src));
        while (!pq.isEmpty()) {
            //vertex with minimum distance to , is removed from the priority queue
            Vertex u = pq.remove();
            u.seen = true;
            for (Graph.Edge edge : u.graphVertex) {
                Vertex v = getVertex(edge.otherEnd(u.graphVertex));
                if (!v.seen) {
                    changed = relax(edge, u.graphVertex);
                    if (changed) {
                        pq.remove(v);
                    }
                    pq.add(v);
                }
            }
        }
    }




    public static void main(String[] args) throws FileNotFoundException {
        Scanner in;

        if (args.length > 0) {
            File inputFile = new File(args[0]);
            in = new Scanner(inputFile);
        } else {
            in = new Scanner(System.in);
        }

        Graph g = Graph.readGraph(in);
        Graph.Vertex src = g.getVertex(in.nextInt());
        int rewards[] = new int[g.size()];
        for (int i = 0; i < rewards.length; ++i) {
            rewards[i] = in.nextInt();
        }
        RewardCollection rc = new RewardCollection(g,rewards,src);
        rc.dijkstraSP​();
        System.out.println();
        Timer timer = new Timer();
        timer.end();
    }
}
