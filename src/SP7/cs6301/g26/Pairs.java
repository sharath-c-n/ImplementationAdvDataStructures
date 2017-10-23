/**
 * @author Sandeep on 10/20/2017
 */
package cs6301.g26;

import java.util.Map;
import java.util.TreeMap;

public class Pairs {
    // RT = O(nlogn).
    // How many indexes i,j (with i != j) are there with A[i] + A[j] = X?
    // A is not sorted, and may contain duplicate elements
    // If A = {3,3,4,5,3,5} then howMany(A,8) returns 6
    public static int howMany(int[] A, int X) {
        TreeMap<Integer, Integer> count = new TreeMap<>(); //count TreeMap to store the count of each Value
        for (int val : A) {
            if (count.containsKey(val))
                count.put(val, count.get(val) + 1);
            else
                count.put(val, 1);
        }
        int pairCount = 0;
        for (Map.Entry<Integer, Integer> vd : count.entrySet()) {
            int val = vd.getKey();
            int c = vd.getValue();
            //If a same element forms pair compute the number of combinations of taking two pairs
            if ((val * 2) == X) {
                //compute nc2 ( n choose 2)
                pairCount += (c * (c - 1)) / 2;
            } else {
                if (count.containsKey(X - val) && (X - val) > val)
                    pairCount += count.get(X - val) * count.get(val);
            }
        }
        return pairCount;
    }

    public static void main(String[] args) {
        int A[] = {3, 3, 4, 5, 3, 5};
        int res = howMany(A, 8);
        System.out.println(res);
    }
}
