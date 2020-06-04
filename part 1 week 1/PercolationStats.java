import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

import java.lang.Math;

public class PercolationStats {
    private final double[] x;
    private final int k;
    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0) {
            throw new IllegalArgumentException();
        }
        k = trials;
        x = new double[k];
        for (int i = 0; i < trials; i++) {
            Percolation p = new Percolation(n);
            while(!p.percolates()) {
                int r = StdRandom.uniform(n) + 1;
                int c = StdRandom.uniform(n) + 1;
                if (!p.isOpen(r, c)) {
                    p.open(r, c);
                }
            }
            double tmp = (double)(p.numberOfOpenSites()) / (double)(n*n);
            x[i] = tmp;
        }
    }
    public double mean() {
        return StdStats.mean(x);
    }
    public double stddev() {
        return StdStats.stddev(x);
    }
    public double confidenceLo() {
        return mean() - 1.96 * stddev() / Math.sqrt(k);
    }
    public double confidenceHi() {
        return mean() + 1.96 * stddev() / Math.sqrt(k);
    }
    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        int k = Integer.parseInt(args[1]);
        PercolationStats p = new PercolationStats(n, k);
        System.out.println("mean()                  = " + p.mean());
        System.out.println("stddev()                = " + p.stddev());
        System.out.println("95% confidence interval = [" + p.confidenceLo() + ", " + p.confidenceHi() + "]");
    }
}
