package cs6301.g26;

import cs6301.g00.ArrayIterator;
import cs6301.g00.Graph;

import java.util.*;


/**
 * FlowGraph:
 *
 * @author : Sharath
 * 19/11/2017
 */
public class FlowGraph extends Graph {

    static class FlowVertex extends Vertex {
        /**
         * This variable is used in various flow algorithms to decide flow conditions
         */
        int height;
        /**
         * This variable is used by standard graph algorithms
         */
        boolean seen;
        /**
         * This variable is used by standard graph algorithms
         */
        FlowVertex parent;
        /**
         * This variable is used by standard graph algorithms
         */
        int distance;

        /**
         * List of forward edges from this vertex
         */
        private List<FlowEdge> FAdj;


        /**
         * Contains the excess flow value for this vertex
         */
        private int excess;

        public FlowVertex(Vertex u) {
            super(u);
            height = 0;
            seen = false;
            FAdj = new ArrayList<>();
            parent = null;
        }

        /**
         * This default iterator skips over edges with 0 or less capacity
         *
         * @return
         */
        @Override
        public Iterator<Edge> iterator() {
            return new FlowIterator(FAdj);
        }

        /**
         * This iterator provides a way to iterate over original graph edges
         *
         * @return
         */
        public Iterable<Edge> originalGraphItr() {
            return () -> new OriginalGraphItr(FAdj);
        }

        /**
         * This function returns the excess flow into the vertex, which are not drained.
         *
         * @return excess flow into vertex
         */
        public int getExcess() {
            return excess;
        }

        /**
         * Updates the excess flow for this vertex
         *
         * @param i : amount of flow that is flowing in or flowing out
         */
        public void addExcess(int i) {
            excess += i;
        }

        /**
         * This function returns the outflow of this vertex.
         *
         * @return out flow of vertex
         */
        public int getOutFlow() {
            int outFlow = 0;
            for (FlowEdge e : FAdj) {
                outFlow += e.getFlow();
            }
            return outFlow;
        }

        public int getFlow(FlowVertex v) {
            for (FlowEdge e : FAdj) {
                //ignore the residual edge,since residual edges have a capacity of 0
                if (e.toVertex() == v && e.capacity > 0)
                    return e.getFlow();
            }
            return 0;
        }
    }

    static class FlowEdge extends Edge {
        /**
         * Indicates the capacity of the edge
         */
        private int capacity;
        /**
         * holds the available flow
         */
        private int availableFlow;
        /**
         * corresponding back edge
         */
        private FlowEdge otherEdge;


        public FlowEdge(FlowVertex x1, FlowVertex x2, int weight, int capacity) {
            super(x1, x2, weight);
            this.capacity = capacity;
            if (capacity > 0) {
                availableFlow = capacity;
            }
        }

        public int getAvailableFlow() {
            return availableFlow;
        }


        /**
         * Updates the flow on this edge and the reverse edge
         *
         * @param flow : how much more flow should happen on this edge
         * @return : available capacity of the edge
         */
        public boolean pushFlow(int flow) {
            if (availableFlow < flow) {
                return false;
            }
            availableFlow -= flow;
            otherEdge.availableFlow += flow;
            ((FlowVertex) fromVertex()).addExcess(-flow);
            ((FlowVertex) toVertex()).addExcess(flow);
            return true;
        }

        public FlowEdge getOtherEdge() {
            return otherEdge;
        }

        public void setOtherEdge(FlowEdge otherEdge) {
            this.otherEdge = otherEdge;
        }

        public int getFlow() {
            return capacity - availableFlow;
        }

        @Override
        public String toString() {
            return super.toString() + "::" + capacity + "::" + getWeight();
        }
    }

    private FlowVertex[] flowVertices;

    public FlowGraph(Graph g, HashMap<Edge, Integer> capacity) {
        super(g);
        flowVertices = new FlowVertex[g.size()];
        for (Vertex u : g) {
            flowVertices[u.getName()] = new FlowVertex(u);
        }

        for (Map.Entry<Edge, Integer> entry : capacity.entrySet()) {
            Vertex u = entry.getKey().fromVertex();
            Vertex v = entry.getKey().toVertex();
            FlowVertex x1 = getVertex(u);
            FlowVertex x2 = getVertex(v);
            FlowEdge edge = new FlowEdge(x1, x2, entry.getKey().getWeight(), capacity.get(entry.getKey()));
            FlowEdge resEdge = new FlowEdge(x2, x1, entry.getKey().getWeight(), 0);
            edge.setOtherEdge(resEdge);
            resEdge.setOtherEdge(edge);

            x1.FAdj.add(edge);
            x2.FAdj.add(resEdge);
        }
    }

    public FlowVertex getVertex(Vertex u) {
        return Vertex.getVertex(flowVertices, u);
    }

    public FlowVertex getVertex(int i) {
        return getVertex(super.getVertex(i));
    }

    /**
     * This iterator iterates over all the edges of the vertices skipping 0 edges
     */
    static class FlowIterator implements Iterator<Edge> {
        Iterator<FlowEdge> it;
        FlowEdge xcur;
        boolean ready;

        FlowIterator(List<FlowEdge> vertex) {
            this.it = vertex.iterator();
        }

        public boolean hasNext() {
            if (!it.hasNext()) {
                return false;
            }
            xcur = it.next();
            while (xcur.getAvailableFlow() == 0 && it.hasNext()) {
                xcur = it.next();
            }
            ready = true;
            return xcur.getAvailableFlow() != 0;
        }

        public Edge next() {
            if (!ready) {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
            }
            ready = false;
            return xcur;
        }

        public void remove() {
        }
    }

    /**
     * This iterator iterates over the original graph edges of the vertices skipping residual  edges
     */
    static class OriginalGraphItr implements Iterator<Edge> {
        Iterator<FlowEdge> it;
        FlowEdge xcur;
        boolean ready;

        OriginalGraphItr(List<FlowEdge> vertex) {
            this.it = vertex.iterator();
        }

        public boolean hasNext() {
            if (!it.hasNext()) {
                return false;
            }
            xcur = it.next();
            while (xcur.capacity == 0 && it.hasNext()) {
                xcur = it.next();
            }
            ready = true;
            return xcur.capacity != 0;
        }

        public Edge next() {
            if (!ready) {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
            }
            ready = false;
            return xcur;
        }

        public void remove() {
        }
    }

    public Iterator<Vertex> iterator() {
        return new ArrayIterator<>(flowVertices, 0, size() - 1);
    }


    public void printGraph() {
        for (Vertex v : this) {
            for (Edge edge : v) {
                System.out.println(edge);
            }

        }
    }

    public void resetSeen() {
        for (FlowVertex vertex : flowVertices) {
            vertex.seen = false;
        }
    }

    public void resetAll(int distance) {
        for (FlowVertex vertex : flowVertices) {
            vertex.seen = false;
            vertex.distance = distance;
            vertex.parent = null;
        }
    }

}
