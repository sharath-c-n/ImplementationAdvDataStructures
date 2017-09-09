
// Starter code for lp1.

// Change following line to your group number


import java.util.Iterator;
import java.util.LinkedList;

public class Num implements Comparable<Num> {

    public static int defaultBase = 10;  // This can be changed to what you want it to be.
    private int base = defaultBase;  // Change as needed
    private LinkedList<Long> numbers;
    private boolean positive = true;

    /* Start of Level 1 */
    Num(String s) {
        if (s.length() == 0)
            throw new NumberFormatException("Zero length Number");
        numbers = new LinkedList<>();
        int count = s.length() - 1;
        while (count >= 0) {
            numbers.add(Long.parseLong(String.valueOf(s.charAt(count--))));
        }
    }

    Num(long x) {
        numbers = new LinkedList<>();
        while (x != 0) {
            numbers.add(x % base);
            x /= base;
        }
    }

    Num() {
        numbers = new LinkedList<>();
    }

    public void addNum(Long x) {
        numbers.add(x);
    }

    int getSize() {
        return numbers.size();
    }

    static Num add(Num a, Num b) {
        long carry = 0;
        Num response = new Num();
        if (a.base() != b.base())
            throw new NumberFormatException("Different bases");
        if (!a.isPositive() && b.isPositive()) {
            return subtract(b, a);
        }

        if (!b.isPositive() && a.isPositive()) {
            return subtract(a, b);
        }

        int base = a.base();
        Iterator<Long> x = a.numbers.iterator();
        Iterator<Long> y = b.numbers.iterator();
        while (x.hasNext() || y.hasNext()) {
            Long sum = a.getNext(x) + b.getNext(y) + carry;
            response.addNum(sum % base);
            carry = sum / base;
        }
        while (carry != 0) {
            response.addNum(carry % base);
            carry = carry / base;
        }
        //Comes here only if both of the operands have same sign
        response.setPositive(a.isPositive());
        return response;
    }

    static Num subtract(Num a, Num b) {
        long borrow = 0;
        Num response = new Num();
        if (a.base() != b.base())
            throw new NumberFormatException("Different bases");
        int base = a.base();

        Iterator<Long> x = a.numbers.iterator();
        Iterator<Long> y = b.numbers.iterator();

        while (x.hasNext() || y.hasNext()) {
            Long operand1 = a.getNext(x) -  borrow;
            Long operand2 = b.getNext(y);
            //rest borrow since it has been applied
            borrow = 0;
            while (operand1 < operand2) {
                operand1 += base;
                borrow++;
            }
            Long diff = operand1 - operand2;
            response.addNum(diff);
        }
        if (borrow != 0) {
            StringBuilder compliment = new StringBuilder();
            compliment.append(borrow);
            for (int i = 0; i < response.getSize(); i++) {
                compliment.append(0);
            }
            response = subtract(new Num(compliment.toString()), response);
            response.setPositive(false);
        }
        response.trim();
        return response;
    }



    // Implement Karatsuba algorithm for excellence credit
    static Num product(Num a, Num b) {
        return null;
    }

    // Use divide and conquer
    static Num power(Num a, long n) {
        return null;
    }
    /* End of Level 1 */

    /* Start of Level 2 */
    static Num divide(Num a, Num b) {
        return null;
    }

    static Num mod(Num a, Num b) {
        return null;
    }

    // Use divide and conquer
    static Num power(Num a, Num n) {
        return null;
    }

    static Num squareRoot(Num a) {
        return null;
    }
    /* End of Level 2 */


    // Utility functions
    // compare "this" to "other": return +1 if this is greater, 0 if equal, -1 otherwise
    public int compareTo(Num other) {
        int value;
        if(!isPositive() && other.isPositive()){
            return -1;
        }

        if(isPositive() && !other.isPositive()){
            return  1;
        }

        if (getSize() == other.getSize()) {
            //Start comparing from the last
            Iterator<Long> curr = numbers.descendingIterator();
            Iterator<Long> o = other.numbers.descendingIterator();
            long currValue = 0, otherValue = 0;
            //Since both the numbers have same size only one can be checked
            while (curr.hasNext()) {
                currValue = curr.next();
                otherValue = o.next();
                if (currValue != otherValue)
                    break;
            }
            value = currValue - otherValue == 0 ? 0 : currValue - otherValue < 0 ? -1 : 1;
            return isPositive() || value == 0 ? value : -value;
        }
        value = getSize() < other.getSize() ? -1 : 1;
        return isPositive() ? value : -value;
    }

    // Output using the format "base: elements of list ..."
    // For example, if base=100, and the number stored corresponds to 10965,
    // then the output is "100: 65 9 1"
    void printList() {
        StringBuilder output = new StringBuilder();
        output.append(base).append(": ");
        for (Long numb : numbers) {
            output.append(numb).append(" ");
        }
        System.out.println(output);
    }

    public Long getNext(Iterator<Long> itr) {
        return itr.hasNext() ? itr.next() : Long.valueOf(0);
    }

    // Return number to a string in base 10
    public String toString() {
        StringBuilder output = new StringBuilder();
        for (Long numb : numbers) {
            output.append(numb);
        }
        if (!isPositive()) {
            output.append("-");
        }
        return output.reverse().toString();
    }

    public int base() {
        return base;
    }

    public boolean isPositive() {
        return positive;
    }

    private void setPositive(boolean positive) {
        this.positive = positive;
    }

    private void trim() {
        Iterator<Long> itr = numbers.descendingIterator();
        while (itr.hasNext()) {
            if (itr.next() != 0) {
                break;
            }
            itr.remove();
        }
    }
}
