package cs6301.g26;
import cs6301.g00.Graph;


import java.util.*;

/**
 * @author  Ankitha
 * This class contains graph utility functions
 */
public class ShortestPath extends GraphAlgorithm<ShortestPath.Vertex> {
    public static final int INFINITY = Integer.MAX_VALUE;
    Graph.Vertex src;

    public ShortestPath(Graph g,Graph.Vertex source) {
        super(g);
        this.src = source;
        node = new Vertex[g.size()];
        for(Graph.Vertex u: g) {
            node[u.getName()] = new Vertex(u);
        }
    }

    static class Vertex implements IndexedNode{
        boolean seen;
        Graph.Vertex parent;
        Graph.Vertex graphVertex;
        int distance;  // distance of vertex from source
        int count;
        LinkedList<Graph.Vertex> path ;
        int index;
        Vertex(Graph.Vertex u) {
            seen = false;
            graphVertex = u;
            distance = INFINITY;
        }

        @Override
        public int getIndex() {
            return index;
        }

        @Override
        public void setIndex(int index) {
            this.index = index;
        }
    }

    // reinitialize allows running graph algorithms  many times, with different sources and reinitialises the graph parameters
    void reinitialize() {
        for(Vertex u: node) {
            u.seen = false;
            u.parent = null;
            u.distance = INFINITY;
            u.index = -1;
        }
        getVertex(src).distance = 0;
    }


    private boolean relax(Graph.Edge e, Graph.Vertex u){
        Graph.Vertex v = e.otherEnd(u);
        if (getVertex(v).distance > getVertex(u).distance + e.getWeight()){
            getVertex(v).distance = getVertex(u).distance + e.getWeight();
            return true;
        }
        return false;
    }

    boolean seen(Graph.Vertex u) {
        return getVertex(u).seen;
    }

    private Graph.Vertex getParent(Graph.Vertex u) {
        return getVertex(u).parent;
    }

    int distance(Graph.Vertex u) {
        return getVertex(u).distance;
    }


    // Visit a node v from u
    void visit(Graph.Vertex u, Graph.Vertex v) {
        Vertex bv = getVertex(v);
        bv.seen = true;
        bv.parent = u;
        bv.distance = distance(u) + 1;
    }

    /* Breadth First search is performed on the input graph */
    void bfs() {
        reinitialize();
        Queue<Graph.Vertex> q = new LinkedList<>();
        q.add(src);
        while(!q.isEmpty()) {
            Graph.Vertex u = q.remove();
            for(Graph.Edge e: u) {
                Graph.Vertex v = e.otherEnd(u);
                if(!seen(v)) {
                    visit(u,v);
                    q.add(v);
                }
            }
        }
    }


    /* Dijkstra is performed on the input graph
    * Vertices are inserted in to the priority Queue and distance is the priority metric */
    public void dijkstraSP​(){
        boolean changed;
        reinitialize();
        Vertex pqArray[] = new Vertex[g.size()];
        IndexedBinaryHeap<Vertex> pq = new IndexedBinaryHeap<>(pqArray, Comparator.comparingInt(x -> x.distance), g.size());
            pq.add(getVertex(src));
        while (!pq.isEmpty()) {
            //vertex with minimum distance to , is removed from the priority queue
            Vertex u = pq.remove();
            u.seen = true;
            for (Graph.Edge edge : u.graphVertex) {
                Vertex v = getVertex(edge.otherEnd(u.graphVertex));
                if(!v.seen){
                    changed = relax(edge,u.graphVertex);
                    if(changed){
                        v.parent = u.graphVertex;
                        //if the vertex is present in the priority queue reduce key else add it to the priority queue
                        if(v.getIndex()> -1)
                        {
                            //reduce key operation is performed for the relaxed edge
                            pq.percolateUp(v.getIndex());
                        }
                        else
                            pq.add(v);
                    }
                }
            }
        }

    }


    /**
     * Finds the topological ordering of the input graph
     * @return topological order
     */
    private List<Graph.Vertex> topologicalSort() {
        //output is stored the order of highest discovery time
        reinitialize();
        ArrayDeque<Graph.Vertex> list = new ArrayDeque<>();
        for (Graph.Vertex vertex : g) {
            if (!getVertex(vertex).seen) {
                topologicalSortUtil(vertex, list);
            }
        }
        return new ArrayList<>(list);
    }

    /**
     * This function will run the topological sort on all vertices of the input graph.
     * @param vertex : graph vertex
     * @param list   : output is stored the order of highest discovery time
     */
    private void topologicalSortUtil(Graph.Vertex vertex, ArrayDeque<Graph.Vertex> list) {
        getVertex(vertex).seen = true;
        for (Graph.Edge e : vertex) {
            if (!getVertex(e.otherEnd(vertex)).seen) {
                topologicalSortUtil(e.otherEnd(vertex), list);
            }
        }
        list.addFirst(vertex);
    }

    public void dagShortestPath() {
        boolean changed;
        reinitialize();
        List<Graph.Vertex> topologicalOrder = topologicalSort();
        for(Graph.Vertex u :topologicalOrder){
                for(Graph.Edge edge : u){
                    Vertex v = getVertex(edge.otherEnd(u));
                    changed = relax(edge,u);
                    if(changed) {
                        v.parent = u;
                    }
                }
        }
    }

    public boolean bellmanFord(){
        //Queue holds vertices that need to be processed
        Queue<Vertex> q = new LinkedList<>();
        boolean changed;
        reinitialize();
        getVertex(src).seen = true;
        q.add(getVertex(src));
        while(!q.isEmpty()){
            Vertex u = q.remove();
            u.seen = false;
            u.count = u.count + 1;
            if(u.count >= g.size())  return false;
            for(Graph.Edge edge : u.graphVertex){
                Vertex v = getVertex(edge.otherEnd(u.graphVertex));
                changed = relax(edge,u.graphVertex);
                if(changed) {
                    v.parent = u.graphVertex;
                    q.add(v);
                    v.seen = true;
                }
            }
        }
        return true;
    }


    public void fastestShortestPaths() {
        if(isPositiveWeighted(g)){
            bfs();
        }
        else if(g.isDirected()  && !isCyclic(g)){
            dagShortestPath();
        }
        else if(!isPositiveWeighted(g)){
            dijkstraSP​();
        }
        else{
            bellmanFord();
        }
    }

    private boolean isPositiveWeighted(Graph g) {
        for(Graph.Vertex v : g){
            for(Graph.Edge e : v){
                if(e.getWeight()<0) return false;
            }
        }
        return  true;
    }

    /**
     * This is the recursive function which is used to find if there is a cycle or not
     * @param vertex : current vertex to be explored
     * @param recursiveStack : keeps track of vertices which are currently in the stack during
     *                       recursive call.
     * @return : returns true if the graph is cyclic.
     */
    private  boolean isCyclicUtil(Graph.Vertex vertex, boolean recursiveStack[]){
        if(!getVertex(vertex).seen)
        {
            // Mark the current node as visited and put it in the recursion stack
            getVertex(vertex).seen = true;
            recursiveStack[vertex.getName()] = true;
            // Recur for all the vertices adjacent to this vertex
            for(Graph.Edge e : vertex)
            {
                if ( !getVertex(e.otherEnd(vertex)).seen && isCyclicUtil(e.otherEnd(vertex),recursiveStack) )
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
     * This is the function exposed by the class which can be called from outside to check if a graph
     * is cyclic or not.
     * @param graph : graph which needs to be checked.
     * @return true if the input graph is cyclic else false.
     */
    private  boolean isCyclic(Graph graph){
        boolean recursiveStack[] = new boolean[graph.size()];
        for(Graph.Vertex vertex : graph){
            getVertex(vertex).seen = true;
        }
        for(Graph.Vertex vertex: graph){
            if(!getVertex(vertex).seen){
                return  isCyclicUtil(vertex,recursiveStack);
            }
        }
        return false;
    }

}