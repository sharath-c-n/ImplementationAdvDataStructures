package cs6301.g26;

import cs6301.g00.Graph;

import java.util.*;

/**
 * RewardCollection:
 * @author : Sharath
 * 07/11/2017
 */
public class RewardCollection extends GraphAlgorithm<RewardCollection.Vertex> {
    /**
     * Source vertex from which we have to find maximum rewards path
     */
    private Graph.Vertex src;
    /**
     * Stores the maximum reward that can be collected from a given source.
     */
    private int maxReward;
    /**
     * Stores the intermediate maximum reward path
     */
    private Graph.Vertex[] path;
    /**
     * Stores the optimal maximum reward path
     */
    private List<Graph.Vertex> optimumPath;
    /**
     * Used while recursion
     */
    private int index = 0;

    public static class Vertex {
        /**
         * Vertex's shortest path distance from the given source
         */
        int distance;
        boolean seen;
        /**
         * reward that can be collected if the vertex is reached using shortest path
         */
        int reward;
        /**
         * Original graph vertex which is represent by this vertex.
         */
        Graph.Vertex graphVertex;

        public Vertex(int reward, Graph.Vertex graphVertex) {
            this.reward = reward;
            distance = Integer.MAX_VALUE;
            seen = false;
            this.graphVertex = graphVertex;
        }

        @Override
        public String toString() {
            return graphVertex.toString();
        }
    }

    public RewardCollection(Graph g, Graph.Vertex src, HashMap<Graph.Vertex,Integer> rewards) {
        super(g);
        this.src = src;
        node = new Vertex[g.size()];
        for (Graph.Vertex v : g) {
            node[v.getName()] = new Vertex(rewards.get(v), v);
        }
        maxReward = 0;
    }

    /**
     * This is the driver function which finds the maximumReward
     * @param optimumPath : The end result will be stored in this queue
     * @return : returns the maximum reward amount that can be collected
     */
    public int findMaxReward(List<Graph.Vertex> optimumPath) {
        dijkstra();
        resetSeen();
        path = new Graph.Vertex[g.size()];
        path[index++] = src;
        this.optimumPath = optimumPath;
        enumerateAllCycles(src, getVertex(src).reward, 0);
        return maxReward;
    }

    /**
     * Enumerates all (shortest cycle) paths from source to find the maximum reward that can
     * be collected.
     * @param currentVertex : vertex from which we have to start the next enumeration
     * @param reward : reward collected so far.
     * @param weight : distance travelled from the source.
     */
    private void enumerateAllCycles(Graph.Vertex currentVertex, int reward, int weight) {
        getVertex(currentVertex).seen = true;
        for (Graph.Edge edge : currentVertex) {
            Vertex otherEnd = getVertex(edge.otherEnd(currentVertex));
            //if this vertex is source
            if (otherEnd.graphVertex == this.src) {
                updateMaxReward(reward);
            }
            //if other vertex is not visited yet
            else if (!otherEnd.seen) {
                /* if this is the shortest path to the vertex,
                 * do all enumerations from the vertex
                 */
                if (weight + edge.getWeight() == otherEnd.distance) {
                    path[index++] = otherEnd.graphVertex;
                    enumerateAllCycles(otherEnd.graphVertex, reward + otherEnd.reward, weight + edge.getWeight());
                    index--;
                }
                /* if not the shortest path, then find path back to source
                 * only if reward is greater than current reward
                 */
                else if (reward > maxReward && isSourceReachable(otherEnd)) {
                    updateMaxReward(reward);
                    /* Reset all the vertex in the path that are after current Vertex, so that we can
                     * enumerate other possible simple cycles
                     */
                    while (path[index - 1] != currentVertex) {
                        getVertex(path[index - 1]).seen = false;
                        index--;
                    }
                }
                otherEnd.seen = false;
            }
        }

    }

    /**
     * Checks if there is a path from current vertex to source and updates the path array
     * if there is a path.
     * @param from : vertex from which the path is to be found
     * @return : true if there is a path
     */
    private boolean isSourceReachable(Vertex from) {
        boolean isReachable = false;
        for (Graph.Edge e : src) {
            if (!getVertex(e.otherEnd(src)).seen) {
                isReachable = true;
                break;
            }
        }
        if (isReachable) {
            isReachable = pathToSource(from);
        }
        return isReachable;
    }

    /**
     * finds a path from current vertex to source if there is one and stores it in path array.
     * @param from : the vertex from which the path to source is to be found.
     * @return : returns true if there is a path from current vertex to source.
     */
    private boolean pathToSource(Vertex from) {
        from.seen = true;
        path[index++] = from.graphVertex;
        for (Graph.Edge e : from.graphVertex) {
            Vertex otherEnd = getVertex(e.otherEnd(from.graphVertex));
            //if we have reached source or source is reachable from other end,return true
            if (otherEnd.graphVertex == this.src || (!otherEnd.seen && pathToSource(otherEnd))) {
                return true;
            }
        }
        index--;
        from.seen = false;
        return false;
    }

    /**
     * Updates the Max reward value and also updates the path to get max reward
     * @param reward : current reward, which will be compared with current Max reward
     */
    private void updateMaxReward(int reward) {
        if (reward > maxReward) {
            maxReward = reward;
            optimumPath.clear();
            for (int i = 0; i < index; i++) {
                optimumPath.add(path[i]);
            }
            optimumPath.add(this.src);
        }

    }

    /**
     * Resets the seen flag of all vertices
     */
    private void resetSeen() {
        for (Vertex v : node) {
            v.seen = false;
        }
    }

    /**
     * Relaxes the vertex
     * @param e edge
     * @param u vertex
     * @return : returns true if the vertex distance got updated
     */
    private boolean relax(Graph.Edge e, Graph.Vertex u) {
        Graph.Vertex v = e.otherEnd(u);
        if (getVertex(v).distance > getVertex(u).distance + e.getWeight()) {
            getVertex(v).distance = getVertex(u).distance + e.getWeight();
            return true;
        }
        return false;
    }

    /**
     * Finds shortest path to all vertices from the given source
     */
    private void dijkstra() {
        boolean changed;
        getVertex(src).distance = 0;
        PriorityQueue<Vertex> pq = new PriorityQueue<>(Comparator.comparingInt(x -> x.distance));
        pq.add(getVertex(src));
        while (!pq.isEmpty()) {
            //vertex with minimum distance from source, is removed from the priority queue
            Vertex u = pq.remove();
            u.seen = true;
            for (Graph.Edge edge : u.graphVertex) {
                Vertex v = getVertex(edge.otherEnd(u.graphVertex));
                if (!v.seen) {
                    changed = relax(edge, u.graphVertex);
                    if (changed) {
                        pq.remove(v);
                        pq.add(v);
                    }
                }
            }
        }
    }
}
