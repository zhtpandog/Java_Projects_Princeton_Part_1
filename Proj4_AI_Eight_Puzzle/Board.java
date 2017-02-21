package algorithms_practice;
//import java.util.Arrays;

//import java.util.Arrays;

import edu.princeton.cs.algs4.*;

public class Board {
	
	private int dim;
	private int[][] current;

	public Board(int[][] blocks) { // construct a board from an n-by-n array of blocks
		
		dim = blocks.length;
		current = blocks;
		
	}
	
	public int dimension() { // board dimension n
		return dim;
	}
	
	public int hamming() { // number of blocks out of place
		
		int ham = 0;
		for (int i = 0; i < dim * dim; i++) {
			if ((current[i/dim][i%dim] != i+1) && current[i/dim][i%dim] != 0) ham++; 
		}
		return ham;
	}
	
	public int manhattan() { // sum of Manhattan distances between blocks and goal
		
		int man = 0;
		for (int i = 0; i < dim * dim; i++) {
			if (current[i/dim][i%dim] != 0) {
				int targX = (current[i/dim][i%dim] - 1) / dim;
				int targY = (current[i/dim][i%dim] - 1) % dim;
				man += Math.abs(i/dim - targX) + Math.abs(i%dim - targY);
			}
		}
		return man;
	}
	
	public boolean isGoal() { // is this board the goal board?
		boolean chk = true;
		for (int i = 0; i < dim * dim; i++) {
			if ((current[i/dim][i%dim] != i+1) && current[i/dim][i%dim] != 0) {
				chk = false; 
				break;
			}	
		}
		
		return chk;
	}
	
	public Board twin() { // a board that is obtained by exchanging any pair of blocks
		
		int rand1, rand2;
		while(true) {
			rand1 = StdRandom.uniform(0,dim*dim);
			rand2 = StdRandom.uniform(0,dim*dim);
			if (rand1 != rand2 && current[rand1/dim][rand1%dim] != 0 && current[rand2/dim][rand2%dim] != 0) break;
		}
		
		int[][] twinbrd = new int[dim][dim];
		twinbrd = copy(current);
		exch(twinbrd, rand1, rand2);
		
		Board brd = new Board(twinbrd);
		return brd;
	}
	
	public boolean equals(Object y) { // does this board equal y?
		
		if (y == this) return true;
		if (y == null) return false;
		if (y.getClass() != this.getClass()) return false;
		
		Board thatBoard = (Board)y;
		if (this.dim != thatBoard.dim) return false;
		
		int[][] arr_thatBoard = thatBoard.current;  
		
		for (int i=0; i<dim; i++){  
            for (int j=0; j<dim; j++){  
                if (current[i][j] != arr_thatBoard[i][j]){  
                    return false;  
                }  
            }  
        }  
        return true;  

	}
	
	public Iterable<Board> neighbors() { // all neighboring boards
		
		Stack<Board> nei = new Stack<Board>();
		
		// find the position of 0
		int loc0 = findIndex(0);
		int x0 = loc0 / dim;
		int y0 = loc0 % dim;
		
		if (x0 - 1 >= 0) { // check above
			int[][] tmp = copy(current);
			exch(tmp, loc0, loc0 - dim);
			Board result = new Board(tmp);
			nei.push(result);
		} 
		
		if (x0 + 1 <= dim-1) { // check below
			int[][] tmp = copy(current);
			exch(tmp, loc0, loc0 + dim);
			Board result = new Board(tmp);
			nei.push(result);
		} 
		
		if (y0 - 1 >= 0) { // check left
			int[][] tmp = copy(current);
			exch(tmp, loc0, loc0 - 1);
			Board result = new Board(tmp);
			nei.push(result);
		}
		
		if (y0 + 1 <= dim-1) { // check right
			int[][] tmp = copy(current);
			exch(tmp, loc0, loc0 + 1);
			Board result = new Board(tmp);
			nei.push(result);
		}
		
		return nei;
	}
	
	public String toString() { // String representation of this board (in the output format specified
		
		String output;
		
		output = dim + "\n";
		
		for(int i = 0; i < dim; i++) {
			for(int j = 0; j < dim; j++) {
				output = output + current[i][j] + " ";
			}
			if(i != dim - 1) output += "\n";
		}
		
		return output;
	}
	
	private int findIndex(int x) {
		int result = 0;
		for(int i = 0; i < dim * dim; i++) {
			if (current[i/dim][i%dim] == x) {
				result = i;
				break;
			}
		}
		return result;
		
	}
	
	private void exch(int[][] input, int a, int b) {
		int tmp = input[a/dim][a%dim];
		input[a/dim][a%dim] = input[b/dim][b%dim];
		input[b/dim][b%dim] = tmp;
	}
	
	private static int[][] copy(int[][] input) {
		int dim = input.length;
		int [][] output = new int[dim][dim];
		for(int i = 0; i < dim*dim; i++) {
			output[i/dim][i%dim] = input[i/dim][i%dim];
		}
		return output;
	}
	
	public static void main(String[] args) { // unit tests
		
		int[][] ipt = {{1,2,3},{4,0,5},{7,8,6}};
		Board bd = new Board(ipt);
//		StdOut.println(Arrays.deepToString(bd.current));
//		StdOut.println(bd.dimension());
//		StdOut.println(bd.hamming());
//		StdOut.println(bd.manhattan());
//		StdOut.println(bd.isGoal());
		StdOut.println(bd.twin().toString());
//		StdOut.println(Arrays.deepToString(bd.current));
//		StdOut.println(bd.equals(bd));
//		
//		for(Board b: bd.neighbors()) StdOut.println(b.toString());
//		StdOut.println(bd.neighbors());
//		
//		StdOut.println(Arrays.deepToString(bd.current));
//	    StdOut.println(Arrays.deepToString(bd.current));
	}
	
	
	
	
	
	
}
