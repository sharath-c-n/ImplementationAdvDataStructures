package cs6301.g26;


import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ankitha on 9/13/2017.
 */

/**
 * Created by Ankitha on 8/29/2017.
 * This class contains the SSCCount which returns the count of the
 * number of strongly connected components
 */
public class GraphUtil extends GraphAlgorithm<GraphUtil.Vertex> {

    public GraphUtil(Graph g) {
        super(g);
        node = new Vertex[g.size()];
        // Create array for storing vertex properties
        for (Graph.Vertex u : g) {
            node[u.getName()] = new Vertex();
        }
    }

    // reinitialize allows running BFS many times, with different sources
    void reinitialize() {
        for (Graph.Vertex u : g) {
            Vertex utilVertex = getVertex(u);
            utilVertex.isVisited = false;
        }
    }

    public class Vertex {
        boolean isVisited;

        public Vertex() {
            this.isVisited = false;
        }
    }


    /**
     * This function will run the topological sort on all vertices of the input graph.
     * been explored so far
     *
     * @return : The number of Strongly connected components in the graph
     */

    public boolean isStronglyConnected() {
        int count = 0;
        reinitialize();
        //Step 1 : find the topological oder of the vertices of the given graph
        List<Graph.Vertex> path = topologicalSort();
        //Step 2 : do a DFS on the reverse graph by picking each vertex from the path in the topological order
        //Step 3: Increment the count of Strongly Connected Components when a vertex which is
        // not explored previously is found indicating the discovery of a new component
        for (Graph.Vertex v : path) {
            if (!getVertex(v).isVisited) {
                count++;
                DFS(v);
            }
        }
        return count == 1;
    }

    private void DFS(Graph.Vertex v) {
        getVertex(v).isVisited = true;
        //reverse graph is got by traversing over the reverse adjacency list which is maintained
        for (Graph.Edge e : v.getRevAdj()) {
            if (!getVertex(e.otherEnd(v)).isVisited) {
                DFS(e.otherEnd(v));
            }
        }
    }

    public List<Graph.Vertex> topologicalSort() {
        //output is stored the order of highest discovery time
        reinitialize();
        ArrayDeque<Graph.Vertex> list = new ArrayDeque<Graph.Vertex>();
        for (Graph.Vertex vertex : g) {
            if (!getVertex(vertex).isVisited) {
                topologicalSortUtil(vertex, list);
            }
        }
        return new ArrayList(list);
    }

    /**
     * This function will run the topological sort on all vertices of the input graph using DFS.
     *
     * @param vertex : graph vertex
     * @param list   : output is stored the order of highest discovery time
     */
    private void topologicalSortUtil(Graph.Vertex vertex, ArrayDeque list) {
        getVertex(vertex).isVisited = true;
        for (Graph.Edge e : vertex) {
            if (!getVertex(e.otherEnd(vertex)).isVisited) {
                topologicalSortUtil(e.otherEnd(vertex), list);
            }
        }
        list.addFirst(vertex);
    }

    Graph.Vertex inEqlOutEdges() {
        for (Graph.Vertex vertex : g) {
            if (vertex.revAdj.size() != vertex.adj.size()) {
                return vertex;
            }
        }
        return null;
    }

}
