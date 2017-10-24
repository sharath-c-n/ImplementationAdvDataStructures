/**
 * @author Sandeep on 10/21/2017
 */
package cs6301.g26;

import java.util.Stack;

public class SCC extends CC {
     XGraph xg;
     Graph.Vertex src;
    SCC(XGraph g, Graph.Vertex src){
        super((Graph)g);
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

    void finishedOrder(Graph.Vertex v, Stack<Graph.Vertex> St)
    {
        getCCVertex(v).seen=true;
        for(Graph.Edge t:v.revAdj)
            if(!getCCVertex(t.otherEnd(v)).seen)
                finishedOrder(t.otherEnd(v), St);
        St.push(v);
    }

    int findSSC()
    {
        Stack<Graph.Vertex> St =new Stack<>();
        for( Graph.Vertex v: g)
            if(!getCCVertex(v).seen)
                finishedOrder(v, St);
        for(Graph.Vertex v:g)
            getCCVertex(v).seen=false;
        int count=0;
        while (St.empty() == false)
        {
            Graph.Vertex v = St.peek();
            St.pop();
            if (!getCCVertex(v).seen) {
                count++;
                dfsVisit(v, count);
            }
        }
        return count;
    }
}
