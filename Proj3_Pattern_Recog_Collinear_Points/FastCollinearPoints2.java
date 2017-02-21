package algorithms_practice;
import java.util.*;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

/**
 * The method segments() should include each maximal line segment containing 4 (or more) points exactly once.
 * For example, if 5 points appear on a line segment in the order p→q→r→s→t,
 * then do not include the subsegments p→s or q→t.
 * <p>
 * Corner cases.
 * Throw a java.lang.NullPointerException either the argument to the constructor is null or if any point in the array is null.
 * Throw a java.lang.IllegalArgumentException if the argument to the constructor contains a repeated point.
 * <p>
 * Performance requirement.
 * The order of growth of the running time of your program should be N^2logN in the worst case and it should use space
 * proportional to N plus the number of line segments returned.
 * FastCollinearPoints should work properly even if the input has 5 or more collinear points.
 */
public class FastCollinearPoints2 {
    private Map<Double, List<Point>> foundSegments = new HashMap<>();
    private List<LineSegment> segments = new ArrayList<>();


    public FastCollinearPoints2(Point[] points) {
        Point[] pointsCopy = Arrays.copyOf(points, points.length);
        checkDuplicatedEntries(pointsCopy);

        for (Point startPoint : points) {
            Arrays.sort(pointsCopy, startPoint.slopeOrder());

            List<Point> slopePoints = new ArrayList<>();
            double slope = 0;
            double previousSlope = Double.NEGATIVE_INFINITY;

            for (int i = 1; i < pointsCopy.length; i++) {
                Point comparePoint = pointsCopy[i];
                slope = startPoint.slopeTo(comparePoint);
                if (slope == previousSlope) {
                    slopePoints.add(comparePoint);
                } else {
                    if (slopePoints.size() >= 3) {
                        slopePoints.add(startPoint);
                        addSegmentIfNew(slopePoints, previousSlope);
                    }
                    slopePoints.clear();
                    slopePoints.add(comparePoint);
                }
                previousSlope = slope;
            }

            if (slopePoints.size() >= 3) {
                slopePoints.add(startPoint);
                addSegmentIfNew(slopePoints, slope);
            }
        }
    }

    private void addSegmentIfNew(List<Point> slopePoints, double slope) {
        List<Point> endPoints = foundSegments.get(slope);
        Collections.sort(slopePoints);

        Point startPoint = slopePoints.get(0);
        Point endPoint = slopePoints.get(slopePoints.size() - 1);

        if (endPoints == null) {
            endPoints = new ArrayList<>();
            endPoints.add(endPoint);
            foundSegments.put(slope, endPoints);
            segments.add(new LineSegment(startPoint, endPoint));
        } else {
            for (Point currentEndPoint : endPoints) {
                if (currentEndPoint.compareTo(endPoint) == 0) {
                    return;
                }
            }
            endPoints.add(endPoint);
            segments.add(new LineSegment(startPoint, endPoint));
        }
    }

    private void checkDuplicatedEntries(Point[] points) {
        Arrays.sort(points);
        for (int i = 0; i < points.length - 1; i++) {
            if (points[i].compareTo(points[i + 1]) == 0) {
                throw new IllegalArgumentException("Duplicated entries in given points");
            }

        }
    }


    public int numberOfSegments() {
        return segments.size();
    }

    public LineSegment[] segments() {
        return segments.toArray(new LineSegment[segments.size()]);
    }
    
public static void main(String[] args) {
		
		// read the N points from a file
        // In in = new In(args[0]);
        In in = new In("/Users/ZhangHaotian/Desktop/princeton_algorithm/hw3/collinear/input400.txt");
        int N = in.readInt();
        System.out.println(N);
        Point[] points = new Point[N];
        for (int i = 0; i < N; i++) {
            int x = in.readInt();
            int y = in.readInt();
         System.out.println("x:" + x + " y:" + y);
            points[i] = new Point(x, y);
       } //print all the points

        // draw the points
        //StdDraw.show(0);
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

       // print and draw the line segments
        FastCollinearPoints2 collinear = new FastCollinearPoints2(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
		
	}
    
    
    
    
    
}