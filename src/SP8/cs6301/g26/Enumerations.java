package cs6301.g26;


/**
 * Enumerations:
 * @author : Sharath
 * 29/10/2017
 */
public class Enumerations<T> {
    private T[] arr;
    private int k;
    private int count;
    private int VERBOSE = 0;
    private T[] chosen;

    public Enumerations(T[] arr) {
        this.arr = arr;
    }

    public void setVerbose(int i) {
        VERBOSE = i > 0 ? 1 : 0;
    }

    public void permute(int k) {
        this.k = k;
        count = 0;
        getPermutations(this.k);
        if(VERBOSE == 0)
            System.out.println("Total permutation : " +count);
    }
    public void combination(int k){
        this.k = k;
        count = 0;
        chosen = (T[]) new Object[k];
        getCombination(0,this.k);
        if(VERBOSE == 0)
            System.out.println("Total combination : " +count);
    }

    private void getCombination(int i, int c) {
        if (c == 0) {
            visit(chosen);
        } else {
            chosen[k-c] = arr[i];
            getCombination(i+1,c-1);
            if(arr.length-i > c){
                getCombination(i+1,c);
            }
        }
    }

    private void getPermutations(int c) {
        if (c == 0) {
            visit(arr);
        } else {
            int d = k - c;
            for (int i = d ; i < arr.length; i++) {
                T temp = arr[d];
                arr[d] = arr[i];
                arr[i] = temp;
                getPermutations(c - 1);
                arr[i] = arr[d];
                arr[d] = temp;
            }
        }
    }


    public void visit(T[] chosenArr) {
        count++;
        if(VERBOSE == 1){
            for(int i =0;i<k;i++)
                System.out.print(chosenArr[i]+" ");
            System.out.println();
        }
    }

    public static void main(String args[]){
        int n = 5;
        Integer[] arr = new Integer[n];
        for(int i=0;i<n;i++){
            arr[i] = i;
        }
        Enumerations<Integer> enumerations = new Enumerations<>(arr);
        //enumerations.setVerbose(1);
        enumerations.combination(2);
    }
}

