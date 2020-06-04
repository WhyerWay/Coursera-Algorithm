import java.lang.Integer;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private final int n, top;
    private int count;
    private boolean[] op, bot;
    private boolean ok;
    private WeightedQuickUnionUF uf;

    private int rcToIndex(int row, int col) {
        return (row - 1) * n + col - 1;
    }
    public Percolation(int n) {
        if (n < 1){
            throw new IllegalArgumentException();
        }
        this.n = n;
        top = n * n;
        count = 0;
        op = new boolean[n * n + 1];
        op[n*n] = true;
        bot = new boolean[n * n + 1];
        for (int i = n*(n-1); i < n*n; i++) {
            bot[i] = true;
        }
        ok = false;
        uf = new WeightedQuickUnionUF(n * n + 1);
    }
    public void open(int row, int col) {
        if (row>n || row<1 || col>n || col<1){
            throw new IllegalArgumentException();
        }
        if (isOpen(row, col)) {
            return;
        }
        int i = rcToIndex(row, col);
        op[i] = true;
        count++;
        if (row == 1) {
            uf.union(i, top);
        }
        int[] nb = new int[] {Integer.max(1, row - 1), col,
                              Integer.min(n, row + 1), col,
                              row, Integer.max(1, col - 1),
                              row, Integer.min(n, col + 1)};
        for (int j = 1; j <= 4; j++) {
            if (isOpen(nb[j*2-2], nb[j*2-1])) {
                int nb_t = rcToIndex(nb[j*2-2], nb[j*2-1]);
                if (bot[nb_t] || bot[uf.find(nb_t)] || bot[uf.find(i)])
                    bot[i] = true;
                uf.union(i, nb_t);
            }
        }
        if (bot[i]) {
            bot[uf.find(i)] = true;
        }
        if (!ok && bot[uf.find(i)] && isFull(row, col)) {
            ok = true;
        }
    }
    public boolean isOpen(int row, int col) {
        if (row>n || row<1 || col>n || col<1){
            throw new IllegalArgumentException();
        }
        int i = rcToIndex(row, col);
        return op[i];
    }
    public boolean isFull(int row, int col) {
        if (row>n || row<1 || col>n || col<1){
            throw new IllegalArgumentException();
        }
        int i = rcToIndex(row, col);
        return uf.find(i) == uf.find(top);
    }
    public int numberOfOpenSites() {
        return count;
    }
    public boolean percolates() {
        return ok;
    }
    public static void main(String[] args) {
    }
}
