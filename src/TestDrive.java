import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.List;

public class TestDrive {

    public static void main(String[] args) {
        RBTree<Integer, String> rbTree = new RBTree<>();
        rbTree.put(3, "C");
        rbTree.put(5, "E");
        rbTree.put(6, "F");
        rbTree.delete(3);
        System.out.println();
    }
}