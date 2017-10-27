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
    }

    public int findSpanningTree(List<Graph.Edge> edges) {
        BFSZeroEdge bfsZero=new BFSZeroEdge(graph,source);
        while( !bfsZero.isSpanningTree()) {
            toZeroWeightGraph(graph, source);
            shrinkComponents();
            printGraph(graph);
            bfsZero=new BFSZeroEdge(graph,source);
        }
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
         /*   if (component == source) {
                continue;
            }*/
         //   XGraph.XEdge minEdge = null;
            //Disable vertex and edges of the children and get minimum edge
            XGraph.XEdge[] minEdges = new XGraph.XEdge[components.size()];
            if (component.isComponent && component.XAdj.size() == 0) {
                for (XGraph.XVertex vertex : component.children) {
                    getMinIncomingEdges(vertex, minEdges);
                    vertex.disable();
                }
            } else {
                getMinIncomingEdges(component, minEdges);
            }
            for (XGraph.XEdge minEdge : minEdges) {
                if (minEdge != null) {
                    XGraph.XVertex toVertex = componentVertices[getComponentNo(minEdge.to)];
                    //Edge already there in original graph no need to add
                    if (toVertex == minEdge.to && component == minEdge.from) {
                        continue;
                    }
                    //Need to create a new vertex
                    minEdge.disabled = true;
                    minEdge = new XGraph.XEdge(component, toVertex, minEdge.getWeight(), minEdge.original);
                    component.XAdj.add(minEdge);
                    toVertex.revXadj.add(minEdge);
                }
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

    private void getMinIncomingEdges(XGraph.XVertex vertex, XGraph.XEdge [] minEdges) {
        //XGraph.XEdge adj = null;

        if (vertex.XAdj.size() > 0) {
            for (XGraph.XEdge edge : vertex.XAdj) {
                if( edge.isDisabled())
                    continue;
            int cno = getComponentNo(vertex), otherCno;
                otherCno = getComponentNo(edge.otherEnd(vertex));
                if (cno != otherCno) {
                    XGraph.XEdge adj= minEdges[otherCno];
                    if (adj == null || minEdges[otherCno].getWeight() > edge.getWeight()) {
                        if (adj != null) {
                            adj.disabled = true;
                        }
                        minEdges[otherCno] = edge;
                    } else {
                        edge.disabled = true;
                    }
                }
            }
        }

    }

    static public void printEdges(XGraph graph) {
        System.out.println("Printing Graph edges");
        for (XGraph.Vertex vertex : graph) {
            for (XGraph.Edge e : ((XGraph.XVertex)vertex).XAdj) {
                if(!((XGraph.XEdge)e).isDisabled())
                System.out.println(e);
            }
        }
        System.out.println("End of Printing Graph edges");
    }

}
