package cs6301.g26;

import cs6301.g00.Graph;

import java.util.*;

/**
 * RewardCollection: finds the maximum rewards that can be collected from a given source and the following
 * constrains
 * 1)find a traversal that starts and ends at source.
 * 2)not allowed to visit any intermediate node more than once.
 * 3)The reward at a node can be collected only if the traversal got there using a
 * shortest path from source to that node.
 * 4)Graph will be undirected
 *
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
         * Needed for bellmanFord
         */
        int count;

        public Vertex(int reward) {
            this.reward = reward;
            distance = Integer.MAX_VALUE;
            seen = false;
        }
    }

    public RewardCollection(Graph g, Graph.Vertex src, Map<Graph.Vertex, Integer> rewards) {
        super(g);
        this.src = src;
        node = new Vertex[g.size()];
        for (Graph.Vertex v : g) {
            node[v.getName()] = new Vertex(rewards.get(v));
        }
        maxReward = 0;
    }

    /**
     * This is the driver function which finds the maximumReward
     *
     * @param optimumPath : The end result will be stored in this queue
     * @return : returns the maximum reward amount that can be collected
     */
    public int findMaxReward(List<Graph.Vertex> optimumPath) {
        if (!bellmanFordTake3()) {
            //Negative cycles exists hence can't find shortest paths
            return -1;
        }
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
     *
     * @param currentVertex : vertex from which we have to start the next enumeration
     * @param reward        : reward collected so far.
     * @param weight        : distance travelled from the source.
     */
    private void enumerateAllCycles(Graph.Vertex currentVertex, int reward, int weight) {
        getVertex(currentVertex).seen = true;
        for (Graph.Edge edge : currentVertex) {
            Graph.Vertex toVertex = edge.otherEnd(currentVertex);
            Vertex otherEnd = getVertex(toVertex);
            //if this vertex is source
            if (toVertex == this.src) {
                updateMaxReward(reward);
            }
            //if other vertex is not visited yet
            else if (!otherEnd.seen) {
                /* if this is the shortest path to the vertex,
                 * do all enumerations from the vertex
                 */
                if (weight + edge.getWeight() == otherEnd.distance) {
                    path[index++] = toVertex;
                    enumerateAllCycles(toVertex, reward + otherEnd.reward, weight + edge.getWeight());
                    index--;
                }
                /* if not the shortest path, then find path back to source
                 * only if reward is greater than current reward
                 */
                else if (reward > maxReward && isSourceReachable(toVertex)) {
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
     *
     * @param from : vertex from which the path is to be found
     * @return : true if there is a path
     */
    private boolean isSourceReachable(Graph.Vertex from) {
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
     *
     * @param from : the vertex from which the path to source is to be found.
     * @return : returns true if there is a path from current vertex to source.
     */
    private boolean pathToSource(Graph.Vertex from) {
        Vertex fromVertex = getVertex(from);
        fromVertex.seen = true;
        path[index++] = from;
        for (Graph.Edge e : from) {
            Graph.Vertex otherEnd = e.otherEnd(from);
            //if we have reached source or source is reachable from other end,return true
            if (otherEnd == this.src || (!getVertex(otherEnd).seen && pathToSource(otherEnd))) {
                return true;
            }
        }
        index--;
        fromVertex.seen = false;
        return false;
    }

    /**
     * Updates the Max reward value and also updates the path to get max reward
     *
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
     * Finds shortest path to all vertices from the given source
     */
    private boolean bellmanFordTake3() {
        getVertex(src).distance = 0;
        Queue<Graph.Vertex> q = new LinkedList<>();
        q.add(src);
        while (!q.isEmpty()) {
            Graph.Vertex u = q.poll();
            Vertex fromVertex = getVertex(u);
            fromVertex.seen = false;
            fromVertex.count = getVertex(u).count + 1;
            if (fromVertex.count >= g.size()) return false;
            for (Graph.Edge e : u.adj) {
                Vertex toVertex = getVertex(e.toVertex());
                if ((fromVertex.distance != Integer.MAX_VALUE) && (toVertex.distance > (fromVertex.distance + e.getWeight()))) {
                    toVertex.distance = fromVertex.distance + e.getWeight();
                    if (!toVertex.seen) {
                        q.add(e.toVertex());
                        toVertex.seen = true;
                    }
                }
            }
        }
        return true;
    }
}
