/** Sample driver program using the graph class
 *  Reads input graph, prints it out, and assigns each node a component number (cno).
 *  @author rbk
 *  Version 1.0: 2017/08/18
 */

package cs6301.g00;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class CC {
    // Class to store information about a vertex in this algorithm
    class CCVertex {
        Graph.Vertex element;
        boolean seen;
        int cno;
        CCVertex(Graph.Vertex u) {
            element = u;
            seen = false;
            cno = -1;
        }
    }
    // Algorithm uses a parallel array for storing information about vertices
    CCVertex[] ccVertex;
    Graph g;

    public CC(Graph g) {
        this.g = g;
        ccVertex = new CCVertex[g.size()];
        for(Graph.Vertex u: g) { ccVertex[u.name] = new CCVertex(u); }
    }

    // Main algorithm for finding the number of connected components of g using DFS
    int findCC() {
        int cno = 0;
        for(Graph.Vertex u: g) {
            if(!seen(u)) {
                cno++;
                dfsVisit(u, cno);
            }
        }
        return cno;
    }

    void dfsVisit(Graph.Vertex u, int cno) {
        visit(u, cno);
        for(Graph.Edge e: u) {
            Graph.Vertex v = e.otherEnd(u);
            if(!seen(v)) {
                dfsVisit(v, cno);
            }
        }
    }

    boolean seen(Graph.Vertex u) {
        CCVertex ccu = getCCVertex(u);
        return ccu.seen;
    }

    // Visit a node by marking it as seen and assigning it a component no
    void visit(Graph.Vertex u, int cno) {
        CCVertex ccu = getCCVertex(u);
        ccu.seen = true;
        ccu.cno = cno;
    }

    // From Vertex to CCVertex (ugly)
    CCVertex getCCVertex(Graph.Vertex u) {
        return ccVertex[u.name];
    }

    // From CCVertex to Vertex
    Graph.Vertex getVertex(CCVertex c) {
        return c.element;
    }

    public static void main(String[] args) throws FileNotFoundException {
        int evens = 0;
        Scanner in;
        if (args.length > 0) {
            File inputFile = new File(args[0]);
            in = new Scanner(inputFile);
        } else {
            in = new Scanner(System.in);
        }
        Graph g = Graph.readGraph(in);
        CC cc = new CC(g);
        int nc = cc.findCC();

        System.out.println("Input Graph has " + nc + " components:");
        for(Graph.Vertex u: g) {
            System.out.print(u + " [ " + cc.getCCVertex(u).cno + " ] :");
            for(Graph.Edge e: u.adj) {
                Graph.Vertex v = e.otherEnd(u);
                System.out.print(e + " ");
            }
            System.out.println();
        }
    }
}

/******************************
 $ java cs6301.g00.CC
 7 6
 1 3 1
 4 3 1
 4 1 1
 2 6 1
 6 7 1
 7 2 1

 Output:
 Input Graph has 3 components:
 1 [ 1 ] :(1,3) (4,1)
 2 [ 2 ] :(2,6) (7,2)
 3 [ 1 ] :(1,3) (4,3)
 4 [ 1 ] :(4,3) (4,1)
 5 [ 3 ] :
 6 [ 2 ] :(2,6) (6,7)
 7 [ 2 ] :(6,7) (7,2)

 */