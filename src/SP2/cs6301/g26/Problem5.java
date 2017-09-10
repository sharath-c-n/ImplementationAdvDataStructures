package cs6301.g26;


/**
 * Created by Ankitha on 8/31/2017.
 * Driver programs for Custom Queue, the actual implementation is in CustomQueue class
 */
public class Problem5 {
    public static void main(String args[]) {
        CustomQueue<Integer> queue = new CustomQueue(6);
        for (int i = 0; i < 32; i++) {
            if (!queue.offer(i)) {
                System.out.println("Failed to insert");
                queue.resize();
                queue.offer(i);
            }
        }
        for (int i = 0; i < 26; i++) {
            System.out.print(queue.poll() + " ");
        }

        queue.resize();

        for (int i = 0; i < 11; i++) {
            if (!queue.offer(i)) {
                System.out.println("Failed to insert\n");
                queue.resize();
                queue.offer(i);
            }
        }

        for (int i = 0; i < 17; i++) {
            System.out.print(queue.poll() + " ");
        }
    }


}
