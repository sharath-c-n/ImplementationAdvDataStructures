/** Diameter of a tree
 *  @author rbk
 *  Version 1.0: 2017/08/18
 */

package cs6301.g00;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.LinkedList;
import java.util.Scanner;

public class DiameterTree {
    public static void main(String[] args) throws FileNotFoundException {
        Scanner in;
        if (args.length > 0) {
            File inputFile = new File(args[0]);
            in = new Scanner(inputFile);
        } else {
            in = new Scanner(System.in);
        }
        Graph g = Graph.readGraph(in);
        List<Graph.Vertex> result = diameter(g);
        for(Graph.Vertex x: result) {
            System.out.print(x + " ");
        }
        System.out.println();
    }

    // Find diameter of a tree (represented as a graph)
    public static List<Graph.Vertex> diameter(Graph g) {
        // Call BFS from some vertex of g
        Graph.Vertex src = g.getVertex(1);
        BFS b = new BFS(g, src);
        b.bfs();
        Graph.Vertex farthest = findFarthest(b);

        // Call BFS again from farthest as source
        src = farthest;
        b.reinitialize(farthest);  // clear all fields and set src to farthest
        b.bfs();
        Graph.Vertex u = findFarthest(b);

        // Get path from farthest vertex to src by using parent link on BFS tree
        List<Graph.Vertex> result = new LinkedList<>();
        while(u != src) {
            result.add(u);
            u = b.getParent(u);
        }
        result.add(src);
        return result;
    }

    static Graph.Vertex findFarthest(BFS b) {
        Graph.Vertex farthest = b.src;
        for(Graph.Vertex u: b.g) {
            if(b.distance(u) > b.distance(farthest)) {
                farthest = u;
            }
        }
        return farthest;
    }
}