/**
 * id[k] is parent of k th element.
 * size[k] is size of component that it's root is k.
 */
public class UnionFind {
    int[] id;
    int[] size;

    public UnionFind() {}

    /**
     * This function create a sequence 0, 1, 2,..., N-1.
     * @param N number of element of sequence
     */
    public UnionFind(int N) {
        id = new int[N];
        size = new int[N];
        for (int i = 0; i < N; i++) id[i] = i;
        for (int i = 0; i < N; i++) size[i] = 1;
    }

    /**
     * Complexity: log*N
     * @param k: element of sequence.
     * @return root of element k.
     */
    public int find(int k) {
        while (id[k] != k) {
            id[k] = id[id[k]];
            k = id[k];
        }
        return k;
    }

    /**
     * Use weight point method.
     * This function is used to union two components containing p, q respectively.
     * @param p element of sequence.
     * @param q element of sequence.
     */
    public void union(int p, int q) {
        int root_p = find(p);
        int root_q = find(q);

        if (size[root_p] > size[root_q]) {
            id[root_q] = root_p;
            size[root_p] += size[root_q];
        } else {
            id[root_p] = root_q;
            size[root_q] += size[root_p];
        }
    }

    /**
     *
     * @param p: element of sequence.
     * @param q element of sequence.
     * @return return true if both p, q were contained same component.
     */
    public boolean connected(int p, int q) {
        return find(p) == find(q);
    }
}
