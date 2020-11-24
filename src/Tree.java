public class Tree<Key> {
    static class Node<Key> {
        Key data;
        Node<Key> left;
        Node<Key> right;

        public Node(Node<Key> left, Node<Key> right, Key data) {
            this.left = left;
            this.right = right;
            this.data = data;
        }

        public Node (Key data) {
            this(null, null, data);
        }
    }

    Node<Key> root;

    public Tree() {}

    public Tree(Node<Key> left, Node<Key> right, Key data) {
        root = new Node<>(left, right, data);
    }

    public Tree(Node<Key> root) {
        this.root = root;
    }

    public void printAll() {
        printAll(root);
    }

    public void printAllPreOder() {
        printAllPreOder(root);
    }

    public void printAllPreOder(Node<Key> node) {
        if (node == null) return;
        System.out.println(node.data);
        printAllPreOder(node.left);
        printAllPreOder(node.right);
    }

    public void printAllLastOder() {
        printAllLastOder(root);
    }

    private void printAllLastOder(Node<Key> node) {
        if (node == null) return;
        printAllLastOder(node.left);
        printAllLastOder(node.right);
        System.out.println(node.data);
    }

    private void printAll(Node<Key> node) {
        if (node == null) return;
        printAll(node.left);
        System.out.println(node.data);
        printAll(node.right);
    }

    public int getHeight() {
        return getHeight(root);
    }

    public boolean isBalance() {
        return isBalance(root);
    }

    private boolean isBalance(Node<Key> node) {
        if (node == null) return true;
        if (Math.abs(getHeight(node.left)-getHeight(node.right)) > 1) return false;
        return isBalance(node.left) && isBalance(node.right);
    }

    private int getHeight(Node<Key> node) {
        if (node == null) return 0;
        return 1 + Math.max(getHeight(node.left), getHeight(node.right));
    }
}
