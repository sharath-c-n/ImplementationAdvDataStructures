package cs6301.g26;


/**
 * This class provides static methods to add new
 * terms to a polynomial ,and to perform add, multiply
 * and eval operations on sparse polynomials by
 * maintaining them in a linked list
 * @author Ankitha
 */
public class Polynomial {
    private Term head = null;
    private Term last = null;
    /*The polynomial has a head and last reference maintained for the linkedList
    The term class has coef,exp and reference to next node*/
    private static class Term {
        int coef;
        int exp;
        Term next;

        Term(int coef, int exp) {
            this.coef = coef;
            this.exp = exp;
        }
    }

    public Polynomial() {
        head = new Term(0,0);
    }



    public void addTerm(Term term) {
        addTerm(term.coef, term.exp);
    }
    /*Adds terms to the linkedList by maintaining the
     terms in the decreasing order of their exponents*/

    public void addTerm(int coef, int exp)
    {
        Term newTerm = new Term(coef, exp);
        Term previous = null;
        Term current = head;
        while (current != null && exp >= current.exp) {
            //If a term with same exponent is added its coefficients are added
            if (exp == current.exp) {
                current.coef += coef;
                return;
            }
            previous = current;
            current = current.next;

        }
        if (previous == null){
            head = newTerm;
        }
        else {
            newTerm.next = current;
            previous.next = newTerm;
            if(current == null){
                last = newTerm;
            }
        }
    }

    /**
     * This will create copy of the original polynomial and return
     *
     * @return : clone of the original polynomial
     */
    public Polynomial clone() {
        Polynomial copy = new Polynomial();
        Term current = head.next;
        while (current != null) {
            copy.addTerm(current);
            current = current.next;
        }
        return copy;
    }

    /**
     * This will return a new polynomial with the sum of two parse polynomials
     *
     * @param a,b : two polynomials to be added
     * @return sum : sum of polynomials
     */
    public  static  Polynomial add(Polynomial a, Polynomial b) {
        Polynomial sum;
        Term x = a.head.next;
        Term y = b.head.next;
        if (x == null) {
            sum = y != null ? b.clone() : null;
        }
        if (y == null) {
            sum = x != null ? a.clone() : null;
        }
        sum = new Polynomial();
        while (x != null || y != null) {
            Term term = null;
            if(x == null){
                term = y;
                y = y.next;
            }
            else if(y == null){
                term = x;
                x = x.next;
            }

           else if (x.exp > y.exp) {
                term = new Term(x.coef, x.exp);
                x = x.next;
            } else if (x.exp < y.exp) {
                term = new Term(y.coef, y.exp);
                y = y.next;
            } else {
                int coef = x.coef + y.coef;
                int exp = x.exp;
                x = x.next;
                y = y.next;
                if (coef == 0) {
                    continue;
                }
                term = new Term(coef, exp);
            }
            sum.addTerm(term);
        }
        return sum;
    }

    /**
     * This will return a new polynomial with the product of two parse polynomials
     *
     * @param a,b : two polynomials to be added
     * @return product : product of polynomials
     */
    public static Polynomial multiply(Polynomial a, Polynomial b) {
        Polynomial product = new Polynomial();
        Term x = a.head.next;
        while (x != null) {
            Polynomial temp = new Polynomial();
            for (Term y = b.head.next; y != null; y = y.next) {
                temp.addTerm(x.coef * y.coef, x.exp + y.exp);
            }
            product = add(product, temp);
            x = x.next;
        }
        return product;
    }

    /**
     * This will return evaluated value of a term in polynomial
     *
     * @param term,val : val with which the term has to be evaluated with
     * @return result : result of evaluation
     */
    public int getTermVal(Term term, int val) {
        int termEval = val;
        for (int i = 1; i < term.exp; i++)
            termEval *= val;
        return termEval * term.coef;
    }


    /**
     * This will return evaluated value of a polynomial
     *
     * @param val : val with which the polynomial has to be evaluated with
     * @return result : result of evaluation
     */
    public int evaluate(int val) {
        int result = 0;
        Term x = head.next;
        while (x != null) {
            result += getTermVal(x, val);
            x = x.next;
        }
        return result;
    }
    public String toString(){
        String output = "";
        Term cur = head.next;
        while(cur!=null){
            output+=((cur.coef>0)?"+":"")+ cur.coef +" x^"+ cur.exp+" ";
            cur = cur.next;
        }
        if(output.startsWith("+")){
            output = output.replaceFirst("\\+"," ");
        }
        return output;
    }

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
