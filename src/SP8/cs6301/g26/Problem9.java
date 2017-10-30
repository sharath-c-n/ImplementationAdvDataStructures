package cs6301.g26;

/**
 * Problem9:
 * @author : Sharath
 * 29/10/2017
 */
public class Problem9 extends KnuthL {
    //algorithm given at https://en.wikipedia.org/wiki/Heap%27s_algorithm
    public static <T> void generate(T[] arr) {
        int[] c = new int[arr.length];
        printArr(arr);
        int i = 0;
        while (i < arr.length) {
            if (c[i] < i) {
                if (i % 2 == 0) {
                    swap(arr, 0, i);
                } else {
                    swap(arr, c[i], i);
                }
                printArr(arr);
                c[i]++;
                i = 0;
            } else {
                c[i] = 0;
                i++;
            }
        }
    }

    public static void main(String args[]){
        int n = 5;
        Integer[] arr = new Integer[n];
        for(int i=0;i<n;i++){
            arr[i] = i;
        }
        generate(arr);
    }
}
