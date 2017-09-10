package cs6301.g26;

/**
 * This class provides a custom Qeue implementation with
 * isFull,isEmpty,offer,peek,poll,resize operations on the queue
 * @author Ankitha,Sharath
 */

public class CustomQueue<E>{
    public static final int DEFAULT_CAPACITY = 16;
    public static final float MAX_THRESHOLD = (float) 0.9;
    public static final float MIN_THRESHOLD = (float) 0.25;
    private Object[] queue;
    private int  front , rear , size;

    public CustomQueue(int initialCapacity) {
        //To preserve the minimum size requirement
        initialCapacity = initialCapacity > DEFAULT_CAPACITY ? initialCapacity : DEFAULT_CAPACITY;
        front = 0;
        rear = -1 ;
        size = 0;
        queue = new Object[initialCapacity];
    }

    //Checks if the Queue has reached it's capacity
    public boolean isFull(){
        return (size == queue.length);
    }

    //Checks if the Queue is empty
    public boolean isEmpty(){
        return(size ==0 );
    }

    /**
     * This method adds an entry in the Queue
     *
     * @param e : entry to be added.
     * @return boolean
     */
    public boolean offer(E e){
        if(isFull()){
            return false;
        }
        else{
            rear = (rear+1)%queue.length;
            queue[rear] = e ;
            size++;

        }
        return true;
    }

    /**
     * This method looks up the entry at the front of the Queue
     *
     * @return entry at the front of the Queue
     */
    public E peek(){
      if(isEmpty()){
          return null;
      }
      else{
          return (E) queue[front];
      }
    }

    /**
     * This method looks up the entry at the front of the Queue
     * and removes it from the Queue
     * @return entry at the front of the Queue
     */
    public E poll(){
        E temp = peek();
        if(temp!=null){
            front = (front+1)%queue.length;
            size--;
        }
        return temp;
    }

    /**
     * This method resizes the Queue
     * If Queue size greater than max Threshold it doubles the Queue size
     * else if it is less than min Threshold it halves it
     */

    public void resize(){
        float current_size = ((float)size/queue.length);
        if( current_size >= MAX_THRESHOLD)
        {
            resize(size*2);
        }
        else if(current_size <= MIN_THRESHOLD){
            resize(size/2);
        }
    }

    /**
     * This method resizes the Queue
     * If method resizes the queue by creating a
     * temporary object with new size copying entries
     * from original Queue to the new one and reassigning
     * it back to the original Queue
     *
     *
     * @param newSize : value to be resized to.
     */
    private void resize(int newSize) {
        newSize = newSize >= DEFAULT_CAPACITY ? newSize : DEFAULT_CAPACITY;
        Object[] newQueue = new Object[newSize];
        rear = -1;
        while(rear < size -1){
            newQueue[++rear] = queue[front];
            front = (front+1)%queue.length;
        }
        front = 0;
        queue = newQueue;
    }
}
