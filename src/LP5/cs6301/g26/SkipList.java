package cs6301.g26;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 * Created by Ankitha on 10/28/2017.
 */

// Skeleton for skip list implementation.

public class SkipList<T extends Comparable<? super T>> implements Iterable<T> {
    public static final int MAX_LEVEL = 32;

    static class Entry<T extends Comparable<? super T>> {
        T element;
        Tuple<T> next[];

        public Entry(T element, int level) {
            this.element = element;
            this.next = new Tuple[level + 1];
        }

        public Entry<T> getNext(int index) {
            return this.next[index].entry;
        }

        public void setNext(int i, Entry<T> n) {
            next[i] = new Tuple<T>(n, 0);
        }
    }

    static class Tuple<T extends Comparable<? super T>> {
        Entry<T> entry;
        int span;

        public Tuple(Entry<T> entry, int span) {
            this.entry = entry;
            this.span = span;
        }
    }


    Entry<T> head;
    Entry<T> tail;
    int curMaxLevel;
    int size;

    // Constructor
    public SkipList() {
        head = new Entry<>(null, MAX_LEVEL);
        tail = new Entry<>(null, MAX_LEVEL);
        curMaxLevel = 0;
        head.setNext(0, tail);
    }


    // return prev[0..maxLevel] of nodes at which search went down one level, looking for x
    private Entry<T>[] find(T key) {
        Entry<T> p = head;
        Entry<T>[] prev = new Entry[curMaxLevel + 1];
        for (int i = curMaxLevel; i >= 0; i--) {
            while (p.getNext(i) != tail && p.getNext(i).element.compareTo(key) < 0) {
                p = p.getNext(i);
            }
            prev[i] = p;
        }
        return prev;
    }

    //choose number of levels for a new node randomly
    private int chooseLevel() {
        int level, mask;
        Random random = new Random();
        mask = (1 << curMaxLevel) - 1;
        level = Integer.numberOfTrailingZeros(random.nextInt() & mask);
        if (level > curMaxLevel) {
            return curMaxLevel + 1;
        } else {
            return curMaxLevel;
        }
    }

    // Add x to list. If x already exists, replace it. Returns true if new node is added to list
    public boolean add(T x) {
        Entry[] prev = find(x);
        if (prev[0].getNext(0) != tail && prev[0].getNext(0).element.compareTo(x) == 0) {
            prev[0].getNext(0).element = x;
            return false;
        } else {
            int level = chooseLevel();
            Entry<T> n = new Entry<>(x, level);
            for (int i = 0; i <= curMaxLevel; i++) {
                n.next[i] = prev[i].next[i];
                prev[i].setNext(i, n);
            }
            updateSpan(prev, n);
            if (level > curMaxLevel) {
                head.setNext(level, n);
                n.setNext(level, tail);
                head.next[level].span = getSpan(curMaxLevel, head, n);
                n.next[level].span = getSpan(curMaxLevel, n, tail);
                curMaxLevel = level;
            }
            return true;
        }
    }

    private void updateSpan(Entry[] prev, Entry<T> n) {
        for (int i = 0; i <= curMaxLevel; i++) {
            int span = getSpan(i, prev[i], n)+1;
            n.next[i].span = prev[i].next[i].span - span;
            prev[i].next[i].span = span;
        }
    }

    private int getSpan(int level, Entry entry, Entry<T> n) {
        int span = 0;
        while (entry != n) {
            if (entry.getNext(level) == tail || entry.getNext(level).element.compareTo(n.element) > 0) {
                if (level == 0 && entry.getNext(level) == tail) {
                    return 1;
                }
                span += getSpan(level - 1, entry, n);
                break;
            } else {
                span += entry.next[level].span;
                entry = entry.next[level].entry;
            }
        }
        return span;
    }

    // Find smallest element that is greater or equal to x
    public T ceiling(T x) {
        Entry<T>[] prev = find(x);
        if (prev[0].getNext(0).element.compareTo(x) == 0) return x;
        else {
            return prev[0].getNext(0).element;
        }
    }

    // Does list contain x?
    public boolean contains(T x) {
        Entry[] prev = find(x);
        return prev[0].getNext(0).element == x;
    }

    // Return first element of list
    public T first() {
        return (T) head.getNext(0).element;
    }

    // Find largest element that is less than or equal to x
    public T floor(T x) {
        Entry<T>[] prev = find(x);
        if (prev[0].getNext(0).element.compareTo(x) == 0) return x;
        else {
            return prev[0].element;
        }
    }

//    // Return element at index n of list.  First element is at index 0.
//    public T get(int n) {
//
//
//    }

    // Is the list empty?
    public boolean isEmpty() {
        return size() == 0;
    }

    // Iterate through the elements of list in sorted order
    public Iterator<T> iterator() {
        return new SkipListIterator<>(this);
    }

    // Return last element of list
    public T last() {
        return null;
    }

    // Reorganize the elements of the list into a perfect skip list
    public void rebuild() {

    }

    // Remove x from list.  Removed element is returned. Return null if x not in list
    public T remove(T x) {
        Entry[] prev = find(x);
        Entry<T> n = prev[0].getNext(0);
        if (n.element.compareTo(x) != 0) return null;
        else {
            for (int i = 0; i <= curMaxLevel; i++) {
                if (prev[i].getNext(i) != tail && prev[i].getNext(i).element.compareTo(x) == 0) {
                    prev[i].next[i] = n.next[i];
                } else break;
            }
        }
        return n.element;
    }

    // Return the number of elements in the list
    public int size() {
        return size;
    }

    public class SkipListIterator<T extends Comparable<? super T>> implements Iterator<T> {
        SkipList.Entry<T> cursor;
        Entry<T> tail;

        public SkipListIterator(SkipList<T> skipList) {
            cursor = skipList.head;
            tail = skipList.tail;
        }

        @Override
        public boolean hasNext() {
            return cursor.getNext(0) != tail;
        }

        @Override
        public T next() {
            if (!hasNext()) {
                throw new java.util.NoSuchElementException();
            }
            cursor = cursor.getNext(0);
            return cursor.element;
        }
    }

}


