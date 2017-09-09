package cs6301.g26;

/**
 * Created by Ankitha on 9/1/2017.
 */
public class Problem9 {
    public static void main(String[] args ){
      Polynomial a= new Polynomial();
      Polynomial b = new Polynomial();
      a.addTerm(1,2);
      a.addTerm(-2,1);
      b.addTerm(1,2);
      b.addTerm(3,1);
      System.out.println(a);
      System.out.println(b);
      System.out.println(a.evaluate(3));
    }

}

