package cs6301.g26;

public class DLLBST {
    DoublyLinkedList list;
    DoublyLinkedList.Entry head;
    DoublyLinkedList.Entry  root;
    DoublyLinkedList.Entry  prev;
    // Precondition: list is sorted
    void sortedListToBST() {
        head = list.head.next;
        root = sortedListToBST(list.size);
    }

    /**
     * Converts a sorted list into balanced binary tree
     * @param n : sorted list
     * @return : The root of the tree
     */
    private DoublyLinkedList.Entry sortedListToBST(int n) {
        if (n == 0){
            return null;
        }

        // Recursively construct the left subtree
        DoublyLinkedList.Entry left = sortedListToBST(n / 2);
        // head_ref now refers to middle node, make middle node as root of BST
        DoublyLinkedList.Entry root = head;
        // Set pointer to left subtree
        root.prev = left;
        // Change head pointer of Linked List for parent recursive calls
        head = head.next;
        //Recursively construct the right subtree and link it with root. The number of nodes in right subtree  istotal nodes - nodes in left subtree - 1 (for root)
        root.next = sortedListToBST(n - n / 2 - 1);
        return root;
    }

    // Precondition: data is arranged as a binary search tree
    //	using prev for left, and next for right
    void BSTtoSortedList() {
        BSTTosortedList(root);
    }

    /**
     * Converts the input BST into a sorted list
     * @param root : root off the BST
     */
    private void BSTTosortedList(DoublyLinkedList.Entry root) {
        if (root == null)
            return;

        // Recursively convert left subtree
        BSTTosortedList(root.prev);
        // convert the current node
        if (prev == null)
            head = root;
        else
        {
            root.prev = prev;
            prev.next = root;
        }
        prev = root;

        // Convert  the right subtree
        BSTTosortedList(root.next);
    }

    /**
     * In-order traversal of the tree
     * @param root : root node of the tree
     */
    private void printTree(DoublyLinkedList.Entry root) {
        if (root == null)
            return;
        printTree(root.prev);
        System.out.print(root.element + " ");
        printTree(root.next);
    }

    public static void main(String args[]){
        int startIdx = 1;
        int endIdx = 9;
        DoublyLinkedList list = new DoublyLinkedList();
        for(int i = startIdx ; i<=endIdx ; i++){
            list.add(i);
        }
        DLLBST dlList = new DLLBST();
        dlList.list = list;
        dlList.sortedListToBST();
        dlList.printTree(dlList.root);
        dlList.BSTtoSortedList();
        dlList.sortedListToBST();
    }

}
