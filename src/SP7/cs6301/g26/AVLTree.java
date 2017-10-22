
/**
 * Starter code for AVL Tree
 */
package cs6301.g26;

import java.util.Scanner;

public class AVLTree<T extends Comparable<? super T>> extends BST<T> {
    static class Entry<T> extends BST.Entry<T> {
        int height;

        //Note that the height of the leaf is considered as 1 instead of 0
        Entry(T x, Entry<T> left, Entry<T> right) {
            super(x, left, right);
            height = 1;
        }

        Entry(T x) {
            this(x, null, null);
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

    /**
     * Right rotates the tree about the pivot
     *
     * @param pivot :
     * @return : the new root for the subtree
     */
    Entry<T> rightRotate(Entry<T> pivot) {
        Entry<T> left = pivot.getLeft();
        Entry<T> right = left.getRight();
        // Perform rotation
        left.right = pivot;
        pivot.left = right;

        // Update heights
        updateHeight(pivot);
        updateHeight(left);

        // Return new root
        return left;
    }


    /**
     * Left rotates the tree about the pivot
     * @param pivot :
     * @return : the new root for the subtree
     */
    private Entry<T> leftRotate(Entry<T> pivot) {
        Entry<T> right = pivot.getRight();
        Entry<T> left = right.getLeft();

        // Perform rotation
        right.left = pivot;
        pivot.right = left;

        //  Update heights
        updateHeight(pivot);
        updateHeight(right);

        // Return new root
        return right;
    }

    private int getHeight(Entry<T> node) {
        return node == null ? 0 : node.height;
    }

    private void updateHeight(Entry<T> node) {
        node.height = Math.max(getHeight(node.getRight()), getHeight(node.getLeft())) + 1;
    }

    int getHeightDiff(Entry<T> node) {
        return node == null ? 0 : getHeight(node.getLeft()) - getHeight(node.getRight());
    }

    boolean isBalanced(Entry<T> node) {
        return Math.abs(getHeightDiff(node)) <= 1;
    }

    private Entry<T> balanceNode(Entry<T> node) {
        int heightDiff = getHeightDiff(node);
        /* Left height is greater than right */
        if (heightDiff > 1) {
            /* Left-Right case */
            if (node.left != null && getHeightDiff(node.getLeft()) < 0) {
                node.left = leftRotate(node.getLeft());
            }
            /* for both Left-Left and Left-Right case */
            return rightRotate(node);
        }
        /*Right height is greater than left*/
        else {
            /* Right-Left case*/
            if (node.right != null && getHeightDiff(node.getRight()) > 0) {
                node.right = rightRotate(node.getRight());
            }
            /* for both Right-Left and Right-Right case */
            return leftRotate(node);
        }
    }

    /**
     * Create the tree entry
     *
     * @param x : value of the entry
     * @return new entry
     */
    protected Entry<T> createEntry(T x) {
        return new Entry<>(x);
    }

    /**
     * This function balances the tree after insertion or deletion of the node
     */
    private void balanceTree() {
        if (stack != null) {
            Entry<T> predecessor;
            while (stack.peek() != null) {
                predecessor = (Entry<T>) stack.pop();
                //Balance left node if necessary
                if (!isBalanced(predecessor.getLeft())) {
                    predecessor.left = balanceNode(predecessor.getLeft());
                }
                //Balance right node if necessary
                else if (!isBalanced(predecessor.getRight())) {
                    predecessor.right = balanceNode(predecessor.getRight());
                }
                //Update height of the predecessor
                updateHeight(predecessor);
            }
            //Balance root if necessary
            predecessor = (Entry<T>) root;
            if (!isBalanced(predecessor)) {
                root = balanceNode(predecessor);
            }
        }
    }

    public boolean add(T key) {
        if (!super.add(key)) {
            return false;
        }
        balanceTree();
        return true;
    }

    public T remove(T key) {
        T removedKey = super.remove(key);
        balanceTree();
        return removedKey;
    }

    public static void main(String[] args) {
        AVLTree<Integer> t = new AVLTree<>();
        Scanner in = new Scanner(System.in);
        while (in.hasNext()) {
            int x = in.nextInt();
            testor(x, t);
            //Checks if the tree is a valid AVL tree after each operation
            if (!TestTrees.isAvl(t.root))
                System.out.println("IsAvl : " + false);
        }

       /* int [] add = {1,3,-1,-10,0,10,12};
        int [] delete = {1,3,12,0,-10};
        for(int x:add){
            System.out.print("Add " + x + " : ");
            t.add(x);
            t.printTree();
        }
        for(int x:delete){
            System.out.print("Remove " + x + " : ");
            t.remove(x);
            t.printTree();
        }*/

        System.out.print("Iterator : ");
        for (int i : t) {
            System.out.print(i + " ");
        }
        System.out.println("\nMin : " + t.min());
        System.out.println("Max : " + t.max());
        System.out.println("IsAvl : " + TestTrees.isAvl(t.root));
    }

}

