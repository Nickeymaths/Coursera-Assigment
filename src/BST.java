import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class BST<Key extends Comparable, Value> {
    private class Node<Key, Value> {
        Key key;
        Value val;
        Node<Key, Value> left;
        Node<Key, Value> right;
        int size;
        int ht;

        public Node(Key key, Value val, int size, int ht) {
            this.key = key;
            this.val = val;
            this.size = size;
        }

        public Node(Key key, Value val, Node<Key, Value> left, Node<Key, Value> right, int size, int ht) {
            this(key, val, size, ht);
            this.left = left;
            this.right = right;
        }
    }
    private Node<Key, Value> root;

    public BST() {}

    public BST(Node<Key, Value> root) {
        this.root = root;
    }

    public void insert(Key key, Value val) {
        root = insert(root, key, val);
    }

    public Node<Key, Value> insert(Node<Key, Value> x, Key key, Value val) {
        if (x == null) {
            return new Node<Key, Value>(key, val, 1, 1);
        }
        int cmp = key.compareTo(x.key);
        if (cmp < 0) {
            x.left = insert(x.left, key, val);
        } else if (cmp > 0) {
            x.right = insert(x.right, key, val);
        } else {
            x.val = val;
        }
        update(x);
        return x;
    }

    public Value get(Key key) {
        return get(root, key);
    }

    public Value get(Node<Key, Value> x, Key key) {
        if (x == null) return null;
        int cmp = key.compareTo(x.key);
        if (cmp < 0) {
            return get(x.left, key);
        } else if (cmp > 0) {
            return get(x.right, key);
        }
        return x.val;
    }

    public boolean contains(Key key) {
        return get(key) != null;
    }

    public Key floor(Key key) {
        return floor(root, key);
    }

    public Key floor(Node<Key, Value> x, Key key) {
        if (x == null) return null;
        int cmp = key.compareTo(x.key);
        if (cmp < 0) {
            return floor(x.left, key);
        } else if (cmp > 0) {
            Key tmp = floor(x.right, key);
            if (tmp != null) {
                return tmp;
            }
            return x.key;
        } else {
            return x.key;
        }
    }

    public Key ceil(Key key) {
        return ceil(root, key);
    }

    private Key ceil(Node<Key, Value> x, Key key) {
        if (x == null) return null;
        int cmp = key.compareTo(x.key);
        if (cmp > 0) {
            return ceil(x.right, key);
        } else if (cmp < 0) {
            Key tmp = ceil(x.left, key);
            if (tmp != null) {
                return tmp;
            }
            return x.key;
        } else {
            return x.key;
        }
    }

    public int rank(Key key) {
        return rank(root, key);
    }

    private int rank(Node<Key, Value> x, Key key) {
        if (x == null) return 0;
        int cmp = key.compareTo(x.key);
        if (cmp < 0) {
            return rank(x.left, key);
        } else if (cmp > 0) {
            return 1 + size(x.left) + rank(x.right, key);
        } else {
            return size(x.left);
        }
    }

    public Key select(int rk) {
        return select(root, rk);
    }

    private Key select(Node<Key, Value> x, int rk) {
        if (x == null) return null;
        if (size(x.left) > rk) return select(x.left, rk);
        else if (size(x.left) < rk) return select(x.right, rk - size(x.left) - 1);
        return x.key;
    }

    public void update(Node<Key, Value> x) {
        x.size = 1 + size(x.left) + size(x.right);
        x.ht = 1 + Math.max(height(x.left), height(x.right));
    }

    public void delete(Key key) {
        root = delete(root, key);
        update(root);
    }

    public Node<Key, Value> delete(Node<Key, Value> x, Key key) {
        if (x == null) return null;
        int cmp = key.compareTo(x.key);
        if (cmp < 0) {
            x.left = delete(x.left, key);
        } else if (cmp > 0) {
            x.right = delete(x.right, key);
        } else {
            if (x.left == null) return x.right;
            if (x.right == null) return x.left;

            Node<Key, Value> upNode = min(x.right);
            upNode.right = deleteMin(x.right);
            upNode.left = x.left;
            x = upNode;
        }
        update(x);
        return x;
    }

    public void deleteMin() {
        root = deleteMin(root);
    }

    public Node<Key, Value> deleteMin(Node<Key, Value> x) {
        if (x == null) return null;
        if (x.left == null) return x.right;
        x.left = deleteMin(x.left);
        update(x);
        return x;
    }

    public void printInOrder() {
        printInOrder(root);
    }

    private void printInOrder(Node<Key, Value> x) {
        if (x == null) return;
        printInOrder(x.left);
        System.out.print(x.val + " ");
        printInOrder(x.right);
    }

    public void printPreOrder() {
        printPreOrder(root);
    }

    private void printPreOrder(Node<Key, Value> x) {
        if (x == null) return;
        System.out.print(x.val + " ");
        printPreOrder(x.left);
        printPreOrder(x.right);
    }

    public void printPostOrder() {
        printPostOrder(root);
    }

    private void printPostOrder(Node<Key, Value> x) {
        if (x == null) return;
        printPostOrder(x.left);
        printPostOrder(x.right);
        System.out.print(x.val + " ");
    }

    public Node<Key, Value> min(Node<Key, Value> x) {
        if (x == null) return null;
        while (x.left != null) {
            x = x.left;
        }
        return x;
    }

    private int size(Node<Key, Value> x) {
        if (x == null) return 0;
        return x.size;
    }

    public int height(Node<Key, Value> x) {
        if (x == null) return 0;
        return 1 + Math.max(height(x.left), height(x.right));
    }

    public Node<Key, Value> getRoot() {
        return root;
    }
    
    public Iterator<Key> keysInOrder() {
        return new keyInOrder();
    }

    private class keyInOrder implements Iterator<Key> {
        private List<Key> keys = new ArrayList<>();
        private Iterator<Key> iterator;

        public keyInOrder() {
            getAllKeyInOrder(root, keys);
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

    private void getAllKeyInOrder(Node<Key, Value> x, List<Key> keys) {
        if (x == null) return;
        getAllKeyInOrder(x.left, keys);
        keys.add(x.key);
        getAllKeyInOrder(x.right, keys);
    }

    public Iterator<Key> keysPreOrder() {
        return new keyPreOrder();
    }

    private class keyPreOrder implements Iterator<Key> {
        private List<Key> keys = new ArrayList<>();
        private Iterator<Key> iterator;

        public keyPreOrder() {
            getAllKeyPreOrder(root, keys);
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

    private void getAllKeyPreOrder(Node<Key, Value> x, List<Key> keys) {
        if (x == null) return;
        keys.add(x.key);
        getAllKeyPreOrder(x.left, keys);
        getAllKeyPreOrder(x.right, keys);
    }

    public Iterator<Key> keysPostOrder() {
        return new keyPreOrder();
    }

    private class keyPostOrder implements Iterator<Key> {
        private List<Key> keys = new ArrayList<>();
        private Iterator<Key> iterator;

        public keyPostOrder() {
            getAllKeyPostOrder(root, keys);
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

    private void getAllKeyPostOrder(Node<Key, Value> x, List<Key> keys) {
        if (x == null) return;
        keys.add(x.key);
        getAllKeyPostOrder(x.left, keys);
        getAllKeyPostOrder(x.right, keys);
    }

    public Iterator<Value> valuesInOrder() {
        return new valueInOrder();
    }

    private class valueInOrder implements Iterator<Value> {
        private List<Value> values = new ArrayList<>();
        private Iterator<Value> iterator;

        public valueInOrder() {
            getAllValueInOrder(root, values);
            iterator = values.iterator();
        }

        @Override
        public boolean hasNext() {
            return iterator.hasNext();
        }

        @Override
        public Value next() {
            return iterator.next();
        }
    }

    private void getAllValueInOrder(Node<Key, Value> x, List<Value> values) {
        if (x == null) return;
        getAllValueInOrder(x.left, values);
        values.add(x.val);
        getAllValueInOrder(x.right, values);
    }

    public Iterator<Value> valuesPreOrder() {
        return new valueInOrder();
    }

    private class valuesPreOrder implements Iterator<Value> {
        private List<Value> values = new ArrayList<>();
        private Iterator<Value> iterator;

        public valuesPreOrder() {
            getAllValuePreOrder(root, values);
            iterator = values.iterator();
        }

        @Override
        public boolean hasNext() {
            return iterator.hasNext();
        }

        @Override
        public Value next() {
            return iterator.next();
        }
    }

    private void getAllValuePreOrder(Node<Key, Value> x, List<Value> values) {
        if (x == null) return;
        getAllValuePreOrder(x.left, values);
        values.add(x.val);
        getAllValuePreOrder(x.right, values);
    }

    public Iterator<Value> valuesPostOrder() {
        return new valueInOrder();
    }

    private class valuesPostOrder implements Iterator<Value> {
        private List<Value> values = new ArrayList<>();
        private Iterator<Value> iterator;

        public valuesPostOrder() {
            getAllValuePostOrder(root, values);
            iterator = values.iterator();
        }

        @Override
        public boolean hasNext() {
            return iterator.hasNext();
        }

        @Override
        public Value next() {
            return iterator.next();
        }
    }

    private void getAllValuePostOrder(Node<Key, Value> x, List<Value> values) {
        if (x == null) return;
        getAllValuePostOrder(x.left, values);
        values.add(x.val);
        getAllValuePostOrder(x.right, values);
    }
}
