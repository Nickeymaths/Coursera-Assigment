import java.util.Comparator;
/**
 * Complexity:
 * Best:    - ~NlogN compares
 * Average: - ~2NlogN compares
 * Worse:   - ~N^2/2 compares probability 1/N!
 */
public class QuickSort {
    private static final int CUTOFF = 10;

    public static int partition(Object[] arr, int lo, int hi, Comparator compare) {
        int i = lo, j = hi + 1;
        while (i <= j) {
            while (less(arr[++i], arr[lo], compare)) {
                if (i == hi) break;
            }

            while(less(arr[lo], arr[--j], compare));
            if (i >= j) break;
            exch(arr, i, j);
        }
        exch(arr, lo, j);
        return j;
    }

    public static int partition(Comparable[] arr, int lo, int hi) {
        int i = lo, j = hi + 1;
        while (i <= j) {
            while (less(arr[++i], arr[lo])) {
                if (i == hi) break;
            }

            while(less(arr[lo], arr[--j]));
            if (i >= j) break;
            exch(arr, i, j);
        }
        exch(arr, lo, j);
        return j;
    }

    public static boolean less(Object A, Object B, Comparator comparator) {
        return comparator.compare(A, B) < 0;
    }

    public static boolean less(Comparable a, Comparable b) {
        return a.compareTo(b) < 0;
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

    private static void sort(Object[] arr, int lo, int hi, Comparator compare) {
        if (lo >= hi) return;
        if (hi - lo <= CUTOFF - 1) {
            InsertionSort.sort(arr, compare);
        }
        int j = partition(arr, lo, hi, compare);
        sort(arr, lo, j - 1, compare);
        sort(arr, j + 1, hi, compare);
    }

    private static void sort(Comparable[] arr, int lo, int hi) {
        if (lo >= hi) return;
        if (hi - lo <= CUTOFF - 1) {
            InsertionSort.sort(arr);
        }
        int j = partition(arr, lo, hi);
        sort(arr, lo, j - 1);
        sort(arr, j + 1, hi);
    }

    public static void sort(Object[] arr, Comparator compare) {
        sort(arr, 0, arr.length - 1, compare);
    }

    public static void sort(Comparable[] arr) {
        sort(arr, 0, arr.length - 1);

    }

    public static void main(String[] args) {
        Student[] students = {new Student(3.2, "A", 123), new Student(3.2, "B", 345), new Student(4, "C", 131)};
        sort(students, Student.STUDENT_COMPARATOR);
        showAll(students);

        Integer[] integers = {2,4,0,1,7,4,3};
        sort(integers);
        showAll(integers);
    }
}
