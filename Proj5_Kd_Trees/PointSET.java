package algorithms_practice;

import java.util.ArrayList;

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.SET;

public class PointSET {
	
	   private SET<Point2D> pointSet;
	
	   public PointSET() { // construct an empty set of points 
		   pointSet = new SET<Point2D>();
	   }
	   
	   public boolean isEmpty() { // is the set empty? 
		   return pointSet.isEmpty();
	   }
	   
	   public int size() { // number of points in the set 
		   return pointSet.size();
	   }
	   
	   public void insert(Point2D p) { // add the point to the set (if it is not already in the set)
		   if(p == null) throw new java.lang.NullPointerException();
		   pointSet.add(p);
	   }
	   
	   public boolean contains(Point2D p) { // does the set contain point p? 
		   if(p == null) throw new java.lang.NullPointerException();
		   return pointSet.contains(p);
	   }
	   
	   public void draw() { // draw all points to standard draw 
		   for (Point2D p: pointSet) {
			   p.draw();
		   }
	   }
	   
	   public Iterable<Point2D> range(RectHV rect) { // all points that are inside the rectangle 
		   if(rect == null) throw new java.lang.NullPointerException();
		   ArrayList<Point2D> ary = new ArrayList<Point2D>();
		   for (Point2D p : pointSet) {
			   if (rect.contains(p)) ary.add(p);
		   }
		   return ary;
	   }
	   
	   public Point2D nearest(Point2D p) { // a nearest neighbor in the set to point p; null if the set is empty 
		   if(p == null) throw new java.lang.NullPointerException();
		   if (pointSet.isEmpty()) return null;
		   
		   Point2D nst = null;
		   double tmpdist = Double.MAX_VALUE;
		   
		   for (Point2D pt: pointSet) {
			   if (pt.distanceTo(p) < tmpdist) {
				   tmpdist = pt.distanceTo(p);
				   nst = pt;
			   }
		   }
		   return nst;
	   }

	   public static void main(String[] args) { // unit testing of the methods (optional) 
		   
	   }
	}