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
    private CC cc;
    private XGraph.XVertex source;
    private XGraph graph;

    SpanningTree(XGraph graph, XGraph.XVertex source) {
        this.graph = graph;
        cc = new CC(graph);
        this.source = source;
    }

    static public void toZeroWeightGraph(XGraph graph, XGraph.XVertex src) {
        src.disable();
        for (Graph.Vertex vertex : graph) {
            int min = Integer.MAX_VALUE;
            for (XGraph.Edge e : ((XGraph.XVertex)vertex).revXadj) {
                if (e.getWeight() < min) {
                    min = e.getWeight();
                }
            }
            for (Graph.Edge e : ((XGraph.XVertex)vertex).revXadj)
                e.setWeight(e.getWeight() - min);
        }
        src.enable();
        printEdges(graph);
    }

    public int findSpanningTree() {
        toZeroWeightGraph(graph, source);
       // shrinkComponents();
        printGraph(graph);
        return 0;
    }

    private void shrinkComponents() {
        int componentCount = cc.findCC();
        XGraph.XVertex componentVertices[] = new XGraph.XVertex[componentCount];
        List<List<XGraph.XVertex>> components = new ArrayList<>();
        for (int i = 0; i < componentCount; i++) {
            components.add(new ArrayList<>());
        }
        for (Graph.Vertex vertex : graph) {
            CC.CCVertex component = cc.getCCVertex(vertex);
            components.get(component.cno-1).add((XGraph.XVertex) vertex);
        }
        int index = 0;
        for (List<XGraph.XVertex> component : components) {
            Graph.Vertex gVertex = new Graph.Vertex(graph.getSize());
            XGraph.XVertex vertex = new XGraph.XVertex(gVertex);
            vertex.isComponent = true;
            vertex.vertexSet = component;
            componentVertices[index++] = vertex;
            graph.addVertex(vertex);
        }
        addEdges(componentVertices);

    }

    private void addEdges(XGraph.XVertex[] components) {
        for (XGraph.XVertex component : components) {
            if (component.isComponent) {
                Graph.Edge[] edges = getMinEdges(component.vertexSet, components.length);
                for (int i = 0; i < edges.length; i++) {
                    if (edges[i] != null) {
                        XGraph.XEdge edge = new XGraph.XEdge(component, components[i-1], edges[i].weight, edges[i]);
                        component.xadj.add(edge);
                        components[i-1].revXadj.add(edge);
                    }
                }
            }
        }
    }

    private Graph.Edge[] getMinEdges(List<XGraph.XVertex> component, int componentCount) {
        Graph.Edge[] adj = new XGraph.Edge[componentCount];
        for (XGraph.XVertex vertex : component) {
            if (vertex.adj.size() > 0) {
                int cno = cc.getCCVertex(vertex).cno, otherCno;
                for (XGraph.Edge edge : vertex.adj) {
                    otherCno = cc.getCCVertex(edge.otherEnd(vertex)).cno-1;
                    if (cno != otherCno) {
                        if (adj[otherCno] == null || adj[otherCno].getWeight() > edge.getWeight()) {
                            adj[otherCno] = edge;
                        }
                    }
                }
            }
            vertex.disable();
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
