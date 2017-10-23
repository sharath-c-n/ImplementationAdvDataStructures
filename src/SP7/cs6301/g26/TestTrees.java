package cs6301.g26;

/**
 * TestTrees:
 *
 * @author : Sharath
 * 19/10/2017
 */
public class TestTrees {

    static class Tuple {
        int height;
        boolean isBalanced;

        public Tuple(int height, boolean isBalanced) {
            this.height = height;
            this.isBalanced = isBalanced;
        }

        @Override
        public String toString() {
            return height + " , " + isBalanced;
        }
    }

    static <T extends Comparable<? super T>>  Tuple isBalanced(TreeEntry<T> node) {
        if (node == null) {
            return new Tuple(0, true);
        }

        Tuple left = isBalanced(node.getLeft());
        Tuple right = isBalanced(node.getRight());

          /* Height of current node is max of heights of left and
             right subtrees plus 1*/
        int height = Math.max(left.height, right.height) + 1;

      /* If difference between heights of left and right
         subtrees is more than 1 then this node is not balanced
         so return 0 */
        if (Math.abs(left.height - right.height) > 1)
            return new Tuple(height, false);

          /* If this node is balanced and left and right subtrees
            are balanced then return true */
        else
            return new Tuple(height, left.isBalanced && right.isBalanced);
    }

    static <T extends Comparable<? super T>>  boolean isBST(TreeEntry<T> node, T min, T max) {
        /* an empty tree is BST */
        if (node == null)
            return true;

         /* false if this node violates the min/max constraint */
        if (node.getKey().compareTo(min) < 0 || node.getKey().compareTo(max) > 0)
            return false;

          /* otherwise check the subtrees recursively,
           tightening the min or max constraint */
        return isBST(node.getLeft(), min, node.getKey()) &&
                isBST(node.getRight(), node.getKey(), max);
    }

    static <T extends Comparable<? super T>>  Tuple isRBBalanced(RedBlackTree.Entry<T> node) {
        if (node == null) {
            return new Tuple(0, true);
        }

        if (node.getLeft() == null && node.getRight() == null) {
            return new Tuple(node.isRed?0:1, true);
        }

        Tuple left = isRBBalanced(node.getLeft());
        Tuple right = isRBBalanced(node.getRight());

          /* Height of current node is max of heights of left and
             right subtrees plus 1*/
        int height = Math.max(left.height, right.height) + (node.isRed?0:1);
        if(node.getLeft() == null || node.getRight() == null){
            return new Tuple(height,left.isBalanced && right.isBalanced);
        }

      /* If difference between heights of left and right
         subtrees is more than 1 then this node is not balanced
         so return false */
        if (left.height!=right.height)
            return new Tuple(height, false);

          /* If this node is balanced and left and right subtrees
            are balanced then return true */
        else
            return new Tuple(height, left.isBalanced && right.isBalanced);
    }

    static boolean isAvl(TreeEntry<Integer> node){
        return isBST(node,Integer.MIN_VALUE,Integer.MAX_VALUE) &&
                isBalanced(node).isBalanced;
    }

    static boolean isSplay(TreeEntry<Integer> node){
        return isBST(node,Integer.MIN_VALUE,Integer.MAX_VALUE) &&
                isBalanced(node).isBalanced;
    }
    static boolean isRedBlack(RedBlackTree.Entry<Integer> node){
        return isBST(node,Integer.MIN_VALUE,Integer.MAX_VALUE) &&
                isRBBalanced(node).isBalanced;

    }

}
