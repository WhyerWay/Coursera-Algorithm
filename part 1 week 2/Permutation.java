import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

//public class Permutation {
//    public static void main(String[] args) {
//        int k = Integer.parseInt(args[0]);
//        RandomizedQueue<String> r = new RandomizedQueue<>();
//        while(!StdIn.isEmpty()) {
//            String s = StdIn.readString();
//            r.enqueue(s);
//        }
//        for (int i = 0; i < k; i++) {
//            String s = r.dequeue();
//            StdOut.println(s);
//        }
//    }
//}

public class Permutation {
    public static void main(String[] args) {
        int k = Integer.parseInt(args[0]);
        if (k <= 0) {
            return;
        }
        RandomizedQueue<String> r = new RandomizedQueue<>();
        int ind = 0;
        while(!StdIn.isEmpty()) {
            ind++;
            if (ind <= k) {
                String s = StdIn.readString();
                r.enqueue(s);
            }
            else {
                int p = StdRandom.uniform(ind) + 1;
                String s = StdIn.readString();
                if (p <= k) {
                    r.dequeue();
                    r.enqueue(s);
                }
            }
        }
        for (String s : r) {
            StdOut.println(s);
        }
//        for (int i = 0; i < k; i++) {
//            String s = r.dequeue();
//            StdOut.println(s);
//        }
    }
}
