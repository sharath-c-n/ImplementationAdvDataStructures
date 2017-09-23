/** @author rbk
 *  Doubly Linked List: for instructional purposes only
 *  Ver 1.0: 2017/08/08
 *  Ver 1.1: 2017/08/30: Fixed error: If last element of list is removed,
 *  "tail" is no longer a valid value.  Subsequently, if items are added
 *  to the list, code would do the wrong thing.
 */

package cs6301.g26;

public class DoublyLinkedList<T> {

    /** Class Entry holds a single node of the list */
    public static class Entry<T> {
        public T element;
        public Entry<T> next;
        public Entry<T> prev;

        Entry(T x, Entry<T> nxt ,Entry prev) {
            element = x;
            next = nxt;
            this.prev = prev;
        }
    }

    // Dummy header is used.  tail stores reference of tail element of list
    public Entry<T> head, tail;
    public int size;

    public DoublyLinkedList() {
        head = new Entry<>(null, null, null);
        tail = head;
        size = 0;
    }

    // Add new elements to the end of the list
    public void add(T x) {
        tail.next = new Entry<>(x, null,null);
        tail.next.prev = tail;
        tail = tail.next;
        size++;
    }

    public void printList() {
        Entry<T> x = head.next;
        while(x != null) {
            System.out.print(x.element + " ");
            x = x.next;
        }
    }

}
