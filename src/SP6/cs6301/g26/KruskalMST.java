
/* Ver 1.0: Starter code for Kruskal's MST algorithm */

package cs6301.g26;

import cs6301.g00.Graph;
import cs6301.g00.Timer;

import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Scanner;
import java.lang.Comparable;
import java.io.FileNotFoundException;
import java.io.File;

public class KruskalMST extends GraphAlgorithm{
    static class Node {
        boolean seen;
        CustomGraph.Vertex parent;
        Node(CustomGraph.Vertex vertex){
            this.parent = vertex;
            seen = false;
        }
    }

    public KruskalMST(CustomGraph g) {
        super(g);
        node = new PrimMST.Node[g.size()];
        for (CustomGraph.Vertex vertex : g)
            setVertex(vertex, new Node(vertex));
    }

    public int kruskal() {
	int wmst = 0;
        PriorityQueue<CustomGraph.Edge> pq = new PriorityQueue<>(Comparator.comparingInt(x -> x.weight));
        for(CustomGraph.Vertex vertex:g){
            for(CustomGraph.Edge edge : vertex){
                pq.add(edge);
            }
        }

        while (!pq.isEmpty()){
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
