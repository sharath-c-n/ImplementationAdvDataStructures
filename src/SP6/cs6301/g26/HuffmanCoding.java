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
    /**
     * Tree node for huffmanTree
     */
    public static class HuffmanNode {
        Character  c;
        int frequency;
        HuffmanNode left,right;

        public HuffmanNode(Character c, int frequency) {
            this.c = c;
            this.frequency = frequency;
        }
    }

    /**
     * Implementation of Huffman algorithm
     * @param input
     * @return
     */
    public static HuffmanNode buildTree(HuffmanNode[] input){
        PriorityQueue<HuffmanNode> pq = new PriorityQueue<>(input.length, Comparator.comparingInt(x->x.frequency));
        //Adding all nodes to the priority queue
        for(HuffmanNode node : input)
            pq.add(node);
        //loop until priority queue has only one element
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

    //Public function to print the codes and character
    public static void printCode(HuffmanNode root){
        ArrayList<Integer> arr = new ArrayList<>();
        printRecursive(root,arr,0);
    }

    //Prints only the leaf nodes
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

    //Print the character followed by its frequency
    private static void printCode(Character c,ArrayList<Integer> arr, int endIdx) {
        System.out.print(c+" : ");
        for(int i =0;i<endIdx;i++)
            System.out.print(arr.get(i)+" ");
        System.out.println();
    }

}
