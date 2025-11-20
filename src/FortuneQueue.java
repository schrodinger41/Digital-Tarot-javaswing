import java.util.LinkedList;
import java.util.Queue;

/**
 * FortuneQueue - simple FIFO queue for fortune messages.
 */
class FortuneQueue {
    private final Queue<String> q = new LinkedList<>();

    public void enqueue(String msg) {
        q.add(msg);
    }

    public String dequeue() {
        return q.poll();
    }

    public boolean isEmpty() {
        return q.isEmpty();
    }

    public int size() {
        return q.size();
    }
}
