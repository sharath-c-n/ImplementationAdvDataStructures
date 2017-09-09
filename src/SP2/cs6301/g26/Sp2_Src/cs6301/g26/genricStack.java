package cs6301.g26;

public class genricStack<T extends Object> {
    private int size;
    private T[] arr;
    private int top;

    public genricStack(int size) {
        this.size = size;
        arr = (T[]) new Object[size];
        top = -1;
    }

    public boolean push(T x) throws Exception {
        try {
            top++;
            arr[top] = x;
            System.out.println("Successfully added the Element  " + x);
        } catch (Exception ex) {
            System.out.println("Stack is Full .Can not push the element");
            top--;
            return false;
        }
        return true;
    }

    public T pop() throws Exception {
        T top_val = null;
        try {
            top_val = arr[top];
            top--;
            System.out.println("Successfully popped the Element " + top_val);
        } catch (Exception ex) {
            System.out.println("Stack is Empty. Can not pop an Element ");
        }
        return top_val;
    }

    public boolean isEmpty() {
        return (top == -1) ? true : false;
    }

    public boolean isFull() {
        return (top == size) ? true : false;
    }

    public static void main(String[] args) throws Exception {
        genricStack<Integer> st = new genricStack<Integer>(4);
        st.push(14);
        st.push(17);
        st.push(19);
        st.push(20);
        st.pop();
        st.pop();
        st.push(35);
        st.pop();
        st.pop();
        st.pop();
        st.pop();
        st.pop();
        st.push(11);
        st.pop();
        st.pop();
    }
}
