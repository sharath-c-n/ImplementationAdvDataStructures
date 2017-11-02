package cs6301.g26;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Random;

/**
 * Created by Ankitha on 10/28/2017.
 */

public class SkipList<T extends Comparable<? super T>> implements Iterable<T> {
    private static final int ALLOWED_MAX_LEVEL = 32;

    static class Entry<T extends Comparable<? super T>> {
        T element;
        Tuple<T> next[];

        public Entry(T element, int level) {
            this.element = element;
            this.next = new Tuple[level + 1];
        }

        public Tuple<T> getNext(int level) {
            return level > next.length ? null : next[level];
        }

        public Entry<T> getNextEntry(int level) {
            Tuple<T> tuple = getNext(level);
            return tuple != null ? tuple.entry : null;
        }

        public T getNextElement(int level) {
            Entry<T> nextEntry = getNextEntry(level);
            return nextEntry == null ? null : nextEntry.element;
        }

        public void setNext(int level, Entry<T> n) {
            next[level] = new Tuple<>(n, 1);
        }

        public void setNext(int level, Tuple<T> tuple) {
            next[level] = tuple;
        }

        @Override
        public String toString() {
            return element.toString();
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
    private Entry<T> lastEntry;
    private int maxLevel;
    private int size;

    // Constructor
    public SkipList() {
        head = new Entry<>(null, ALLOWED_MAX_LEVEL);
        maxLevel = 0;
        head.setNext(0, (Entry<T>) null);
        size = 0;
    }


    // return prev[0..maxLevel] of nodes at which search went down one level, looking for x
    private Entry<T>[] find(T key) {
        Entry<T> p = head;
        Entry<T>[] prev = new Entry[maxLevel + 1];
        for (int i = maxLevel; i >= 0; i--) {
            while (p.getNextEntry(i) != null && p.getNextElement(i).compareTo(key) < 0) {
                p = p.getNextEntry(i);
            }
            prev[i] = p;
        }
        return prev;
    }

    private void reBuild(Entry<T> start, Entry<T> end, int height) {
        if (start != end && start.getNextEntry(0) != end && height >= 0) {
            Entry<T> mid = findMid(start, end);
            Tuple<T> temp = mid.getNext(0);
            mid.next = new Tuple[height + 1];
            mid.setNext(0, temp);
            reBuild(start, mid, height - 1);
            reBuild(mid, end, height - 1);
            connect(start, mid, end, height);
        }
    }

    private void connect(Entry<T> start, Entry<T> mid, Entry<T> end, int height) {
        if (height > 0) {
            start.setNext(height, mid);
            mid.setNext(height, end);
            start.getNext(height).span = getSpan(height - 1, start, mid);
            mid.getNext(height).span = getSpan(height - 1, mid, end);
        }
    }

    //rebuild skipList
    public void reBuild() {
        int maxLevel = (int) Math.ceil(Math.log(((float) size / 2)) / Math.log(2));
        reBuild(head, null, maxLevel - 1);
        this.maxLevel = maxLevel - 1;
    }

    private Entry<T> findMid(Entry<T> start, Entry<T> end) {
        Entry<T> fastPtr = start.getNextEntry(0);
        Entry<T> slowPtr = start.getNextEntry(0);
        while (fastPtr != end && fastPtr.getNextEntry(0) != end) {
            slowPtr = slowPtr.getNextEntry(0);
            fastPtr = fastPtr.getNextEntry(0).getNextEntry(0);
        }
        return slowPtr;
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
        if (nextEntry != null && nextEntry.element.compareTo(x) == 0) {
            nextEntry.element = x;
            return false;
        } else {
            int level = chooseLevel();
            int upto = maxLevel < level ? maxLevel : level;
            nextEntry = new Entry<>(x, level);
            for (int currentLevel = 0; currentLevel <= upto; currentLevel++) {
                nextEntry.setNext(currentLevel, prev[currentLevel].getNext(currentLevel));
                prev[currentLevel].setNext(currentLevel, nextEntry);
                if (currentLevel != 0) {
                    prev[currentLevel].getNext(currentLevel).span = getSpan(currentLevel - 1, prev[currentLevel], nextEntry);
                    nextEntry.getNext(currentLevel).span = (nextEntry.getNext(currentLevel).span + 1) - prev[currentLevel].getNext(currentLevel).span;
                }
            }
            while (upto < maxLevel) {
                Tuple<T> next = prev[++upto].getNext(upto);
                next.span++;
            }
            size++;
            if (level > maxLevel) {
                head.setNext(level, nextEntry);
                nextEntry.setNext(level, (Entry<T>) null);
                head.next[level].span = getSpan(maxLevel, head, nextEntry);
                nextEntry.next[level].span = size - head.next[level].span + 1;
                maxLevel = level;
            }
        }
        if (nextEntry.getNextEntry(0) == null) {
            lastEntry = nextEntry;
        }
//        printList();
        return true;
    }

    private int getSpan(int level, Entry<T> entry, Entry<T> n) {
        int span = 0;
        while (entry != n) {
            //need to test after refactor
            span += entry.getNext(level).span;
            entry = entry.getNextEntry(level);
        }
        return span;
    }

    // Find smallest element that is greater or equal to x
    public T ceiling(T x) {
        Entry<T> prev = find(x)[0];
        if (prev.getNextElement(0) == null) {
            return null;
        }
        return prev.getNextElement(0).compareTo(x) == 0 ? x : prev.getNextElement(0);
    }

    // Does list contain x?
    public boolean contains(T x) {
        Entry<T>[] prev = find(x);
        return prev[0].getNextElement(0) != null && prev[0].getNextElement(0).compareTo(x) == 0;
    }

    // Return first element of list
    public T first() {
        return head.getNextElement(0);
    }

    // Find largest element that is less than or equal to x
    public T floor(T x) {
        Entry<T>[] prev = find(x);
        if (prev[0].getNextEntry(0) == null) {
            return null;
        }
        return prev[0].getNextEntry(0).element.compareTo(x) == 0 ? x : prev[0].element;
    }

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


    // Remove x from list.  Removed element is returned. Return null if x not in list
    public T remove(T x) {
        Entry<T>[] prev = find(x);
        Entry<T> n = prev[0].getNextEntry(0);
        if (n.element.compareTo(x) != 0) return null;
        else {
            if (n.getNextEntry(0) == null) {
                lastEntry = prev[0];
            }
            for (int i = 0; i <= maxLevel; i++) {
                if (prev[i].getNextEntry(i) != null && prev[i].getNextElement(i).compareTo(x) == 0) {
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

    public Entry<T> getEntry(int index) {
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
        return entry;
    }

    public T get(int index) {
        Entry<T> result = getEntry(index);
        return result == null ? null : result.element;
    }

    // Return the number of elements in the list
    public int size() {
        return size;
    }

    public class SkipListIterator<T extends Comparable<? super T>> implements Iterator<T> {
        SkipList.Entry<T> cursor;

        public SkipListIterator(SkipList<T> skipList) {
            cursor = skipList.head;
        }

        @Override
        public boolean hasNext() {
            return cursor.getNextEntry(0) != null;
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
            while (pre != null) {
                System.out.print(pre.element);
                //Print span lines
                for (int j = 0; j < pre.next[i].span; j++) {
                    System.out.print(" - ");
                }
                pre = pre.next[i].entry;
            }
            System.out.print("null");
        }
    }

}


