/**
 * @author Sandeep on 10/20/2017
 */
package cs6301.g26;

import java.util.TreeMap;
import java.util.TreeSet;

public class LongestStreak {

    // Given an array A of integers, find the length of a longest streak of
    //consecutive integers that occur in A (not necessarily contiguously):
    static int longestStreak(int[] A) { // RT = O(nlogn).
        int ans = 0;
        TreeSet<Integer> visited = new TreeSet<>();
        TreeMap<Integer, Integer> StartingStreak = new TreeMap<>();
        TreeMap<Integer, Integer> EndingStreak = new TreeMap<>();
        for (int val : A) {
            if (!visited.contains(val)) {
                Integer endVal = (EndingStreak.get(val - 1));
                Integer startVal = StartingStreak.get(val + 1);
                endVal = (endVal == null) ? 0 : endVal;
                startVal = (startVal == null) ? 0 : startVal;
                StartingStreak.put(val - endVal, startVal + 1 + endVal);
                EndingStreak.put(val + startVal, startVal + 1 + endVal);
                ans = Math.max(ans, startVal + 1 + endVal);
                visited.add(val);
            }
        }
        return ans;
    }

    public static void main(String[] args) {
        int[] A = {1, 7, 9, 4, 1, 7, 4, 8, 7, 1, 5, 10,6};
        int res = longestStreak(A);
        System.out.println("The Longest Streak is " + res);
    }
}
