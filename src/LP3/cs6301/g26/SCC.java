/**
 * @author Sandeep on 10/21/2017
 */
package cs6301.g26;

public class SCC extends CC {
     XGraph xg;
     Graph.Vertex src;
    SCC(XGraph g, Graph.Vertex src){
        super((Graph)g);
        this.xg=g;
        this.src=src;
    }
   public void Connected(){
        int nc=findCC();
        System.out.println("Input Graph has " + nc + " components:");
        for(Graph.Vertex u: xg) {
            System.out.print(u + " [ " + getCCVertex(u).cno + " ] :");
            for(Graph.Edge e: u.adj) {

                Graph.Vertex v = e.otherEnd(u);
                System.out.print(e + " ");
            }
            System.out.println();
        }
    }
}
