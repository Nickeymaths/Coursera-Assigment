import java.util.Comparator;

/**
 * Complexity:
 * Worse: ~N/2logN
 * Best : logN
 */
public class MergeSort {
    private static final int CUTOFF = 1;

    public static void merge(Object[] arr, Object[] aux, int lo, int mid, int hi, Comparator v) {
        if (less(arr[mid], arr[mid + 1], v)) return;
        int i = 0, j = mid + 1;
        for (int k = 0; k < arr.length; k++) {
            if (i > mid) aux[k] = arr[j++];
            else if (j > hi) aux[k] = arr[i++];
            else if (less(arr[i], arr[j], v)) aux[k] = arr[i++];
            else aux[k] = arr[j++];
        }
    }

    private static void sort(Object[] arr, Object[] aux, int lo, int hi, Comparator v) {
        if (lo >= hi) return;
        if (hi -lo <= CUTOFF - 1) {
            InsertionSort.sort(arr, v);
            return;
        }
        int mid = lo + (hi - lo)/2;
        sort(aux, arr, lo, mid, v);
        sort(aux, arr, mid + 1, hi, v);
        merge(arr, aux, lo, mid, hi, v);
    }

    public static void sort(Object[] arr, Comparator v) {
        Object[] aux = new Object[arr.length];
        for (int i = 0; i < arr.length; i++) {
            aux[i] = arr[i];
        }
        sort(arr, aux, 0, arr.length - 1, v);
        for (int i = 0; i < arr.length; i++) {
            arr[i] = aux[i];
        }
    }

    public static boolean less(Object A, Object B, Comparator comparator) {
        return comparator.compare(A, B) < 0;
    }

    public static void exch(Object[] arr, int i, int j) {
        Object tmp = arr[i];
        arr[i] = arr[j];
        arr[j] = tmp;
    }

    public static void showAll(Object[] arr) {
        for (Object obj : arr) {
            System.out.println(obj.toString());
        }
    }

    public static void main(String[] args) {
        Student[] students = {new Student(3.2, "A", 123), new Student(3.2, "B", 345), new Student(4, "C", 131)};
        sort(students, Student.STUDENT_COMPARATOR);
        showAll(students);
    }
}
