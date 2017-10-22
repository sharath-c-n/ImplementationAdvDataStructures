
/**
 * @author Binary search tree map (starter code)
 * Implement this class using one of the BST implementations: BST, AVLTree, RedBlackTree, or, SplayTree.
 * Do not use TreeMap or any of Java's maps.
 **/

package cs6301.g26;


import java.util.Iterator;

public class BSTMap<K extends Comparable<? super K>, V> implements Iterable<K> {
    private AVLTree<Tuple<K, V>> tree;

    static class Tuple<K extends Comparable<? super K>, V> implements Comparable<Tuple<K, V>> {
        K key;
        V value;

        public Tuple(K key, V value) {
            this.key = key;
            this.value = value;
        }

        @Override
        public int compareTo(Tuple<K, V> tuple2) {
            return key.compareTo(tuple2.key);
        }
    }

    BSTMap() {
        tree = new AVLTree<>();
    }

    public V get(K key) {
        Tuple<K, V> entry = tree.get(new Tuple<>(key, null));
        return entry == null ? null : entry.value;
    }

    public boolean put(K key, V value) {
        return tree.add(new Tuple<>(key, value));
    }

    // Iterate over the keys stored in the map, in order
    public Iterator<K> iterator() {
        return new BSTMapIterator<>(tree.root);
    }

    public static void main(String[] args){
        BSTMap<String,Integer> t = new BSTMap<>();
        t.put("sing",2);
        t.put("King",4);
        t.put("Ring",8);
        for(String i : t)
            System.out.println("("+ i +"," + t.get(i)+ ")");
    }
}
