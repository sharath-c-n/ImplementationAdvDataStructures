package cs6301.g26;

import cs6301.g00.Graph;


/**
 * Created by Ankitha on 11/4/2017.
 */
public class TopologicalOrder extends GraphAlgorithm<TopologicalOrder.Vertex>{
    private long count;
    private Boolean VERBOSE ;
    private Graph.Vertex[] topologicalOrder;
    private  int index;

    public TopologicalOrder(Graph g) {
        super(g);
        topologicalOrder = new Graph.Vertex[g.size()];
        node = new Vertex[g.size()];
        count = 0;
        index = 0;
        for(Graph.Vertex vertex :g){
            node[vertex.getName()] = new Vertex(vertex.revAdj.size());
        }
    }

    class Vertex {
        boolean seen;
        int inDegree;
        public Vertex(int inDegree) {
            this.seen = false;
            this.inDegree = inDegree;
        }
    }

    private void topologicalSort() {
        // To indicate whether all topological are found
        // or not
        boolean flag = false;
        for (Graph.Vertex vertex : g) {
            Vertex curVertex = getVertex(vertex);
            if (curVertex.inDegree == 0 && !curVertex.seen) {
                for (Graph.Edge edge : vertex) {
                    getVertex(edge.otherEnd(vertex)).inDegree--;
                }
                topologicalOrder[index++] = vertex;
                curVertex.seen = true;
                topologicalSort();

                curVertex.seen = false;
                //removes the last vertex in the topological Order
                index--;
                for (Graph.Edge edge : vertex) {
                    getVertex(edge.otherEnd(vertex)).inDegree++;
                }
                flag = true;
            }
        }

        if (!flag) {
            printVertices();
        }
    }

    private void printVertices(){
        count++;
        if(VERBOSE){
            for (Graph.Vertex vertex:topologicalOrder)
                System.out.print(vertex+" ");
            System.out.println();
        }
    }

    public long getTopologicalOrder(){
        topologicalSort();
        return count;
    }

    public long getTSOrderCount(){
        VERBOSE = false ;
        return getTopologicalOrder();
    }

    public long printAllOrders(){
        VERBOSE = true ;
        return getTopologicalOrder();
    }
}