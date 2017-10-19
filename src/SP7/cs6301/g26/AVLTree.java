
/**
 * Starter code for AVL Tree
 */
package SP7.cs6301.g26;

import java.util.Scanner;

public class AVLTree<T extends Comparable<? super T>> extends BST<T> {
    static class Entry<T> extends BST.Entry<T> {
        int height;

        Entry(T x, Entry<T> left, Entry<T> right) {
            super(x, left, right);
            height = 0;
        }

        Entry(T x) {
            super(x);
            height = 0;
        }

        @Override
        public Entry<T> getLeft() {
            return (Entry<T>) super.getLeft();
        }

        @Override
        public Entry<T> getRight() {
            return (Entry<T>) super.getRight();
        }

    }

    AVLTree() {
        super();
    }

    // A utility function to right rotate subtree rooted with y
    // See the diagram given above.
    Entry<T> rightRotate(Entry<T> node) {
        Entry<T> left = (Entry<T>) node.left;
        Entry<T> right = (Entry<T>) left.right;

        // Perform rotation
        left.right = node;
        node.left = right;

        // Update heights
        node.height = getMaxHeight(node);
        left.height = getMaxHeight(left);

        // Return new root
        return left;
    }

    private int getHeight(Entry<T> node){
        return node == null ? 0: node.height;
    }

    private int getMaxHeight(Entry<T> node) {
        return Math.max(getHeight(node.getRight()), getHeight(node.getLeft())) + 1;
    }

    // A utility function to left rotate subtree rooted with x
    // See the diagram given above.
    private Entry<T> leftRotate(Entry<T> node) {
        Entry<T> right = node.getRight();
        Entry<T> left = right.getLeft();

        // Perform rotation
        right.left = node;
        node.right = left;

        //  Update heights
        node.height = getMaxHeight(node);
        right.height = getMaxHeight(right);

        // Return new root
        return right;
    }

    int getBalance(Entry<T> node) {
        if (node == null)
            return 0;
        return getHeight(node.getLeft()) - getHeight(node.getRight());
    }

    Entry<T> insert(Entry<T> node, T key) {

        /* 1.  Perform the normal BST insertion */
        if (node == null) {
            size++;
            return (new Entry<>(key));
        }

        if (key.compareTo(node.getValue()) < 0)
            node.left = insert(node.getLeft(), key);
        else if (key.compareTo(node.getValue()) > 0)
            node.right = insert(node.getRight(), key);
        else // Duplicate
        {
            node.element = key;
            return node;
        }

        /* 2. Update height of this ancestor node */
        node.height = getMaxHeight(node);

        /* 3. Get the balance factor of this ancestor
              node to check whether this node became
              unbalanced */
        int balance = getBalance(node);

        // If this node becomes unbalanced, then there
        // are 4 cases Left Left Case
        if (balance > 1 && key.compareTo(node.left.getValue()) < 0)
            return rightRotate(node);

        // Right Right Case
        if (balance < -1 && key.compareTo(node.right.getValue()) > 0)
            return leftRotate(node);

        // Left Right Case
        if (balance > 1 && key.compareTo(node.left.getValue()) > 0) {
            node.left = leftRotate(node.getLeft());
            return rightRotate(node);
        }

        // Right Left Case
        if (balance < -1 && key.compareTo(node.right.getValue()) < 0) {
            node.right = rightRotate(node.getRight());
            return leftRotate(node);
        }
        /* return the (unchanged) node pointer */
        return node;
    }

    public boolean add(T x) {
        int previousSize = size;
        root = insert((Entry<T>) root, x);
        return size > previousSize;
    }


    Entry<T> deleteNode(Entry<T> root, T key)
    {
        // STEP 1: PERFORM STANDARD BST DELETE
        if (root == null)
            return root;

        // If the key to be deleted is smaller than
        // the root's key, then it lies in left subtree
        if (key.compareTo(root.getValue()) < 0)
            root.left = deleteNode(root.getLeft(), key);

            // If the key to be deleted is greater than the
            // root's key, then it lies in right subtree
        else if (key.compareTo(root.getValue()) > 0)
            root.right = deleteNode(root.getRight(), key);

            // if key is same as root's key, then this is the node
            // to be deleted
        else
        {

            // node with only one child or no child
            if ((root.left == null) || (root.right == null))
            {
                Entry<T> temp = null;
                if (temp == root.getLeft())
                    temp = root.getRight();
                else
                    temp = root.getLeft();

                // No child case
                if (temp == null)
                {
                    root = null;
                }
                else   // One child case
                    root = temp; // Copy the contents of
                // the non-empty child
                size--;
            }
            else
            {

                // node with two children: Get the inorder
                // successor (smallest in the right subtree)
                Entry<T> temp = (Entry<T>) min(root.getRight());

                // Copy the inorder successor's data to this node
                root.element = temp.element;

                // Delete the inorder successor
                root.right = deleteNode(root.getRight(), temp.element);
            }
        }

        // If the tree had only one node then return
        if (root == null)
            return root;

        // STEP 2: UPDATE HEIGHT OF THE CURRENT NODE
        root.height = getMaxHeight(root);

        // STEP 3: GET THE BALANCE FACTOR OF THIS NODE (to check whether
        //  this node became unbalanced)
        int balance = getBalance(root);

        // If this node becomes unbalanced, then there are 4 cases
        // Left Left Case
        if (balance > 1 && getBalance(root.getLeft()) >= 0)
            return rightRotate(root);

        // Left Right Case
        if (balance > 1 && getBalance(root.getLeft()) < 0)
        {
            root.left = leftRotate(root.getLeft());
            return rightRotate(root);
        }

        // Right Right Case
        if (balance < -1 && getBalance(root.getRight()) <= 0)
            return leftRotate(root);

        // Right Left Case
        if (balance < -1 && getBalance(root.getRight()) > 0)
        {
            root.right = rightRotate(root.getRight());
            return leftRotate(root);
        }

        return root;
    }

    public T remove(T x) {
        int previousSize = size;
        root = deleteNode((Entry<T>) root, x);
        return size < previousSize ? x : null;
    }

    public static void main(String[] args) {
        BST<Integer> t = new AVLTree<>();
        Scanner in = new Scanner(System.in);
        while (in.hasNext()) {
            int x = in.nextInt();
            if (x > 0) {
                System.out.print("Add " + x + " : ");
                t.add(x);
                t.printTree();
            } else if (x < 0) {
                System.out.print("Remove " + x + " : ");
                t.remove(-x);
                t.printTree();
            } else {
                Comparable[] arr = t.toArray();
                System.out.print("Final: ");
                for (int i = 0; i < t.size; i++) {
                    System.out.print(arr[i] + " ");
                }
                System.out.println();
                break;
            }
        }
        System.out.print("Iterator : ");
        for (int i : t) {
            System.out.print(i + " ");
        }
        System.out.println("\nMin : " + t.min());
        System.out.println("Max : " + t.max());
    }

}

