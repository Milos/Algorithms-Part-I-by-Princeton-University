
/************************************************************************
 *
 * Examines 4 points at a time and checks whether they all lie on the same line
 * segment Returns all such segments
 *
 * Time Complexity: O(n^2 * log(n))
 * Space Complexity: n
 *
 **********************************************************************/
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;
import java.util.ArrayList;
import java.util.Arrays;
import static java.util.Collections.sort;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author milos
 */
public class FastCollinearPoints {

    private final List<LineSegment> lineSegments;

    // finds all line segments containing 4 or more points
    public FastCollinearPoints(Point[] points) {
        if (points == null) {
            throw new NullPointerException("Input points array is null");
        }

        lineSegments = new ArrayList<>();

        for (int i = 0; i < points.length - 3; i++) {
            Point origin = points[i];
            Point[] copy = Arrays.copyOf(points, points.length);
            Arrays.sort(copy, i + 1, copy.length, origin.slopeOrder()); // merge sort
            LinkedList<Point> collinearPoints = new LinkedList<>();            
            
            double previousSlope = 0.0; // line segment is horisontal (default)
            
            for (int j = i; j < copy.length; j++) {
                if (copy[j] == null) {
                    throw new NullPointerException("Point is null");
                }
                double slopeToCurrent = origin.slopeTo(copy[j]);
                if (previousSlope == slopeToCurrent) {
                    collinearPoints.add(copy[j]);
                } else { // check for new line segment
                    previousSlope = slopeToCurrent;
                    if (collinearPoints.size() > 2) {
                        collinearPoints.add(origin);
                        addSegment(collinearPoints); // create line segment
                        collinearPoints.clear();
                    } else { // start over 
                        collinearPoints.clear();
                        collinearPoints.add(copy[j]);
                    }
                }
            } // if last slopeToCurrent is same as previousSlope
            if (collinearPoints.size() > 2) {
                collinearPoints.add(origin);
                addSegment(collinearPoints);
            }
        }
    }
    
    private void addSegment(LinkedList<Point> collinearPoints) {
        sort(collinearPoints);                
        lineSegments.add(new LineSegment(collinearPoints.getFirst(), collinearPoints.getLast()));        
    }

    // the number of line segments
    public int numberOfSegments() {
        return lineSegments.size();
    }

    // the line segments
    public LineSegment[] segments() {
        return lineSegments.toArray(new LineSegment[]{}); // list -> array
    }

    public static void main(String[] args) {
        // read the n points from a file
        In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        // print and draw the line segments
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}
