import edu.princeton.cs.algs4.WeightedQuickUnionUF;

/**
 * 
 * @author ZhangHaotian
 * Princeton Algorithms Part 1
 * Programming Assignment 1: Percolation
 * More detailed explanation: http://coursera.cs.princeton.edu/algs4/assignments/percolation.html
 * 
 */

public class Percolation {
	
	private boolean[][] grid; // grid representation, true for open, false for blocked
	private int size; // size of the percolation matrix
	private WeightedQuickUnionUF uf; // union find object
	
	/**
	 * Create n-by-n grid, with all sites blocked
	 * @param n size 
	 */
	public Percolation(int n) { 
		
		if (n <= 0) throw new java.lang.IllegalArgumentException();

		size = n;
		uf = new WeightedQuickUnionUF(n*n+2); // extra 2 topmost and bottom most nodes
		
		// initialize the grid to be closed
		grid = new boolean[n][n];
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				grid[i][j] = false;  
			}
		}	
	}
	
	/**
	 * Open site (row i, column j) if it is not open already
	 * @param i row index, start from 1
	 * @param j column index, start from 1
	 */
	public void open(int i, int j) {
		if (i <= 0 || i > size || j <= 0 || j > size)  throw new IndexOutOfBoundsException();
		
		// open only when its not already open
		// if adjacent block are open, union them
		if (!grid[i-1][j-1]) {
			grid[i-1][j-1] = true;		
			if (i - 1 > 0 && isOpen(i-1, j)) uf.union(trans(i,j), trans(i-1,j));
			if (i + 1 <= size && isOpen(i+1, j))  uf.union(trans(i,j), trans(i+1,j));
			if (j - 1 > 0 && isOpen(i, j-1)) uf.union(trans(i,j), trans(i,j-1));
			if (j + 1 <= size && isOpen(i, j+1)) uf.union(trans(i,j), trans(i,j+1));
			
			// dealing with first row, should also connect to the first block
			if (i == 1) uf.union(trans(i,j), size*size);

			// // dealing with last row, should also connect to the last block
			if (i == size) uf.union(trans(i,j), size*size+1);
		}
		
	}
	
	/**
	 * Is site (row i, column j) open?
	 * @param i row index, start from 1
	 * @param j column index, start from 1
	 * @return is site (row i, column j) open or not
	 */
	public boolean isOpen(int i, int j) { 
		if (i <= 0 || i > size || j <= 0 || j > size)  throw new IndexOutOfBoundsException();
		
		return grid[i-1][j-1];
		
	}
	
	/**
	 * Is site (row i, column j) full?
	 * @param i row index, start from 1
	 * @param j column index, start from 1
	 * @return Is site (row i, column j) full or not
	 */
	public boolean isFull(int i, int j) {
		if (i <= 0 || i > size || j <= 0 || j > size)  throw new IndexOutOfBoundsException();
		return uf.connected(trans(i,j), size*size);
	}
    
    /**
     * Number of open sites
     * @return
     */
    public int numberOfOpenSites() {
        int cnt = 0;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (grid[i][j] == true) cnt++;
            }
        }
        return cnt;
    }
	
	/**
	 * Does this system percolate?
	 * @return percolate or not
	 */
	public boolean percolates() {
		if (uf.connected(size*size, size*size+1)) return true;
		else return false;
	}
	
	/**
	 * Transform (i,j) coordinates to array index
	 * @param i row index, start from 1
	 * @param j column index, start from 1
	 * @return array index, start from 0
	 */
	private int trans(int i, int j) {
		return (i-1)*size+j-1;
	}
	
	/**
	 * Unit test
	 * @param args
	 */
	public static void main(String[] args) {
		
	}
	
	
}
