
/**
 * @author rbk
 * Ver 1.0: 2017/09/29
 * Example to extend Graph/Vertex/Edge classes to implement algorithms in which nodes and edges
 * need to be disabled during execution.  Design goal: be able to call other graph algorithms
 * without changing their codes to account for disabled elements.
 * <p>
 * Ver 1.1: 2017/10/09
 * Updated iterator with boolean field ready. Previously, if hasNext() is called multiple
 * times, then cursor keeps moving forward, even though the elements were not accessed
 * by next().  Also, if program calls next() multiple times, without calling hasNext()
 * in between, same element is returned.  Added UnsupportedOperationException to remove.
 *
 * @author Sharath
 * Ver 2.0 2017/10/26
 * Updated iterators to iterate over zero edges and skip disabled edges/verices. Added new fields in vertex
 * to store reverse Edges , to indicate if the vertex is seen, is a component and couple of iterator to iterate over
 * edges. In edge class added a field to store original edge reference.
 **/

package cs6301.g26;

import cs6301.g00.ArrayIterator;
import cs6301.g00.Graph;

import java.util.*;


public class XGraph extends Graph {

    static public class XVertex extends Vertex {
        /**
         * Used to disable the vertex
         */
        private boolean disabled;
        /**
         * Used to identify if this vertex is a result of union of vertices
         */
        private boolean isComponent;
        /**
         * Used while doing BFS/DFS or while running other algorithms,
         * need not be private as this doesn't affect graph state
         */
        boolean seen;
        /**
         * List of outgoing edges
         */
        private List<XEdge> xAdj;
        /**
         * List of incoming edges
         */
        private List<XEdge> revXadj;
        /**
         * Used to store the Spanning tree incoming edge to this vertex
         * need not be private as it doesn't affect graph state
         */
        Graph.Edge stEdge;

        public XVertex(Vertex u) {
            super(u);
            disabled = false;
            isComponent = false;
            xAdj = new LinkedList<>();
            revXadj = new LinkedList<>();
            seen = false;
            stEdge = null;
        }

        public boolean isDisabled() {
            return disabled;
        }

        public void disable() {
            disabled = true;
        }

        public void enable() {
            disabled = false;
        }

        /**
         * Iterator to iterate over only the zero weighted outgoing edges
         * @return : iterator
         */
        @Override
        public Iterator<Edge> iterator() {
            return new XZeroEdgeIterator(this.xAdj);
        }

        /**
         * Used to iterate over zero weighted incoming edges
         * @return : iterator
         */
        public Iterable<Edge> getRevEdgeItr() {
            return () -> new XZeroEdgeIterator(this.revXadj);
        }

        /**
         * Used to iterate over all outgoing edges which are not disabled, note that even zero weighted
         * edges will be included
         * @return : iterator
         */
        public Iterable<Edge> getNonZeroItr() {
            return () -> new NonZeroItrator(this.xAdj);
        }
        /**
         * Used to iterate over all incoming edges which are not disabled, note that even zero weighted
         * edges will be included
         * @return : iterator
         */
        public Iterable<Edge> getNonZeroRevItr() {
            return () -> new NonZeroItrator(this.revXadj);
        }

        public void addEdge(XEdge edge){
            xAdj.add(edge);
        }

        public void addRevEdge(XEdge edge){
            revXadj.add(edge);
        }

        public boolean isComponent(){
            return isComponent;
        }
    }

    static class XEdge extends Edge {
        /**
         * Used to enable or disable edge
         */
        private boolean disabled;
        /**
         * Used to store the original edge to which this edge corresponds to
         */
        private Edge original;

        XEdge(XVertex from, XVertex to, int weight, Edge edge) {
            super(from, to, weight);
            disabled = false;
            original = edge;
        }

        boolean isDisabled() {
            return disabled;
        }

        public void disable() {
            this.disabled = true;
        }

        public Edge getOriginal() {
            return original;
        }

    }


    private XVertex[] xv; // vertices of graph
    private int xVertexSize = 0;

    public XGraph(Graph g) {
        super(g);
        xv = new XVertex[2 * g.size()];  // Extra space is allocated in array for nodes to be added later
        for (Vertex u : g) {
            xv[u.getName()] = new XVertex(u);
        }

        // Make copy of edges
        for (Vertex u : g) {
            for (Edge e : u) {
                Vertex v = e.otherEnd(u);
                XVertex x1 = getVertex(u);
                XVertex x2 = getVertex(v);
                XEdge edge = new XEdge(x1, x2, e.getWeight(), e);
                x1.xAdj.add(edge);
                x2.revXadj.add(edge);
            }
        }
        xVertexSize = super.size();
    }

    @Override
    public Iterator<Vertex> iterator() {
        return new XGraphIterator(this);
    }

    class XGraphIterator implements Iterator<Vertex> {
        Iterator<XVertex> it;
        XVertex xcur;
        boolean ready;

        XGraphIterator(XGraph xg) {
            // Iterate over existing elements only
            this.it = new ArrayIterator<>(xg.xv, 0, size() - 1);
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

        public Vertex next() {
            if (!ready) {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
            }
            ready = false;
            return xcur;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

    }

    static class XZeroEdgeIterator implements Iterator<Edge> {
        Iterator<XEdge> it;
        XEdge xcur;
        boolean ready;

        XZeroEdgeIterator(List<XEdge> vertex) {
            this.it = vertex.iterator();
        }


        public boolean hasNext() {
            if (!it.hasNext()) {
                return false;
            }
            xcur = it.next();
            while ((xcur.getWeight() != 0 || xcur.isDisabled()) && it.hasNext()) {
                xcur = it.next();
            }
            ready = true;
            return xcur.getWeight() == 0 && !xcur.isDisabled();
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

    static class NonZeroItrator implements Iterator<Edge> {
        Iterator<XEdge> it;
        XEdge xcur;
        boolean ready;

        NonZeroItrator(List<XEdge> vertex) {
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

    @Override
    public Vertex getVertex(int n) {
        return xv[n - 1];
    }

    XVertex getVertex(Vertex u) {
        return Vertex.getVertex(xv, u);
    }

    public boolean addVertex(XVertex vertex) {
        if (xVertexSize >= 2 * super.size()) {
            return false;
        }
        xv[xVertexSize++] = vertex;
        return true;
    }

    public XVertex getNewComponent(){
        Vertex gVertex = new Graph.Vertex(xVertexSize);
        XGraph.XVertex vertex = new XGraph.XVertex(gVertex);
        vertex.isComponent = true;
        addVertex(vertex);
        return vertex;
    }

    public int size() {
        return xVertexSize;
    }

    public void enableGraphVertices() {
        int count = 0;
        for (XVertex v : xv) {
            if (count == xVertexSize) {
                break;
            }
            count++;
            v.disabled = v.isComponent;
        }
    }
}