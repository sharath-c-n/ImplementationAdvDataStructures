
// Starter code for lp1.

// Change following line to your group number


import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class Num implements Comparable<Num> {

    public static int defaultBase = 10;  // This can be changed to what you want it to be.
    private long base = defaultBase;  // Change as needed
    private List<Long> numbers;
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
        if (x == 0) {
            numbers.add(0L);
        }
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

    static Num unsignedAdd(Num a, Num b) {
        long carry = 0;
        Num response = new Num();
        long base = a.base();
        Iterator<Long> x = a.numbers.iterator();
        Iterator<Long> y = b.numbers.iterator();
        while (x.hasNext() || y.hasNext()) {
            Long sum = getNext(x) + getNext(y) + carry;
            response.addNum(sum % base);
            carry = sum / base;
        }
        if (carry != 0) {
            response.addNum(carry % base);
        }
        return response;
    }

    static Num add(Num a, Num b) {
        if ((!a.isPositive() && b.isPositive()) || (!b.isPositive() && a.isPositive())) {
            return subtract(a, b);
        }
        Num response = unsignedAdd(a, b);
        //Comes here only if both of the operands have same sign
        response.setPositive(a.isPositive());
        return response;
    }

    static Num unsignedSub(Num a, Num b) {
        long borrow = 0;
        Num response = new Num();
        Iterator<Long> x, y;
        x = a.numbers.iterator();
        y = b.numbers.iterator();
        while (x.hasNext() || y.hasNext()) {
            Long operand1 = getNext(x) - borrow;
            Long operand2 = getNext(y);
            //rest borrow since it has been applied
            borrow = 0;
            if (operand1 < operand2) {
                operand1 += a.base;
                borrow++;
            }
            Long diff = operand1 - operand2;
            response.addNum(diff);
        }
        response.trim();
        return response;
    }

    static Num subtract(Num a, Num b) {
        Num response;
        if (!b.isPositive() && a.isPositive()) {
            return unsignedAdd(a, b);
        }

        if (!a.isPositive() && b.isPositive()) {
            response = unsignedAdd(a, b);
            //Comes here only if both of the operands have same sign
            response.setPositive(false);
            return response;
        }

        if (a.compareTo(b) == 0) {
            response = new Num();
            response.addNum(0L);
            return response;
        }
        if (a.compareTo(b) > 0) {
            //Check if both a and b are negative, no need to check if b is negative since if the execution
            //comes here both should be of same sign
            return a.isPositive() ? unsignedSub(a, b) : unsignedSub(b, a);
        } else {
            //Check if both a and b are negative
            response = a.isPositive() ? unsignedSub(b, a) : unsignedSub(a, b);
            //If a is negative and b is also negative but greater than a, then don't set as negative number
            if (a.isPositive() && b.isPositive()) {
                response.setPositive(false);
            }
            return response;
        }
    }

    public static Num divide(Num a, long b) {
        if (b == 0)
            throw new IllegalArgumentException("Divisor is zero");
        if (a.getSize() < String.valueOf(b).length()) {
            return new Num(0);
        }
        Iterator<Long> itr = ((LinkedList<Long>) a.numbers).descendingIterator();
        Num quotient = new Num();
        Num dividend = new Num(0);
        Num numB = new Num(b);
        int compareTo;
        long next;
        while (itr.hasNext()) {
            next = itr.next();
            dividend.shiftLeft((long) String.valueOf(next).length());
            dividend = add(dividend, new Num(next));
            dividend.trim();
            compareTo = dividend.compareTo(numB);
            if (compareTo < 0 || dividend.isZero()) {
                quotient.shiftLeft(1L);
            } else if (compareTo == 0) {
                quotient.shiftLeft(1L);
                quotient = add(quotient, new Num(1));
                dividend = new Num(0);
            } else {
                long multiplier = 1;
                long divisor;
                while (compareTo > 0) {
                    multiplier *= 2;
                    divisor = b * multiplier;
                    compareTo = dividend.compareTo(new Num(divisor));
                }
                if (compareTo == 0) {
                    quotient.shiftLeft((long) String.valueOf(multiplier).length());
                    quotient = add(quotient, new Num(multiplier));
                    dividend = new Num(0L);
                } else {
                    long high = multiplier;
                    long low = multiplier / 2;
                    long mid = 1;
                    while (high > low) {
                        mid = low + (high - low) / 2;
                        compareTo = dividend.compareTo(new Num(b * mid));
                        if (compareTo == 0 || (compareTo > 0 && dividend.compareTo(new Num(b * (mid + 1))) < 0)) {
                            break;
                        }
                        if (compareTo > 0) {
                            low = mid;
                        } else {
                            high = mid;
                        }
                    }
                    divisor = b * mid;
                    dividend = subtract(dividend, new Num(divisor));
                    quotient.shiftLeft((long) String.valueOf(mid).length());
                    quotient = add(quotient, new Num(mid));
                }
            }
        }
        if (!quotient.isZero()) {
            quotient.trim();
        }
        return quotient;
    }


    public void shiftLeft(Long shift) {
        for (int i = 0; i < shift; i++) {
            ((LinkedList<Long>) numbers).addFirst(0L);
        }
    }

    public Num multiply(Num b) {
        Iterator<Long> multiplier = b.numbers.iterator();
        Num product = new Num();
        long carry, itrNo = 0;

        while (multiplier.hasNext()) {
            Num tempProd = new Num();
            carry = 0;
            Long value = multiplier.next();
            tempProd.shiftLeft(itrNo++);
            for (Long term : numbers) {
                Long temp = term * value + carry;
                tempProd.addNum(temp % base);
                carry = temp / base;
            }
            if (carry != 0) {
                tempProd.addNum(carry);
            }
            product = add(product, tempProd);
        }
        if (!isPositive() ^ !b.isPositive()) {
            product.setPositive(false);
        }
        return product;
    }

    public Num subNum(long start, long end) {
        Num number = new Num();
        if (getSize() < start) {
            return number;
        }
        int index = 0;
        for (Long term : numbers) {
            if (index >= start && index < end) {
                number.addNum(term);
            }
            index++;
        }
        return number;
    }

    // Implement Karatsuba algorithm for excellence credit
    static Num product(Num a, Num b) {
        if (a.getSize() == 1 || b.getSize() == 1) {
            return a.multiply(b);
        }
        int m = Math.max(a.getSize(), b.getSize()) / 2;
        Num high1 = a.subNum(m, a.getSize());
        Num low1 = a.subNum(0, m);
        Num high2 = b.subNum(m, b.getSize());
        Num low2 = b.subNum(0, m);
        Num z0 = product(low1, low2);
        Num z1 = product(add(low1, high1), add(low2, high2));
        Num z2 = product(high1, high2);
        Num y0 = power(new Num(a.base), m);
        Num y1 = power(y0, 2);
        Num t1 = z2.multiply(y1);
        Num t2 = y0.multiply(subtract(subtract(z1, z2), z0));
        Num t3 = add(t1, t2);
        t3.trim();
        return add(t3, z0);
    }

    protected Num clone() {
        Num cloned = new Num();
        cloned.setPositive(isPositive());
        cloned.numbers = (LinkedList<Long>) ((LinkedList<Long>) numbers).clone();
        return cloned;
    }

    // Use divide and conquer
    static Num power(Num a, long n) {
        Num temp;
        if (n == 0)
            return new Num(1);
        temp = power(a, n / 2);
        if (n % 2 == 0)
            return temp.multiply(temp);
        else
            return a.multiply(temp).multiply(temp);
    }
    /* End of Level 1 */

    static Num abs(Num a) {
        Num unSigned = a.clone();
        unSigned.setPositive(true);
        return unSigned;
    }

    static Num divide(Num a, Num b) {
        Num quotient = reminderOrQuotient(abs(a), abs(b), true);
        if (a.isPositive() ^ b.isPositive()) {
            quotient.setPositive(false);
        }
        return quotient;
    }

    /* Start of Level 2 */
    static Num reminderOrQuotient(Num a, Num b, boolean isQuotient) {
        if (b.isZero()) {
            throw new IllegalArgumentException("Argument divisor is zero");
        }
        if (a.getSize() < b.getSize()) {
            return new Num(0);
        }
        Iterator<Long> itr = ((LinkedList<Long>) a.numbers).descendingIterator();
        Num quotient = new Num();
        Num dividend = new Num(0);
        Num two = new Num(2);
        int compareTo;
        long next;
        while (itr.hasNext()) {
            next = itr.next();
            dividend.shiftLeft((long) String.valueOf(next).length());
            dividend = add(dividend, new Num(next));
            dividend.trim();
            compareTo = dividend.compareTo(b);
            if (compareTo < 0 || dividend.isZero()) {
                quotient.shiftLeft(1L);
            } else if (compareTo == 0) {
                quotient.shiftLeft(1L);
                quotient = add(quotient, new Num(1));
                dividend = new Num(0);
            } else {
                Num multiplier = new Num(1);
                Num divisor;
                while (compareTo > 0) {
                    multiplier = product(multiplier, two);
                    divisor = product(b, multiplier);
                    compareTo = dividend.compareTo(divisor);
                }
                if (compareTo == 0) {
                    quotient.shiftLeft((long) multiplier.getSize());
                    add(quotient, multiplier);
                    dividend = new Num(0);
                } else {
                    multiplier = getMultiplicant(multiplier, divide(multiplier, 2), b, dividend);
                    divisor = product(b, multiplier);
                    dividend = subtract(dividend, divisor);
                    quotient.shiftLeft((long) multiplier.getSize());
                    quotient = add(quotient, multiplier);
                }
            }
        }
        if (!quotient.isZero()) {
            quotient.trim();
        }
        return isQuotient ? quotient : dividend;
    }

    private static Num getMultiplicant(Num high, Num low, Num divisor, Num dividend) {
        Num mid = new Num(1);
        int compareTo;
        while (high.compareTo(low) > 0) {
            mid = divide(add(low, high), 2);
            compareTo = dividend.compareTo(product(divisor, mid));
            if (compareTo == 0 || (compareTo > 0 && dividend.compareTo(product(divisor, add(mid, new Num(1)))) < 0)) {
                break;
            }
            if (compareTo > 0) {
                low = mid;
            } else {
                high = mid;
            }
        }
        return mid;
    }

    static Num mod(Num a, Num b) {
        return reminderOrQuotient(abs(a), abs(b), false);
    }

    // Use divide and conquer
    static Num power(Num a, Num n) {
        Num temp;
        if (n.isZero())
            return new Num(1);
        temp = power(a, divide(n, 2));
        if (mod(n, new Num(2)).isZero())
            return product(temp, temp);
        else
            return product(product(a, temp), temp);
    }

    static Num squareRoot(Num a) {
        // Base Cases
        Num one = new Num(1);
        if (a.isZero() || a.compareTo(one) == 0) {
            return a;
        }

        Num left = one.clone();
        Num right = a;
        Num sqrt = new Num(0);
        while (left.compareTo(right) <= 0) {
            Num mid = divide(add(left, right), 2);
            if (product(mid, mid).compareTo(a) == 0) {
                return mid;
            }
            if (product(mid, mid).compareTo(a) < 0) {
                left = add(mid, one);
                sqrt = mid;
            } else {
                right = subtract(mid, one);
            }
        }
        return sqrt;
    }
    /* End of Level 2 */


    // Utility functions
    // compare "this" to "other": return +1 if this is greater, 0 if equal, -1 otherwise
    public int compareTo(Num other) {
        int value;
        if (!isPositive() && other.isPositive()) {
            return -1;
        }

        if (isPositive() && !other.isPositive()) {
            return 1;
        }

        if (getSize() == other.getSize()) {
            //Start comparing from the last
            Iterator<Long> curr = ((LinkedList<Long>) numbers).descendingIterator();
            Iterator<Long> o = ((LinkedList<Long>) other.numbers).descendingIterator();
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

    public boolean isZero() {
        return getSize() == 1 && numbers.get(0) == 0;
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

    public static Long getNext(Iterator<Long> itr) {
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

    public long base() {
        return base;
    }

    public boolean isPositive() {
        return positive;
    }

    public void setPositive(boolean positive) {
        this.positive = positive;
    }

    private void trim() {
        Iterator<Long> itr = ((LinkedList<Long>) numbers).descendingIterator();
        while (itr.hasNext()) {
            if (itr.next() != 0) {
                break;
            }
            itr.remove();
        }
    }
}
