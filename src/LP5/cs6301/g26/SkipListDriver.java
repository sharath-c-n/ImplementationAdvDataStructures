package cs6301.g26;

import cs6301.g00.Graph;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Created by Ankitha on 10/29/2017.
 */
public class SkipListDriver {
    public static void main(String[] args) {
       SkipList<Integer> skipList = new SkipList();
        Scanner in = new Scanner(System.in);
        while (in.hasNext()) {
            int x = in.nextInt();
            testor(x, skipList);
            for (Integer i : skipList) {
                System.out.print(i + " ");
            }
        }

        System.out.print("Iterator : ");
        for (Integer i : skipList) {
            System.out.print(i + " ");
        }

    }

    public static void testor(int x, SkipList<Integer> skipList) {
        if (x > 0) {
            System.out.print("Add " + x + " : ");
            skipList.add(x);
        } else if (x < 0) {
            System.out.print("Remove " + x + " : ");
            skipList.remove(-x);
        }

    }
}
