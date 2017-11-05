package cs6301.g26;

import cs6301.g00.Shuffle;

import java.util.Map;
import java.util.Random;
import java.util.TreeMap;

/**
 * Created by Ankitha on 10/29/2017.
 */
public class SkipListDriver {
    public static void main(String[] args) {
        addRemoveTest();
        //reArrangetest(skipList);
    }


    private static void reArrangetest(SkipList<Integer> skipList) {
        int size = 10000;
        int[] arr = new int[size];
        for (int i = 1; i < size; i++) {
            arr[i] = i;
        }
        Shuffle.shuffle(arr);
        for (int x : arr) {
            skipList.add(x);
        }
        skipList.reBuild();
        Shuffle.shuffle(arr);
        for (Integer i : arr) {
            if(i == size-1)
                break;
            if (skipList.get(i) != (i+1)) {
                System.out.println("Not in sorted order!!!" + i + "::" + skipList.get(i));
                break;
            }
        }
    }

    public static void addRemoveTest() {
        int size = 800000;
        int[] arr = new int[size];
        for (int i = 0; i < size; i++) {
            arr[i] = i;
        }
        Shuffle.shuffle(arr);
        SkipList<Integer> skipList = new SkipList<>();
        TreeMap<Integer,Integer> treeMap = new TreeMap<>();
        Random random = new Random();
        for (int x : arr) {
            skipList.add(x);
            treeMap.put(x,x);
        }
        int prev = skipList.get(0);
        for (Integer i : skipList) {
            if (prev > i) {
                System.out.println("Not in sorted order!!!" + prev + "::" + i);
                break;
            }
            prev = i;
        }
        System.out.println("In sorted order!!!" + prev + " " + skipList.last());
        int[] removeList = new int[size/2];
        for (int i = 0; i < size/2; i++) {
            removeList[i] = random.nextInt(size/2);
        }
        Shuffle.shuffle(removeList);
        for (int x : removeList) {
            skipList.remove(x);
            treeMap.remove(x);
        }
        if(findSum(skipList)!=findSum2(treeMap)){
            System.out.println("Failed test case");
        }
        else
            System.out.println("Proper");
    }

    private static int findSum(SkipList<Integer> skipList) {
        int sum =0;
        for (Integer i : skipList){
            sum+=i;
        }
        return sum;
    }
    private static int findSum2(TreeMap<Integer,Integer> skipList) {
        int sum =0;
        for (Map.Entry<Integer,Integer> i : skipList.entrySet()){
            sum+=i.getValue();
        }
        return sum;
    }
}
