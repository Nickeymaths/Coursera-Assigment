import java.io.PrintWriter;
import java.util.Comparator;
public class PriorityQueue {
    private  Object[] arr;
    private int N;
    private Comparator v;

    public PriorityQueue(int capacity) {
        arr = new Object[capacity + 1];
    }

    public PriorityQueue() {
        this(1);
    }

    public PriorityQueue(int capacity, Comparator v) {
        this(capacity);
        this.v = v;
    }

    public void add(Object item) {
        if (N == arr.length - 1) resize(2 * arr.length);
        arr[++N] = item;
        swim(N);
    }

    public Object remove() {
        Object rmItem = arr[1];
        exch(1, N--);
        arr[N+1] = null;
        if (N == arr.length/4) resize(arr.length/2);
        sink(1);
        return rmItem;
    }

    public void swim(int k) {
        while (k > 1) {
            if (less(k/2, k)) exch(k/2, k);
            k = k/2;
        }
    }

    public void sink(int k) {
        while (2 * k <= N) {
            int j = 2 * k;
            if (j < N && less(j, j + 1)) ++j;
            if (less(k, j)) exch(k, j);
            k = j;
        }
    }

    public boolean isEmpty(){
        return N == 0;
    }

    public boolean less(int i, int j) {
        if (v == null) {
            return ((Comparable)arr[i]).compareTo(arr[j]) < 0;
        }
        return v.compare(arr[i], arr[j]) < 0;
    }

    public void exch(int i, int j) {
        Object tmp = arr[i];
        arr[i] = arr[j];
        arr[j] = tmp;
    }

    public void resize(int capacity) {
        Object[] aux = new Object[capacity];
        for (int i = 1; i <= N; i++) {
            aux[i] = arr[i];
        }
        arr = aux;
    }

    public static void main(String[] args) {
        PriorityQueue queue = new PriorityQueue();
        queue.add(13);
        queue.add(10);
        queue.add(3);
        queue.add(5);
        queue.add(1);

        while (!queue.isEmpty()) {
            System.out.println(queue.remove());
        }
    }
}
