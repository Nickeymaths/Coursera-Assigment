import java.util.Scanner;

public class AVL {

    private Node root;             // root of st.BST

    private class Node {
        private String key;           // sorted by key
        private Integer val;         // associated data
        private Node left, right;  // left and right subtrees
        private int count;             // number of nodes in subtree
        private int height;

        public Node(String key, Integer val, int count, int height) {
            this.key = key;
            this.val = val;
            this.count = count;
            this.height = height;
        }
        public String toString() {
            return "(" + key + "," + val + ") ";
        }
    }

    public boolean isEmpty() {
        return size() == 0;
    }

    public void inorderPrint() {
        if (isEmpty()) System.out.print("Empty tree");
        else inorderPrint(root);
    }

    private void inorderPrint(Node x) {
        if (x != null) {
            inorderPrint(x.left);
            System.out.print(x);
            inorderPrint(x.right);
        }
    }

    public void delete(String key) {
        root = delete(root, key);
    }

    private Node delete(Node x, String key) {
        if (x == null) return null;

        int cmp = x.key.compareTo(key);
        if      (cmp > 0) x.left = delete(x.left, key);
        else if (cmp < 0) x.right = delete(x.right, key);
        else {
            if (x.left == null) return x.right;
            if (x.right == null) return x.left;
            String prev = max(x.left);
            x.left = delete(x.left, prev);
            x.key = prev;
        }
        return balance(x);
    }

    private String max(Node x) {
        while (x.right != null) x = x.right;
        return x.key;
    }

    public void put(String key, Integer val) {
        if (val != null) {
            root = put(root, key, val);
        }
    }

    private Node put(Node node, String key, Integer val) {
        if (node == null) return new Node(key, val, 1, 1);
        int cmp = node.key.compareTo(key);
        if      (cmp > 0)   node.left = put(node.left, key, val);
        else if (cmp < 0)   node.right = put(node.right, key, val);
        else                node.val = val;
        return balance(node);
    }

    private Node balance(Node node) {
        int diff = height(node.left) - height(node.right);
        if      (diff > 1) return leftCase(node);
        else if (diff < -1) return rightCase(node);
        update(node);
        return node;
    }

    private Node rightCase(Node x) {
        if (height(x.right.right) < height(x.right.left)) x.right = rotateRight(x.right);
        return rotateLeft(x);
    }

    private Node leftCase(Node x) {
        if (height(x.left.left) < height(x.left.right)) x.left = rotateLeft(x.left);
        return rotateRight(x);
    }

    private Node rotateLeft(Node x) {
        Node upNode = x.right;
        x.right = upNode.left;
        upNode.left = x;
        update(x);
        update(upNode);
        return upNode;
    }

    private void update(Node x) {
        x.height = 1 + Math.max(height(x.left), height(x.right));
        x.count = 1 + size(x.left) + size(x.right);
    }

    private Node rotateRight(Node x) {
        Node upNode = x.left;
        x.left = upNode.right;
        upNode.right = x;
        update(x);
        update(upNode);
        return upNode;
    }

    public int size() {
        return size(root);
    }

    // return number of key-value pairs in st.BST rooted at x
    private int size(Node x) {
        if (x == null) return 0;
        else return x.count;
    }

    public int height() {
        return height(root);
    }
    private int height(Node x) {
        if (x == null) return 0;
        return x.height;
    }

    public void printTree() {
        int rows = 2 * height();
        int columns = size()*2 ;
        String grid[][] = new String[13][21];
        print(root, 0, 0, grid);
        System.out.print(" \n");
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < 21; j++) {
                if (grid[i][j]==null)
                    System.out.print(" ");
                else
                    System.out.printf(grid[i][j]);
            }
            System.out.print("\n");
        }
    }

    private void print(Node x, int row, int offset, String grid[][]) {
        if (x!=null) {
            int column = 2*size(x.left) + offset;
            grid[row][column+1] = x.key;
            grid[row+1][column] = "/";
            grid[row+1][column+2] = "\\";
            print(x.left, row+2, offset, grid);
            print(x.right, row+2, column + 2, grid);
        }
    }

    public static void main(String[] args) {
        AVL st = new AVL();

        Scanner input = new Scanner(System.in);
        for (int i = 0; input.hasNext(); i++) {
            String key = input.next();
            if (key.equals("-")) st.delete(input.next());
            else st.put(key, i);
            st.printTree();
        }

        st.inorderPrint();
        st.printTree();
    }
}
