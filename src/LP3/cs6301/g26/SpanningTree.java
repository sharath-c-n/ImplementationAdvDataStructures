package cs6301.g26;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cs6301.g00.Graph;

/**
 * SpanningTree:
 *
 * @author : Sharath
 * 23/10/2017
 */
public class SpanningTree {
    private SCC cc;
    private XGraph.XVertex source;
    private XGraph graph;

    SpanningTree(XGraph graph, XGraph.XVertex source) {
        this.graph = graph;
        cc = new SCC(graph, source);
        this.source = source;
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
        graph.enableAll();
        return populateEdges(edges);
    }

    private void expand(XGraph.XVertex source, Graph.Edge edge) {
        XGraph.XEdge xEdge = (XGraph.XEdge) edge;
        if (source.isComponent) {
            //Expand the component which edge.to vertex as root
            expand(graph.getVertex(xEdge.original.toVertex()), xEdge.original);
        } else if (edge != null) {
            //If vertex not visited
            if (source.stEdge == null) {
                source.stEdge = xEdge.original;
            } else {
                //Return if already explored
                return;
            }
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
        cc = new SCC(graph, source);
        int componentCount = cc.findSSC();
        List<List<XGraph.XVertex>> components = new ArrayList<>();
        for (int i = 0; i < componentCount; i++) {
            components.add(new ArrayList<>());
        }
        for (Graph.Vertex vertex : graph) {
            CC.CCVertex component = cc.getCCVertex(vertex);
            components.get(component.cno - 1).add((XGraph.XVertex) vertex);
        }
        return components;
    }

    //Create components and disable the contents of the component
    private void shrinkComponents() {
        List<List<XGraph.XVertex>> components = getComponents();
        XGraph.XVertex componentVertices[] = new XGraph.XVertex[components.size()];

        int index = 0;
        //create component
        for (List<XGraph.XVertex> component : components) {
            componentVertices[index++] = createComponent(component);

        }
        //Add edges
        for (XGraph.XVertex component : componentVertices) {
            //Disable vertex and edges of the children and get minimum edge
            HashMap<Integer, XGraph.XEdge> minEdges = new HashMap<>();
            if (component.isComponent && component.XAdj.size() == 0) {
                for (XGraph.XVertex vertex : component.children) {
                    getMinIncomingEdges(vertex, minEdges);
                    vertex.disable();
                }
            } else {
                getMinIncomingEdges(component, minEdges);
            }
            for (Map.Entry<Integer, XGraph.XEdge> edgeEntry : minEdges.entrySet()) {
                XGraph.XEdge minEdge = edgeEntry.getValue();
                XGraph.XVertex toVertex = componentVertices[getComponentNo(minEdge.toVertex())];
                //Edge already there in original graph no need to add
                if (toVertex == minEdge.toVertex() && component == minEdge.fromVertex()) {
                    continue;
                }
                //Need to create a new vertex
                minEdge.disabled = true;
                //Avoid loopback
                if (toVertex.isComponent && toVertex != minEdge.toVertex()) {
                    minEdge = new XGraph.XEdge(component, toVertex, minEdge.getWeight(), minEdge);
                } else {
                    minEdge = new XGraph.XEdge(component, toVertex, minEdge.getWeight(), minEdge.original);
                }
                component.XAdj.add(minEdge);
                toVertex.revXadj.add(minEdge);
            }
        }
    }

    private int getComponentNo(Graph.Vertex v) {
        return cc.getCCVertex(v).cno - 1;
    }

    private XGraph.XVertex createComponent(List<XGraph.XVertex> component) {
        if (component.size() == 1) {
            return component.get(0);
        }
        Graph.Vertex gVertex = new Graph.Vertex(graph.size());
        XGraph.XVertex vertex = new XGraph.XVertex(gVertex);
        vertex.isComponent = true;
        vertex.children = component;
        graph.addVertex(vertex);
        return vertex;
    }

    private void getMinIncomingEdges(XGraph.XVertex vertex, HashMap<Integer, XGraph.XEdge> minEdges) {
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
                    ((XGraph.XEdge)edge).disabled = true;
                }
            }
        }

    }
}
