public class ST<Key extends Comparable<Key>, Value> {
    private int N;
    private Key[] keys;
    private Value[] values;

    public ST() {
        this(1);
    }

    public ST(int capacity) {
        keys = (Key[])new Comparable[capacity];
        values = (Value[]) new Object[capacity];
    }

    public void put(Key key, Value value) {
        if (get(key) != null) {
            int position = rank(key);
            values[position] = value;
        } else {
            if (N == keys.length) resize(2 * N);
            int i = 0;
            while (i < N && keys[i].compareTo(key) < 0) ++i;
            for (int j = N - 1; j >= i; j--) {
                keys[j+1] = keys[j];
                values[j+1] = values[j];
            }
            keys[i] = key;
            values[i] = value;
            ++N;
        }
    }

    private void resize(int capacity) {
        Key[] aux_Key = (Key[]) new Comparable[capacity];
        Value[] aux_val = (Value[]) new Object[capacity];
        for (int i = 0; i < N; i++) {
            aux_Key[i] = keys[i];
            aux_val[i] = values[i];
        }
        keys = aux_Key;
        values = aux_val;
    }

    public Value get(Key key) {
        if (isEmpty()) return null;
        int rk = rank(key);
        if (rk < N && keys[rk].equals(key)) return values[rk];
        return null;
    }

    private int rank(Key key) {
        int lo = 0, hi = N - 1;
        while (lo <= hi) {
            int mid = lo + (hi - lo)/2;
            int cmp = keys[mid].compareTo(key);
            if (cmp < 0) lo = mid + 1;
            else if (cmp > 0) hi = mid - 1;
            else return mid;
        }
        return lo;
    }

    public void printAll() {
        for (int i = 0; i < N; i++) {
            System.out.println(keys[i].toString() + ":" + values[i].toString());
        }
    }

    public boolean contain(Key key) {
        return get(key) != null;
    }

    public void deleteKey(Key key) {
    }

    public Iterable<Key> keys() {
        return null;
    }

    public boolean isEmpty() {
        return N == 0;
    }

    public Key select(int k) {
        return keys[k];
    }
}
