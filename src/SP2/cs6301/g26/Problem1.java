
/**
 * @author Sandeep on 9/9/2017
 */

package cs6301.g26;

import java.util.ArrayList;
import java.util.ListIterator;
import java.util.List;
import java.util.Scanner;

public class Problem1 {

/*
 * This member function returns the elements common to List l1 and List l2 in sorted order.
 * @param  List l1 and List l2 are input sorted lists.
 * @param List outList returns the common elements in l1 and l2 in sorted order.
 */

    public static <T extends Comparable<? super T>> void intersect(List<T> l1, List<T> l2, List<T> outList) {
        ListIterator<T> it1 = l1.listIterator();
        ListIterator<T> it2 = l2.listIterator();
        T x1 = next(it1);
        T x2 = next(it2);
        while (x1 != null && x2 != null) {
            if (x1.compareTo(x2) < 0)
                x1 = next(it1);
            else if (x1.compareTo(x2) > 0)
                x2 = next(it2);
            else {
                outList.add(x1);
                x1 = next(it1);
                x2 = next(it2);
            }
        }
    }
/*
 * This member function returns the union of List l1,List l2 in the sorted order.
 * @param  List l1 and List l2 are input sorted lists.
 * @param List outList returns union of lists in sorted order.
 */

    public static <T extends Comparable<? super T>> void union(List<T> l1, List<T> l2, List<T> outList) {
        ListIterator<T> it1 = l1.listIterator();
        ListIterator<T> it2 = l2.listIterator();
        T x1 = next(it1);
        T x2 = next(it2);
        // While Loop breaks when one of the List iterators reach the end or both reach the End.
        while (x1 != null && x2 != null) {
            if (x1.compareTo(x2) < 0) {
                addTo(outList, x1);
                x1 = next(it1);
            } else if (x1.compareTo(x2) > 0) {
                addTo(outList, x2);
                x2 = next(it2);
            } else {
                addTo(outList, x1);
                x1 = next(it1);
                x2 = next(it2);
            }
        }
        //When only one of the List Iterator reaches the end Need to copy the other list elements into outList.
        while (x1 != null) {
            addTo(outList, x1);
            x1 = next(it1);
        }
        while (x2 != null) {
            addTo(outList, x2);
            x2 = next(it2);
        }
    }

/*
 * This member function returns the difference of Lists l1 and l2 in the sorted order.
 * @param  List l1 and List l2 are input sorted lists.
 * @param List outList returns l1-l2 i.e the Elements in l1 that are not present in l2.
 */

    public static <T extends Comparable<? super T>> void difference(List<T> l1, List<T> l2, List<T> outList) {
        ListIterator<T> it1 = l1.listIterator();
        ListIterator<T> it2 = l2.listIterator();
        T x1 = next(it1);
        T x2 = next(it2);
        while (x1 != null && x2 != null) {
            if (x1.compareTo(x2) < 0) {
                addTo(outList, x1);
                x1 = next(it1);
            } else if (x1.compareTo(x2) > 0)
                x2 = next(it2);
            else {
                x1 = next(it1);
                x2 = next(it2);
            }
        }
        // When Only second List Iterator reaches the End. Copy the First List to outList
        while (x1 != null) {
            addTo(outList, x1);
            x1 = next(it1);
        }

    }
/*
 * Helper Function to Find the next Element of List.
 * @param List iterator is given as input.
 * @returns an element if Iterator has next otherwise returns Null.
 */

    private static <T extends Comparable<? super T>> T next(ListIterator<T> it) {
        return (it.hasNext() ? it.next() : null);
    }

/* Helper Function to handle the case when Input List contains Duplicate elements.
 * @param outList : output list
 * @param x : value to be added to the outList
 * checks and add an element if the outlist does not contain that Element.
 */

    private static <T extends Comparable<? super T>> void addTo(List<T> outList, T x) {
        if (!outList.isEmpty()) {
            if ((outList.get(outList.size() - 1)) != x)
                outList.add(x);
        } else
            outList.add(x);

    }

    public static void main(String[] args) {
        Scanner inp = new Scanner(System.in);

        List<Integer> list1 = new ArrayList<>();
        List<Integer> list2 = new ArrayList<>();
        List<Integer> output = new ArrayList<>();
        int size1, size2;
        size1 = inp.nextInt();
        size2 = inp.nextInt();
        while ((size1--) > 0) {
            int val = inp.nextInt();
            list1.add(val);
        }
        while ((size2--) > 0) {
            int val = inp.nextInt();
            list2.add(val);
        }
        System.out.println(" Intersections of Lists ");
        intersect(list1, list2, output);
        for (int val : output) {
            System.out.print(val + " ");
        }
        System.out.print("\n");
        System.out.println(" Union of Lists ");
        output = new ArrayList<>();
        union(list1, list2, output);
        for (int val : output) {
            System.out.print(val + " ");
        }
        System.out.print("\n");

        System.out.println(" Difference of Lists ");
        output = new ArrayList<>();
        difference(list1, list2, output);
        for (int val : output) {
            System.out.print(val + " ");
        }
        System.out.print("\n");
    }
}
