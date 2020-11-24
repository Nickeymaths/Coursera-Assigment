import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class BinaryTree<Key> implements Iterable<Key> {
    public Node<Key> root;

    @Override
    public Iterator<Key> iterator() {
        return new InOrderIterator();
    }

    private class InOrderIterator implements Iterator<Key> {
        List<Key> keys = new ArrayList<>();
        Iterator<Key> iterator;

        public InOrderIterator() {
            getKeysByInOrderTraversal(root, keys);
            iterator = keys.iterator();
        }

        @Override
        public boolean hasNext() {
            return iterator.hasNext();
        }

        @Override
        public Key next() {
            return iterator.next();
        }
    }

    public Iterator<Key> getPreOrderIterator() {
        return new PreOrderIterator();
    }

    private class PreOrderIterator implements Iterator<Key> {
        List<Key> keys = new ArrayList<>();
        Iterator<Key> iterator;

        public PreOrderIterator() {
            getKeysByPreOrderTraversal(root, keys);
            iterator = keys.iterator();
        }

        @Override
        public boolean hasNext() {
            return iterator.hasNext();
        }

        @Override
        public Key next() {
            return iterator.next();
        }
    }

    private void getKeysByPreOrderTraversal(Node<Key> node, List<Key> keys) {
        if (node == null) return;
        keys.add(node.key);
        getKeysByPreOrderTraversal(node.left, keys);
        getKeysByPreOrderTraversal(node.right, keys);
    }

    private void getKeysByInOrderTraversal(Node<Key> node, List<Key> keys) {
        if (node == null) return;
        getKeysByInOrderTraversal(node.left, keys);
        keys.add(node.key);
        getKeysByInOrderTraversal(node.right, keys);
    }

    public Iterator<Key> getPostOrderIterator() {
        return new PostOrderIterator();
    }

    private class PostOrderIterator implements Iterator<Key> {
        List<Key> keys = new ArrayList<>();
        Iterator<Key> iterator;

        public PostOrderIterator() {
            getKeysByPostOrderTraversal(root, keys);
            iterator = keys.iterator();
        }

        @Override
        public boolean hasNext() {
            return iterator.hasNext();
        }

        @Override
        public Key next() {
            return iterator.next();
        }
    }

    private void getKeysByPostOrderTraversal(Node<Key> node, List<Key> keys) {
        if (node == null) return;
        getKeysByPostOrderTraversal(node.left, keys);
        getKeysByPostOrderTraversal(node.right, keys);
        keys.add(node.key);
    }


    static class Node<Key> {
        private final Key key;
        private Node<Key> left, right;
        public Node(Key key) {
            this.key = key;
        }

        public Node(Key key, Node<Key> left, Node<Key> right) {
            this(key);
            this.left = left;
            this.right = right;
        }

        public String toString() {
            return key.toString();
        }
    }

    private void inOrderPrint() {
        inOrderPrint(root);
    }

    private void inOrderPrint(Node<Key> node) {
        if (node==null) return;
        inOrderPrint(node.left);
        System.out.print(node.key + " ");
        inOrderPrint(node.right);
    }

    public void preOrderPrint() {
        preOrderPrint(root);
    }

    private void preOrderPrint(Node<Key> node) {
        if (node==null) return;
        System.out.print(node.key + " ");
        preOrderPrint(node.left);
        preOrderPrint(node.right);
    }

    public void insert(Key key) {
        Queue<Node<Key>> queue = new ConcurrentLinkedQueue<>();
        Node<Key> newNode = new Node<Key>(key);
        if (root == null) {
            root = newNode;
        } else {
            queue.add(root);
            while (!queue.isEmpty()) {
                Node<Key> rmNode = queue.remove();
                if (rmNode.left != null || rmNode.right != null) {
                    if (rmNode.left != null) {
                        queue.add(rmNode.left);
                    } else {
                        rmNode.left = newNode;
                        return;
                    }
                    if (rmNode.right != null) {
                        queue.add(rmNode.right);
                    } else {
                        rmNode.right = newNode;
                        return;
                    }
                }
            }

            Node<Key> insertionNode = height(root.left) > height(root.right)? root.right : root.left;
            Node<Key> prevNode = root;
            while (prevNode.left != null)  {
                prevNode = insertionNode;
                insertionNode = insertionNode.left;
            }

            prevNode.left = newNode;
        }
    }

    public static void main(String[] args) {
        BinaryTree<String> tree = new BinaryTree<>();

        for (int i = 0; i < 7; i++) {
            tree.insert("A" + i);
        }

       Iterator<String> InOrderIterator = tree.iterator();
       while (InOrderIterator.hasNext()) {
           System.out.print(InOrderIterator.next() + " ");
       }
       System.out.println();
       Iterator<String> PreOrderIterator = tree.getPreOrderIterator();
       while (PreOrderIterator.hasNext()) {
           System.out.print(PreOrderIterator.next() + " ");
       }
       System.out.println();
       Iterator<String> PostOrderIterator = tree.getPostOrderIterator();
       while (PostOrderIterator.hasNext()) {
           System.out.print(PostOrderIterator.next() + " ");
       }
       System.out.println();
    }

    public Node<Key> find(Key key) {
        return find(key, root);
    }

    private Node<Key> find(Key key, Node<Key> node) {
        if (node == null) return null;
        if (node.key.equals(key)) return node;
        Node<Key> result = find(key, node.left);
        return result != null ? result : find(key, node.right);
    }

    private int height() {
        return height(root);
    }

    private int height(Node<Key> node) {
        if (node == null) return 0;
        return 1 + Math.max(height(node.left), height(node.right));
    }

    public int size() {
        return size(root);
    }

    private int size(Node<Key> node) {
        if (node == null) return 0;
        return size(node.left) + size(node.right) + 1;
    }

    public boolean isBalance() {
        return isBalance(root);
    }

    private boolean isBalance(Node<Key> node) {
        if (node == null) return true;
        if (Math.abs(height(node.left) - height(node.right)) <= 1) {
            return isBalance(node.left) && isBalance(node.right);
        }
        return false;
    }
}