import java.util.Comparator;

/**
 * Complexity:
 * Worse:   ~N^2/2 compares, exchanges
 * Average: ~N^2/4 compares, exchanges
 * Best:    ~N     compares, exchanges
 */
public class InsertionSort {
    public static void sort(Object[] arr, Comparator comparator) {
        for (int i = 0; i < arr.length; ++i) {
            for (int j = i; j > 0; --j) {
                if (less(arr[j], arr[j-1], comparator)) {
                    exch(arr, j, j-1);
                }
            }
        }
    }

    public static void sort(Comparable[] arr) {
        for (int i = 0; i < arr.length; ++i) {
            for (int j = i; j > 0; --j) {
                if (less(arr[j], arr[j-1])) {
                    exch(arr, j, j-1);
                }
            }
        }
    }

    public static boolean less(Object A, Object B, Comparator comparator) {
        return comparator.compare(A, B) < 0;
    }

    public static boolean less(Comparable A, Comparable B) {
        return A.compareTo(B) < 0;
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
