package cs6301.g26;


import java.util.ArrayList;
import java.util.List;

import static cs6301.g26.LP3.printGraph;

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

    static public void toZeroWeightGraph(XGraph graph, XGraph.XVertex src) {
        src.disable();
        for (Graph.Vertex vertex : graph) {
            int min = Integer.MAX_VALUE;
            for (XGraph.Edge e : ((XGraph.XVertex) vertex).revXadj) {
                if (e.getWeight() < min) {
                    min = e.getWeight();
                }
            }
            for (Graph.Edge e : ((XGraph.XVertex) vertex).revXadj)
                e.setWeight(e.getWeight() - min);
        }
        src.enable();
        printEdges(graph);
    }

    public int findSpanningTree(List<Graph.Edge> edges) {
        toZeroWeightGraph(graph, source);
        shrinkComponents();
        toZeroWeightGraph(graph, source);
        printGraph(graph);
        return 0;
    }

    //Get a list of all Strongly connected component
    private List<List<XGraph.XVertex>> getComponents() {
        cc = new SCC(graph,source);
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
            //Don't process source
            if (component == source) {
                continue;
            }
            XGraph.XEdge minEdge = null;
            //Disable vertex and edges of the children and get minimum edge
            if (component.isComponent) {
                XGraph.XEdge adj;
                for (XGraph.XVertex vertex : component.children) {
                    adj = getMinIncomingEdges(vertex);
                    if (minEdge == null) {
                        minEdge = adj;
                    } else if (adj != null && adj.getWeight() < minEdge.getWeight()) {
                        minEdge.disabled = true;
                        minEdge = adj;
                    }
                    vertex.disable();
                }
            } else {
                minEdge = getMinIncomingEdges(component);
            }

            if (minEdge != null) {
                XGraph.XVertex fromVertex = componentVertices[getComponentNo(minEdge.from)];
                //Edge already there in original graph no need to add
                if (fromVertex == minEdge.from && component == minEdge.to) {
                    continue;
                }
                //Need to create a new vertex
                minEdge.disabled = true;
                minEdge = new XGraph.XEdge(fromVertex, component, minEdge.getWeight(), minEdge.original);
                component.revXadj.add(minEdge);
                fromVertex.XAdj.add(minEdge);
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
        Graph.Vertex gVertex = new Graph.Vertex(graph.getSize());
        XGraph.XVertex vertex = new XGraph.XVertex(gVertex);
        vertex.isComponent = true;
        vertex.children = component;
        graph.addVertex(vertex);
        return vertex;
    }

    private XGraph.XEdge getMinIncomingEdges(XGraph.XVertex vertex) {
        XGraph.XEdge adj = null;
        if (vertex.revXadj.size() > 0) {
            int cno = cc.getCCVertex(vertex).cno, otherCno;
            for (XGraph.XEdge edge : vertex.revXadj) {
                otherCno = cc.getCCVertex(edge.otherEnd(vertex)).cno;
                if (cno != otherCno) {
                    if (adj == null || adj.getWeight() > edge.getWeight()) {
                        if (adj != null) {
                            adj.disabled = true;
                        }
                        adj = edge;
                    } else {
                        edge.disabled = true;
                    }
                }
            }
        }
        return adj;
    }

    static public void printEdges(XGraph graph) {
        System.out.println("Printing Graph edges");
        for (Graph.Vertex vertex : graph) {
            for (XGraph.Edge e : vertex) {
                System.out.println(e);
            }
        }
        System.out.println("End of Printing Graph edges");
    }

}
