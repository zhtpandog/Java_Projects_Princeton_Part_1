package algorithms_practice;
// import java.util.Iterator;

import edu.princeton.cs.algs4.*;

public class Solver {
	
	private boolean isSolvable = true;
	private SearchNode tmp;
	
	private class SearchNode implements Comparable<SearchNode>{
		Board bd;
		int rbdmoves; // total moves to reach current board
		SearchNode prev;
		
		@Override
		public int compareTo(SearchNode that) {
			if ((this.rbdmoves + this.bd.manhattan()) < (that.rbdmoves + that.bd.manhattan())) return -1;
			else if ((this.rbdmoves + this.bd.manhattan()) == (that.rbdmoves + that.bd.manhattan())) return 0;
			else return 1;
		}
	}
	
	public Solver(Board initial) { // find a solution to the initial board (using A* algo)
		if (initial == null) throw new java.lang.NullPointerException();
		
		// initialize the priority queue 1
		SearchNode sn1 = new SearchNode();
		sn1.bd = initial; sn1.rbdmoves = 0; sn1.prev = null;
		MinPQ<SearchNode> pq1 = new MinPQ<SearchNode>(); 
		pq1.insert(sn1);
		
		// initialize the priority queue 2
		SearchNode sn2 = new SearchNode();
		sn2.bd = initial.twin(); sn2.rbdmoves = 0; sn2.prev = null;
		MinPQ<SearchNode> pq2 = new MinPQ<SearchNode>(); 
		pq2.insert(sn2);
		
		while (true) {
			
			// delete from pq the search node with minimum priority
			SearchNode sna = pq1.delMin(); // sna is this search node to be deleted
			SearchNode snb = pq2.delMin(); // snb is twin's search node to be deleted
			
			// if the dequeued node corresponds to a goal board, the problems is solved
			tmp = sna;
			
			if (sna.bd.isGoal()) {
				break;
			}
			if (snb.bd.isGoal()) {
				isSolvable = false;
				break;
			}
			
			// insert into pq all neighboring search nodes
			Iterable<Board> nb1 = sna.bd.neighbors();
			
			for (Board b : nb1) {
				if (sna.prev == null || !sna.prev.bd.equals(b)) {
					SearchNode x = new SearchNode();
					x.bd = b; x.rbdmoves = sna.rbdmoves + 1; x.prev = sna;
					pq1.insert(x);
				}
			}
			
			Iterable<Board> nb2 = snb.bd.neighbors();
			
			for (Board b : nb2) {
				if (snb.prev == null || !snb.prev.bd.equals(b)) {
					SearchNode x = new SearchNode();
					x.bd = b; x.rbdmoves = snb.rbdmoves + 1; x.prev = snb;
					pq2.insert(x);
				}
			}
			
		}
		
	}
	
	public boolean isSolvable() { // is the initial board solvable?
		return isSolvable;
	}
	
	public int moves() { // minimum number of moves to solve initial board; -1 if unsolvable
		if (this.isSolvable() == false) return -1;
		else return  tmp.rbdmoves;
	}
	
	public Iterable<Board> solution() { // sequence of boards in a shortest solution; null if unsolvable
		if (this.isSolvable() == false) return null;
		
		Stack<Board> result = new Stack<Board>();
		
		SearchNode t = tmp;
		while (t != null) {
			result.push(t.bd);
			t = t.prev;
		}
		
		return result;
		
	}
	
	
	
	public static void main(String[] args) { // solve a slider puzzle
		
		// create initial board from file
	    // In in = new In(args[0]);
		In in = new In("/Users/ZhangHaotian/Desktop/princeton_algorithm/hw4/8puzzle/puzzle3x3-03.txt");
	    int n = in.readInt();
	    int[][] blocks = new int[n][n];
	    for (int i = 0; i < n; i++)
	        for (int j = 0; j < n; j++)
	            blocks[i][j] = in.readInt();
	    Board initial = new Board(blocks);

	    // solve the puzzle
	    Solver solver = new Solver(initial);

	    // print solution to standard output
	    if (!solver.isSolvable())
	        StdOut.println("No solution possible");
	    else {
	        StdOut.println("Minimum number of moves = " + solver.moves());
	        for (Board board : solver.solution())
	            StdOut.println(board);
	    }
		
	}
	
}
