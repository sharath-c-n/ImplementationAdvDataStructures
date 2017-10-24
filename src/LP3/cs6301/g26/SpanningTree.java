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

    public int findSpanningTree() {
        toZeroWeightGraph(graph, source);
        shrinkComponents();
        toZeroWeightGraph(graph, source);
        printGraph(graph);
        return 0;
    }

    //Get a list of all Strongly connected component
    private List<List<XGraph.XVertex>> getComponents() {
        int componentCount = cc.findCC();
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
            if(component.isComponent){
                //Disable vertex and edges of the children and get minimum edge
                XGraph.XEdge edge = getMinIncomingEdges(component.children);
                if(edge!=null){
                    component.revAdj.add(edge);
                    int otherCno = cc.getCCVertex(edge.from).cno;
                    componentVertices[otherCno -1].XAdj.add(edge);
                }
            }
        }
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

    private XGraph.XEdge getMinIncomingEdges(List<XGraph.XVertex> component) {
        XGraph.XEdge adj = null;
        for (XGraph.XVertex vertex : component) {
            if (vertex.revXadj.size() > 0) {
                int cno = cc.getCCVertex(vertex).cno, otherCno;
                for (XGraph.XEdge edge : vertex.revXadj) {
                    otherCno = cc.getCCVertex(edge.otherEnd(vertex)).cno;
                    if (cno != otherCno) {
                        if (adj == null || adj.getWeight() > edge.getWeight()) {
                            if(adj != null){
                                adj.disabled = true;
                            }
                            adj = edge;
                        }
                        else {
                            edge.disabled = true;
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
