package cs6301.g26;

import cs6301.g00.Timer;

import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Scanner;
import java.io.FileNotFoundException;
import java.io.File;

public class PrimMST extends GraphAlgorithm<PrimMST.Node> {
    static final int Infinity = Integer.MAX_VALUE;

    //The below class can be simplified for Prim1 implementation by removing unwanted  fields
    public static class Node implements IndexedNode {
        boolean seen;
        CustomGraph.Vertex parent;
        CustomGraph.Vertex graphVertex;
        public int d;
        int index;

        public Node(CustomGraph.Vertex vertex) {
            seen = false;
            parent = null;
            d = Infinity;
            graphVertex = vertex;
        }

        @Override
        public int getIndex() {
            return index;
        }

        @Override
        public void setIndex(int index) {
            this.index = index;
        }
    }

    public PrimMST(CustomGraph g) {
        super(g);
        node = new Node[g.size()];
        for (CustomGraph.Vertex vertex : g)
            setVertex(vertex, new Node(vertex));
    }

    public void resetNodes() {
        for (Node n : node) {
            n.seen = false;
            n.parent = null;
            n.d = Infinity;
        }
    }

    public int prim1(CustomGraph.Vertex s) {
        int wmst = 0;
        PriorityQueue<CustomGraph.Edge> pq = new PriorityQueue<>(Comparator.comparingInt(x -> x.weight));
        getVertex(s).seen = true;
        getVertex(s).parent = null;
        for (CustomGraph.Edge e : s) {
            pq.add(e);
        }
        while (!pq.isEmpty()) {
            CustomGraph.Edge edge = pq.poll();
            Node toVertex = getVertex(edge.to);
            Node fromVertex = getVertex(edge.from);
            if (toVertex.seen || fromVertex.seen) {
                if (toVertex.seen && fromVertex.seen)
                    continue;
                Node currentVertex;
                if (toVertex.seen) {
                    currentVertex = fromVertex;
                    currentVertex.parent = toVertex.graphVertex;
                } else {
                    currentVertex = toVertex;
                    currentVertex.parent = fromVertex.graphVertex;
                }
                currentVertex.seen = true;
                wmst += edge.weight;
                for (CustomGraph.Edge e : currentVertex.graphVertex) {
                    if (!getVertex(e.otherEnd(edge.to)).seen)
                        pq.add(e);
                }
            }
        }
        return wmst;
    }

    public int prim2(CustomGraph.Vertex s) {
        int wmst = 0;
        resetNodes();
        Node pqArray[] = new Node[g.size()];
        getVertex(s).d = 0;
        IndexedBinaryHeap<PrimMST.Node> pq = new IndexedBinaryHeap<>(pqArray, Comparator.comparingInt(x -> x.d), g.size());
        for(CustomGraph.Vertex vertex : g){
            pq.add(getVertex(vertex));
        }
        while (!pq.isEmpty()) {
            Node u = pq.remove();
            u.seen = true;
            wmst += u.d;
            for (CustomGraph.Edge edge : u.graphVertex) {
                Node v = getVertex(edge.otherEnd(u.graphVertex));
                if (!v.seen && edge.weight < v.d) {
                    v.d = edge.weight;
                    v.parent = u.graphVertex;
                    pq.percolateUp(v.getIndex());
                }
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
        PrimMST mst = new PrimMST(g);
        int wmst = mst.prim2(s);
        timer.end();
        System.out.println(wmst);
    }
}
