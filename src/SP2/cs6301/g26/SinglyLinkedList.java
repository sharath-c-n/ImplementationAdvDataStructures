/** @author rbk
 *  Singly linked list: for instructional purposes only
 *  Ver 1.0: 2017/08/08
 *  Ver 1.1: 2017/08/30: Fixed error: If last element of list is removed,
 *  "tail" is no longer a valid value.  Subsequently, if items are added
 *  to the list, code would do the wrong thing.
 */

package cs6301.g26;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class SinglyLinkedList<T> implements Iterable<T> {

    /** Class Entry holds a single node of the list */
    static class Entry<T> {
        T element;
        Entry<T> next;

        Entry(T x, Entry<T> nxt) {
            element = x;
            next = nxt;
        }
    }

    // Dummy header is used.  tail stores reference of tail element of list
    Entry<T> head, tail;
    int size;

    public SinglyLinkedList() {
        head = new Entry<>(null, null);
        tail = head;
        size = 0;
    }

    public Iterator<T> iterator() { return new SLLIterator<>(this); }

    private class SLLIterator<E> implements Iterator<E> {
        SinglyLinkedList<E> list;
        Entry<E> cursor, prev;
        boolean ready;  // is item ready to be removed?

        SLLIterator(SinglyLinkedList<E> list) {
            this.list = list;
            cursor = list.head;
            prev = null;
            ready = false;
        }

        public boolean hasNext() {
            return cursor.next != null;
        }

        public E next() {
            prev = cursor;
            cursor = cursor.next;
            ready = true;
            return cursor.element;
        }

        // Removes the current element (retrieved by the most recent next())
        // Remove can be called only if next has been called and the element has not been removed
        public void remove() {
            if(!ready) {
                throw new NoSuchElementException();
            }
            prev.next = cursor.next;
            // Handle case when tail of a list is deleted
            if(cursor == list.tail) {
                list.tail = prev;
            }
            cursor = prev;
            ready = false;  // Calling remove again without calling next will result in exception thrown
            size--;
        }
    }

    // Add new elements to the end of the list
    public void add(T x) {
        tail.next = new Entry<>(x, null);
        tail = tail.next;
        size++;
    }

    public void printList() {
	/* Code without using implicit iterator in for each loop:

        Entry<T> x = head.next;
        while(x != null) {
            System.out.print(x.element + " ");
            x = x.next;
        }
	*/

        System.out.print(this.size + ": ");
        for(T item: this) {
            System.out.print(item + " ");
        }

        System.out.println();
    }

    // Rearrange the elements of the list by linking the elements at even index
    // followed by the elements at odd index. Implemented by rearranging pointers
    // of existing elements without allocating any new elements.
    public void unzip() {
        if(size < 3) {  // Too few elements.  No change.
            return;
        }

        Entry<T> tail0 = head.next;
        Entry<T> head1 = tail0.next;
        Entry<T> tail1 = head1;
        Entry<T> c = tail1.next;
        int state = 0;

        // Invariant: tail0 is the tail of the chain of elements with even index.
        // tail1 is the tail of odd index chain.
        // c is current element to be processed.
        // state indicates the state of the finite state machine
        // state = i indicates that the current element is added after taili (i=0,1).
        while(c != null) {
            if(state == 0) {
                tail0.next = c;
                tail0 = c;
                c = c.next;
            } else {
                tail1.next = c;
                tail1 = c;
                c = c.next;
            }
            state = 1 - state;
        }
        tail0.next = head1;
        tail1.next = null;
    }

    public void multiUnzip(int k) {
        if (size < 3 || (size <k)) {  // Too few elements.  No change.
            return;
        }
        int counter = 0;
        // state stores indicates the state of finite state machine
        // state =i indicates the current element is added after tails[i] where i=0,1,...,k-1

        int state = 0;
        ArrayList<Entry<T>> tails = new ArrayList<>();
        ArrayList<Entry<T>> heads = new ArrayList<>();
        //Initialization of tails and heads
        tails.add(head.next);
        while (counter < k - 1) {
            heads.add(tails.get(counter).next);
            tails.add(heads.get(counter));
            counter++;
        }
        Entry<T> c = tails.get(tails.size() - 1).next;
        while (c != null) {
            tails.get(state).next = c;
            tails.set(state, c);
            c = c.next;
            state = (state + 1) % (k); //state is in the range of [0, k-1]
        }
        counter = 0;
        //Assign the tails of chains of elements to the head of the next chain of elements
        while (counter < k-1) {
            tails.get(counter).next = heads.get(counter);
            counter = counter + 1;
        }
        tails.get(tails.size() - 1).next = null;

    }
}