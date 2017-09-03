package cs6301; /**
 * Created by Ankitha on 8/29/2017.
 */
import java.io.File;
import java.util.Scanner;

import cs6301.g26.graph.Bfs;
import cs6301.g26.graph.Cyclic;
import cs6301.g26.graph.Graph;
public class GraphMain {

    public static void main(String[] args) {

        try {

            String fileName = "input.txt";
            File file = new File(fileName);
            Scanner input = new Scanner(file);
            Graph g = Graph.readGraph(input,false);
            if(!Cyclic.isCyclic(g)){
                Bfs.diameter(g);
            }
            else{
                System.out.println("Whatever!!");
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}
