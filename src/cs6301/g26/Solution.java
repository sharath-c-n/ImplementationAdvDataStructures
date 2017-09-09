package cs6301.g26;

import java.util.LinkedList;
import java.util.Arrays;
import java.util.Scanner;
import java.util.Queue;

//Class Vertex-Record Stores the Vertex and depth relative to the Start node of BFS

class Vertex_Record {
    private Graph.Vertex ver;
    private int height;

    Vertex_Record(Graph.Vertex v, int dep) {
        this.ver = v;
        this.height = dep;
    }

    public Graph.Vertex Get_Vertex() {
        return this.ver;
    }

    public int getHeight() {
        return this.height;
    }
}

public class Solution {
    private static boolean[] visit;
    private static int[] dist;

    Solution(int size) {
        dist = new int[size + 1];
        visit = new boolean[size + 1];
        Arrays.fill(visit, Boolean.FALSE);
    }

    //BFS  with parameters start node and condition is set to true to store dist of a node from the start node.
    private static Graph.Vertex BFS(Graph.Vertex node, boolean cond) {
        Graph.Vertex max_node_root = null;
        Queue<Vertex_Record> q = new LinkedList<>();
        q.add(new Vertex_Record(node, 0));
        if (cond)
            dist[node.name] = 0;
        while (!q.isEmpty()) {
            Vertex_Record top_record = q.poll();
            visit[top_record.Get_Vertex().name] = true;
            max_node_root = top_record.Get_Vertex();
            for (Graph.Edge mss : top_record.Get_Vertex().adj) {
                if (!visit[mss.from.getName()]) {
                    q.add(new Vertex_Record(mss.from, top_record.getHeight() + 1));
                    if (cond)
                        dist[mss.from.name] = top_record.getHeight() + 1;

                } else if (!visit[mss.to.getName()]) {
                    q.add(new Vertex_Record(mss.to, top_record.getHeight() + 1));
                    if (cond)
                        dist[mss.to.name] = top_record.getHeight() + 1;
                }
            }
        }
        return max_node_root;
    }

    private static LinkedList<Graph.Vertex> diameter(Graph g) {

        LinkedList<Graph.Vertex> res = new LinkedList<>();
        if (g.n < 1)
            return res;
        //Choose vertex 1 as arbitary vertex

        Graph.Vertex start_node = BFS(g.getVertex(1), false);
        Arrays.fill(visit, Boolean.FALSE);
        Graph.Vertex prev_node = BFS(start_node, true);
        res.add(prev_node);
        while (prev_node.name != start_node.name) {
            int mx_ans = dist[prev_node.name];
            Graph.Vertex temp = prev_node;
            for (Graph.Edge e : temp.adj) {
                if (e.from.name != temp.name) {
                    if (dist[e.from.name] == mx_ans - 1) {
                        prev_node = e.from;
                        break;
                    }
                } else if (e.to.name != temp.name) {
                    if (dist[e.to.name] == mx_ans - 1) {
                        prev_node = e.to;
                        break;
                    }
                }
            }
            res.add(prev_node);
        }
        return res;
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        Graph inp = Graph.readGraph(in);
        Solution inst = new Solution(inp.n);
        LinkedList<Graph.Vertex> ans = diameter(inp);
        System.out.println(" The longest path is ");
        for (Graph.Vertex v : ans) {
            System.out.print(v);
            if (v.name != ans.getLast().name)
                System.out.print("  ->  ");
        }
        System.out.println("\n");
    }
}
