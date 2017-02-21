package algorithms_practice;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

public class FastCollinearPoints {
	
	private int segNum;
	private ArrayList<LineSegment> segment = new ArrayList<LineSegment>();
	private ArrayList<Point> headList = new ArrayList<Point>();
	private ArrayList<Point> tailList = new ArrayList<Point>();
	private ArrayList<Double> slopeList = new ArrayList<Double>();
	
	public FastCollinearPoints(Point[] points)  { // finds all line segments containing 4 or more points
		if (points == null) throw new java.lang.NullPointerException();
		validateDup(points);
		segNum = 0;
		
		// copy all the points to copy[]
		Point[] copy = new Point[points.length];
		for (int i = 0; i < points.length; i++) {
			validate(points[i]);
			copy[i] = points[i];
		}
		
		Arrays.sort(copy);// newly added
		
		for (int i = 0; i < copy.length; i++) { // reference point
			Point[] otherPoints = new Point[points.length-1-i];
		
			for (int x = i + 1; x < copy.length; x++) {
				otherPoints[x-i-1] = copy[x];
			}
			
			Arrays.sort(otherPoints, copy[i].slopeOrder()); // sort points according to their slope to i
			
			// pick out points that share equal slopes
			for (int j = 0; j < otherPoints.length; j++) {
				
				ArrayList<Point> tmpSameSlopeArr = new ArrayList<Point>();
				tmpSameSlopeArr.add(otherPoints[j]);
				
				double tmpRefSlope = copy[i].slopeTo(otherPoints[j]); //current reference slope value
				
				for (int k = j+1; k < otherPoints.length; k++) {
					if (otherPoints[j].slopeTo(otherPoints[k]) == tmpRefSlope) {
						tmpSameSlopeArr.add(otherPoints[k]);
					}
				}// obtained a line of points with same slope
				
				if (tmpSameSlopeArr.size() >= 3) {
				
					Collections.sort(tmpSameSlopeArr);
					
					// find tail and head
					if (copy[i].compareTo(tmpSameSlopeArr.get(0)) < 0) {
						boolean flag = true;
						Point hd = copy[i];
						Point tl = tmpSameSlopeArr.get(tmpSameSlopeArr.size()-1);
						for (int w = 0; w < headList.size(); w++) {
							if (headList.get(w).equals(hd) && tailList.get(w).equals(tl)) flag = false;
							if (headList.get(w).equals(hd) && slopeList.get(w) == tmpRefSlope) flag = false;
							if (tailList.get(w).equals(tl) && slopeList.get(w) == tmpRefSlope) flag = false;
						}
						
						if (flag == true) {
							LineSegment ls = new LineSegment(hd, tl);
							segment.add(ls);
							segNum++;
							headList.add(copy[i]);
							tailList.add(tmpSameSlopeArr.get(tmpSameSlopeArr.size()-1));
							slopeList.add(tmpRefSlope);
						}					
						
					} else if (copy[i].compareTo(tmpSameSlopeArr.get(0)) > 0) {
						boolean flag = true;
						Point hd = tmpSameSlopeArr.get(0);
						Point tl = copy[i];
						for (int w = 0; w < headList.size(); w++) {
							if (headList.get(w).equals(hd) && tailList.get(w).equals(tl)) flag = false;
							if (headList.get(w).equals(hd) && slopeList.get(w) == tmpRefSlope) flag = false;
							if (tailList.get(w).equals(tl) && slopeList.get(w) == tmpRefSlope) flag = false;
						}
						if (flag == true) {
							LineSegment ls = new LineSegment(hd, tl);
							segment.add(ls);
							segNum++;
						}

					} else {
						boolean flag = true;
						Point hd = tmpSameSlopeArr.get(0);
						Point tl = tmpSameSlopeArr.get(tmpSameSlopeArr.size()-1);
						for (int w = 0; w < headList.size(); w++) {
							if (headList.get(w).equals(hd) && tailList.get(w).equals(tl)) flag = false;
							if (headList.get(w).equals(hd) && slopeList.get(w) == tmpRefSlope) flag = false;
							if (tailList.get(w).equals(tl) && slopeList.get(w) == tmpRefSlope) flag = false;
						}
						if (flag == true) {
							LineSegment ls = new LineSegment(hd, tl);
							segment.add(ls);
							segNum++;
						}

					}
				}			
			}
			
		}
	}   
	
	public  int numberOfSegments() { // the number of line segments
		return segNum;
	}       
	
	public LineSegment[] segments()   { // the line segments
		LineSegment[] seg = new LineSegment[segNum];
		for(int i = 0; i < segNum; i++) {
			seg[i] = segment.get(i);
		}
		return seg;
	}
	
	
	private void validateDup(Point[] points) {
		for (int i = 0; i < points.length; i++) {
			for (int j = i+1; j < points.length; j++) {
				if (points[i].equals(points[j])) throw new java.lang.IllegalArgumentException();
			}
		}
	}
	
	
	private void validate(Point p) {
		if (p == null) throw new java.lang.NullPointerException();
	}
	
	public static void main(String[] args) {
		
		// read the N points from a file
        // In in = new In(args[0]);
        In in = new In("/Users/ZhangHaotian/Desktop/princeton_algorithm/hw3/collinear/input200.txt");
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
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
		
	}
}

