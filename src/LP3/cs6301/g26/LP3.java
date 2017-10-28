// Do not rename this file or move it away from cs6301/g??
package cs6301.g26;

import java.util.*;
import java.io.File;
import java.io.FileNotFoundException;

import cs6301.g00.Graph.Vertex;
import cs6301.g00.Graph;
import cs6301.g00.Graph.Edge;
import cs6301.g00.Timer;

public class LP3 {
    static int VERBOSE = 0;

    public static void main(String[] args) throws FileNotFoundException {
        Scanner in;
        if (args.length > 0) {
            File inputFile = new File(args[0]);
            in = new Scanner(inputFile);
        } else {
            in = new Scanner(System.in);
        }
        if (args.length > 1) {
            VERBOSE = Integer.parseInt(args[1]);
        }

        int start = in.nextInt();  // root node of the MST
        Graph g = Graph.readDirectedGraph(in);
        Vertex startVertex = g.getVertex(start);
        List<Edge> dmst = new ArrayList<>();

        Timer timer = new Timer();
        int wmst = directedMST(g, startVertex, dmst);
        timer.end();
        findValidity(dmst,g.size());
        System.out.println(wmst);
        if (VERBOSE > 0) {
            System.out.println("_________________________");
            for (Edge e : dmst) {
                System.out.print(e);
            }
            System.out.println();
            System.out.println("_________________________");
        }
        System.out.println(timer);
    }
    public static  void findValidity( List<Edge> dmst, int size){

        Vector<Integer> vl[]= new Vector[size+1];
        for(int i=0;i<=size;i++){
            vl[i]=new Vector<Integer>();
        }
        for( Edge e: dmst){
            if(e!=null)
                vl[e.fromVertex().getName()].addElement(e.toVertex().getName());
        }
        //System.out.println(vl[0]);
        Queue<Integer> q = new LinkedList<>();
        q.add(0);
        int [] seen= new int[size+1];
        for(int i=0;i<=size;i++)
            seen[i]=0;
        int count=0;
        while( !q.isEmpty()){
            Integer top = q.peek();
            seen[top.intValue()]=1;
            count++;
            q.poll();
            for(Integer val:vl[top]){
                if( seen[val.intValue()]==0) {
                    q.add(val);
                }
            }
        }

        if(count==size){
            System.out.println(count+ "  Valid Path");
        }
        else
            System.out.println(count+ "  Not a Valid Path");
    }

    /**
     * TO DO: List dmst is an empty list. When your algorithm finishes,
     * it should have the edges of the directed MST of g rooted at the
     * start vertex.  Edges must be ordered based on the vertex into
     * which it goes, e.g., {(7,1),(7,2),null,(2,4),(3,5),(5,6),(3,7)}.
     * In this example, 3 is the start vertex and has no incoming edges.
     * So, the list has a null corresponding to Vertex 3.
     * The function should return the total weight of the MST it found.
     */
    public static int directedMST(Graph g, Vertex start, List<Edge> dmst) {
        SpanningTree spanningTree = new SpanningTree(g, start);
        return spanningTree.findSpanningTree(dmst);
    }

    static void printGraph(XGraph g) {
        for (XGraph.Vertex v : g) {
            for (Graph.Edge e : v) {
                System.out.println(v + " " + e.otherEnd(v) + " " + e.getWeight());
            }
        }
    }
}