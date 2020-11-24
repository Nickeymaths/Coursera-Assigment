import java.util.Comparator;
public class Student {

    public static Comparator<Student> STUDENT_COMPARATOR = customComparator();

    private double score;
    private String name;
    private int id;

    public Student(double score, String name, int id) {
        this.score = score;
        this.name = name;
        this.id = id;
    }

    public static Comparator<Student> customComparator() {
        return new Comparator<Student>() {
            @Override
            public int compare(Student o1, Student o2) {
                if (o1.score > o2.score) {
                    return -1;
                }
                if (o1.score < o2.score) {
                    return 1;
                }
                if (!o1.name.equals(o2.name)) {
                    return o1.name.compareTo(o2.name);
                }
                if (o1.id < o2.id) {
                    return -1;
                }
                if (o1.id > o2.id) {
                    return 1;
                }
                return 0;
            }
        };
    }

    @Override
    public String toString() {
        return "Student[name=" + this.name + ",score=" + this.score + ",id=" + this.id + "]";
    }
}
