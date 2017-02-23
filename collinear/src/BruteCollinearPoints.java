
/**
 * ********************************************************************
 *
 * Examines 4 points at a time and checks whether they all lie on the same line segment
 * Returns all such segments
 *
 * Time Complexity: O(n^4)
 *
 * Space Complexity: n
 *
 *********************************************************************
 */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author milos
 */
public class BruteCollinearPoints {

    private final List<LineSegment> lineSegments;

// finds all line segments containing 4 or more points
    public BruteCollinearPoints(Point[] points) {
        if (points == null) {
            throw new NullPointerException("Input points array is null");
        }

        lineSegments = new ArrayList<>();

        for (int i = 0; i < points.length; i++) {
            for (int j = i + 1; j < points.length; j++) {
                final double slopeToJ = calculateSlope(points, i, j);
                for (int k = j + 1; k < points.length; k++) {
                    final double slopeToK = calculateSlope(points, i, k);
                    if (slopeToJ == slopeToK) {
                        for (int l = k + 1; l < points.length; l++) {
                            final double slopeToL = calculateSlope(points, i, l);
                            if (slopeToJ == slopeToL) { // i,j,k,l are collinear
                                // sort the array and create LineSegment
                                Point[] collinearPoints = new Point[4];
                                collinearPoints[0] = points[i];
                                collinearPoints[1] = points[j];
                                collinearPoints[2] = points[k];
                                collinearPoints[3] = points[l];                                
                                
                                addSegment(collinearPoints);                                                                
                                break;
                            }
                        }
                    }
                }
            }
        }
    }

    private void addSegment(Point[] collinearPoints) {
        Arrays.sort(collinearPoints);                
        lineSegments.add(new LineSegment(collinearPoints[0], collinearPoints[3]));        
    }

    private double calculateSlope(Point[] points, int a, int b) {
        if (points[a] == null || points[b] == null) {
            throw new NullPointerException("Point is null");
        }

        final double slopeToB = points[a].slopeTo(points[b]);

        if (slopeToB == Double.NEGATIVE_INFINITY) {
            throw new IllegalArgumentException("duplicate points: " + points[b]);
        }
        return slopeToB;
    }

    // the number of line segments
    public int numberOfSegments() {
        return lineSegments.size();
    }

    public LineSegment[] segments() {
        return lineSegments.toArray(new LineSegment[]{});

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
        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}
