package cs6301.g26;

import cs6301.g00.Graph;

import java.util.ArrayList;

/**
 * Created by Ankitha on 11/4/2017.
 */
public class XGraph extends GraphAlgorithm<XGraph.Vertex>{
    int count;
    Boolean VERBOSE ;
    ArrayList<Graph.Vertex> topologicalOrder;

    public XGraph(Graph g) {
        super(g);
        topologicalOrder = new ArrayList<>();
        node = new Vertex[g.size()];
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
                topologicalOrder.add(vertex);
                curVertex.seen = true;
                topologicalSort();

                curVertex.seen = false;
                //removes the last vertex in the topological Order
                topologicalOrder.remove(topologicalOrder.size() - 1);
                for (Graph.Edge edge : vertex) {
                    getVertex(edge.otherEnd(vertex)).inDegree--;
                }
                flag = true;
            }
        }

        if (!flag) {
            printVertices(topologicalOrder);
        }
    }

    private void printVertices(ArrayList<Graph.Vertex> tsOrder){
        count++;
        if(VERBOSE){
            for (Graph.Vertex vertex:tsOrder)
                System.out.print(vertex+" ");
            System.out.println();
        }
    }

    public int getTSOrderCount(){
        VERBOSE = false ;
        topologicalSort();
        return count;
    }

    public void printVertices(){
        VERBOSE = true ;
        topologicalSort();
    }
}
