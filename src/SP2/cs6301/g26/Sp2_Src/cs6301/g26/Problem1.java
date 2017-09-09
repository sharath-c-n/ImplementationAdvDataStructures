package cs6301.g26;

import java.util.ArrayList;
import java.util.ListIterator;
import java.util.List;
import java.util.Scanner;

public class Problem1 {

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

    public static <T extends Comparable<? super T>> void union(List<T> l1, List<T> l2, List<T> outList) {
        ListIterator<T> it1 = l1.listIterator();
        ListIterator<T> it2 = l2.listIterator();
        T x1 = next(it1);
        T x2 = next(it2);
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
        while (x1 != null) {
            addTo(outList, x1);
            x1 = next(it1);
        }
        while (x2 != null) {
            addTo(outList, x2);
            x2 = next(it1);
        }
    }

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
        while (x1 != null) {
            addTo(outList, x1);
            x1 = next(it1);
        }

    }

    private static <T extends Comparable<? super T>> T next(ListIterator<T> it) {
        return (it.hasNext() ? it.next() : null);
    }
  //Helper function that takes care when input contains duplicates
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
        intersect(list1, list2, output);
        for (int val : output) {
            System.out.print(val + " ");
        }
        System.out.print("\n");
        output = new ArrayList<>();
        union(list1, list2, output);
        for (int val : output) {
            System.out.print(val + " ");
        }
        System.out.print("\n");

        output = new ArrayList<>();
        difference(list1, list2, output);
        for (int val : output) {
            System.out.print(val + " ");
        }
        System.out.print("\n");
    }
}
