// Sample code for Level 2 driver for lp1

// Change following line to your group number

public class LP1L2 {
    public static void main(String[] args) {
	Num x = new Num(992);
	Num y = new Num("81");
	Num z = Num.add(x, y);
	System.out.println(z);
	Num temp= Num.divide(x,y);
	System.out.println(temp);
	Num a = Num.power(x, y);
	System.out.println(a);
	z.printList();
    }
}
