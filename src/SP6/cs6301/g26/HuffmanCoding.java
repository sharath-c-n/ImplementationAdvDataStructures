package cs6301.g26;

import sun.net.www.http.Hurryable;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * HuffmanCoding:
 * @author : Sharath
 * 03/10/2017
 */
public class HuffmanCoding {
    public static class HuffmanNode {
        Character  c;
        int frequency;
        HuffmanNode left,right;

        public HuffmanNode(Character c, int frequency) {
            this.c = c;
            this.frequency = frequency;
        }
    }

    public static HuffmanNode buildTree(HuffmanNode[] input){
        PriorityQueue<HuffmanNode> pq = new PriorityQueue<>(input.length, Comparator.comparingInt(x->x.frequency));
        for(HuffmanNode node : input)
            pq.add(node);
        while(pq.size() > 1){
            HuffmanNode node1 = pq.poll();
            HuffmanNode node2 = pq.poll();
            HuffmanNode newNode = new HuffmanNode(null,node1.frequency+node2.frequency);
            newNode.left = node1;
            newNode.right = node2;
            pq.add(newNode);
        }
        return pq.poll();
    }

    public static void printCode(HuffmanNode root){
        ArrayList<Integer> arr = new ArrayList<>();
        printRecursive(root,arr,0);
    }

    private static void printRecursive(HuffmanNode root, ArrayList<Integer> arr,int endIdx) {
        if(root.c != null){
            printCode(root.c,arr,endIdx);
            return ;
        }
        if(root.left!=null){
            arr.add(endIdx,0);
            printRecursive(root.left,arr,endIdx+1);
        }
        if(root.right!=null){
            arr.add(endIdx,1);
            printRecursive(root.right,arr,endIdx+1);
        }
    }

    private static void printCode(Character c,ArrayList<Integer> arr, int endIdx) {
        System.out.print(c+" : ");
        for(int i =0;i<endIdx;i++)
            System.out.print(arr.get(i)+" ");
        System.out.println();
    }

}
