
package cs6301.g26;


public class GraphAlgorithm<T> {
    CustomGraph g;
    // Algorithm uses a parallel array for storing information about vertices
    T[] node;

    public GraphAlgorithm(CustomGraph g) {
	this.g = g;
    }

    T getVertex(CustomGraph.Vertex u) {
	return CustomGraph.Vertex.getVertex(node, u);
    }

    void setVertex(CustomGraph.Vertex u, T vertex) {
	    CustomGraph.Vertex.setVertex(node, u, vertex);
    }

}

