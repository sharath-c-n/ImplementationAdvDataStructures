package cs6301.g26;

import cs6301.g00.Shuffle;

import java.util.Random;
import java.util.Scanner;

/**
 * Created by Ankitha on 10/29/2017.
 */
public class SkipListDriver {
    public static void main(String[] args) {
       /* SkipList<Integer> skipList = new SkipList<>();
        Scanner in = new Scanner(System.in);
       while(in.hasNext()){
            int x = in.nextInt();
            testor(x, skipList);
        }*/
       addRemoveTest();
    }

    public static void addRemoveTest() {
        int size = 10001;
        int[] arr = new int[size];
        for (int i = 0; i < size; i++) {
            arr[i] = i;
        }
        Shuffle.shuffle(arr);
        SkipList<Integer> skipList = new SkipList<>();
        for (int x : arr) {
            testor(x, skipList);
        }
        System.out.print("Iterator : ");
        int prev = skipList.get(0);
        System.out.println("Prev: " + prev);
        for (Integer i : skipList) {
            if (prev > i) {
                System.out.println("Not in sorted order!!!" + prev + "::" + i);
                break;
            }
            prev = i;
        }
        System.out.println("In sorted order!!!" + prev + " " + skipList.last());
        int[] removeList = new int[50];
        for (int i = 0; i < 50; i++) {
            removeList[i] = i;
        }
        Shuffle.shuffle(removeList);
        for (int x : removeList) {
            skipList.remove(x);
        }
        Shuffle.shuffle(arr);
        System.out.println("First:" + skipList.get(9950));
        for (int x : arr) {
            if (x < size - 50 && skipList.get(x) != x + 50) {
                System.out.println("Not matching : " + x + "==" + skipList.get(x));
                break;
            }
            if (x < size - 50)
                System.out.println("Matching : " + (x + 50) + "==" + skipList.get(x));

        }
        /*for(int x : arr){
            testor(-x, skipList);
            if(skipList.size() == 0)
                break;
            prev = skipList.get(0);
            for (Integer i : skipList) {
                if(prev > i)
                {
                    System.out.println("Not in sorted order!!!" + prev +"::"+i);
                    break;
                }
                prev = i;
            }
        }*/
        System.out.println("In sorted order!!!" + skipList.size());
    }

    public static void testor(int x, SkipList<Integer> skipList) {
        if (x > 0) {
            // System.out.print("Add " + x + "  ");
            skipList.add(x);
        } else if (x < 0) {
            System.out.print("Remove " + x + "  ");
            // skipList.remove(-x);
        }

    }
}
