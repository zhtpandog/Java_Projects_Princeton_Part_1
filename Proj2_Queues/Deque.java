

import java.util.Iterator;

/**
 * Implementation of a generic deque
 * @author ZhangHaotian
 *
 * @param <Item>
 */
public class Deque<Item> implements Iterable<Item> {
	
	private Node first, last; // first and last nodes
	private int cnt; // keep track of size
	
	/**
	 * Inner class Node
	 * @author ZhangHaotian
	 *
	 */
	private class Node {
		Item item;
		Node next;
		Node prev;
	}
	
	/**
	 * Construct an empty Deque
	 */
	public Deque() {
		first = null; 
		last = null; 
		cnt = 0;
	}
	
	/**
	 * Is the deque empty?
	 * @return
	 */
	public boolean isEmpty() { 
		return cnt == 0;
	}
	
	/**
	 * Return the number of items in the deque
	 * @return
	 */
	public int size() { 
		return cnt;
	}
	
	/**
	 * Add the item to the front 
	 * @param item
	 */
	public void addFirst(Item item) { 
		if (item == null) throw new java.lang.NullPointerException();
		Node oldFirst = first;
		first = new Node();
		first.item = item;
		first.next = oldFirst;
		first.prev = null;
		if (cnt >= 1) oldFirst.prev = first;
		if (cnt == 0) last = first;
		cnt++;
	}
	
	/**
	 * Add the item to the end
	 * @param item
	 */
	public void addLast(Item item) {
		if (item == null) throw new java.lang.NullPointerException();
		Node oldLast = last;
		last = new Node();
		last.item = item;
		last.prev = oldLast;
		last.next = null;
		if (cnt >= 1) oldLast.next = last;
		if (cnt == 0) first = last;
		cnt++;
		
	}
	
	/**
	 * Remove and return the item from the front
	 * @return
	 */
	public Item removeFirst() { 
		if (cnt == 0) throw new java.util.NoSuchElementException();
		Item item = first.item;
		first = first.next;
		cnt--;
		if (isEmpty()) last = null;
		else first.prev = null;
		return item;
	}
	
	/**
	 * Remove and return the item from the end
	 * @return
	 */
	public Item removeLast() { 
		if (cnt == 0) throw new java.util.NoSuchElementException();
		Item item = last.item;
		last = last.prev;
		cnt--;
		if (isEmpty()) first = null;
		else last.next = null;
		return item;
	}
	
	/**
	 * Iterator 
	 */
	public Iterator<Item> iterator() {
		return new DequeIterator();
	}
	
	/**
	 * Return an iterator over items in order from front to end
	 * @author ZhangHaotian
	 *
	 */
	private class DequeIterator implements Iterator<Item> {
		
		private Node current = first;
		
		public boolean hasNext() {
			return current != null; 
		}
		
		public void remove() {
			throw new java.lang.UnsupportedOperationException();
		}
		
		public Item next() {
			if (!hasNext()) {
				throw new java.util.NoSuchElementException();
			}
			Item item  = current.item;
			current = current.next;
			return item;
		}
	}
	
	
	/**
	 * Unit test
	 * @param args
	 */
	public static void main(String[] args) { //unit testing
		Deque<Integer> deque = new Deque<>();
//		Iterator  = deque.iterator();
//		
//		dq.addFirst(15);
//		dq.addFirst(10);
//		dq.addFirst(11);
//		int a1 = dq.removeLast();
//		StdOut.println(a1);
//		int a2 = dq.removeFirst();
//		StdOut.println(a2);
//		dq.addFirst(13);
//		StdOut.println("size: " + dq.size());
//		//System.out.println(itr.next().toString());
//		//System.out.println(itr.next().toString());
//		int ax = dq.removeFirst();
//		StdOut.println(ax);
//		int ay = dq.removeFirst();
//		StdOut.println(ay);
//		StdOut.println(dq.isEmpty());
//		dq.addLast(44);
//		dq.addLast(22);
//		int a3 = dq.removeFirst();
//		StdOut.println(a3);
//		int a4 = dq.removeLast();
//		StdOut.println(a4);
//		dq.addFirst(43);
//		dq.addLast(99);
//		StdOut.println("size: " + dq.size());	
//		//System.out.println(itr.next().toString());
//		//System.out.println(itr.next().toString());
//		StdOut.println(dq.isEmpty());
//		int am = dq.removeLast();
//		int an = dq.removeLast();
//		StdOut.println(dq.isEmpty());
//		
//		for (int i = 0; i < 50; i++) {
//			dq.addFirst(StdRandom.uniform());
//		}
//		
//		for (int i = 0; i < 50; i++) {
//			boolean s = dq.isEmpty();
//			dq.removeLast();
//		}
		
//		deque.addLast(1);
        deque.addFirst(2);
        deque.addFirst(2);
        deque.removeFirst();
//        deque.addLast(3);
//        StdOut.println(deque.removeFirst());   //==> 2
//        deque.addFirst(5);
//        StdOut.println(deque.removeFirst());  // ==> 5
//        deque.addFirst(7);
//        StdOut.println(deque.removeFirst());   //==> 7
//        deque.addLast(9);
//        deque.addFirst(10);
//        deque.addFirst(11);
//        StdOut.println(deque.removeLast());    //==> 9
//        StdOut.println(deque.size());
		
	}
}
