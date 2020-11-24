
public class STVersion2<Key extends Comparable<Key>, Value> {
    private Key[] keys;
    private Value[] values;
    private int N;

    public STVersion2() {
        this(1);
    }

    public STVersion2(int capacity) {
        keys = (Key[]) new Comparable[capacity];
        values = (Value[]) new Object[capacity];
    }

    public void put(Key key, Value value) {
        if (get(key) != null) {
            values[rank(key)] = value;
        } else {
            int i = 0;
            while (i < N && keys[i].compareTo(key) < 0) ++i;
            if (N == keys.length) resize(2 * N);
            for (int j = N - 1; j >= i; j--) {
                keys[j+1] = keys[j];
                values[j+1] = values[j];
            }
            keys[i] = key;
            values[i] = value;
            ++N;
        }
    }

    public Value get(Key key) {
        int rk = rank(key);
        if (rk < N && rk != -1) return values[rk];
        return null;
    }

    public Key min(){
        if (isEmpty()) return null;
        return keys[0];
    }

    public Key max() {
        if (isEmpty()) return null;
        return keys[N-1];
    }

    public Key floor(Key key) {
        if (min().compareTo(key) > 0) return null;
        int lo = 0, hi = N - 1;
        while (lo <= hi) {
            int mid = lo + (hi - lo)/2;
            if (keys[mid].compareTo(key) <= 0 && keys[mid+1].compareTo(key) > 0) return keys[mid];
            else if (keys[mid].compareTo(key) < 0) lo = mid + 1;
            else if (keys[mid].compareTo(key) > 0) hi = mid - 1;
        }
        return null;
    }

    public Key ceil(Key key) {
        if (max().compareTo(key) < 0) return null;
        int lo = 0, hi = N - 1;
        while (lo <= hi) {
            int mid = lo + (hi - lo)/2;
            if (keys[mid-1].compareTo(key) < 0 && keys[mid].compareTo(key) >= 0) return keys[mid];
            else if (keys[mid].compareTo(key) < 0) lo = mid + 1;
            else if (keys[mid].compareTo(key) > 0) hi = mid - 1;
        }
        return null;
    }

    /**
     * @param rank: number of element less or equals than Key
     * @return: The key is satisfied.
     */
    public Key select(int rank) {
        if (isEmpty()) return null;
        return keys[rank];
    }

    public int rank(Key key) {
        int lo = 0, hi = N-1;
        while (lo <= hi) {
            int mid = lo + (hi - lo)/2;
            int cmp = keys[mid].compareTo(key);
            if (cmp < 0) lo = mid + 1;
            else if (cmp > 0) hi = mid - 1;
            else return mid;
        }
        return -1;
    }

    public boolean isEmpty() {
        return N == 0;
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

    public void printAll() {
        for (int i = 0; i < N; i++) {
            System.out.println(keys[i].toString() + ":" + values[i].toString());
        }
    }
}
