

import java.util.Iterator;
import edu.princeton.cs.algs4.StdRandom;

/**
 * Implementation of a randomized queue
 * @author ZhangHaotian
 *
 * @param <Item>
 */
public class RandomizedQueue<Item> implements Iterable<Item> {
	
	   private Node first, last; // first and last node
	   private int cnt; // counter of items
	   
	   /**
		 * Inner class Node
		 * @author ZhangHaotian
		 *
		 */
	   private class Node{
		   Item item;
		   Node next;
		   Node prev;
	   }
	   
	   /**
	    * Construct an empty randomized queue
	    */
	   public RandomizedQueue() {
		   first = null; 
		   last = null; 
		   cnt = 0;
	   }
	   
	   /**
	    * Is the queue empty?
	    * @return
	    */
	   public boolean isEmpty() { 
		   return cnt == 0;
	   }
	   
	   /**
	    * Return the number of items on the queue
	    * @return
	    */
	   public int size() {
		   return cnt;
	   }
	   
	   /**
	    * Add the item
	    * @param item
	    */
	   public void enqueue(Item item) {
		   if (item == null) throw new java.lang.NullPointerException();
		   
		   Node oldlast = last;
		   
		   last = new Node();
		   last.item = item;
		   last.next = null;
		   last.prev = oldlast;
		   
		   if (cnt == 0) {
		     	first = last;
		   } else {
				oldlast.next = last;
		   }
		   cnt++;
	   }
	   
	   /**
	    * Remove and return a random item
	    * @return
	    */
	   public Item dequeue() {
		   if (cnt == 0) throw new java.util.NoSuchElementException();
		   
		   int ind = StdRandom.uniform(cnt);
		   Node tmpNode = first;
		   
		   while (ind > 0) {
			   tmpNode = tmpNode.next;
			   ind--;
		   }
		  
		   Item item = tmpNode.item;
		   
		   if (tmpNode.prev == null && tmpNode.next == null) { // only node
			   first = null; last = null;
		   } else if (tmpNode.prev == null && tmpNode.next != null) { // it is the first node
			   first = tmpNode.next;
			   tmpNode = null;
		   } else if (tmpNode.next == null && tmpNode.prev != null) { // it is the last node
			   last = tmpNode.prev;
			   tmpNode = null;			   
		   } else { // other conditions
			   tmpNode.prev.next = tmpNode.next;
			   tmpNode.next.prev = tmpNode.prev;
			   tmpNode =  null;
		   }
		   
		   cnt--;
		   
		   return item;
	   }
	   
	   /**
	    * Return (but do not remove) a random item
	    * @return
	    */
	   public Item sample() { 
		   if(cnt == 0) throw new java.util.NoSuchElementException();
		   
		   int ind = StdRandom.uniform(cnt);
		   Node tmpNode = first;
		   
		   while (ind > 0) {
			   tmpNode = tmpNode.next; // tmpNode cannot be null
			   ind--;
		   }
		   
		   return tmpNode.item;
		   
	   }
	   
	   /**
	    * iterator
	    */
	   public Iterator<Item> iterator() {
			return new RqIterator();
		}
		
	   /**
	    * Return an iterator over items in order from front to end
	    * @author ZhangHaotian
	    *
	    */
		private class RqIterator implements Iterator<Item> {
			
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
		 * Unit testing
		 * @param args
		 */
	   public static void main(String[] args)   {
		  
		   
//		    RandomizedQueue<Double> dq = new RandomizedQueue<>();
//			dq.enqueue(15);
//			dq.enqueue(10);
//			dq.enqueue(5);
//			
//			StdOut.println(dq.sample());
//			StdOut.println(dq.sample());
//			StdOut.println(dq.sample());
//			StdOut.println(dq.sample());
//			StdOut.println(dq.sample());
//			
//			StdOut.println("dequeue " + dq.dequeue());
//			StdOut.println(dq.sample());
//			StdOut.println(dq.sample());
//			
//		
//			dq.enqueue(13);
//			StdOut.println(dq.size());
//			StdOut.println(dq.isEmpty());
//			
//			dq.enqueue(44);
//			dq.enqueue(22);
//		
//			
//			StdOut.println(dq.size());
//			
//			//
//			StdOut.println(dq.sample());
//			StdOut.println(dq.sample());
//			StdOut.println(dq.sample());
//			StdOut.println(dq.sample());
//			
//			StdOut.println("de:" + dq.dequeue());
//			StdOut.println("de:" + dq.dequeue());
//			StdOut.println("de:" + dq.dequeue());
//			StdOut.println("de:" + dq.dequeue());
//			StdOut.println(dq.isEmpty());		   
		    
//		for (int i = 0; i < 50; i++) {
//			dq.enqueue(StdRandom.uniform());
//		}
//		
//		for (int i = 0; i < 50; i++) {
//			//boolean s = dq.isEmpty();
//			dq.dequeue();
//		}
	   }
	}
