
/* Ver 1.0: Starter code for Prim's MST algorithm */

package cs6301.g26;

import cs6301.g00.Timer;

import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Scanner;
import java.lang.Comparable;
import java.io.FileNotFoundException;
import java.io.File;

public class PrimMST extends GraphAlgorithm {
    static final int Infinity = Integer.MAX_VALUE;

    public static class Node {
        boolean seen;
        CustomGraph.Vertex parent;

        public Node() {
            seen = false;
        }
    }

    public PrimMST(CustomGraph g) {
        super(g);
        node = new Node[g.size()];
        for (CustomGraph.Vertex vertex : g)
            setVertex(vertex, new Node());
    }

    public int prim1(CustomGraph.Vertex s) {
        int wmst = 0;
        PriorityQueue<CustomGraph.Edge> pq = new PriorityQueue<>(Comparator.comparingInt(x -> x.weight));
        ((Node) getVertex(s)).seen = true;
        ((Node) getVertex(s)).parent = null;
        for (CustomGraph.Edge e : s) {
            pq.add(e);
        }
        while (!pq.isEmpty()) {
            CustomGraph.Edge edge = pq.poll();
            if (((Node) getVertex(edge.from)).seen) {
                Node toVertex = ((Node) getVertex(edge.to));
                if (toVertex.seen)
                    continue;
                toVertex.seen = true;
                toVertex.parent = edge.from;
                wmst += edge.weight;
                for (CustomGraph.Edge e : edge.to) {
                    if (!((Node) getVertex(e.otherEnd(edge.to))).seen)
                        pq.add(e);
                }
            }
        }
        return wmst;
    }

    public int prim2(CustomGraph.Vertex s) {
        int wmst = 0;
        // SP6.Q6: Prim's algorithm using IndexedHeap<PrimVertex>:

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
        PrimMST mst = new PrimMST(g);
        int wmst = mst.prim2(s);
        timer.end();
        System.out.println(wmst);
    }
}
