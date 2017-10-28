/**
 * @author Sandeep on 10/21/2017
 */
package cs6301.g26;

import java.util.LinkedList;
import java.util.Queue;

public class BFSZeroEdge extends BFS {
    BFSZeroEdge(Graph g, Graph.Vertex src) {
        super(g, src);
    }
    boolean isSpanningTree(){
        bfs();
        for(XGraph.Vertex vertex:g ){
            if(vertex!=null && vertex!=src ){
                if(!seen(vertex))
                    return false;
            }
        }
        return  true;
    }
}
