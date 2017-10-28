package cs6301.g26;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cs6301.g00.Graph;

/**
 * SpanningTree: Finds A Spanning tree for a directed non negative weighted graph.
 * @author : Sharath
 * 23/10/2017
 */
public class SpanningTree {
    private SCC scc;
    private XGraph.XVertex source;
    private XGraph graph;

    SpanningTree(Graph graph, Graph.Vertex source) {
        this.graph = new XGraph(graph);
        this.source = this.graph.getVertex(source);
    }

    /**
     * This is the main function which will generate the spanning tree for the input graph
     * @param edges : this list will contains the edges of the spanning tree at the end of run.
     * @return : weight of the spanning tree
     */
    public int findSpanningTree(List<Graph.Edge> edges) {
        while (!isSpanningTree()) {
            toZeroWeightGraph();
            shrinkComponents();
        }
        return populateEdges(edges);
    }

    /**
     * Checks if the current graph has a spanning tree of zero edges
     * @return : true is there is a spanning tree of zero edges else false.
     */
    private boolean isSpanningTree() {
        BFSZeroEdge bfsZero = new BFSZeroEdge(graph, source);
        return bfsZero.isSpanningTree();
    }

    /**
     * This will reduce the weight of the incoming edges of every vertex by the weight of the edge with
     * minimum weight of all the incoming edges to that particular vertex.
     */
    private void toZeroWeightGraph() {
        source.disable();
        for (Graph.Vertex v : graph) {
            int min = Integer.MAX_VALUE;
            XGraph.XVertex vertex = (XGraph.XVertex) v;
            for (XGraph.Edge e : vertex.getNonZeroRevItr()) {
                if (e.getWeight() < min) {
                    min = e.getWeight();
                }
            }
            for (Graph.Edge e : vertex.getNonZeroRevItr())
                e.setWeight(e.getWeight() - min);
        }
        source.enable();
    }

    /**
     * Creates components and disables the contents of the components.
     */
    private void shrinkComponents() {
        List<List<XGraph.XVertex>> components = getComponents();
        XGraph.XVertex componentVertices[] = new XGraph.XVertex[components.size()];

        int index = 0;
        //create or assign component
        for (List<XGraph.XVertex> component : components) {
            componentVertices[index++] = component.size() == 1 ? component.get(0) :
                    graph.getNewComponent();
        }
        index = 0;
        //Add edges
        for (XGraph.XVertex component : componentVertices) {
            //Disable vertex and edges of the children and get minimum edge
            HashMap<Integer, XGraph.XEdge> minEdges = new HashMap<>();
            //Process all child vertices only if this is a new component
            if (components.get(index).size() > 1) {
                for (XGraph.XVertex vertex : components.get(index++)) {
                    getMinEdges(vertex, minEdges);
                    vertex.disable();
                }
            } else {
                index++;
                getMinEdges(component, minEdges);
            }
            for (Map.Entry<Integer, XGraph.XEdge> edgeEntry : minEdges.entrySet()) {
                XGraph.XEdge minEdge = edgeEntry.getValue();
                XGraph.XVertex toVertex = componentVertices[scc.getComponentNo(minEdge.toVertex())];
                //Edge already there in original graph no need to add
                if (toVertex == minEdge.toVertex() && component == minEdge.fromVertex()) {
                    continue;
                }
                //Need to create a new edge
                minEdge.disable();
                //Avoid loopback
                if (toVertex.isComponent() && toVertex != minEdge.toVertex()) {
                    minEdge = new XGraph.XEdge(component, toVertex, minEdge.getWeight(), minEdge);
                } else {
                    minEdge = new XGraph.XEdge(component, toVertex, minEdge.getWeight(), minEdge.getOriginal());
                }
                component.addEdge(minEdge);
                toVertex.addRevEdge(minEdge);
            }
        }
    }

    /**
     * This will generate the MST edges list and also calculates the total MST weight
     * @param edges : edges list
     * @return : weigh of the MST
     */
    private int populateEdges(List<Graph.Edge> edges) {
        expand(source, null);
        graph.enableGraphVertices();
        int weight = 0;
        for (Graph.Vertex v : graph) {

            Graph.Edge edge = ((XGraph.XVertex) v).stEdge;
            if (edge != null) {
                weight += edge.getWeight();
            }
            edges.add(edge);
        }
        return weight;
    }

    /**
     * Expands each components and finds the MST in them.
     * @param source : root of the MST to be found in the component
     * @param edge   : incoming edge to the vertex
     */
    private void expand(XGraph.XVertex source, Graph.Edge edge) {
        source.seen = true;
        XGraph.XEdge xEdge = (XGraph.XEdge) edge;
        if (source.isComponent()) {
            //Expand the component, with original edge's end vertex as root
            expand(graph.getVertex(xEdge.getOriginal().toVertex()), xEdge.getOriginal());
        } else if (edge != null) {
            source.stEdge = xEdge.getOriginal();
        }
        //explore other components
        for (Graph.Edge e : source) {
            if (!graph.getVertex(e.toVertex()).seen) {
                expand(graph.getVertex(e.toVertex()), e);
            }
        }
    }


    /**
     * Creates a consolidated list of all SCC
     * @return :list of all Strongly connected component
     */
    private List<List<XGraph.XVertex>> getComponents() {
        scc = new SCC(graph);
        int componentCount = scc.findSSC();
        List<List<XGraph.XVertex>> components = new ArrayList<>();
        for (int i = 0; i < componentCount; i++) {
            components.add(new ArrayList<>());
        }
        for (Graph.Vertex vertex : graph) {
            CC.CCVertex component = scc.getCCVertex(vertex);
            components.get(component.cno - 1).add((XGraph.XVertex) vertex);
        }
        return components;
    }


    /**
     * Returns a list of all minimum outgoing edges out of all the children in the component
     * @param vertex   : child of the component
     * @param minEdges : list of edges
     */
    private void getMinEdges(XGraph.XVertex vertex, HashMap<Integer, XGraph.XEdge> minEdges) {
        for (Graph.Edge edge : vertex.getNonZeroItr()) {
            int cno = scc.getComponentNo(vertex), otherCno;
            otherCno = scc.getComponentNo(edge.otherEnd(vertex));
            if (cno != otherCno) {
                XGraph.XEdge adj = minEdges.get(otherCno);
                if (adj == null || adj.getWeight() > edge.getWeight()) {
                    if (adj != null) {
                        adj.disable();
                    }
                    minEdges.put(otherCno, (XGraph.XEdge) edge);
                } else {
                    ((XGraph.XEdge) edge).disable();
                }
            }
        }

    }
}
