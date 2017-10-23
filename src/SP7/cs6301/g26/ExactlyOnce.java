/**
 * @author Sandeep on 10/20/2017
 */
package cs6301.g26;

import java.util.*;

public class ExactlyOnce{
    //Given an array A, return an array B that has those elements of A that
    // occur exactly once, in the same order in which they appear in A:
    // Ex: A = {6,3,4,5,3,5}.  exactlyOnce(A) returns {6,4}
    static <T extends Comparable<? super T>> T[] exactlyOnce(T[] A) { // RT = O(nlogn).
        TreeMap<T, Integer> valIndexMap = new TreeMap<>(); //TreeMap to store Val Index key value pairs
        TreeMap<Integer, T> indexValMap = new TreeMap<>(); //TreeMap to Store Index Val key value pair.
        TreeSet<T> mark = new TreeSet<>(); //Mark the visited duplicate elements
        for (int i = 0; i < A.length; i++) {
            T val = A[i];
            if (!valIndexMap.containsKey(val)) {
                valIndexMap.put(val, i);
                indexValMap.put(i, val);
            } else {
                if (!mark.contains(val)) {
                    Integer index = valIndexMap.get(val);
                    if (index != null) {
                        indexValMap.remove(index);
                    }
                    mark.add(val);
                }
            }
        }
        Comparable [] arr = new Comparable[indexValMap.size()];
        int index = 0;
        for (Map.Entry<Integer, T> val : indexValMap.entrySet()) {
            arr[index++] = val.getValue();
        }
        return (T[])arr;
    }

    public static void main(String[] args) {
        Integer[] A = {6, 3, 4, 5, 3, 5};
        Comparable[] res = exactlyOnce(A);
        System.out.println("The Result Array is ");
        for (Comparable val : res)
            System.out.print(val + " ");
    }
}
