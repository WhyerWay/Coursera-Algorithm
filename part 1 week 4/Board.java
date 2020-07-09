import edu.princeton.cs.algs4.StdOut;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

public class Board {
    private final int n;
    private int[] arr;
    private int hamming;
    private int manhattan;

    // create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)
    public Board(int[][] tiles) {
        this.n = tiles.length;
        this.arr = new int[n*n];
        hamming = 0;
        manhattan = 0;
        int ind = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                arr[ind++] = tiles[i][j];
                if (arr[ind-1] != 0) {
                    int[] ij = new int[2];
                    to2D(ij,arr[ind-1]-1);
                    manhattan += Math.abs(ij[0] - i) + Math.abs(ij[1] - j);
                    if (arr[ind-1] != ind) hamming++;
                }
            }
        }
    }

    // string representation of this board
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(n + "\n");
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                s.append(String.format("%2d ", arr[to1D(i,j)]));
            }
            s.append("\n");
        }
        return s.toString();
    }

    // board dimension n
    public int dimension() {
        return n;
    }

    // number of tiles out of place
    public int hamming() { return hamming; }

    // sum of Manhattan distances between tiles and goal
    public int manhattan() {
        return manhattan;
    }

    // is this board the goal board?
    public boolean isGoal() {
        return this.hamming() == 0;
    }

    // does this board equal y?
    public boolean equals(Object y) {
        if (this == y) return true;
        if (y == null) return false;
        if (getClass() != y.getClass()) return false;
        Board yb = (Board)y;
        if (n != yb.n) return false;
        return Arrays.equals(arr, yb.arr);
    }

    // all neighboring boards
    public Iterable<Board> neighbors() {
        Queue<Board> res = new LinkedList<>();
        int ind = 0;
        while (arr[ind] != 0) {
            ind++;
        }
        if (ind % n > 0) { //left
            Board b = new Board(new int[n][n]);
            b.arr = arr.clone();
            b.arr[ind-1] = 0;
            b.arr[ind] = arr[ind-1];
            if (b.arr[ind] == ind + 1) b.hamming = hamming - 1;
            else if (arr[ind-1] == ind) b.hamming = hamming + 1;
            else b.hamming = hamming;
            b.manhattan = ((ind-1)%n < (arr[ind-1]-1)%n) ? manhattan - 1 : manhattan + 1;
            res.add(b);
        }
        if (ind % n < n-1) { //right
            Board b = new Board(new int[n][n]);
            b.arr = arr.clone();
            b.arr[ind+1] = 0;
            b.arr[ind] = arr[ind+1];
            if (b.arr[ind] == ind + 1) b.hamming = hamming - 1;
            else if (arr[ind+1] == ind+2) b.hamming = hamming + 1;
            else b.hamming = hamming;
            b.manhattan = ((ind+1)%n > (arr[ind+1]-1)%n) ? manhattan - 1 : manhattan + 1;
            res.add(b);
        }
        if (ind - n >= 0) { //up
            Board b = new Board(new int[n][n]);
            b.arr = arr.clone();
            b.arr[ind-n] = 0;
            b.arr[ind] = arr[ind-n];
            if (b.arr[ind] == ind + 1) b.hamming = hamming - 1;
            else if (arr[ind-n] == ind-n+1) b.hamming = hamming + 1;
            else b.hamming = hamming;
            b.manhattan = ((ind-n)/n < (arr[ind-n]-1)/n) ? manhattan - 1 : manhattan + 1;
            res.add(b);
        }
        if (ind + n < n*n) { //down
            Board b = new Board(new int[n][n]);
            b.arr = arr.clone();
            b.arr[ind+n] = 0;
            b.arr[ind] = arr[ind+n];
            if (b.arr[ind] == ind + 1) b.hamming = hamming - 1;
            else if (arr[ind+n] == ind+n+1) b.hamming = hamming + 1;
            else b.hamming = hamming;
            b.manhattan = ((ind+n)/n > (arr[ind+n]-1)/n) ? manhattan - 1 : manhattan + 1;
            res.add(b);
        }
        return res;
    }

    // a board that is obtained by exchanging any pair of tiles
    public Board twin() {
        int[] swap = {0,0};
        while (arr[swap[0]] == 0) {
            swap[0]++;
        }
        swap[1] = swap[0] + 1;
        while (arr[swap[1]] == 0) {
            swap[1]++;
        }
        int ind = 0;
        int[][] tmp = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (ind == swap[0]) tmp[i][j] = arr[swap[1]];
                else if (ind == swap[1]) tmp[i][j] = arr[swap[0]];
                else tmp[i][j] = arr[ind];
                ind++;
            }
        }
        Board b = new Board(tmp);
        return b;
    }

    // unit testing (not graded)
    public static void main(String[] args) {
    }

    private int to1D(int i, int j) {
        return i * n + j;
    }

    private void to2D(int[] ij, int ind) {
        ij[0] = ind / n;
        ij[1] = ind % n;
    }

}
