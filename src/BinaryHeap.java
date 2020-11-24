import java.util.Comparator;

public class BinaryHeap {
    private Object[] arr;
    private int N;
    private Comparator v;

    public BinaryHeap(Object[] arr) {
        this.arr = new Object[arr.length + 1];
        for (int i = 0; i < arr.length; i++) {
            this.arr[i+1] = arr[i];
        }
        N = arr.length;
    }

    public BinaryHeap(Object[] arr, Comparator comparator) {
        this(arr);
        this.v = comparator;
    }

    public void sort() {
        int old_N = N;
        for (int k = N/2; k > 0; --k) {
            sink(k);
        }
        while (!isEmpty()) {
            remove(1);
        }
        N = old_N;
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

    public void insert(int value) {
        if (N == arr.length - 1) resize(2 * N + 1);
        arr[++N] = value;
        swim(N);
    }

    public void remove(int k) {
        exch(k, N--);
        sink(1);
    }

    public void exch(int i, int j) {
        Object tmp = arr[i];
        arr[i] = arr[j];
        arr[j] = tmp;
    }

    public void resize(int capacity) {
        Object[] aux = new Object[capacity];
        for (int i = 0; i < arr.length; i++) {
            aux[i] = arr[i];
        }
        arr = aux;
    }

    public boolean isEmpty() {
        return N == 0;
    }

    public boolean less(int i, int j) {
        if (v == null) {
            return ((Comparable)arr[i]).compareTo(arr[j]) < 0;
        }
        return v.compare(arr[i], arr[j]) < 0;
    }

    public void showAll() {
        for (int i = 1; i <= N; i++) {
            System.out.print(arr[i].toString() + " ");
        }
    }

    public static void main(String[] args) {
        Student[] students = {new Student(3.2, "A", 123), new Student(3.2, "B", 345), new Student(4, "C", 131)};
        BinaryHeap binaryHeap = new BinaryHeap(students, Student.STUDENT_COMPARATOR);
        binaryHeap.sort();
        binaryHeap.showAll();
    }
}
