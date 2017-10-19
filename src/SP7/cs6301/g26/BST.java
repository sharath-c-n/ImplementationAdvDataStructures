/**
 * @author Binary search tree (starter code)
 **/

package SP7.cs6301.g26;


import java.util.Iterator;
import java.util.Scanner;
import java.util.Stack;

public class BST<T extends Comparable<? super T>> implements Iterable<T> {
    static class Entry<T> implements TreeEntry<T> {
        T element;
        Entry<T> left, right;

        Entry(T x) {
            this.element = x;
            this.left = this.right = null;
        }

        Entry(T x, Entry<T> left, Entry<T> right) {
            this.element = x;
            this.left = left;
            this.right = right;
        }

        @Override
        public Entry<T> getLeft() {
            return left;
        }

        @Override
        public Entry<T> getRight() {
            return right;
        }

        @Override
        public T getValue() {
            return element;
        }
    }

    protected Entry<T> root;
    protected int size;
    private Stack<Entry<T>> stack;

    public BST() {
        root = null;
        size = 0;
    }


    /**
     * Returns true if x is contained in tree
     *
     * @param x : value to be searched in the tree
     * @return : true/false
     */
    public boolean contains(T x) {
        Entry<T> t = find(x);
        return t != null && t.element.compareTo(x) == 0;
    }

    /**
     * returns element in tree that is equal to x is returned, null otherwise.
     *
     * @param x : value to be searched
     * @return : entry or null
     */
    public T get(T x) {
        Entry<T> t = find(x);
        return t != null && t.element.compareTo(x) == 0 ? t.element : null;
    }

    /**
     * If tree contains a node with same key, replace element by x.
     *
     * @param x : element to be added to the tree
     * @return : true if x is a new element added to tree.
     */
    public boolean add(T x) {
        if (root == null) {
            root = new Entry<>(x);
            size = 1;
            return true;
        }
        Entry<T> t = find(x);
        if (x.compareTo(t.element) == 0) {
            t.element = x;
            return false;
        } else if (x.compareTo(t.element) < 0) {
            t.left = new Entry<>(x);
        } else {
            t.right = new Entry<>(x);
        }
        size++;
        return true;
    }

    public Entry<T> find(T x) {
        stack = new Stack<>();
        stack.push(null);
        return find(root, x);
    }

    private Entry<T> find(Entry<T> t, T x) {
        if (t == null || x.compareTo(t.element) == 0) {
            return t;
        }
        while (true) {
            if (x.compareTo(t.element) < 0) {
                if (t.left == null) break;
                else {
                    stack.push(t);
                    t = t.left;
                }
            } else if (x.compareTo(t.element) == 0) break;
            else {
                if (t.right == null) break;
                else {
                    stack.push(t);
                    t = t.right;
                }
            }
        }
        return t;
    }

    public T min() {
        Entry<T> node = min(root);
        return node  == null ? null : node.element;
    }

    public Entry<T> min(Entry<T> node) {
        if (node == null) return null;
        while (node.left != null) {
            node = node.left;
        }
        return node;
    }


    public T max() {
        Entry<T> node = max(root);
        return node  == null ? null : node.element;
    }

    public Entry<T> max(Entry<T> node) {
        if (node == null) return null;
        while (node.right != null) {
            node = node.right;
        }
        return node;
    }

    /**
     * Remove x from tree.
     *
     * @param x : the element that needs to be removed from the tree
     * @return : x if found, otherwise return null
     */
    public T remove(T x) {
        if (root == null) return null;
        Entry<T> t = find(x);
        if (t.element != x) return null;
        T result = t.element;
        if (t.left == null || t.right == null) {
            bypass(t);
        } else // t has 2 children
        {
            stack.push(t);
            Entry<T> minRight = find(t.right, t.element);
            t.element = minRight.element;
            bypass(minRight);

        }
        size--;
        return result;
    }

    /**
     * replace element t with its successor;
     *
     * @param t : element to be replaced
     */
    private void bypass(Entry<T> t) {
        Entry<T> pt = stack.peek();
        Entry<T> c = t.left == null ? t.right : t.left;
        if (pt == null) {
            root = c;
        } else if (pt.left == t) {
            pt.left = c;
        } else {
            pt.right = c;
        }
    }

    public Iterator<T> iterator() {
        return new BTIterator<>(root);
    }

    public Comparable[] toArray() {
        Comparable[] arr = new Comparable[size];
        addToArray(root, arr, 0);
        return arr;
    }

    private int addToArray(TreeEntry<T> entry, Comparable[] arr, int index) {
        if (entry.getLeft() != null) {
            index = addToArray(entry.getLeft(), arr, index);
        }

        arr[index++] = entry.getValue();

        if (entry.getRight() != null) {
            index = addToArray(entry.getRight(), arr, index);
        }
        return index;
    }

    public void printTree() {
        System.out.print("[" + size + "]");
        printTree(root);
        System.out.println();
    }

    // Inorder traversal of tree
    void printTree(Entry<T> node) {
        if (node != null) {
            printTree(node.left);
            System.out.print(" " + node.element);
            printTree(node.right);
        }
    }

    public static void main(String[] args) {
        BST<Integer> t = new BST<>();
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
/*
Sample input:
1 3 5 7 9 2 4 6 8 10 -3 -6 -3 0

Output:
Add 1 : [1] 1
Add 3 : [2] 1 3
Add 5 : [3] 1 3 5
Add 7 : [4] 1 3 5 7
Add 9 : [5] 1 3 5 7 9
Add 2 : [6] 1 2 3 5 7 9
Add 4 : [7] 1 2 3 4 5 7 9
Add 6 : [8] 1 2 3 4 5 6 7 9
Add 8 : [9] 1 2 3 4 5 6 7 8 9
Add 10 : [10] 1 2 3 4 5 6 7 8 9 10
Remove -3 : [9] 1 2 4 5 6 7 8 9 10
Remove -6 : [8] 1 2 4 5 7 8 9 10
Remove -3 : [8] 1 2 4 5 7 8 9 10
Final: 1 2 4 5 7 8 9 10 

*/
