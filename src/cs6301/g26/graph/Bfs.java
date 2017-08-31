package cs6301.g26.graph;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * Created by Ankitha on 8/29/2017.
 */
public class Bfs {
    public static List<Vertex> diameter(Graph graph){
         int[] predecessor = new int[graph.size()];
         int d1 = bfs(graph.getVertex(1),graph.size(),null);
         int d2 = bfs(graph.getVertex(d1+1),graph.size(),predecessor);
         //get ll
        System.out.println("d1--"+d1+"--"+d2);
        return getPath(graph,d1,d2,predecessor);
    }

    private static List<Vertex> getPath(Graph graph, int d1, int d2, int[] predecessor) {
        LinkedList<Vertex> path = new LinkedList<Vertex>();
        while(d1!=d2){
            path.addFirst(graph.getVertex(d2+1));
            d2 = predecessor[d2];
        }
        path.addFirst(graph.getVertex(d1+1));
        return path;
    }


    private static int bfs(Vertex vertex,int graphSize,int[] predecessor) {
        int distance[] = new int[graphSize];
        Arrays.fill(distance,0);
        Queue<Vertex> queue= new LinkedList<Vertex>() ;
        queue.add(vertex);
        distance[vertex.getName()]=0;
        while(!queue.isEmpty()){
            Vertex current = queue.poll();
            for(Edge edge : current){
                Vertex otherEnd = edge.otherEnd(current);
                if(distance[otherEnd.getName()]== 0){
                    if(predecessor!=null && otherEnd!=vertex)
                        predecessor[otherEnd.getName()]= current.getName();
                    queue.add(otherEnd);
                    distance[otherEnd.getName()] = distance[current.getName()]+1;
                }
            }
        }

        int maxDistance=Integer.MIN_VALUE;
        int vertexIndex = -1;
        for(int i=0; i<graphSize;i++){
            if(distance[i]>maxDistance){
                maxDistance = distance[i];
                vertexIndex = i;
            }
        }
        return vertexIndex;
    }


}



