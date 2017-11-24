package cs6301.g26;

import cs6301.g00.Graph;
import cs6301.g00.Graph.Edge;
import cs6301.g00.Graph.Vertex;

import java.io.File;
import java.util.Scanner;
import java.util.Set;


public class LP7 {
    static int VERBOSE = 0;
    public static void main(String[] args) {
        if (args.length > 0) {
            VERBOSE = Integer.parseInt(args[0]);
        }
        File inputFile = new File("C:\\Users\\Sandeep\\IdeaProjects\\ImplementationAdvData\\src\\LP7\\Testcases\\lp7-in3.txt");
        //   in = new Scanner(inputFile);
        try {
            java.util.Scanner in = new Scanner(inputFile);
            Graph g = Graph.readDirectedGraph(in);
            cs6301.g00.Timer timer = new cs6301.g00.Timer();
            int s = in.nextInt();
            int t = in.nextInt();
            java.util.HashMap<Edge, Integer> capacity = new java.util.HashMap<>();

            for (Vertex u : g) {
                for (Edge e : u) {
                    capacity.put(e, 1);
                }
            }
            Flow f = new Flow(g, g.getVertex(s), g.getVertex(t), capacity);
                int val= f.dinitzMaxFlow();
            System.out.println(" THE MAX FLOW IS "+val);
           Set<Vertex> Sv= f.minCutS();

           System.out.println(Sv);

            Set<Vertex> Tv= f.minCutT();

            System.out.println(Tv);

            if (VERBOSE > 0) {
                for (Vertex u : g) {
                    System.out.print(u + " : ");
                    for (Edge e : u) {
                        System.out.print(e + ":" + f.flow(e) + "/" + f.capacity(e) + " | ");
                    }
                    System.out.println();
                }
            }

            System.out.println(timer.end());
        }
        catch( Exception ex){
            System.out.println("");
        }
    }

}

