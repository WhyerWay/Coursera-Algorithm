import java.util.ArrayList;
import java.util.Comparator;

public class FastCollinearPoints {
    private int count;
    private LineSegment[] ls;

    private void sort (Point[] points, Point[] aux, int lo, int mid, int hi, Comparator<Point> order) {
        if (lo == hi) return;
        if (order == null) {
            sort(points, aux, lo, lo+(mid-lo)/2, mid, null);
            sort(points, aux, mid+1, mid+1+(hi-mid-1)/2, hi, null);
            merge(points, aux, lo, mid, hi, null);
        }
        else {
            sort(points, aux, lo, lo+(mid-lo)/2, mid, order);
            sort(points, aux, mid+1, mid+1+(hi-mid-1)/2, hi, order);
            merge(points, aux, lo, mid, hi, order);
        }
    }
    private void merge (Point[] points, Point[] aux, int lo, int mid, int hi, Comparator<Point> order) {
        int i = lo;
        int j = mid + 1;
        if (order == null) {
            for (int k = lo; k <= hi; k++) {
                if (i > mid) aux[k] = points[j++];
                else if (j > hi) aux[k]=  points[i++];
                else if (points[j].compareTo(points[i]) < 0) aux[k] = points[j++];
                else if (points[j].compareTo(points[i]) == 0) throw new IllegalArgumentException();
                else aux[k] = points[i++];
            }
        }
        else {
            for (int k = lo; k <= hi; k++) {
                if (i > mid) aux[k] = points[j++];
                else if (j > hi) aux[k]=  points[i++];
                else if (order.compare(points[j], points[i]) < 0) aux[k] = points[j++];
                else aux[k] = points[i++];
            }
        }
        for (int k = lo; k <= hi; k++) {
            points[k] = aux[k];
        }
    }
    public FastCollinearPoints(Point[] points) {
        count = 0;
        if (points == null) throw new IllegalArgumentException();
        int n = points.length;
        Point[] aux = new Point[n];
        Point[] P = new Point[n];
        for (int i = 0; i < n; i++) {
            if (points[i] == null) throw new IllegalArgumentException();
            aux[i] = points[i];
            P[i] = points[i];
        }
        sort(P, aux, 0, (n-1)/2, n-1, null);
        ArrayList<LineSegment> ls_t = new ArrayList<LineSegment>();

        for (int i = 0; i < n; i++) { // choose base point
            Point begin = points[i];
            Point[] pt = new Point[n];
            for (int k = 0; k < n; k++) {
                pt[k] = P[k];
            }
            sort(pt, aux, 0, (n-1)/2, n-1, begin.slopeOrder()); // sort by slope
            double target = Double.NEGATIVE_INFINITY;
            int d = 0;
            Point start = pt[0];
            for (int k = 0; k < n; k++) {
                double ts = begin.slopeTo(pt[k]);
                if (ts == target) {
                    d++;
                    if (d >= 4 && start.compareTo(begin) > 0 && k == n-1) {
                        ls_t.add(new LineSegment(begin, pt[k]));
                    }
                }
                else if (d >= 4) {
                    if (start.compareTo(begin) < 0) {
                        d = 2;
                        start = pt[k];
                        target = ts;
                    }
                    else {
                        ls_t.add(new LineSegment(begin, pt[k-1]));
                        d = 2;
                        start = pt[k];
                        target = ts;
                    }
                }
                else {
                    d = 2;
                    start = pt[k];
                    target = ts;
                }
            }
        }
        count = ls_t.size();
        ls = new LineSegment[count];
        for (int i = 0; i < count; i++) {
            ls[i] = ls_t.get(i);
        }
    }
    public int numberOfSegments() {return count;}
    public LineSegment[] segments() {
        LineSegment[] res = new LineSegment[ls.length];
        for (int i = 0; i < ls.length; i++) {
            res[i] = ls[i];
        }
        return res;
    }

    public static void main(String[] args) {
    }
}
