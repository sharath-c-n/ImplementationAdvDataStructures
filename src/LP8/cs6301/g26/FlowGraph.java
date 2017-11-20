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
        int height;
        boolean seen;
        List<FlowEdge> FAdj;
        List<FlowEdge> FRevAdj;

        public FlowVertex(Vertex u) {
            super(u);
            height = 0;
            seen = false;
            FAdj = new ArrayList<>();
            FRevAdj = new ArrayList<>();
        }

        @Override
        public Iterator<Edge> iterator() {
            return new FlowIterator(FAdj);
        }

        public Iterator<FlowEdge> residualItr() {
            return new FlowResidualItr(FAdj, FRevAdj);
        }
    }

    static class FlowEdge extends Edge {
        boolean isRevEdge;
        int capacity;
        int availableFlow;
        int backFlow;
        FlowEdge otherEdge;


        public FlowEdge(FlowVertex x1, FlowVertex x2, int weight, int capacity, boolean isRevEdge) {
            super(x1, x2, weight);
            availableFlow = this.capacity = capacity;
            backFlow = 0;
            this.isRevEdge = isRevEdge;
        }

        public int getAvailableFlow() {
            return availableFlow;
        }


        public boolean pushflow(int flow) {
            if (availableFlow < flow) {
                return false;
            } else {
                availableFlow -= flow;
                otherEdge.availableFlow += flow;
            }
            return true;
        }

        public FlowEdge getOtherEdge() {
            return otherEdge;
        }

        public void setOtherEdge(FlowEdge otherEdge) {
            this.otherEdge = otherEdge;
        }

        @Override
        public String toString() {
            return super.toString() + "::" + availableFlow + "::" + getWeight();
        }
    }

    private FlowVertex[] flowVertices;

    public FlowGraph(Graph g, HashMap<Edge, Integer> capacity, HashMap<Edge, Integer> cost) {
        super(g);
        flowVertices = new FlowVertex[g.size()];
        for (Vertex u : g) {
            flowVertices[u.getName()] = new FlowVertex(u);
        }

        for (Map.Entry<Edge, Integer> entry : cost.entrySet()) {
            Vertex u = entry.getKey().fromVertex();
            Vertex v = entry.getKey().toVertex();
            FlowVertex x1 = getVertex(u);
            FlowVertex x2 = getVertex(v);
            FlowEdge edge = new FlowEdge(x1, x2, entry.getValue(), capacity.get(entry.getKey()), false);
            FlowEdge resEdge = new FlowEdge(x2, x1, -entry.getValue(), 0, true);
            edge.setOtherEdge(resEdge);
            resEdge.setOtherEdge(edge);

            x1.FAdj.add(edge);
            x2.FAdj.add(resEdge);

            x1.FRevAdj.add(resEdge);
            x2.FRevAdj.add(edge);
        }
    }


    private FlowVertex getVertex(Vertex u) {
        return Vertex.getVertex(flowVertices, u);
    }

    public FlowVertex getVertex(int i) {
        return getVertex(super.getVertex(i));
    }

    public Vertex getOrgVertex(int i) {
        return super.getVertex(i);
    }

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

    static class FlowResidualItr implements Iterator<FlowEdge> {
        Iterator<FlowEdge> itr;
        Iterator<FlowEdge> revAdjItr;
        boolean switched = false;
        FlowEdge cursor = null;
        boolean ready = false;

        FlowResidualItr(List<FlowEdge> adj, List<FlowEdge> revAdj) {
            this.itr = adj.iterator();
            this.revAdjItr = revAdj.iterator();
        }

        public boolean hasNext() {
            if (ready)
                return true;
            if (!itr.hasNext()) {
                if (switched) {
                    return false;
                }
                switched = true;
                itr = revAdjItr;
                if (!itr.hasNext())
                    return false;
            }

            cursor = itr.next();
            if ((switched && cursor.backFlow == 0) || (!switched && cursor.availableFlow == 0)) {
                return hasNext();
            }
            ready = true;
            return true;
        }

        public FlowEdge next() {
            if (!ready && !hasNext()) {
                throw new NoSuchElementException();
            }
            ready = false;
            return cursor;
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

    public void printResidualGraph() {
        for (Vertex v : this) {
            for (Iterator<FlowEdge> it = ((FlowVertex) v).residualItr(); it.hasNext(); ) {
                Edge edge = it.next();
                System.out.println(edge);
            }

        }
    }
}
