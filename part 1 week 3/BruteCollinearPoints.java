public class BruteCollinearPoints {
    private int count;
    private LineSegment[] ls;

    private void sort (Point[] points, Point[] aux, int lo, int mid, int hi) {
        if (lo == hi) return;
        sort(points, aux, lo, lo+(mid-lo)/2, mid);
        sort(points, aux, mid+1, mid+1+(hi-mid-1)/2, hi);
        merge(points, aux, lo, mid, hi);
    }
    private void merge (Point[] points, Point[] aux, int lo, int mid, int hi) {
        int i = lo;
        int j = mid + 1;
        for (int k = lo; k <= hi; k++) {
            if (i > mid) aux[k] = points[j++];
            else if (j > hi) aux[k]=  points[i++];
            else if (points[j].compareTo(points[i]) < 0) aux[k] = points[j++];
            else if (points[j].compareTo(points[i]) == 0) throw new IllegalArgumentException();
            else aux[k] = points[i++];
        }
        for (int k = lo; k <= hi; k++) {
            points[k] = aux[k];
        }
    }
    public BruteCollinearPoints(Point[] points) {
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
        sort(P, aux, 0, (n-1)/2, n-1);
        LineSegment[] ls_t = new LineSegment[n];

        for (int i = 0; i < n-3; i++) {
            for (int j = i+1; j < n-2; j++) {
                for (int k = j+1; k < n-1; k++) {
                    if (P[i].slopeTo(P[j]) != P[i].slopeTo(P[k])) continue;
                    for (int l = k+1; l < n; l++) {
                        if (P[i].slopeTo(P[j]) != P[i].slopeTo(P[l])) continue;
                        else {
                            ls_t[count++] = new LineSegment(P[i], P[l]);
                        }
                    }
                }
            }
        }
        ls = new LineSegment[count];
        for (int i = 0; i < count; i++) {
            ls[i] = ls_t[i];
        }
    }
    public int numberOfSegments() {
        return count;
    }
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