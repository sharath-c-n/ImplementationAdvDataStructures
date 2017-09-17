package cs6301.g26;// G26

public class LP1L2 {
    public static void main(String[] args) {
	Num x = new Num("32437274782774");
	Num y = new Num("100000");
	Num z = Num.divide(x, y);

	System.out.println(z);
	System.out.println(x);
	x.printList();
    }
}
