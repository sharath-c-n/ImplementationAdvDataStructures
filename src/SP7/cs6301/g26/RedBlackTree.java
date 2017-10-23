
/**
 * Starter code for Red-Black Tree
 */
package cs6301.g26;

import javafx.scene.transform.Rotate;

import java.util.Comparator;
import java.util.Scanner;

public class RedBlackTree<T extends Comparable<? super T>> extends BST<T> {
    static class Entry<T> extends BST.Entry<T> {
        boolean isRed;


        Entry(T x, Entry<T> left, Entry<T> right) {
            super(x, left, right);
            isRed = true;
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

    RedBlackTree() {
        super();
    }

    protected Entry<T> createEntry(T x) {
        return new Entry<>(x);
    }

    public boolean add(T key) {
        //Entry<T> key = createEntry(x);
        if (!super.add(key)) {
            return false;
        }
        repair(key);
        ((Entry<T>) root).isRed = false;
        return true;
    }


    protected Entry<T> find(BST.Entry<T> t, T x) {
            if (t == null || x.compareTo(t.key) == 0) {
                return (Entry<T>) t;
            }
            while (true) {
                if (x.compareTo(t.key) < 0) {
                    if (t.left == null) break;
                    else {
                        reColor((Entry<T>) t);
                        stack.push(t);
                        t = t.getLeft();
                    }
                } else if (x.compareTo(t.key) == 0) break;
                else {
                    if (t.right == null) break;
                    else {
                        reColor((Entry<T>) t);
                        stack.push(t);
                        t = t.getRight();
                    }
                }
            }
            return (Entry<T>)t;
    }

    private void reColor(Entry<T> t) {
        Entry<T> rchild = t.getRight();
        Entry<T> lchild = t.getLeft();
        if(rchild !=null && lchild!=null && rchild.isRed && lchild.isRed){
            t.isRed = true;
            rchild.isRed = lchild.isRed = false;
        }
        else if((rchild ==null || !rchild.isRed ) &&( lchild ==null || !lchild.isRed)){
            t.isRed = true;
        }
    }


    void repair(T key) {

        if (stack != null && stack.size() > 2) {
            Entry<T> parent = (Entry<T>) stack.peek();
            Entry<T> curEntry = stack.peek().right!=null && stack.peek().right.key == key  ? parent.getRight() : parent.getLeft();

            if( stack.size() > 2) {
                parent = (Entry<T>) stack.pop();
                curEntry = parent.getRight()!=null && parent.getRight().key == key ? parent.getRight() : parent.getLeft();
                Entry<T> grandParent = (Entry<T>) stack.pop();
                Entry<T> uncle = grandParent.getRight() == parent ? grandParent.getLeft() : grandParent.getRight();
                Entry<T> greatGrandParent = (Entry<T>) stack.peek();
                if (curEntry == root || parent == root || !parent.isRed) return;
               if (uncle==null || !uncle.isRed) {
                    //Left-Left Case
                    if (grandParent.left == parent && parent.left == curEntry) {
                        replaceChild(grandParent, greatGrandParent, rightRotate(grandParent));
                    }
                    //Right-Right case
                    else if (grandParent.right == parent && parent.right == curEntry) {
                        replaceChild(grandParent, greatGrandParent, leftRotate(grandParent));
                    }
                    //Left-Right case
                    else if (grandParent.left == parent && parent.right == curEntry) {
                        replaceChild(parent, grandParent, leftRotate(parent));
                        replaceChild(grandParent, greatGrandParent, rightRotate(grandParent));
                    }
                    //Right-Left case
                    else if (grandParent.right == parent && parent.left == curEntry) {
                        replaceChild(parent, grandParent, rightRotate(parent));
                        replaceChild(grandParent, greatGrandParent, leftRotate(grandParent));
                    }
                    parent.isRed = false;
                    grandParent.isRed = true;
                    return;
                }

            }

        }

    }

    void replaceChild(Entry<T> child, Entry<T> parent, Entry<T> rotatedEntry) {
        if(parent == null){
            root = rotatedEntry;
            return;
        }
        if (child == parent.getRight()) {
            parent.right = rotatedEntry;
        } else {
            parent.left = rotatedEntry;
        }
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

        // Return new root
        return right;
    }


    /**
     * Right rotates the tree about the pivot
     * @param pivot :
     * @return : the new root for the subtree
     */
    Entry<T> rightRotate(Entry<T> pivot) {
        Entry<T> left = (Entry<T>) pivot.left;
        Entry<T> right = (Entry<T>) left.right;

        // Perform rotation
        left.right = pivot;
        pivot.left = right;

        // Return new root
        return left;
    }


    /**
     * replace key t with its successor;
     *
     * @param t : key to be replaced
     */
    protected void bypass(BST.Entry<T> t) {
       Entry<T> pt = (Entry<T>) stack.peek();
       Entry<T> c = (Entry<T>) (t.left == null ? t.right : t.left);
        if (pt == null) {
            root = c;
        } else if (pt.left == t) {
            pt.left = c;
        } else {
            pt.right = c;
        }
        if(!((Entry<T>)t).isRed && c!=null){
            fix(c);

        }
        if(root!=null){
            ((Entry<T>)root).isRed = false;
        }
    }


    void fix(Entry<T> t) {
        if( stack.size() > 2) {

            Entry<T> parent = (Entry<T>) stack.pop();
            Entry<T> curEntry =  parent.getRight()!=null && parent.getRight().key == t ? parent.getRight() : parent.getLeft();
            Entry<T> sibling =  parent.getRight()!=null && parent.getRight().key == curEntry ? parent.getLeft() : parent.getRight();
            Entry<T> grandParent = (Entry<T>) stack.pop();
            Entry<T> rightChild = sibling!=null ?sibling.getRight():null;
            Entry<T> leftChild = sibling!=null ? sibling.getLeft():null;
            if( t.isRed){
                t.isRed = false;
                return;
            }
            else if(sibling.isRed){
                if(t == stack.peek().getRight()){
                    replaceChild(parent, grandParent, rightRotate(parent));
                }
                else{
                    replaceChild(parent, grandParent, leftRotate(parent));
                }
                swapColor(parent,sibling);
            }
            if((sibling==null || !sibling.isRed) && (leftChild!=null && leftChild.isRed) && rightChild!=null &&rightChild.isRed){
                if(sibling == parent.getRight() && sibling.getLeft()==leftChild){
                    replaceChild(sibling, parent, rightRotate(sibling));
                    swapColor(rightChild,sibling);
                }
                if(sibling == parent.getLeft() && sibling.getRight()==rightChild){
                    replaceChild(sibling, parent, rightRotate(sibling));
                    swapColor(rightChild,sibling);
                }
                if(rightChild.isRed && sibling == parent.getRight() && sibling.getRight()==rightChild){
                    replaceChild(parent, grandParent, leftRotate(parent));
                    rightChild.isRed = false;
                    swapColor(parent,sibling);
                }
                if(leftChild.isRed && sibling == parent.getLeft() && sibling.getLeft()==leftChild){
                    replaceChild(parent, grandParent, rightRotate(parent));
                    leftChild.isRed = false;
                    swapColor(parent,sibling);
                }
            }
        }
        }

    void swapColor(Entry<T> node1,Entry<T> node2){
        boolean temp = node1.isRed;
        node2.isRed = node1.isRed;
        node1.isRed = temp;
    }



    public static void main(String[] args) {
        RedBlackTree<Integer> t = new RedBlackTree<>();
        Scanner in = new Scanner(System.in);
        while (in.hasNext()) {
            int x = in.nextInt();
            testor(x, t);
            //Checks if the tree is a valid AVL tree after each operation
            if (!TestTrees.isRedBlack((Entry<Integer>) t.root))
                System.out.println("is RedBlack : " + false);
        }
        System.out.print("Iterator : ");
        for (int i : t) {
            System.out.print(i + " ");
        }
        System.out.println("\nMin : " + t.min());
        System.out.println("Max : " + t.max());
        System.out.println("is RedBlack : " + TestTrees.isRedBlack((Entry<Integer>) t.root));
    }


}

