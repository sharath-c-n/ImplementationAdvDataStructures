package cs6301.g26;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;

/**
 * Created by Ankitha on 8/31/2017.
 */
public class IterativeMergeSort {

    private static class StackFrame {
        int  left , right;
        boolean isMerge;

        public StackFrame(boolean isMerge, int left, int right) {
            this.isMerge = isMerge;
            this.left = left;
            this.right = right;
        }
    }

    private static void mergeSort(int[] arr) {
        Deque<StackFrame> stack = new ArrayDeque<StackFrame>();
        stack.push(new StackFrame(false,0,arr.length-1));
        while(!stack.isEmpty()){

                StackFrame stackObj = stack.pop();
                if(stackObj.isMerge){
                    merge(arr,stackObj.left,stackObj.right);
                }
                else{
                    if(stackObj.left<stackObj.right){
                        int mid = stackObj.left+(stackObj.right-stackObj.left)/2;
                        stack.push(new StackFrame(true,stackObj.left,stackObj.right));
                        stack.push(new StackFrame(false,stackObj.left,mid));
                        stack.push(new StackFrame(false,mid+1 ,stackObj.right));
                    }

                }

        }
    }

    private static void merge(int[] arr, int left, int right) {
        int tmp[] = new int[right-left+1];
        int mid = left+ (right-left)/2;
        int i = left, j = mid+1;

        int k = 0;
        while (i <= mid && j <= right)
        {
            if (arr[i] <= arr[j])
            {
                tmp[k] = arr[i];
                i++;
            }
            else
            {
                tmp[k] = arr[j];
                j++;
            }
            k++;
        }
        //copy the remaining elements
        while (i <= mid)
        {
            tmp[k] = arr[i];
            i++;
            k++;
        }

        while (j <= right)
        {
            tmp[k] = arr[j];
            j++;
            k++;
        }

        j=0;
        for(i=left;j<k;i++){
            arr[i] = tmp[j++];
        }
    }



    public static void main(String args[]){
        int arr[] = {10,9,8,7,6,5,4,3,2,1};
        mergeSort(arr);
        Arrays.stream(arr).forEach(System.out::println);
    }

}
