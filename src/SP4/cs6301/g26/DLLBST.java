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
        printTree(root);
        System.out.println();
    }

    private void printTree(DoublyLinkedList.Entry root) {
        if (root == null)
            return;
        printTree(root.prev);
        System.out.print(root.element + " ");
        printTree(root.next);
    }


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

    public static void main(String args[]){
        int startIdx = 1;
        int endIdx = 9;
        DoublyLinkedList list = new DoublyLinkedList();
        for(int i = startIdx ; i<=endIdx ; i++){
            list.add(i);
        }
       // list.printList();
        DLLBST dlList = new DLLBST();
        dlList.list = list;
        dlList.sortedListToBST();
        dlList.BSTtoSortedList();
        dlList.sortedListToBST();
        //list.printList();
    }

}
