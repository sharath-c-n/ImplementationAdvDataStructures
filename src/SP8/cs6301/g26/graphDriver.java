package cs6301.g26;
import cs6301.g00.Graph;
import cs6301.g00.Timer;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Scanner;

/**
 * Created by Ankitha on 10/29/2017.
 */
public class graphDriver {
    public static void main(String[] args) throws FileNotFoundException {
        Scanner in;

        if (args.length > 0) {
            File inputFile = new File(args[0]);
            in = new Scanner(inputFile);
        } else {
            in = new Scanner(System.in);
        }

        Graph g = Graph.readDirectedGraph(in);
        ShortestPath sp = new ShortestPath(g,g.getVertex(1));
        sp.bfs();
        sp.dagShortestPath();
        sp.bellmanFord();
        sp.dijkstraSP​();
        sp.fastestShortestPaths();
    }
}



