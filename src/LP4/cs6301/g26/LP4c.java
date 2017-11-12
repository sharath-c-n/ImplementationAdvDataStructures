// Driver code for LP4, part c
package cs6301.g26;
import cs6301.g00.Graph;

public class LP4c {
    static int VERBOSE = 0;

    public static void main(String[] args) {
        if(args.length > 0) { VERBOSE = Integer.parseInt(args[0]); }
        java.util.Scanner in = new java.util.Scanner(System.in);
        Graph g = Graph.readDirectedGraph(in);
        int source = in.nextInt();
        int target = in.nextInt();
        cs6301.g00.Timer t = new cs6301.g00.Timer();
        LP4 handle = new LP4(g, g.getVertex(source));
        long result = handle.countShortestPaths(g.getVertex(target));
        if(VERBOSE > 0) { LP4.printGraph(g, null, g.getVertex(source), g.getVertex(target), 0); }
        System.out.println(result + "\n" + t.end());
    }
}