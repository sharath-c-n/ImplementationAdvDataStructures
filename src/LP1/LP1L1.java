// Sample code for Level 1 driver for lp1

// Change following line to your group number


import java.math.BigInteger;
import java.util.Random;

public class LP1L1 {
    public static void main(String[] args) {
        //4554496762895730
       Num x = new Num("46767476439492787877890089895438975384");
       Num y = new Num("46767476439492787877890089895438975384");
       Num z = Num.product(x, y);
       // x.printList();
        System.out.println(Num.divide(z,x));
        System.out.println(Num.divideR(z,x));
        System.out.println(x);
       /* StringBuilder numerator = new StringBuilder();
        StringBuilder  denominator = new StringBuilder();

        Random random = new Random();

        for (int i = 0; i < Math.pow(10, 6); i++)
            numerator.append(random.nextInt(9));

        for(int i =0; i< Math.pow(10,3);i++)
            denominator.append(random.nextInt(9));


        Num a = new Num(numerator.toString());
       // Num b = new Num(denominator.toString());
        String op = a.toString();
        System.out.println(op);
        System.out.println(denominator.toString());
        System.out.println(op.equals(denominator.toString()));*/
        /*Num c = new Num(denominator.toString());
        System.out.println(Num.divideR(a, c).compareTo(
                new Num(new BigInteger(numerator.toString()).divide(
                        new BigInteger(denominator.toString())).toString())));*/


        /*char[] vv = new char[1000006];
        char [] vv1= new char [1003];
        final int ss = 10;
        for (int i = 0; i < 1000006; i++) {
            Random rand = new Random();
            if (i == 0)
                vv[i] = Character.forDigit((rand.nextInt(9) + 1), ss);
            else
                vv[i] = Character.forDigit((rand.nextInt(10)), ss);
        }
        for (int i = 0; i < 1003; i++) {
            Random rand = new Random();
            if (i == 0)
                vv1[i] = Character.forDigit((rand.nextInt(9) + 1), ss);
            else
                vv1[i] = Character.forDigit((rand.nextInt(10)), ss);
        }
        BigInteger a = new BigInteger(new String(vv));
        Num x = new Num(new String(vv));
        Num y = new Num(new String(vv1));
        BigInteger b = new BigInteger(new String(vv1));
        Num z = Num.add(x, y);
        System.out.println(" Addition result is  ");
        System.out.println(z);
        System.out.println("Started Big Integer division");
        BigInteger c = a.divide(b);
        System.out.println("Started Num division");
        Num temp = Num.divideR(x, y);
        System.out.println("I am here");
        Num sd = new Num(c.toString());
        System.out.println("Quotient from Big Integer and Num");
        if (temp.compareTo(sd) == 0)
            System.out.println("BOTH ARE EQUAL");
        else
            System.out.println("NOT EQUAL");*/
    }
}
