import java.util.Comparator;
import edu.princeton.cs.algs4.StdDraw;

public class Point implements Comparable<Point> {

    private final int x;     // x-coordinate of this point
    private final int y;     // y-coordinate of this point

    public Point(int x, int y) {
//        if (x == null || y == null) {
//            throw new IllegalArgumentException();
//        }
        /* DO NOT MODIFY */
        this.x = x;
        this.y = y;
    }
    public void draw() {
        /* DO NOT MODIFY */
        StdDraw.point(x, y);
    }
    public void drawTo(Point that) {
        /* DO NOT MODIFY */
        StdDraw.line(this.x, this.y, that.x, that.y);
    }

    /**
     * Returns the slope between this point and the specified point.
     * Formally, if the two points are (x0, y0) and (x1, y1), then the slope
     * is (y1 - y0) / (x1 - x0). For completeness, the slope is defined to be
     * +0.0 if the line segment connecting the two points is horizontal;
     * Double.POSITIVE_INFINITY if the line segment is vertical;
     * and Double.NEGATIVE_INFINITY if (x0, y0) and (x1, y1) are equal.
     *
     * @param  that the other point
     * @return the slope between this point and the specified point
     */
    public double slopeTo(Point that) {
        /* YOUR CODE HERE */
        if (compareTo(that) == 0) return Double.NEGATIVE_INFINITY;
        if (this.x == that.x) return Double.POSITIVE_INFINITY;
        if (this.y == that.y) return +0.0;
        return (double)(that.y - this.y) / (that.x - this.x);
    }

    /**
     * Compares two points by y-coordinate, breaking ties by x-coordinate.
     * Formally, the invoking point (x0, y0) is less than the argument point
     * (x1, y1) if and only if either y0 < y1 or if y0 = y1 and x0 < x1.
     *
     * @param  that the other point
     * @return the value <tt>0</tt> if this point is equal to the argument
     *         point (x0 = x1 and y0 = y1);
     *         a negative integer if this point is less than the argument
     *         point; and a positive integer if this point is greater than the
     *         argument point
     */
    public int compareTo(Point that) {
        /* YOUR CODE HERE */
        if (this.y < that.y) return -1;
        else if (this.y == that.y) {
            if (this.x < that.x) return -1;
            else if (this.x == that.x) return 0;
            else return 1;
        }
        return 1;
    }

    private class SlopeOrder implements Comparator<Point> {
        private Point a;
        SlopeOrder(Point a) {
            this.a = a;
        }
        public int compare(Point b, Point c) {
            if (a.slopeTo(b) < a.slopeTo(c)) return -1;
            else if (a.slopeTo(b) == a.slopeTo(c)) return 0;
            else return 1;
        }
    }
    public Comparator<Point> slopeOrder() {
        return new SlopeOrder(this);
    }
    public String toString() {
        /* DO NOT MODIFY */
        return "(" + x + ", " + y + ")";
    }

//    public static int test (Point b, Point c, Comparator<Point> order) {
//        return order.compare(b,c);
//    }
    public static void main(String[] args) {
        /* YOUR CODE HERE */
//        Point p1 = new Point(1,1);
//        Point p2 = new Point(2,2);
//        Point p3 = new Point(3,2);
//        System.out.println(test(p2,p3,p1.slopeOrder()));
    }
}
