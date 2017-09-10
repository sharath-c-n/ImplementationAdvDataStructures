package cs6301.g26;

/**
 * @author Sandeep on 9/9/2017
 */

import java.util.Scanner;

public class GenericStack<T> {
    private int size;
    private T[] arr;
    private int top;

    public GenericStack(int size) {
        this.size = size;
        arr = (T[]) new Object[size];
        top = -1;
    }

    /**
     * This member function pushes the value on to the Stack.
     *
     * @param x: The value to push onto the stack.
     * @return indicates whether push is successful or not.
     * Throws an Illegal state Exception when the size of the stack is Full.
     */
    public boolean push(T x) throws IllegalStateException {
        if (!isFull()) {
            arr[++top] = x;
        } else {
            throw new IllegalStateException("Stack full");
        }
        return true;
    }

    /**
     * This member function pops the last element from the Stack.
     *
     * @return returns a popped element from the Stack.
     * Throws an Illegal state Exception when the size of the stack is Empty.
     **/
    public T pop() throws IllegalStateException {
        if (isEmpty()) {
            throw new IllegalStateException("Stack is Empty!!!");
        }
        return arr[top--];
    }

    /**
     * This member function checks whether the stack is empty.
     */
    public boolean isEmpty() {
        return top == -1;
    }

    /**
     * This member function checks whether the stack is Full.
     */
    public boolean isFull() {
        return top == size;
    }

    public static void main(String[] args) throws Exception {
        GenericStack<Integer> st = new GenericStack<Integer>(4);
        Scanner in = new Scanner(System.in);
        int option = 0;
        System.out.println(" Enter option 1: push\n\t\t\t  2: pop\n\t\t\t  3:exit");
        option = in.nextInt();
        while (option < 3) {
            //Push opertation
            if (option == 1) {
                try {
                    System.out.println("Enter the number to push into Stack");
                    int val = in.nextInt();
                    st.push(val);
                } catch (IllegalStateException msg) {
                    System.err.println(msg);
                }

            } else //pop operation
            {
                try {
                    int val = st.pop();
                    System.out.println("The value popped from the stack is " + val);
                } catch (Exception msg) {
                    System.err.println(msg);
                }
            }
            option = in.nextInt();
        }
    }
}