package cs6301.g26;

import java.util.LinkedList;
import java.util.Arrays;
import java.util.Scanner;
import java.util.Queue;

public class Solution {
    public static Scanner in;
    public static boolean[] visit;
    public static int vertices;
    public static Graph.Vertex max_node_root;

    Solution(int size) {
        vertices = size;
        visit = new boolean[vertices + 1];
        Arrays.fill(visit, Boolean.FALSE);
    }

    public static void BFS(Graph ss, Graph.Vertex node) {
        Queue<Graph.Vertex> q = new LinkedList<Graph.Vertex>();
        q.add(node);
        while (!q.isEmpty()) {
            Graph.Vertex top_record = q.poll();
            visit[top_record.name] = true;
            max_node_root = top_record;
            for (Graph.Edge mss : top_record.adj) {
                if (visit[mss.from.getName()] == false) {
                    q.add(mss.from);
                } else if (visit[mss.to.getName()] == false) {
                    q.add(mss.to);
                }
            }
        }
    }

    public static LinkedList<Graph.Vertex> diameter(Graph g) {

        LinkedList<Graph.Vertex> res = new LinkedList<>();
        if (g.n < 1)
            return res;
        BFS(g, g.getVertex(1));
        Graph.Vertex start_node = max_node_root;
        Arrays.fill(visit, Boolean.FALSE);
        BFS(g, max_node_root);
        Graph.Vertex prev_node = max_node_root;
        res.add(prev_node);
        while (prev_node.name != start_node.name) {
            for (Graph.Edge e : prev_node.adj) {
                if (e.from.name != prev_node.name) {
                    prev_node = e.from;
                    res.add(e.from);
                    break;
                }
                if (e.to.name != prev_node.name) {
                    prev_node = e.to;
                    res.add(e.to);
                    break;
                }
            }
        }
        return res;
    }

    public static void main(String[] args) {
        in= new Scanner(System.in);
        Graph inp = Graph.readGraph(in);
        Solution inst = new Solution(inp.n);
        LinkedList<Graph.Vertex> ans = diameter(inp);
        System.out.println(" the longest diameter is ");
        for (Graph.Vertex v : ans) {
            System.out.print(v);
            if(v!=ans.getLast())
            System.out.print("  ->  ");
        }
        System.out.println("\n");
    }
}
