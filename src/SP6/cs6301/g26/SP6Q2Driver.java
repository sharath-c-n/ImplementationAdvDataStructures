package cs6301.g26;

/**
 * SP6Q2Driver:
 *
 * @author : Sharath
 * 03/10/2017
 */
public class SP6Q2Driver {
    public static void main(String[] args) {
        char arr[] = {'a', 'b', 'c', 'd', 'e', 'f'};
        int freq[] = {5, 9, 12, 13, 16, 45};
        HuffmanCoding.HuffmanNode [] nodes = new HuffmanCoding.HuffmanNode[arr.length];
        for (int i = 0; i < arr.length; i++) {
                nodes[i] = new HuffmanCoding.HuffmanNode(arr[i],freq[i]);
        }
        HuffmanCoding.HuffmanNode root = HuffmanCoding.buildTree(nodes);
        HuffmanCoding.printCode(root);
    }
}
