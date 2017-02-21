package algorithms_practice;

import java.util.ArrayList;

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdOut;

public class KdTree {	   
	   
	   private static class Node {
		   private Point2D p;      // the point
		   private RectHV rect;    // the axis-aligned rectangle corresponding to this node
		   private Node lb;        // the left/bottom subtree
		   private Node rt;        // the right/top subtree
		   
		   public Node(Point2D p, RectHV rect) {
			   this.p = p;
			   this.rect = rect;
		   }
	   }
	   
	   // private SET<Point2D> pointSet;
	   private Node root;
	   private int cnt;
	
	   public KdTree() { // construct an empty set of points 
		   //root = null;
		   cnt = 0;
	   }
	   
	   public boolean isEmpty() { // is the set empty? 
		   return root == null;
	   }
	   
	   public int size() { // number of points in the set
		   return cnt;
	   }
	   
	   public void insert(Point2D p) { // add the point to the set (if it is not already in the set)
		   if(p == null) throw new java.lang.NullPointerException();
		   RectHV rect = new RectHV(0,0,1,1);
		   root = insert(root, p, true, rect);
	   }
	   
	   // b = true is x-d, false is y-d
	   private Node insert(Node x, Point2D p, boolean b, RectHV r) {
		   if (x != null && contains(p)) return root;
		  	   
		   if (x == null) {
			   cnt++;
			   // draw the line
			   if (b) {
				   Point2D p1 = new Point2D(p.x(),r.ymin());
				   Point2D p2 = new Point2D(p.x(),r.ymax());
				   p1.drawTo(p2);
			   } else if (!b) {
				   Point2D p1 = new Point2D(r.xmin(),p.y());
				   Point2D p2 = new Point2D(r.xmax(),p.y());
				   p1.drawTo(p2);
			   }
			   // draw the line
			   return new Node(p,r); 
		   }
		   
		   // node to be inserted compared with current node
		   // compare x-d
		   if (b == true) {			   
			   int cmpx = 0;
			   // p smaller than x in x-d
			   if (p.x() < x.p.x()) cmpx = -1;
			   else cmpx = 1;
			   
			   if (cmpx < 0) { // add to left
				   RectHV rect = new RectHV(r.xmin(), r.ymin(), x.p.x(), r.ymax());
				   x.lb = insert(x.lb, p, false, rect);
			   } else { // add to right
				   RectHV rect = new RectHV(x.p.x(), r.ymin(), r.xmax(), r.ymax());
				   x.rt = insert(x.rt, p, false, rect);
			   }
		   }
		   
		   // compare y-d
		   if (b == false) {
			   int cmpy = 0;
			   // p smaller than x in y-d
			   if (p.y() < x.p.y()) cmpy = -1;
			   else cmpy = 1;
			   
			   if (cmpy < 0) { // add to bottom
				   RectHV rect = new RectHV(r.xmin(), r.ymin(), r.xmax(), x.p.y());
				   x.lb = insert(x.lb, p, true, rect);
			   } else {
				   RectHV rect = new RectHV(r.xmin(), x.p.y(), r.xmax(), r.ymax());
				   x.rt = insert(x.rt, p, true, rect);
			   }
		   }
		   
		   return x;
		   
	   }
	   
	   public boolean contains(Point2D p) { // does the set contain point p? 
		   if(p == null) throw new java.lang.NullPointerException();
		   return contains(root, p, true);
	   }
	   
	   private boolean contains(Node x, Point2D p, boolean b) {
		   if (p == x.p) return true;
		   
		   if (b == true) {			   
			   int cmpx = 0;
			   // p smaller than x in x-d
			   if (p.x() < x.p.x()) cmpx = -1;
			   else cmpx = 1;
			   
			   if (cmpx < 0) { // go left
				   if (x.lb == null) return false;
				   return contains(x.lb, p, false);
			   } else { // go right
				   if (x.rt == null) return false;
				   return contains(x.rt, p, false);
			   }
		   }
		   
		   // compare y-d
		   if (b == false) {
			   int cmpy = 0;
			   // p smaller than x in y-d
			   if (p.y() < x.p.y()) cmpy = -1;
			   else cmpy = 1;
			   
			   if (cmpy < 0) { // go bottom
				   if (x.lb == null) return false;
				   return contains(x.lb, p, true);
			   } else {
				   if (x.rt == null) return false;
				   return contains(x.rt, p, true);
			   }
		   }
		   
		   return false;
	   }
	   
	   public void draw() { // draw all points to standard draw 
		   
	   }
	   
	   public Iterable<Point2D> range(RectHV rect) { // all points that are inside the rectangle 
		   if(rect == null) throw new java.lang.NullPointerException();
		   ArrayList<Point2D> ary = new ArrayList<Point2D>();
		   
		   ary = range(root, rect, ary);
		   
		   return ary;
	   }
	   
	   private ArrayList<Point2D> range(Node x, RectHV rect, ArrayList<Point2D> aryy) {
		   if (x.rect.intersects(rect)) {
			   if (rect.contains(x.p)) {
				   aryy.add(x.p);
			   }
			   if (x.lb != null) aryy = range(x.lb, rect, aryy);
			   if (x.rt != null) aryy = range(x.rt, rect, aryy);
		   }
		   return aryy;
	   }
	   
	   public Point2D nearest(Point2D p) { // a nearest neighbor in the set to point p; null if the set is empty 
		   if(p == null) throw new java.lang.NullPointerException();
		   
		   Point2D nst = root.p;
		   nst = nearest(root, p, nst, true);
		   
		   
		   return nst;
	   }
	   
	   private Point2D nearest(Node x, Point2D p, Point2D nrst, boolean b) {
		   if (x != null && contains(p)) return p;
		   if (x.p.distanceTo(p) < p.distanceTo(nrst)) nrst = x.p;
		   
		   if (nrst.distanceTo(p) > distPtRect(p, x.rect)) {
			   if (b == true) { // check x-d
				   if (x.p.x() - p.x() >= 0) {
					   if (x.lb != null) nrst = nearest(x.lb, p, nrst, false);
					   if (x.rt != null) nrst = nearest(x.rt, p, nrst, false);
				   } else {
					   if (x.rt != null) nrst = nearest(x.lb, p, nrst, false);
					   if (x.lb != null) nrst = nearest(x.rt, p, nrst, false);
				   }
			   } else { // check y-d
				   if (x.p.y() - p.y() >= 0) {
					   if (x.lb != null) nrst = nearest(x.lb, p, nrst, true);
					   if (x.rt != null) nrst = nearest(x.rt, p, nrst, true);
				   } else {
					   if (x.rt != null) nrst = nearest(x.lb, p, nrst, true);
					   if (x.lb != null) nrst = nearest(x.rt, p, nrst, true);
				   }
			   }
			  
		   }
		   return nrst;
	   }
	   
	   private double distPtRect(Point2D pt, RectHV rect) {
		   if (pt.x() < rect.xmin() && pt.y() < rect.ymin()) {
			   return pt.distanceTo(new Point2D(rect.xmin(), rect.ymin()));
		   } else if (pt.x() > rect.xmax() && pt.y() < rect.ymin()) {
			   return pt.distanceTo(new Point2D(rect.xmax(), rect.ymin()));
		   } else if (pt.x() < rect.xmin() && pt.y() > rect.ymax()) {
			   return pt.distanceTo(new Point2D(rect.xmin(), rect.ymax()));
		   } else if (pt.x() > rect.xmax() && pt.y() > rect.ymax()) {
			   return pt.distanceTo(new Point2D(rect.xmax(), rect.ymax()));
		   } else if (pt.x() <= rect.xmin()) {
			   return rect.xmin() - pt.x();
		   } else if (pt.x() >= rect.xmax()) {
			   return pt.x() - rect.xmax();
		   } else if (pt.y() <= rect.ymin()) {
			   return rect.ymin() - pt.y();
		   } else {
			   return pt.y() - rect.ymax();
		   } 
	   }

	   public static void main(String[] args) { // unit testing of the methods (optional) 
		   
		   KdTree kd = new KdTree();
		   
		   Point2D p1 = new Point2D(0.7, 0.2);
		   Point2D p2 = new Point2D(0.5, 0.4);
		   Point2D p3 = new Point2D(0.2, 0.3);
		   Point2D p4 = new Point2D(0.4, 0.7);
		   Point2D p5 = new Point2D(0.9, 0.6);
		   
		  
		   
		   kd.insert(p1);		   
		   StdOut.println(kd.contains(p1));
		   kd.insert(p2);		   
		   kd.insert(p3);		   
		   StdOut.println(kd.contains(p4));
		   kd.insert(p4);		   
		   StdOut.println(kd.contains(p3));
		   StdOut.println(kd.contains(p4));
		   StdOut.println(kd.contains(p5));
		   kd.insert(p5);
		   StdOut.println(kd.contains(p5));
		   
		   RectHV rc = new RectHV(0,0,0.6,1);
		   int ccc = 0;
		   for(Point2D p : kd.range(rc)) {
			   ccc++;
			   StdOut.println(p.toString());
		   }
		   StdOut.println(ccc);
		   StdOut.println(kd.nearest(new Point2D(0.4, 0.71)));
	   }
	}