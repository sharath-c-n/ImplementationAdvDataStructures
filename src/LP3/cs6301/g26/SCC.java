/**
 * @author Sandeep on 10/21/2017
 */
package cs6301.g26;

import java.util.Stack;

public class SCC extends CC {
     private XGraph xg;
     private Graph.Vertex src;
    SCC(XGraph g, Graph.Vertex src){
        super(g);
        this.xg=g;
        this.src=src;
    }
   public void Connected(){
        int nc=findSSC();
        System.out.println("Input Graph has " + nc + " components:");
        for(Graph.Vertex u: xg) {
            System.out.println(u + " [ " +" has a component " + getCCVertex(u).cno + " ] :");
        }
    }

    void finishedOrder(XGraph.XVertex v, Stack<Graph.Vertex> St)
    {
        getCCVertex(v).seen=true;
     //   System.out.println("Vertex "+v+" List is "+v.revXadj);
        v.setRevItr();
        for(XGraph.Edge t:v)
            if(!getCCVertex(t.otherEnd(v)).seen)
                finishedOrder((XGraph.XVertex) t.otherEnd(v), St);
        St.push(v);
    }

    int findSSC()
    {
        Stack<Graph.Vertex> stack =new Stack<>();
        for( Graph.Vertex v: g)
            if(!getCCVertex(v).seen)
                finishedOrder((XGraph.XVertex) v, stack);
        for(Graph.Vertex v:g)
            getCCVertex(v).seen=false;
        int count=0;
        while (!stack.empty())
        {
            Graph.Vertex v = stack.peek();
            stack.pop();
            if (!getCCVertex(v).seen) {
                count++;
                dfsVisit(v, count);
            }
        }
        return count;
    }
}
