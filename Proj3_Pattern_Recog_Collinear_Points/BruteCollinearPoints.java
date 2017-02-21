package algorithms_practice;
import java.util.ArrayList;
import java.util.Arrays;

import edu.princeton.cs.algs4.*;

public class BruteCollinearPoints {
	
	private int segNum;
	private ArrayList<LineSegment> segment = new ArrayList<LineSegment>();
	private ArrayList<Integer> tailList = new ArrayList<Integer>();
	private ArrayList<Double> slopeList = new ArrayList<Double>();
	
	
	public BruteCollinearPoints(Point[] points)  { // finds all line segments containing 4 points
		if (points == null) throw new java.lang.NullPointerException();
		validateDup(points);
		segNum = 0;
		
		Point[] copy = new Point[points.length];
		for (int i = 0; i < points.length; i++) {
			validate(points[i]);
			copy[i] = points[i];
		}
		
		Arrays.sort(copy);
		
		
		for (int i = 0; i < copy.length; i++) { // i
			
			double tmpSlope = 0;
			int temp = 0;
			
			for (int j = i+1; j < copy.length; j++) { // j
				double slope1 = copy[i].slopeTo(copy[j]);  
				
				for (int k = j+1; k < copy.length; k++) {	// k			
					double slope2 = copy[j].slopeTo(copy[k]);
					
					if (slope1 != slope2) continue;
				
					int z = k + 1;
					for (; z < copy.length; z++) { // z
						double slope3 = copy[k].slopeTo(copy[z]);
						if (slope2 == slope3) {
							temp = z;
							tmpSlope = slope3;
						}
						
						
					}
					
					//System.out.println(temp);
					
					//if (z == copy.length && temp != 0 && k == copy.length-2 && j == copy.length-3)
					if (z == copy.length && temp != 0) {
						int flag = 0;
						if (segment.isEmpty() == false) {
							for (int q = 0; q < tailList.size(); q++) {
								if (tailList.get(q) == temp) {
									for (int w = 0; w < slopeList.size(); w++) {
										if (slopeList.get(w) == tmpSlope) {
											flag = 1;
										}
									}
								}
							}
						}
						
						if (flag == 0) {
							segNum++;
							tailList.add(temp);
							slopeList.add(tmpSlope);
							segment.add(new LineSegment(copy[i], copy[temp]));
						}
					}				
				}
			}
		}
	}
	
	public int numberOfSegments()  { // the number of line segments
		return segNum;
	}
	public LineSegment[] segments()  { // the line segments
		LineSegment[] seg = new LineSegment[segNum];
		for(int i = 0; i < segNum; i++) {
			seg[i] = segment.get(i);
		}
		return seg;
	}
	
	private void validate(Point p) {
		if (p == null) throw new java.lang.NullPointerException();
	}
	
	private void validateDup(Point[] points) {
		for (int i = 0; i < points.length; i++) {
			for (int j = i+1; j < points.length; j++) {
				if (points[i].equals(points[j])) throw new java.lang.IllegalArgumentException();
			}
		}
	}

	public static void main(String[] args) {
	
		// read the N points from a file
        // In in = new In(args[0]);
        In in = new In("/Users/ZhangHaotian/Desktop/princeton_algorithm/hw3/collinear/input6.txt");
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
        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
		
	}
	
	
}
