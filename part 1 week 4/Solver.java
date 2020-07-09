import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.MinPQ;

public class Solver {
    private int moves;
    private final Stack<Board> path;
    private boolean solvable;

    private class State implements Comparable<State> {
        public Board board;
//        public int hamming;
        public int manhattan;
        public int step;
        public int priority;
        public State prev;

        public State(Board b, int s, State st) {
            board = b;
//            hamming = b.hamming();
            manhattan = b.manhattan();
            step = s;
            priority = manhattan +step;
            prev = st;
        }

        @Override
        public int compareTo(State that) {
            if (this.priority < that.priority)  return -1;
            if (this.priority > that.priority)  return +1;
//            if (this.hamming < that.hamming) return -1;
//            if (this.hamming > that.hamming) return +1;
            return 0;
        }
    }

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        if (initial == null) throw new IllegalArgumentException();
        MinPQ<State> myPQ = new MinPQ<>();
        MinPQ<State> twinPQ = new MinPQ<>();
        State self = new State(initial,0,null);
        State twin = new State(initial.twin(),0,null);
        myPQ.insert(self);
        twinPQ.insert(twin);
        moves = -1;
        path = new Stack<>();
        solvable = false;
        while (!myPQ.isEmpty()) {
            self = myPQ.delMin();
            if (self.board.isGoal()) {
                solvable = true;
                break;
            }
            twin = twinPQ.delMin();
            if (twin.board.isGoal()) {
                solvable = false;
                break;
            }
            Iterable<Board> nb = self.board.neighbors();
            Iterable<Board> twinnb = twin.board.neighbors();
            for (Board b : nb) {
                State s = new State(b,self.step+1,self);
                if (!(self.prev!= null && s.board.equals(self.prev.board))) {
                    myPQ.insert(s);
                }
            }
            for (Board b : twinnb) {
                State s = new State(b,twin.step+1,twin);
                if (!(twin.prev!= null && s.board.equals(twin.prev.board))) {
                    twinPQ.insert(s);
                }
            }
        }
        if (solvable) {
            while (self != null) {
                path.push(self.board);
                moves++;
                self = self.prev;
            }
        }
    }

    // is the initial board solvable? (see below)
    public boolean isSolvable() {
        return solvable;
    }

    // min number of moves to solve initial board; -1 if unsolvable
    public int moves() {
        if (!isSolvable()) return -1;
        return moves;
    }

    // sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution() {
        if (!isSolvable()) return null;
        return path;
    }

    // test client (see below)
    public static void main(String[] args) {
    }
}
