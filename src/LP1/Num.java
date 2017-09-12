
// Starter code for lp1.

// Change following line to your group number


import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class Num implements Comparable<Num> {

    public static int defaultBase = 10;  // This can be changed to what you want it to be.
    private final static int minSupportedBase = 2;
    private final static int maxSupportedBase = 10000;
    private long base = 100;  // Change as needed
    private List<Long> numbers;
    private boolean positive = true;

    /* Start of Level 1 */
    Num(String s) {
        if (s.length() > 0) {
            Num x = new Num(Long.parseLong(String.valueOf(s.charAt(0))));
            Num base = new Num(Num.defaultBase);
            for (int i = 1; i < s.length(); i++) {
                x = add(product(x, base), new Num(Long.parseLong(String.valueOf(s.charAt(i)))));
            }
            numbers = x.numbers;
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

    Num(long x, long radix) {
        base = radix;
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

    /**
     * Adds a number to the back of the current numbers list.
     * Make sure that the x is withing the base, that is x%base should had been done already
     *
     * @param x : the number to be added
     */
    public void addNum(Long x) {
        numbers.add(x);
    }

    /**
     * Adds a number to the front of the current numbers list(units place).
     * Make sure that the x is withing the base, that is x%base should had been done already
     *
     * @param x : the number to be added
     */
    public void addFront(Long x) {
        ((LinkedList<Long>) numbers).addFirst(x);
    }


    /**
     * Returns the number of nodes in the current numbers list.
     *
     * @return total numbers of nodes in the list
     */
    int getSize() {
        return numbers.size();
    }

    /**
     * Adds 2 number without checking for the sign.
     *
     * @param a : Of type Num
     * @param b : of Type Num
     * @return : a new number which the sum of a and b
     */
    static Num unsignedAdd(Num a, Num b) {
        long carry = 0;
        Num response = new Num();
        response.setBase(a.base);
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

    /**
     * Signed addition of the two parameters
     *
     * @param a : Of type Num
     * @param b : of Type Num
     * @return : a new number which the sum of a and b
     */
    static Num add(Num a, Num b) {
        if ((!a.isPositive() && b.isPositive()) || (!b.isPositive() && a.isPositive())) {
            return subtract(a, b);
        }
        Num response = unsignedAdd(a, b);
        //Comes here only if both of the operands have same sign
        response.setPositive(a.isPositive());
        return response;
    }

    /**
     * Subtracts 2 number without checking for the sign.
     *
     * @param a : Of type Num
     * @param b : of Type Num
     * @return : a new number which the difference of a and b
     */
    static Num unsignedSub(Num a, Num b) {
        long borrow = 0;
        Num response = new Num();
        response.setBase(a.base);
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
            //Will always be less than base hence no need to do mod
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

    /**
     * Divides a number with long value
     *
     * @param a : the number to be divided
     * @param b : the divisor
     * @return : the quotient of type Num
     */
    public static Num divide(Num a, long b) {
        int compareTo;
        if (b == 0) {
            throw new IllegalArgumentException("Argument divisor is zero");
        }
        compareTo = a.compareTo(new Num(b));
        if (compareTo < 0) {
            return new Num(0);
        }
        if (compareTo == 0) {
            return new Num(1);
        }
        Iterator<Long> itr = ((LinkedList<Long>) a.numbers).descendingIterator();
        Num quotient = new Num();
        Num dividend = new Num();
        Num numB = new Num(b);
        while (itr.hasNext()) {
            dividend.addFront(itr.next());
            compareTo = dividend.compareTo(numB);
            if (compareTo < 0 || dividend.isZero()) {
                quotient.shiftLeft(1L);
            } else if (compareTo == 0) {
                quotient.addFront(1L);
                dividend = new Num(0);
            } else {
                long high = a.base;
                long low = 1;
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
                Long divisor = b * mid;
                dividend = subtract(dividend, new Num(divisor));
                quotient.shiftLeft((long) String.valueOf(mid).length());
                quotient = add(quotient, new Num(mid));
            }
        }
        if (!quotient.isZero()) {
            quotient.trim();
        }
        return quotient;
    }


    /**
     * Shifts the nodes in the numbers lift by the specified number of positions
     *
     * @param shift : number of positions to shift
     */
    public void shiftLeft(Long shift) {
        for (int i = 0; i < shift; i++) {
            ((LinkedList<Long>) numbers).addFirst(0L);
        }
    }

    /**
     * Multiplies the given number with the callee, uses normal n2 algorithm.
     * Should be used when multiplying small numbers or size 1 or 2.
     *
     * @param b : the number to be multiplied
     * @return : the product of this and the b.
     */
    public Num multiply(Num b) {
        Iterator<Long> multiplier = b.iterator();
        Num product = new Num();
        product.setBase(base);
        long carry, itrNo = 0;

        while (multiplier.hasNext()) {
            Num tempProd = new Num();
            tempProd.setBase(b.base);
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
            product = unsignedAdd(product, tempProd);
        }
        if (isPositive() ^ b.isPositive()) {
            product.setPositive(false);
        }
        product.trim();
        return product;
    }

    public Num subNum(long start, long end) {
        Num number = new Num();
        number.setBase(base);
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
        if (a.getSize() < 5 || b.getSize() < 5) {
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
        Num y0 = smallPower(new Num(a.base, a.base), m);
        Num y1 = y0.multiply(y0);
        Num t1 = z2.multiply(y1);
        Num t2 = y0.multiply(unsignedSub(subtract(z1, z2), z0));
        Num t3 = unsignedAdd(t1, t2);
        t3.trim();
        z0.trim();
        return unsignedAdd(t3, z0);
    }

    protected Num clone() {
        Num cloned = new Num();
        cloned.setPositive(isPositive());
        cloned.base = base;
        cloned.numbers = (LinkedList<Long>) ((LinkedList<Long>) numbers).clone();
        return cloned;
    }

    /*This function is used only in product function to find small powers */
    private static Num smallPower(Num a, long n) {
        Num temp;
        if (n == 0)
            return new Num(1, a.base);
        temp = power(a, n / 2);
        if (n % 2 == 0)
            return temp.multiply(temp);
        else
            return a.multiply(temp).multiply(temp);
    }

    // Use divide and conquer
    static Num power(Num a, long n) {
        Num temp;
        if (n == 0)
            return new Num(1, a.base);
        temp = power(a, n / 2);
        if (n % 2 == 0)
            return product(temp, temp);
        else
            return product(product(a, temp), temp);
    }
    /* End of Level 1 */

    /**
     * Returns a new number which is positive and a copy of the given number
     *
     * @param a : Num type
     * @return : positive clone of a
     */
    static Num abs(Num a) {
        Num unSigned = a.clone();
        unSigned.setPositive(true);
        return unSigned;
    }

    static Num divide(Num a, Num b) {
        boolean sign = a.isPositive() ^ b.isPositive();
        a = a.isPositive() ? a : abs(a);
        b = b.isPositive() ? b : abs(b);
        Num quotient = reminderOrQuotient(a, b, true);
        quotient.setPositive(!sign);
        return quotient;
    }


    /* Start of Level 2 */
    static Num reminderOrQuotient(Num a, Num b, boolean isQuotient) {
        int compareTo;
        if (b.isZero()) {
            throw new IllegalArgumentException("Argument divisor is zero");
        }
        compareTo = a.compareTo(b);
        if (compareTo < 0) {
            return isQuotient ? new Num(0) : a.clone();
        }
        if (compareTo == 0) {
            return isQuotient ? new Num(1) : new Num(0);
        }
        Iterator<Long> itr = ((LinkedList<Long>) a.numbers).descendingIterator();
        Num quotient = new Num();
        Num dividend = new Num(0);
        while (itr.hasNext()) {
            dividend.addFront(itr.next());
            dividend.trim();
            compareTo = dividend.compareTo(b);
            if (compareTo < 0 || dividend.isZero()) {
                quotient.shiftLeft(1L);
            } else if (compareTo == 0) {
                quotient.addFront(1L);
                dividend = new Num(0);
            } else {
                Num multiplier = getMultiplicant(new Num(a.base), new Num(1), b, dividend);
                Num divisor = product(b, multiplier);
                dividend = unsignedSub(dividend, divisor);
                quotient.shiftLeft((long) multiplier.getSize());
                quotient = unsignedAdd(quotient, multiplier);
            }
        }
        if (!quotient.isZero()) {
            quotient.trim();
        }
        if (a.isPositive() ^ b.isPositive())
            quotient.setPositive(false);
        return isQuotient ? quotient : dividend;
    }

    static Num divideR(Num a, Num b) {
        if (b.isZero())
            throw new IllegalArgumentException("Divisor is zero");
        if (a.getSize() < String.valueOf(b).length()) {
            return new Num(0);
        }
        Iterator<Long> itr = ((LinkedList<Long>) a.numbers).descendingIterator();
        Num quotient = new Num();
        Num dividend = new Num();
        while (itr.hasNext()) {
            while (itr.hasNext()) {
                int lengt = dividend.numbers.size();
                long gr = itr.next();
                if (lengt != 0 || gr != 0)
                    ((LinkedList<Long>) dividend.numbers).addFirst(gr);
                if (dividend.compareTo(b) >= 0)
                    break;
                ((LinkedList<Long>) quotient.numbers).addFirst(0L);
            }
            if (dividend.numbers.size() >= b.numbers.size()) {
                dividend.trim();
                for (long i = a.base; i >= 1; i--) {
                    Num c = product(b, new Num(i));
                    if (c.compareTo(dividend) <= 0) {
                        ((LinkedList<Long>) quotient.numbers).addFirst(i);
                        if (c.compareTo(dividend) == 0)
                            dividend = new Num();
                        else
                            dividend = subtract(dividend, c);
                        break;
                    }
                }
            }
            dividend.trim();
        }
        quotient.trim();
        return (quotient);
    }

    /**
     * Does a binary search and returns the multiplier which is just less than the dividend.
     *
     * @param high     : usually equal to base
     * @param low      : usually equal to 1
     * @param divisor  ;  the number which is the divisor
     * @param dividend : the number to be divided
     * @return : Num
     */
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
            return new Num(1, a.base);
        temp = power(a, divide(n, 2));
        if (mod(n, new Num(2, a.base)).isZero())
            return product(temp, temp);
        else
            return product(product(a, temp), temp);
    }

    public Iterator<Long> iterator() {
        return numbers.iterator();
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
        if (base == Num.defaultBase) {
            for (Long numb : numbers) {
                output.append(numb);
            }
        } else {
            Num x = new Num(0, Num.defaultBase);
            Num numBase = new Num(base, Num.defaultBase);
            Iterator<Long> iterator = ((LinkedList<Long>) numbers).descendingIterator();
            while (iterator.hasNext()) {
                x = add(product(x, numBase), new Num(iterator.next(), Num.defaultBase));
            }
            for (Long numb : x.numbers) {
                output.append(numb);
            }
        }
        if (!isPositive()) {
            output.append("-");
        }
        return output.reverse().toString();
    }

    public long base() {
        return base;
    }

    public void setBase(long base) {
        if (base >= minSupportedBase && base <= maxSupportedBase)
            this.base = base;
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
