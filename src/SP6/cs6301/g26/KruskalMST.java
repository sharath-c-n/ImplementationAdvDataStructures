/**
 * KruskalMST
 * @author : sharath
 */

package cs6301.g26;

import cs6301.g00.Timer;

import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Scanner;
import java.io.FileNotFoundException;
import java.io.File;

public class KruskalMST extends GraphAlgorithm<KruskalMST.Node> implements DisjointSet<KruskalMST.Node> {

    @Override
    public Node findSet(Node u) {
        Node vertex;
        if (u == u.parent)
            vertex = u;
        else {
            vertex = findSet(u.parent);
            //Set compression
            u.parent = vertex;
        }
        return vertex;
    }

    @Override
    public void union(Node u, Node v) {
        Node parent = findSet(v);
        parent.parent = findSet(u);
    }

    static class Node {
        Node parent;
        CustomGraph.Vertex parentGraphNode;
        CustomGraph.Vertex graphNode;

        Node(CustomGraph.Vertex vertex) {
            this.parent = this;
            this.graphNode = vertex;
        }
    }

    public KruskalMST(CustomGraph g) {
        super(g);
        node = new KruskalMST.Node[g.size()];
        for (CustomGraph.Vertex vertex : g)
            setVertex(vertex, new Node(vertex));
    }

    /**
     * Implementation of  kruskal algorithm
     * @return : sum of all weights of the minimum spanning tree
     */
    public int kruskal() {
        int wmst = 0;
        PriorityQueue<CustomGraph.Edge> pq = new PriorityQueue<>(Comparator.comparingInt(x -> x.weight));
        for (CustomGraph.Vertex vertex : g) {
            for (CustomGraph.Edge edge : vertex) {
                pq.add(edge);
            }
        }

        while (!pq.isEmpty()) {
            CustomGraph.Edge edge = pq.remove();
            if (findSet(getVertex(edge.to)) != findSet(getVertex(edge.from))) {
                Node toVertex = getVertex(edge.to);
                toVertex.parentGraphNode = edge.from;
                union(getVertex(edge.from), toVertex);
                wmst += edge.weight;
            }
        }

        return wmst;
    }


    public static void main(String[] args) throws FileNotFoundException {
        Scanner in;

        if (args.length > 0) {
            File inputFile = new File(args[0]);
            in = new Scanner(inputFile);
        } else {
            in = new Scanner(System.in);
        }

        CustomGraph g = CustomGraph.readGraph(in);
        CustomGraph.Vertex s = g.getVertex(1);

        Timer timer = new Timer();
        KruskalMST mst = new KruskalMST(g);
        int wmst = mst.kruskal();
        timer.end();
        System.out.println(wmst);
    }
}
