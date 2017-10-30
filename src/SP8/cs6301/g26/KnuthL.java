package cs6301.g26;

/**
 * KnuthL:
 *
 * @author : Sharath
 * 29/10/2017
 */
public class KnuthL{
    static public <T extends Comparable<? super T>>  void knuthL(T[] arr){
        printArr(arr);
        out:
        while (true) {
            int j = arr.length - 2;
            while (arr[j].compareTo(arr[j+1]) >= 0) {
                if (j == 0)
                    break out;
                j--;
            }
            int l = arr.length - 1;
            while (arr[j].compareTo(arr[l]) >= 0) {
                l--;
            }
            swap(arr,j, l);

            int k = j + 1;
            l = arr.length - 1;
            while (k < l) {
                swap(arr,k, l);
                k++;
                l--;
            }
            printArr(arr);
        }
    }

    static public <T>  void swap(T[] arr,int i, int j){
        T temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }
    static public <T> void printArr(T[] arr){
        for(T i : arr){
            System.out.print(i + " ");
        }
        System.out.println();
    }

    public static void main(String args[]){
        Integer[] arr = {1,2,2,3,3,4};
        KnuthL.knuthL(arr);
    }
}
