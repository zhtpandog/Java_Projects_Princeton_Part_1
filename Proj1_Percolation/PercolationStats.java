import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

/**
 * 
 * @author ZhangHaotian
 * Princeton Algorithms Part 1
 * Programming Assignment 1: Percolation
 * More detailed explanation: http://coursera.cs.princeton.edu/algs4/assignments/percolation.html
 *
 */

public class PercolationStats {
	
	private double[] resultArray; 
	private int tryNum; // try how many times
	private int size; // grid size
	
	/**
	 * Perform trials independent experiments on an n-by-n grid
	 * @param n
	 * @param trials
	 */
	public PercolationStats(int n, int trials) {  
		if (n<=0 || trials <= 0) throw new java.lang.IllegalArgumentException();
		
		tryNum = trials;
		size = n;
		
		resultArray = new double[trials];
		
		for(int i = 0; i < trials; i++) {
			int cnt = 0;
			Percolation per = new Percolation(n);
			while(!per.percolates()) {
				int rand1 = StdRandom.uniform(1,n+1);
				int rand2 = StdRandom.uniform(1,n+1);
				if (!per.isOpen(rand1, rand2)) {
					per.open(rand1, rand2);
					cnt++;					
				}
			}
			resultArray[i] = (double)cnt / (size*size);
		}
	}
	
	/**
	 * Sample mean of percolation threshold
	 * @return
	 */
	public double mean() {                         
		return StdStats.mean(resultArray);
	}
	
	/**
	 * Sample standard deviation of percolation threshold
	 * @return
	 */
	public double stddev() {                       
		return StdStats.stddev(resultArray);		
	}
	
	/**
	 * Low  end point of 95% confidence interval
	 * @return
	 */
	public double confidenceLo() {                 
		return this.mean() - 1.96 * this.stddev() / Math.sqrt(tryNum);
	}
	
	/**
	 * High end point of 95% confidence interval
	 * @return
	 */
	public double confidenceHi() {                 
		return this.mean() + 1.96 * this.stddev() / Math.sqrt(tryNum);
	}
	
	/**
	 * Unit test
	 * @param args
	 */
	public static void main(String[] args) {   
		int n1 = Integer.parseInt(args[0]); //n
		int n2 = Integer.parseInt(args[1]); //trials
		PercolationStats p = new PercolationStats(n1,n2);
		System.out.println("mean                    = " + p.mean());
		System.out.println("stddev                  = " + p.stddev());
		System.out.println("95% confidence interval = " + "[" + p.confidenceLo() + ", " + p.confidenceHi() + "]" );	
	}	
}
