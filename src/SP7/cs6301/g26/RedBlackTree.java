package cs6301.g26;

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
        if (!super.add(key)) {
            return false;
        }
        if (stack != null && stack.size() > 2) {
            repair(getNode(key, (Entry<T>) stack.peek()));
        }
        ((Entry<T>) root).isRed = false;
        return true;
    }


    protected Entry<T> find(BST.Entry<T> t, T x) {
        Entry<T> node = (Entry<T>) t;
        Entry<T> newNode;
        if (node == null || x.compareTo(node.key) == 0) {
            return node;
        }
        while (true) {
            if (x.compareTo(node.key) < 0) {
                if (node.left == null) break;
                else {
                    if (isRed(node.getRight())) {
                       newNode = leftRotate(node);
                        replaceChild(node, (Entry<T>) stack.peek(), newNode);
                        newNode.isRed = false;
                        node.isRed = true;
                    }
                    stack.push(node);
                    node = node.getLeft();
                }
            } else if (x.compareTo(node.key) == 0) break;
            else {
                if (node.right == null) break;
                else {
                    if (isRed(node.getLeft())) {
                        newNode =  rightRotate(node);
                        replaceChild(node, (Entry<T>) stack.peek(), newNode);
                        newNode.isRed = false;
                        node.isRed = true;
                    }
                    stack.push(node);
                    node = node.getRight();
                }
            }
            recolor((Entry<T>) stack.peek());
        }
        return node;
    }

    private void recolor(Entry<T> t) {
        if (!t.isRed && isRed(t.getLeft()) && isRed(t.getRight())) {
            t.getRight().isRed = t.getLeft().isRed = false;
            t.isRed = true;
        }
    }


    /**
     * Repairs the redBlack subtree after addition of the key
     *
     * @param curEntry : key that just got added to the tree
     */
    private void repair(Entry<T> curEntry) {
        if (curEntry != null && !stack.isEmpty() && stack.size() > 1) {
            Entry<T> parent = (Entry<T>) stack.pop();
            Entry<T> grandParent = (Entry<T>) stack.pop();
            Entry<T> greatGrandParent = !stack.isEmpty() ? (Entry<T>) stack.peek() : null;
            if (curEntry == root || parent == root || !parent.isRed) return;
            //Left Case
            if (grandParent.left == parent) {
                //Left-Right
                if (parent.right == curEntry) {
                    replaceChild(parent, grandParent, leftRotate(parent));
                    parent = grandParent.getLeft();
                }
                replaceChild(grandParent, greatGrandParent, rightRotate(grandParent));
            }
            //Right case
            else {
                //Right-Left case
                if (parent.left == curEntry) {
                    replaceChild(parent, grandParent, rightRotate(parent));
                    parent = grandParent.getRight();
                }
                replaceChild(grandParent, greatGrandParent, leftRotate(grandParent));
            }
            parent.isRed = false;
            grandParent.isRed = true;
        }
    }

    private void replaceChild(Entry<T> child, Entry<T> parent, Entry<T> rotatedEntry) {
        if (parent == null) {
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
     *
     * @param pivot :
     * @return : the updated pivot, it'll no longer be the root of subtree
     */
    private Entry<T> leftRotate(Entry<T> pivot) {
        Entry<T> right = pivot.getRight();
        // cannot rotate
        if (right == null) {
            return pivot;
        }
        Entry<T> left = right.getLeft();
        right.left = pivot;
        pivot.right = left;

        // Return new root
        return right;
    }

    /**
     * Right rotates the tree about the pivot
     *
     * @param pivot :
     * @return : the new root for the subtree
     */
    private Entry<T> rightRotate(Entry<T> pivot) {
        Entry<T> left = pivot.getLeft();
        // Perform rotation
        if (left == null) {
            return pivot;
        }
        Entry<T> right = left.getRight();
        left.right = pivot;
        pivot.left = right;

        // Return new root
        return left;
    }


    /**
     * replace key t with its successor, it also fixes the tree violations
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
        if (!((Entry<T>) t).isRed) {
            fix(c);
        }
        if (root != null) {
            ((Entry<T>) root).isRed = false;
        }
    }


    /**
     * Fix tree violations after a node is removed
     *
     * @param t : entry that replaced the successor node in the tree.
     */
    private void fix(Entry<T> t) {
        while (stack.size() > 1 && t != root) {
            Entry<T> parent = (Entry<T>) stack.pop();
            Entry<T> grandParent = (Entry<T>) stack.peek();
            Entry<T> sibling = getSibling(parent, t);

            //successor replacing node is red
            if (isRed(t)) {
                t.isRed = false;
                return;
            }

            //Right Cases
            if (parent.getRight() == sibling) {
                //Right-left case
                if (!isRed(sibling.getLeft()) && sibling.getLeft() != null) {
                    replaceChild(sibling, parent, rightRotate(sibling));
                    swapColor(parent.getRight(), sibling);
                    sibling = parent.getRight();
                    //Right-right case
                    if (isRed(sibling.getRight()) && sibling.getRight() != null) {
                        replaceChild(parent, grandParent, leftRotate(parent));
                        swapColor(parent, sibling);
                        sibling.getRight().isRed = false;
                    }
                    return;
                }
                //Right-right case
                if (isRed(sibling.getRight()) && sibling.getRight() != null) {
                    replaceChild(parent, grandParent, leftRotate(parent));
                    swapColor(parent, sibling);
                    sibling.getRight().isRed = false;
                    return;
                }
            }
            //Left Cases
            else {
                //Left-right case
                if (isRed(sibling.getRight()) && sibling.getRight() != null) {
                    replaceChild(sibling, parent, leftRotate(sibling));
                    swapColor(parent.getLeft(), sibling);
                    sibling = parent.getLeft();
                    if (isRed(sibling.getLeft())) {
                        replaceChild(parent, grandParent, rightRotate(parent));
                        swapColor(parent, sibling);
                        sibling.getRight().isRed = false;
                    }
                    return;
                }
                //Left-left case
                if (isRed(sibling.getLeft())) {
                    replaceChild(parent, grandParent, rightRotate(parent));
                    swapColor(parent, sibling);
                    sibling.getRight().isRed = false;
                    return;
                }
            }
        }
    }

    private boolean isRed(Entry<T> node) {
        return node != null && node.isRed;
    }

    /**
     * Given the parent and the key the left or the right child is returned if key matches with any
     * of the child.
     *
     * @param x    : key to be matched
     * @param node : parent node
     * @return : child node whose key is same as x
     */
    private Entry<T> getNode(T x, Entry<T> node) {
        if (node == null) {
            return null;
        }
        Entry<T> left = node.getLeft();
        Entry<T> right = node.getRight();
        return left != null && left.getKey() == x ? left : right.getKey() == x ? right : null;
    }

    /**
     * Returns the sibling of the other child of the parent
     *
     * @param parent : parent whose child is to be returned
     * @param child  : one of the children of parent
     * @return : other child
     */
    private Entry<T> getSibling(Entry<T> parent, Entry<T> child) {
        Entry<T> sibling = null;
        if (parent != null) {
            if (parent.getLeft() == child) {
                sibling = parent.getRight();
            } else if (parent.getRight() == child) {
                sibling = parent.getLeft();
            }
        }
        return sibling;
    }

    //swaps colors of given nodes
    private void swapColor(Entry<T> node1, Entry<T> node2) {
        boolean temp = node1.isRed;
        node2.isRed = node1.isRed;
        node1.isRed = temp;
    }


    public static void main(String[] args) {
        RedBlackTree<Integer> t = new RedBlackTree<>();
        Scanner in = new Scanner(System.in);
        while (in.hasNext()) {
            int x = in.nextInt();
            testor(Math.abs(x), t);
            //Checks if the tree is a valid RedBlack tree after each operation
            if (!TestTrees.isRedBlack((RedBlackTree.Entry<Integer>) t.root))
                System.out.println("is RedBlack : " + false);
        }
        System.out.print("Iterator : ");
        for (int i : t) {
            System.out.print(i + " ");
        }
        System.out.println("\nMin : " + t.min());
        System.out.println("Max : " + t.max());
        System.out.println("is RedBlack : " + TestTrees.isRedBlack((RedBlackTree.Entry<Integer>) t.root));
    }
}
