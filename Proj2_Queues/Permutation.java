

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class Permutation {
	public static void main(String[] args) {
		
		RandomizedQueue<String> rq = new RandomizedQueue<>();
		int num = Integer.parseInt(args[0]);
		
		// int num = 3;
		
		for (int i = 0; i < num; i++) {
			String str = StdIn.readString();
			rq.enqueue(str);
		}
		
		while (!rq.isEmpty()) {
			StdOut.println(rq.dequeue());
		}
	}
}
