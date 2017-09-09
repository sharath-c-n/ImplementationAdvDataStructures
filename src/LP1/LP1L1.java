// Sample code for Level 1 driver for lp1

// Change following line to your group number

import java.math.BigInteger;

public class LP1L1 {
    public static void main(String[] args) {
        Num x = new Num(998);
        Num y = new Num("999");
        Num z = Num.subtract(x, y);
        System.out.println(z);
        System.out.println(Num.defaultBase);
        System.out.println(y.compareTo(x));
    /*Num a = Num.power(x, 8);
	System.out.println(a);
	z.printList();
    }
    */
    }
}
