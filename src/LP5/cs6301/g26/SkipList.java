package cs6301.g26;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Random;

/**
 * Created by Ankitha on 10/28/2017.
 */

// Skeleton for skip list implementation.

public class SkipList<T extends Comparable<? super T>> implements Iterable<T> {
    private static final int ALLOWED_MAX_LEVEL = 32;

    static class Entry<T extends Comparable<? super T>> {
        T element;
        Tuple<T> next[];

        public Entry(T element, int level) {
            this.element = element;
            this.next = new Tuple[level + 1];
        }

        public Tuple<T> getNext(int index) {
            return index > next.length ? null : next[index];
        }

        public Entry<T> getNextEntry(int index) {
            Tuple<T> tuple = this.next[index];
            return tuple != null ? tuple.entry : null;
        }

        public T getNextElement(int index) {
            Entry<T> nextEntry = getNextEntry(index);
            return nextEntry == null ? null : nextEntry.element;
        }

        public void setNext(int i, Entry<T> n) {
            next[i] = new Tuple<>(n, 1);
        }

        public void setNext(int i, Tuple<T> tuple) {
            next[i] = tuple;
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


    private Entry<T> head;
    private Entry<T> tail;
    private Entry<T> lastEntry;
    private int maxLevel;
    private int size;

    // Constructor
    public SkipList() {
        head = new Entry<>(null, ALLOWED_MAX_LEVEL);
        tail = new Entry<>(null, ALLOWED_MAX_LEVEL);
        maxLevel = 0;
        head.setNext(0, tail);
        size = 0;
    }


    // return prev[0..maxLevel] of nodes at which search went down one level, looking for x
    private Entry<T>[] find(T key) {
        Entry<T> p = head;
        Entry<T>[] prev = new Entry[maxLevel + 1];
        for (int i = maxLevel; i >= 0; i--) {
            while (p.getNextEntry(i) != tail && p.getNextElement(i).compareTo(key) < 0) {
                p = p.getNextEntry(i);
            }
            prev[i] = p;
        }
        return prev;
    }

    //choose number of levels for a new node randomly
    private int chooseLevel() {
        int level, mask;
        Random random = new Random();
        mask = (1 << maxLevel) - 1;
        level = Integer.numberOfTrailingZeros(random.nextInt() & mask);
        if (level > maxLevel) {
            return maxLevel + 1;
        } else {
            return level;
        }
    }

    // Add x to list. If x already exists, replace it. Returns true if new node is added to list
    public boolean add(T x) {
        Entry<T>[] prev = find(x);
        Entry<T> nextEntry = prev[0].getNextEntry(0);
        if (nextEntry != tail && nextEntry.element.compareTo(x) == 0) {
            nextEntry.element = x;
            return false;
        } else {
            int level = chooseLevel();
            int upto = maxLevel < level ? maxLevel : level;
            nextEntry = new Entry<>(x, level);
            for (int i = 0; i <= upto; i++) {
                nextEntry.setNext(i, prev[i].getNext(i));
                prev[i].setNext(i, nextEntry);
                if (i != 0) {
                    prev[i].getNext(i).span = getSpan(i - 1, prev[i], nextEntry);
                    nextEntry.getNext(i).span = (nextEntry.getNext(i).span + 1) - prev[i].getNext(i).span;
                }
            }
            while (upto < maxLevel) {
                Tuple<T> next = prev[++upto].getNext(upto);
                if (next.entry == tail || next.entry.element.compareTo(nextEntry.element) > 0) {
                    next.span++;
                }
            }
            size++;
            if (level > maxLevel) {
                head.setNext(level, nextEntry);
                nextEntry.setNext(level, tail);
                head.next[level].span = getSpan(maxLevel, head, nextEntry);
                nextEntry.next[level].span = size - head.next[level].span + 1;
                maxLevel = level;
            }
        }
        if (nextEntry.getNextEntry(0) == tail) {
            lastEntry = nextEntry;
        }
//        printList();
        return true;
    }

    private int getSpan(int level, Entry<T> entry, Entry<T> n) {
        int span = 0;
        while (entry != n) {
            if (entry.getNextEntry(level) == tail || entry.getNextElement(level).compareTo(n.element) > 0) {
                if (level == 0 && entry.getNextEntry(level) == tail) {
                    return span;
                }
                span += getSpan(level - 1, entry, n);
                break;
            } else {
                span += entry.getNext(level).span;
                entry = entry.getNextEntry(level);
            }
        }
        return span;
    }

    // Find smallest element that is greater or equal to x
    public T ceiling(T x) {
        Entry<T> prev = find(x)[0];
        if (prev.getNextElement(0) == tail) {
            return null;
        }
        return prev.getNextElement(0).compareTo(x) == 0 ? x : prev.getNextElement(0);
    }

    // Does list contain x?
    public boolean contains(T x) {
        Entry<T>[] prev = find(x);
        return prev[0].getNextElement(0) != tail && prev[0].getNextElement(0).compareTo(x) == 0;
    }

    // Return first element of list
    public T first() {
        return head.getNextElement(0);
    }

    // Find largest element that is less than or equal to x
    public T floor(T x) {
        Entry<T>[] prev = find(x);
        if (prev[0].getNextEntry(0) == tail) {
            return null;
        }
        return prev[0].getNextEntry(0).element.compareTo(x) == 0 ? x : prev[0].element;
    }

//    // Return element at index n of list.  First element is at index 0.
//    public T get(int n) {
//
//
//    }

    // Is the list empty?
    public boolean isEmpty() {
        return size == 0;
    }

    // Iterate through the elements of list in sorted order
    public Iterator<T> iterator() {
        return new SkipListIterator<>(this);
    }

    // Return last element of list
    public T last() {
        return lastEntry.element;
    }

    // Reorganize the elements of the list into a perfect skip list
    public void rebuild() {

    }

    // Remove x from list.  Removed element is returned. Return null if x not in list
    public T remove(T x) {
        Entry<T>[] prev = find(x);
        Entry<T> n = prev[0].getNextEntry(0);
        if (n.element.compareTo(x) != 0) return null;
        else {
            for (int i = 0; i <= maxLevel; i++) {
                if (prev[i].getNextEntry(i) != tail && prev[i].getNextElement(i).compareTo(x) == 0) {
                    Tuple<T> next = n.getNext(i);
                    int span = next.span + prev[i].getNext(i).span - 1;
                    prev[i].setNext(i, next);
                    prev[i].getNext(i).span = span;

                } else {
                    prev[i].getNext(i).span--;
                }
            }
        }
        size--;
        return n.element;
    }

    public T get(int index) {
        index++;
        if (index > size) {
            throw new NoSuchElementException();
        }
        int elementIdx = 0;
        int level = maxLevel;
        Entry<T> entry = head;
        while (elementIdx != index) {
            while (entry.getNext(level).span + elementIdx > index) {
                level--;
            }
            elementIdx += entry.getNext(level).span;
            entry = entry.getNext(level).entry;
        }
        return entry.element;
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
            return cursor.getNextEntry(0) != tail;
        }

        @Override
        public T next() {
            if (!hasNext()) {
                throw new java.util.NoSuchElementException();
            }
            cursor = cursor.getNextEntry(0);
            return cursor.element;
        }
    }

    public void printList() {
        for (int i = maxLevel; i >= 0; i--) {
            System.out.println();
            Entry<T> pre = head;
            while (pre != tail) {
                System.out.print(pre.element);
                printLines(pre.next[i].span);
                pre = pre.next[i].entry;
            }
            System.out.print("null");
        }
    }

    private void printLines(int span) {
        for (int i = 0; i < span; i++) {
            System.out.print(" - ");
        }
    }
}


