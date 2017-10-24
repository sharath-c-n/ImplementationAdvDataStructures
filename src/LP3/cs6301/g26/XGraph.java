
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
 **/

package cs6301.g26;

import cs6301.g00.ArrayIterator;

import java.util.*;


public class XGraph extends Graph {

    public int getSize() {
        return xVertexSize;
    }

    static public class XVertex extends Vertex {
        boolean disabled;
        boolean isComponent;
        List<XVertex> children;
        List<XEdge> XAdj;
        List<XEdge> revXadj;

        XVertex(Vertex u) {
            super(u);
            disabled = false;
            isComponent = false;
            XAdj = new LinkedList<>();
            revXadj = new LinkedList<>();
        }

        boolean isDisabled() {
            return disabled;
        }

        void disable() {
            disabled = true;
        }

        void enable() {
            disabled = false;
        }

        @Override
        public Iterator<Edge> iterator() {
            return new XZeroEdgeIterator(this);
        }

        public Iterator<Edge> nonZeroIterator() {
            return new XZeroEdgeIterator(this);
        }


        class XVertexIterator implements Iterator<Edge> {
            XEdge cur;
            Iterator<XEdge> it;
            boolean ready;

            XVertexIterator(XVertex u) {
                this.it = u.XAdj.iterator();
                ready = false;
            }

            public boolean hasNext() {
                if (ready) {
                    return true;
                }
                if (!it.hasNext()) {
                    return false;
                }
                cur = it.next();
                while (cur.isDisabled() && it.hasNext()) {
                    cur = it.next();
                }
                ready = true;
                return !cur.isDisabled();
            }

            public Edge next() {
                if (!ready) {
                    if (!hasNext()) {
                        throw new NoSuchElementException();
                    }
                }
                ready = false;
                return cur;
            }

            public void remove() {
                throw new UnsupportedOperationException();
            }
        }

    }

    static class XEdge extends Edge implements Comparable<Edge> {
        boolean disabled;
        Edge original;

        XEdge(XVertex from, XVertex to, int weight, Edge edge) {
            super(from, to, weight);
            disabled = false;
            original = edge;
        }

        boolean isDisabled() {
            XVertex xfrom = (XVertex) from;
            XVertex xto = (XVertex) to;
            return disabled || xfrom.isDisabled() || xto.isDisabled();
        }

        @Override
        public int compareTo(Graph.Edge o) {
            int result = getWeight() - o.getWeight();
            if(result == 0)
                return 0;
            return result > 0 ? 1:-1;
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
                XEdge edge= new XEdge(x1, x2, e.weight, e);
                x1.XAdj.add(edge);
                x2.revXadj.add(edge);
            }
        }
        xVertexSize = n;
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
            this.it = new ArrayIterator<>(xg.xv, 0, xg.size() - 1);  // Iterate over existing elements only
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

        XZeroEdgeIterator(XVertex vertex) {
            this.it = vertex.XAdj.iterator();
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

    @Override
    public Vertex getVertex(int n) {
        return xv[n - 1];
    }

    XVertex getVertex(Vertex u) {
        return Vertex.getVertex(xv, u);
    }

    public boolean addVertex(XVertex vertex){
        if(xVertexSize >= 2*n){
            return false;
        }
        xv[xVertexSize++] = vertex;
        return true;
    }

    void disable(int i) {
        XVertex u = (XVertex) getVertex(i);
        u.disable();
    }
}