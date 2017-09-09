package cs6301.g26;


public class GenericStack<T> {
    private int size;
    private T[] arr;
    private int top;

    public GenericStack(int size) {
        this.size = size;
        arr = (T[]) new Object[size];
        top = -1;
    }

    public boolean push(T x) throws IllegalStateException {
        if (!isFull()) {
            arr[++top] = x;
        } else {
            throw new IllegalStateException("Stack full");
        }
        return true;
    }

    public T pop() throws IllegalStateException {
        if (isEmpty()) {
            throw new IllegalStateException("Stack is Empty!!!");
        }
        return arr[top--];
    }

    public boolean isEmpty() {
        return top == -1;
    }

    public boolean isFull() {
        return top == size;
    }

    public static void main(String[] args) throws Exception {
        GenericStack<Integer> st = new GenericStack<Integer>(4);
        st.push(14);
        st.push(17);
        st.push(19);
        st.push(20);
        System.out.println(st.pop());
        System.out.println(st.pop());
        st.push(35);
        System.out.println(st.pop());
        System.out.println(st.pop());
        System.out.println(st.pop());
        System.out.println(st.pop());
        System.out.println(st.pop());
        st.push(11);
        System.out.println(st.pop());
        System.out.println(st.pop());
    }
}