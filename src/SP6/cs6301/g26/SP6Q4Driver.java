package cs6301.g26;

import cs6301.g00.Timer;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * SP6Q4Driver:
 *
 * @author : Sharath
 * 07/10/2017
 */
public class SP6Q4Driver {
    public static void main(String[] args) throws FileNotFoundException {
        Scanner in;

        if (args.length > 0) {
            File inputFile = new File(args[0]);
            in = new Scanner(inputFile);
        } else {
            in = new Scanner(System.in);
        }

        CustomGraph g = CustomGraph.readGraph(in);
        CustomGraph.Vertex s = g.getVertex(5);

        Timer timer = new Timer();
        PrimMST mst = new PrimMST(g);
        int wmst = mst.prim1(s);
        timer.end();
        System.out.println(wmst);
    }
}
