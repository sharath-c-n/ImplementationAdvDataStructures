package cs6301.g26;

import java.util.Iterator;

/**
 * BSTMapIterator:
 *
 * @author : Sharath
 * 19/10/2017
 */
public class BSTMapIterator<K extends Comparable<? super K>, V> implements Iterator<K> {
    private BTIterator<BSTMap.Tuple<K, V>> baseIterator;

    public BSTMapIterator(TreeEntry<BSTMap.Tuple<K, V>> root) {
        baseIterator = new BTIterator<>(root);
    }

    @Override
    public boolean hasNext() {
        return baseIterator.hasNext();
    }

    @Override
    public K next() {
        BSTMap.Tuple<K, V> next = baseIterator.next();
        return next.key;
    }
}
