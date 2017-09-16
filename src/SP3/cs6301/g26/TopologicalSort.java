package cs6301.g26;

import java.io.FileNotFoundException;
import java.util.*;

/**
 * Created by Ankitha on 8/29/2017.
 * This class contains the Topological Sort util function to find the
 * topological ordering of the graph vertices
 */
public class TopologicalSort {
    /**
     * This function will run the topological sort on all vertices of the input graph.
     * @param graph      : graph whose topological order has to be found
     * @param vertexCount : number of vertex in the input graph
    been explored so far
     * @return : The list containing the topological ordering if the vertices.
     */
    public static List<Graph.Vertex> topologicalSort1(Graph graph, int vertexCount) {
        //visited array : keeps track of visited vertices
        boolean visited[] = new boolean[vertexCount];
        //output is stored the order of highest discovery time
        ArrayDeque<Graph.Vertex> list =  new ArrayDeque<Graph.Vertex>();
        for (int i = 0; i < vertexCount; i++)
            visited[i] = false;
        for(Graph.Vertex vertex : graph){
            if (!visited[vertex.getName()]) {
                topologicalSortUtil(vertex, visited, list);
            }
        }
        return new ArrayList(list);
    }


    /**
     * This function will run the topological sort on all vertices of the input graph using DFS.
     * @param vertex  : graph vertex
     * @param visited : keeps track of visited vertices
     * @param list    : output is stored the order of highest discovery time
     */
    private static  void topologicalSortUtil(Graph.Vertex vertex, boolean visited[],ArrayDeque list)
    {
        visited[vertex.getName()] = true;
        for(Graph.Edge e : vertex){
            if(!visited[e.otherEnd(vertex).getName()]){
                topologicalSortUtil(e.otherEnd(vertex), visited,list);
            }
        }
        list.addFirst(vertex);
    }


    /**
     * This function will run the topological sort on all vertices of the input
     * graph by reducing the incoming edge count.
     * @param graph      : graph whose topological order has to be found
     * @param vertexCount : number of vertex in the input graph
    been explored so far
     * @return : The list containing the topological ordering if the vertices.
     */
    public static ArrayList<Integer> topologicalSort2(Graph graph, int vertexCount)
    {
        //parallel array which keeps count of incident edges of each vertex
        int [] incomingEdges = new int[vertexCount];
        //queues up the vertices and provisions for removal of vertex with 0 incident edges
        ArrayDeque<Integer> vertexQueue = new ArrayDeque<Integer>();
        //output is added to the list in the highest order of discovery
        ArrayDeque<Integer> list =  new ArrayDeque<>();
        //revAdj gives count of incoming edges
        for(Graph.Vertex v : graph){
            incomingEdges[v.getName()]= v.getRevAdj().size();
        }
        for(int i = 0;i<vertexCount;i++){
            vertexQueue.add(i);
        }

        while(vertexQueue.size()!=0){
            //pop the queue element
            int curVertex = vertexQueue.pop();
            //if the popped queue vertex has 0 incoming edges add to list and reduce incoming edge count
            // for vertices which are on the other end of it
            if(incomingEdges[curVertex]==0){
                list.addLast(curVertex);
                Graph.Vertex v = graph.getVertex(curVertex+1);
                for(Graph.Edge e : v){
                    incomingEdges[e.otherEnd(v).getName()]-= 1;
                }
            }
            else{
                //if the popped vertex has more than 0 incoming add it back to the queue
                vertexQueue.add(curVertex);
            }
        }
        return new ArrayList<Integer>(list);
    }

    public static void main(String[] args) throws FileNotFoundException {
        Scanner input = Util.readInput();
        Graph g = Graph.readGraph(input, true);
          /*Check if the graph has cycles*/
        if (!Cyclic.isCyclic(g)) {
            List<Graph.Vertex> path = TopologicalSort.topologicalSort1(g,g.size());
            /*Print the returned path  list*/
            for (Graph.Vertex vertex : path) {
                System.out.print(vertex.getName() + " ");
            }
            System.out.println("\nconnected components "+SCC.SSCCount(g,g.size()));
        } else {
            System.out.println("Not a valid undirected graph");
        }
    }



}



