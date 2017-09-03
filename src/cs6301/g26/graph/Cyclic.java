package cs6301.g26.graph;

/**
 * Created by Ankitha on 8/29/2017.
 */
public class Cyclic {
    static boolean isCyclicUtil(Vertex v, boolean visited[],int parent){
        visited[v.getName()] = true;
        for(Edge edge : v){
            if(!visited[edge.otherEnd(v).getName()]){
                if(isCyclicUtil(edge.otherEnd(v),visited,v.getName()))
                    return true;
            }
            else{
                if(edge.otherEnd(v).getName()!=parent){
                    return true;
                }
            }
        }
        return false;
    }

   public static boolean isCyclic(Graph graph){
        boolean visited[] = new boolean[graph.size()];
        for(Vertex vertex : graph){
            visited[vertex.getName()]= false;
        }
        for(Vertex vertex: graph){
            if(!visited[vertex.getName()]){
                if(isCyclicUtil(vertex,visited,-1)){
                    return true;
                }
            }
        }
        return false;
    }
}

