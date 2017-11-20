package cs6301.g26;

import cs6301.g00.ArrayIterator;
import cs6301.g00.Graph;

import java.util.*;

/**
 * FlowGraph:
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
            return new FlowItrator(FAdj);
        }
    }

    static class FlowEdge extends Edge {
        int cost;
        int capacity;
        int flow;
        int backFlow;
        boolean isDisabled;


        public FlowEdge(FlowVertex x1, FlowVertex x2, int weight,int capacity) {
            super(x1, x2, weight);
            this.capacity = capacity;
            backFlow = 0;
            isDisabled = false;
        }

        public int getCost() {
            return cost;
        }

        public int getFlow() {
            return flow;
        }

        public boolean isDisabled() {
            return isDisabled;
        }

        public void disable() {
            this.isDisabled = true;
        }

        public void enable() {
            this.isDisabled = false;
        }

    }
    private FlowVertex[] flowVertices;
    public FlowGraph(Graph g,HashMap<Edge, Integer> capacity,HashMap<Edge, Integer> cost) {
        super(g);
        flowVertices = new FlowVertex[g.size()];
        for (Vertex u : g) {
            flowVertices[u.getName()] = new FlowVertex(u);
        }

        for(Map.Entry<Edge,Integer> entry : cost.entrySet()){
            Vertex u = entry.getKey().fromVertex();
            Vertex v = entry.getKey().toVertex();
            FlowVertex x1 = getVertex(u);
            FlowVertex x2 = getVertex(v);
            FlowEdge edge = new FlowEdge(x1, x2, entry.getValue(),capacity.get(entry.getKey()));
            x1.FAdj.add(edge);
            x2.FRevAdj.add(edge);
        }
    }

    public void setCost(HashMap<Edge, Integer> cost){

    }
    private FlowVertex getVertex(Vertex u) {
        return Vertex.getVertex(flowVertices,u);
    }

    static class FlowItrator implements Iterator<Edge> {
        Iterator<FlowEdge> it;
        FlowEdge xcur;
        boolean ready;

        FlowItrator(List<FlowEdge> vertex) {
            this.it = vertex.iterator();
        }


        public boolean hasNext() {
            if (!it.hasNext()) {
                return false;
            }
            xcur = it.next();
            while (xcur.isDisabled() && it.hasNext()) {
                xcur = it.next();
            }
            ready = true;
            return !xcur.isDisabled();
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
}
