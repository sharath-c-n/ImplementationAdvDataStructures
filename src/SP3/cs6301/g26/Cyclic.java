package cs6301.g26;

/**
 * Created by Ankitha on 8/29/2017.
 * This class finds out  if there is a cycle in the input graph or not
 */
public class Cyclic {

    /**
     * This is the recursive function which is used to find if there is a cycle or not
     * @param vertex : current vertex to be explored
     * @param visited : A list which contains entries of all the vertices and if the vertex is
     *                visited it will be masked as true.
     * @param parent : the parent of the current vertex which is being explored
     * @return : returns true if the graph is cyclic.
     */
    private static boolean isCyclicUtil(Graph.Vertex vertex, boolean visited[], int parent){
        visited[vertex.getName()] = true;
        for(Graph.Edge edge : vertex){
            if(!visited[edge.otherEnd(vertex).getName()]){
                if(isCyclicUtil(edge.otherEnd(vertex),visited,vertex.getName()))
                    return true;
            }
            else{
                if(edge.otherEnd(vertex).getName()!=parent){
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * This is the recursive function which is used to find if there is a cycle or not
     * @param vertex : current vertex to be explored
     * @param visited : A list which contains entries of all the vertices and if the vertex is
     *                visited it will be masked as true.
     * @param recursiveStack : keeps track of vertices which are currently in the stack during
     *                       recursive call.
     * @return : returns true if the graph is cyclic.
            */
    private static boolean isCyclicUtil(Graph.Vertex vertex, boolean visited[], boolean recursiveStack[]){
        if(visited[vertex.getName()] == false)
        {
            // Mark the current node as visited and put it in the recursion stack
            visited[vertex.getName()] = true;
            recursiveStack[vertex.getName()] = true;
            // Recur for all the vertices adjacent to this vertex
            for(Graph.Edge e : vertex)
            {
                if ( !visited[e.otherEnd(vertex).getName()] && isCyclicUtil(e.otherEnd(vertex), visited, recursiveStack) )
                return true;
               else if (recursiveStack[e.otherEnd(vertex).getName()])
                return true;
            }
        }
        // remove the vertex from recursion stack
        recursiveStack[vertex.getName()] = false;
        return false;
    }

    /**
     * This is the fiunction exposed by the class which can be called from outside to check if a graph
     * is cyclic or not.
     * @param graph : graph which needs to be checked.
     * @return true if the input graph is cyclic else false.
     */
   public static boolean isCyclic(Graph graph){
        boolean visited[] = new boolean[graph.size()];
        boolean recursiveStack[] = new boolean[graph.size()];
        for(Graph.Vertex vertex : graph){
            visited[vertex.getName()]= false;
        }
        for(Graph.Vertex vertex: graph){
            if(!visited[vertex.getName()]){
                    return graph.isDirected()? isCyclicUtil(vertex,visited,recursiveStack):isCyclicUtil(vertex,visited,-1);
            }
        }
        return false;
    }

    /**
     * Checks if the graph is directed and acyclic.
     * @param graph input graph
     * @return : true if graph is a DAG
     */
    public static boolean isDAG(Graph graph){
       return graph.isDirected() && !isCyclic(graph);
    }
}

