package cs6301.g26;

import java.util.Stack;

/**
 * XCC:
 *
 * @author : Sharath
 * 24/10/2017
 */
public class XCC extends CC {
    Stack<XGraph.XVertex> stack;
    public XCC(XGraph g) {
        super(g);
        stack = new Stack<>();
    }

    // Visit a node by marking it as seen and assigning it a component no
    void visit(Graph.Vertex u, int cno) {
        CCVertex ccu = getCCVertex(u);
        ccu.seen = true;
        ccu.cno = cno;
    }
}
