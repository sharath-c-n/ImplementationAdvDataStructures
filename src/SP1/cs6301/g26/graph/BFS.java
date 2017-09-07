package cs6301.g26.graph;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * Created by Ankitha on 8/29/2017.
 * This class contains the Breadth First Search util function as well as the function to
 * find the diameter of a graph
 */
public class BFS {

    /**
     * Function to find the diameter of a graph
     *
     * @param graph : input graph
     * @return : The path of the longest diameter stored in a list.
     */
    public static List<Graph.Vertex> diameter(Graph graph) {
        int[] predecessor = new int[graph.size()];
        int d1 = bfs(graph.getVertex(1), graph.size(), null);
        int d2 = bfs(graph.getVertex(d1 + 1), graph.size(), predecessor);
        return getPath(graph, d1, d2, predecessor);
    }

    private static List<Graph.Vertex> getPath(Graph graph, int d1, int d2, int[] predecessor) {
        LinkedList<Graph.Vertex> path = new LinkedList<Graph.Vertex>();
        while (d1 != d2) {
            path.addFirst(graph.getVertex(d2 + 1));
            d2 = predecessor[d2];
        }
        path.addFirst(graph.getVertex(d1 + 1));
        return path;
    }


    /**
     * This function will run the BFS algorithm on the input vertex.
     * @param vertex      : vertex which needs to be inspected
     * @param vertexCount : number of vertex in the input graph
     * @param predecessor : The array which tracks the predecessors of all the vertices which have
     *                    been explored so far
     * @return : The vertex which is at the maximum distance from the input vertex.
     */
    private static int bfs(Graph.Vertex vertex, int vertexCount, int[] predecessor) {
        /*Array to store the distances of each reachable vertex from the input vertex*/
        int distance[] = new int[vertexCount];
        Arrays.fill(distance, 0);

        Queue<Graph.Vertex> queue = new LinkedList<Graph.Vertex>();
        /*Add the current vertex into the queue*/
        queue.add(vertex);
        distance[vertex.getName()] = 0;
        /*Do BFS until we have explored all the reachable vertices, at which point the queue will become
        * empty*/
        while (!queue.isEmpty()) {
            /*Remove the first element from the queue*/
            Graph.Vertex current = queue.poll();
            /*Add all the reachable vertices from the current vertex to the queue*/
            for (Graph.Edge edge : current) {
                Graph.Vertex otherEnd = edge.otherEnd(current);
                /*If the vertex is not explored yet then only add the vertex to the queue*/
                if (distance[otherEnd.getName()] == 0) {
                    /*Populate the predecessor array only if it's given*/
                    if (predecessor != null && otherEnd != vertex) {
                        predecessor[otherEnd.getName()] = current.getName();
                    }
                    queue.add(otherEnd);
                    /*Calculate the distance between the current node and the node
                     which is passed as the input to the function*/
                    distance[otherEnd.getName()] = distance[current.getName()] + 1;
                }
            }
        }

        /*Finding the vertex which is the farthest from the given vertex*/
        int maxDistance = Integer.MIN_VALUE;
        int vertexIndex = -1;
        for (int i = 0; i < vertexCount; i++) {
            if (distance[i] > maxDistance) {
                maxDistance = distance[i];
                vertexIndex = i;
            }
        }
        return vertexIndex;
    }


}



