package cs6301.g26;


import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * Created by Ankitha on 9/1/2017.
 * This is the driver class for recursive and iterative reversal of the singly linked list.
 */
public class Problem4 {
    public static void main(String[] args) throws NoSuchElementException {
        int n = 10;
        if(args.length > 0) {
            n = Integer.parseInt(args[0]);
        }

        SinglyLinkedList<Integer> lst = new SinglyLinkedList<>();
        for(int i=1; i<=n; i++) {
            lst.add(i);
        }
        lst.printList();
        Iterator<Integer> it = lst.iterator();
        Scanner in = new Scanner(System.in);
        whileloop:
        while(in.hasNext()) {
            int com = in.nextInt();
            switch(com) {
                case 1:  // Move to next element and print it
                    if (it.hasNext()) {
                        System.out.println(it.next());
                    } else {
                        break whileloop;
                    }
                    break;
                case 2:  // Remove element
                    it.remove();
                    lst.printList();
                    break;
                case 3: //Reverse List
                    lst.recReverse();
                    lst.printList();
                    break;
                case 4: //Iterative reverse
                    lst.itrReverse();
                    lst.printList();
                    break;
                default:  // Exit loop
                    break whileloop;
            }
        }
        lst.printList();
    }
}



