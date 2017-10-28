package cs6301.g26;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cs6301.g00.Graph;

/**
 * SpanningTree:
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
        scc = new SCC(this.graph, this.source);
    }

    private void toZeroWeightGraph(XGraph graph, XGraph.XVertex src) {
        src.disable();
        for (Graph.Vertex v : graph) {
            int min = Integer.MAX_VALUE;
            XGraph.XVertex vertex = (XGraph.XVertex) v;
            for (XGraph.Edge e : vertex.revXadj) {
                if (e.getWeight() < min) {
                    min = e.getWeight();
                }
            }
            for (Graph.Edge e : vertex.revXadj)
                e.setWeight(e.getWeight() - min);
        }
        src.enable();
    }

    public int findSpanningTree(List<Graph.Edge> edges) {
        BFSZeroEdge bfsZero = new BFSZeroEdge(graph, source);
        while (!bfsZero.isSpanningTree()) {
            toZeroWeightGraph(graph, source);
            shrinkComponents();
            bfsZero = new BFSZeroEdge(graph, source);
        }
        expand(source, null);
        graph.enableGraphVertices();
        return populateEdges(edges);
    }

    private void expand(XGraph.XVertex source, Graph.Edge edge) {
        if (source.seen) {
            return;
        }
        source.seen = true;
        XGraph.XEdge xEdge = (XGraph.XEdge) edge;
        if (source.isComponent) {
            //Expand the component which edge.to vertex as root
            expand(graph.getVertex(xEdge.original.toVertex()), xEdge.original);
        } else if (edge != null) {
            source.stEdge = xEdge.original;
        }
        //explore other components
        for (Graph.Edge e : source) {
            expand(graph.getVertex(e.toVertex()), e);
        }
    }

    private int populateEdges(List<Graph.Edge> edges) {
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

    //Get a list of all Strongly connected component
    private List<List<XGraph.XVertex>> getComponents() {
        scc = new SCC(graph, source);
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

    //Create components and disable the contents of the component
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
                XGraph.XVertex toVertex = componentVertices[getComponentNo(minEdge.toVertex())];
                //Edge already there in original graph no need to add
                if (toVertex == minEdge.toVertex() && component == minEdge.fromVertex()) {
                    continue;
                }
                //Need to create a new edge
                minEdge.disabled = true;
                //Avoid loopback
                if (toVertex.isComponent && toVertex != minEdge.toVertex()) {
                    minEdge = new XGraph.XEdge(component, toVertex, minEdge.getWeight(), minEdge);
                } else {
                    minEdge = new XGraph.XEdge(component, toVertex, minEdge.getWeight(), minEdge.original);
                }
                component.xAdj.add(minEdge);
                toVertex.revXadj.add(minEdge);
            }
        }
    }

    private int getComponentNo(Graph.Vertex v) {
        return scc.getCCVertex(v).cno - 1;
    }

    private void getMinEdges(XGraph.XVertex vertex, HashMap<Integer, XGraph.XEdge> minEdges) {
        for (Graph.Edge edge : vertex.getNonZeroItr()) {
            int cno = getComponentNo(vertex), otherCno;
            otherCno = getComponentNo(edge.otherEnd(vertex));
            if (cno != otherCno) {
                XGraph.XEdge adj = minEdges.get(otherCno);
                if (adj == null || adj.getWeight() > edge.getWeight()) {
                    if (adj != null) {
                        adj.disabled = true;
                    }
                    minEdges.put(otherCno, (XGraph.XEdge) edge);
                } else {
                    ((XGraph.XEdge) edge).disabled = true;
                }
            }
        }

    }
}
