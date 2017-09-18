package cs6301.g26;// G26

import com.sun.javafx.image.BytePixelSetter;

import java.math.BigInteger;
public class LP1L2 {
    public static void main(String[] args) {
	Num x = new Num("-1");
	Num y = new Num("5");
	Num z = Num.power(x,y);
   BigInteger a= new BigInteger(x.toString());
   BigInteger b= new BigInteger(y.toString());
   BigInteger c = a.mod(b);
	System.out.println(z);
	System.out.println(c);
	System.out.println(y);
	if(c.compareTo(new BigInteger(z.toString()))==0)
		System.out.println("EQUAL");
		else
		System.out.println("Not Equal");
	x.printList();
    }
}
