
/**
 * @author Sandeep on 9/14/2017
 */
package cs6301.g26;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;

public class BridgeCut {
    private int[] dis_time; //Array to store DiscoveryTime of Each Vertex
    private int[] low;
    private boolean[] seen;
    static boolean[] cut_vertices;
    static int curr_time = 0;

    BridgeCut(Graph g) {
        dis_time = new int[g.size() + 1];
        low = new int[g.size() + 1];
        seen = new boolean[g.size() + 1];
        cut_vertices = new boolean[g.size() + 1];
    }

    /*
     * This Function returns list of Bridge Edges in given Graph
     *
     * @param g: Input Graph
     *
     * @returns List of Graph Edges
     */
    public List<Graph.Edge> findBridgeCut(Graph g) {

        if (g.size() == 0) //Check if there are no vertices
            return null;
        List<Graph.Edge> edg = new ArrayList<>();
        DFS(g.getVertex(1), null, null, edg);
        return edg;
    }

    /*
     * This Function does DFS, stores the BridgeEdges and also Cut Vertices.
     *
     * @param v: Current root Vertex v.
     * @param par: Parent of current root.
     * @param edg: stores the edge between v and its parent.
     * @param edgList: stores the Bridge Edges.
     *
     */
    public void DFS(Graph.Vertex v, Graph.Vertex par, Graph.Edge edg, List<Graph.Edge> edgList) {
        int ver = v.getName();
        if (!seen[ver]) { //Visit only if a Vertex is not previously visited.
            curr_time++;
            seen[ver] = true;
            dis_time[ver] = curr_time;
            low[ver] = curr_time; //Initially low of a vertex is its Discovery Time

            int children = 0;
            for (Graph.Edge e : v.getAdj()) {
                int otherEnd = e.otherEnd(v).getName();
                if (!seen[otherEnd]) {
                    children++;
                    DFS(e.otherEnd(v), v, e, edgList);
                    //Add the cut vertex if it has a child whose low value is at least the Discovery time of current vertex.
                    if (low[otherEnd] >= dis_time[ver] && !cut_vertices[ver] && par != null)
                        cut_vertices[ver] = true;
                    low[ver] = Math.min(low[ver], low[otherEnd]);
                } else if (seen[otherEnd] && par != null && otherEnd != par.getName()) {
                    low[ver] = Math.min(low[ver], dis_time[otherEnd]);
                }
            }


            //To check whether current vertex is root and the root is a cutVertex if it has at least two children
            if (par == null) {
                if (children >= 2)
                    cut_vertices[ver] = true;
            }

            //Add an Edge as Bridge if low of Vertex is at least the Discovery time of Vertex.
            if (low[ver] >= dis_time[ver] && par != null)
                edgList.add(edg);
        }
    }

    public static void main(String[] args) throws FileNotFoundException {
        Scanner input = Util.readInput();
        Graph g = Graph.readGraph(input, false);
        BridgeCut bb = new BridgeCut(g);
        List<Graph.Edge> ed = bb.findBridgeCut(g);
        System.out.println(" The Bridge Edges");
        for (Graph.Edge e : ed)
            System.out.print(e + "     ");
        System.out.println();
        System.out.println("The Cut Vertices are");
        for (Graph.Vertex v : g) {
            if (BridgeCut.cut_vertices[v.getName()])
                System.out.print(v + "  ");
        }
    }
}
