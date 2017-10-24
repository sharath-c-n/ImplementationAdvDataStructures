package cs6301.g26;

import java.util.Scanner;
/**
 *
 * @author : Ankitha
 */

public class SplayTree<T extends Comparable<? super T>> extends BST<T> {
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
    SplayTree() {
	super();
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
     *return value associated with the given key
     *if no such value, return null
     **/

    protected Entry<T> find(BST.Entry<T> t, T key) {
        root = splay((Entry<T>) root, key);
        int cmp = key.compareTo(root.key);
        if (cmp == 0) return (Entry<T>) root;
        else
            return null;
    }

    /* This splays the key, then  swaps the root (call it node A) with its successor, it's successor (call it Node B)
     * is moved to the root position by splaying for the deletion key in A's
     * right subtree. Finally, A's right child is made the new root's right child.
     * @param key : value to be added
     * @return boolean
     */

    public boolean add(T key) {
        // splay key to root
        if (root == null) {
            root = createEntry(key);
            return true;
        }

        root = splay((Entry<T>) root, key);

        int cmp = key.compareTo(root.key);

        // Insert new node at root
        if (cmp < 0) {
            Entry<T> n = createEntry(key);
            n.left = root.left;
            n.right = root;
            root.left = null;
            root = n;
        }
        // Insert new node at root
        else if (cmp > 0) {
            Entry<T> n = createEntry(key);
            n.right = root.right;
            n.left = root;
            root.right = null;
            root = n;
        }

        // duplicate key
        else {
            root.key = key;
        }
        return  true;
    }
     /* This splays the key, then  swaps the root (call it node A) with its successor, it's successor (call it Node B)
     * is moved to the root position by splaying for the deletion key in A's
     * right subtree. Finally, A's right child is made the new root's right child.
     * @param key : value to be removed
     * @return deleted Entry
     */
    public T remove(T key) {
        if (root == null) return null; // empty tree

        root = splay((Entry<T>) root, key);

        int cmp = key.compareTo(root.key);

        if (cmp == 0) {
            if (root.left == null) {
                root = root.right;
            }
            else {
                Entry<T> x = (Entry<T>) root.getRight();
                root = root.left;
                splay((Entry<T>) root, key);
                root.right = x;
            }
            return root.key;
        }
        return null;
    }

    /* splay key in the tree rooted at Node h. If a node with that key exists,
      * it is splayed to the root of the tree. If it does not, the last node
      * along the search path for the key is splayed to the root.
      *   @param h : node to be splayed at
      *   @param key : value to be splayed to root
      *   @return deleted Entry*/

    private Entry<T> splay(Entry<T> h, T key) {
        if (h == null) return null;

        int cmp1 = key.compareTo(h.key);

        if (cmp1 < 0) {
            // key not in tree, so we're done
            if (h.left == null) {
                return h;
            }
            int cmp2 = key.compareTo(h.left.key);
            if (cmp2 < 0) {
                h.left.left = splay(h.getLeft().getLeft(), key);
                h = rotateRight(h);
            }
            else if (cmp2 > 0) {
                h.left.right = splay(h.getLeft().getRight(), key);
                if (h.left.right != null)
                    h.left = rotateLeft(h.getLeft());
            }

            if (h.left == null) return h;
            else
                return rotateRight(h);
        }

        else if (cmp1 > 0) {
            // key not in trees
            if (h.right == null) {
                return h;
            }

            int cmp2 = key.compareTo(h.right.key);
            if (cmp2 < 0) {
                h.right.left  = splay(h.getRight().getLeft(), key);
                if (h.right.left != null)
                    h.right = rotateRight(h.getRight());
            }
            else if (cmp2 > 0) {
                h.right.right = splay(h.getRight().getRight(), key);
                h = rotateLeft(h);
            }

            if (h.right == null) return h;
            else
                return rotateLeft(h);
        }

        else return h;
    }

    // right rotate
    private Entry<T> rotateRight(Entry<T> h) {
        Entry<T> x = h.getLeft();
        h.left = x.right;
        x.right = h;
        return x;
    }

    // left rotate
    private Entry<T> rotateLeft(Entry<T> h) {
        Entry<T> x = h.getRight();
        h.right = x.left;
        x.left = h;
        return x;
    }


    public static void main(String[] args) {
        SplayTree<Integer> t = new SplayTree<>();
        Scanner in = new Scanner(System.in);
        while (in.hasNext()) {
            int x = in.nextInt();
            testor(x, t);
            //Checks if the tree is a valid Splay  tree after each operation
            if(x>0){
                if(!(t.root.getKey()==x ) && !TestTrees.isBST(t.root,Integer.MIN_VALUE,Integer.MAX_VALUE)){
                    System.out.println("Is Splay : " + false);
                }
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
