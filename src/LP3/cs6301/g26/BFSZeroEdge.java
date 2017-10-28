/**
 * @author Sandeep on 10/21/2017
 */
package cs6301.g26;
import cs6301.g00.Graph;

public class BFSZeroEdge extends BFS {
    BFSZeroEdge(Graph g, Graph.Vertex src) {
        super(g, src);
    }
    boolean isSpanningTree(){
        bfs();
        visit(src,src);
        for(Graph.Vertex vertex: g ){
            if(vertex!=null){
                if(!seen(vertex))
                    return false;
            }
        }
        return  true;
    }
}
