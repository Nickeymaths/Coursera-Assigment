/**
 * leaning left red-black tree.
 * @param <Key>
 * @param <Val>
 */
public class RBTree<Key extends Comparable, Val> {
    private enum COLOR {
        BLACK,
        RED
    }
    private class Node{
        Node left;
        Node right;
        Key key;
        Val val;
        int ht;
        int size;
        COLOR color;
        // color = 1 color is red.
        // color = 0 color is black.

        public Node(Key key, Val val, int ht, int size, COLOR color) {
            this.key = key;
            this.val = val;
            this.ht = ht;
            this.size = size;
            this.color = color;
        }

        public Node(Key key, Val val, int ht, int size, COLOR color
                , Node left, Node right) {
            this(key, val, ht, size, color);
            this.left = left;
            this.right = right;
        }
    }

    Node root;

    public RBTree() {}

    public RBTree(Node root) {
        this.root = root;
    }

    public void put(Key key, Val val) {
        root = put(root, key, val);
    }

    public void delete(Key key) {
        root = delete(root, key);
    }

    private Node delete(Node x, Key key) {
        if (x == null) return null;
        int cmp = key.compareTo(x.key);
        if (cmp < 0) {
            x.left = delete(x.left, key);
        } else if (cmp > 0) {
            x.right = delete(x.right, key);
        } else {
            if (x.left == null) return x.right;
            if (x.right == null) return  x.left;
            Node upNode = min(x.right);
            upNode.right = deleteMin(x.right);
            upNode.left = x.left;
            upNode.color = x.color;
            x = upNode;
        }
        update(x);
        return x;
    }

    private Node put(Node x, Key key, Val val) {
        if (x == null) {
            return new Node(key, val, 1, 1, COLOR.RED, null, null);
        }
        int cmp = key.compareTo(x.key);
        if (cmp < 0) {
            x.left = put(x.left, key, val);
        } else if (cmp > 0) {
            x.right = put(x.right, key, val);
        } else {
            x.key = key;
            x.val = val;
        }

        if (isRed(x.left) && !isRed(x.right)) x = rotateRight(x);
        if (isRed(x.right) && isRed(x.right.right)) x = rotateLeft(x);
        if (isRed(x.left) && isRed(x.right)) x = flipColor(x);
        return x;
    }

    private Node rotateRight(Node x) {
        Node upNode = x.left;
        x.left = upNode.right;
        upNode.right = x;
        upNode.color = x.color;
        x.color = COLOR.RED;
        update(x);
        update(upNode);
        return upNode;
    }

    private Node rotateLeft(Node x) {
        Node upNode = x.right;
        x.right = upNode.left;
        upNode.left = x;
        upNode.color = x.color;
        x.color = COLOR.RED;
        update(x);
        update(upNode);
        return upNode;
    }

    private Node flipColor(Node x) {
        x.left.color = COLOR.BLACK;
        x.right.color = COLOR.BLACK;
        x.color = COLOR.RED;
        return x;
    }

    private void update(Node x) {
        if (x == null) return;
        x.size = 1 + size(x.left) + size(x.right);
        x.ht = 1 + Math.max(height(x.left), height(x.right));
    }

    private int height(Node x) {
        if (x == null) return 0;
        return x.ht;
    }

    private int size(Node x) {
        if (x == null) return 0;
        return x.size;
    }

    private boolean isRed(Node x) {
        if (x == null) return false;
        return x.color == COLOR.RED;
    }

    private Node min(Node x) {
        if (x == null) return null;
        while (x.left != null) x = x.left;
        return x;
    }

    public Node deleteMin(Node x) {
        if (x == null) return null;
        else if (x.left == null) {
            if (x.right != null) x.right.color = x.color;
            return x.right;
        }
        else x.left = deleteMin(x.left);
        update(x);
        return x;
    }
}
