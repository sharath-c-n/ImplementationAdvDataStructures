package cs6301.g26;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Stack;

/**
 * BTIterator: a generic iterator which iterates any binary tree which implements TreeEntry node.
 * The iteration is done in inOrder fashion
 * @author : Sharath
 * 12/10/2017
 */
public class BTIterator<T> implements Iterator<T> {
    /**
     * Maintains the list of nodes that will be processed next
     */
    private Stack<TreeEntry<T>> stack;

    public BTIterator(TreeEntry<T> root) {
        stack = new Stack<>();
        addToStack(root);
    }

    /**
     * Adds all the elements which are to the left of the current node to the stack.
     * @param node : Node to be processed
     */
    private void addToStack(TreeEntry<T> node) {
        while (node != null) {
            stack.add(node);
            node = node.getLeft();
        }
    }

    @Override
    public boolean hasNext() {
        return !stack.isEmpty();
    }

    @Override
    public T next() {
        if (hasNext()) {
            TreeEntry<T> entry = stack.pop();
            if (entry.getRight() != null){
                addToStack(entry.getRight());
            }
            return entry.getKey();
        }
        throw new NoSuchElementException();
    }

}
