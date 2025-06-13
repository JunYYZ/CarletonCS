package comp2402w24l3;

// From within the Lab directory (or wherever you put the comp2402w24l3 directory):
// javac comp2402w24l3/GeneSet.java
// java comp2402w24l3/GeneSet

// You will submit this file to the autograder.
// It will not pass many of the tests, if any.
// Your task is to modify the class to pass the tests.

// Do not add any other imports.
// (You shouldn't need any, and you'll fail the autograder if you do.)

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Iterator;

public class GeneSet {

	/**
	 * Represents a single "mega" node in our set. Do not remove this.
	 */
	@SuppressWarnings("unchecked")
	protected static class Node {
		String s;
		Node[] next;

		public Node(String s, int h) {
			this.s = s;
			next = (Node[]) Array.newInstance(Node.class, h + 1);
		}

		public int height() {
			return next.length - 1;
		}
	}

	/**
	 * The dummy node, also known as a sentinel. Do not remove this.
	 */
	protected Node dummy;

	/**
	 * The maximum height of any element
	 */
	int h;

	/**
	 * The number of elements stored in the set
	 */
	int n;

	/**
	 * Constructor
	 */
	@SuppressWarnings("unchecked")
	public GeneSet() {
		n = 0;
		dummy = new Node(null, 32);
		h = 0;
	}

	/**
	 * Returns the number of Strings in the set.
	 */
	public int size() {
		return n;
	}

	/**
	 * Clears the set so that it has no elements.
	 */
	public void clear() {
		n = 0;
		h = 0;
		Arrays.fill(dummy.next, null);
	}

	/**
	 * This returns a fairly detailed String representation of the GeneSet including
	 * the structure of the underlying Skiplist. This should be helpful for
	 * debugging purposes.
	 * 
	 * @return
	 */
	public String toString() {
		StringBuilder sb = new StringBuilder();
		StringBuilder[] levels = new StringBuilder[h + 1];
		int[] prevIndex = new int[h + 1];
		int[] extraDigits = new int[h + 1];
		for (int r = 0; r < levels.length; r++) {
			levels[r] = new StringBuilder();
			levels[r].append("L" + r + ":\t |");
			prevIndex[r] = -1;
			extraDigits[r] = 0;
		}

		// start at level 0
		// for each element in L0, figure out its index
		// determine which levels it's in, then
		// then add a bunch of -- for each index
		int index = -1;
		Node u = dummy;
		while (u != null) {
			if (u.s != null) { // not at the sentinel
				// determine which levels have this particular element
				int u_h = u.height();
				// add - for every level
				for (int r = 0; r <= h; r++) {
					levels[r].append("-");
					if (r <= u_h) {
						levels[r].append(u.s);
					} else {
						for (int i = 0; i < u.s.length(); i++) {
							levels[r].append("-");
						}
					}
					if (u.next[0] == null) {
						levels[r].append("\n");
					}
				}
			}
			u = u.next[0];
			index++;
		}
		for (int r = h; r >= 0; r--) {
			sb.append(levels[r].toString());
		}
		return sb.toString();
	}

	/**
	 * Find the node u that precedes the value s in the set.
	 *
	 * @param s - the value to search for
	 * @return a node u that maximizes u.s subject to the constraint that u.s < s
	 *         --- or dummy if u.s >= s for all nodes s
	 */
	protected Node findPredNode(String s) {
		Node u = dummy;
		int r = h;
		while (r >= 0) {
			while (u.next[r] != null && u.next[r].s.compareTo(s) < 0) {
				u = u.next[r]; // go right in list r
			}
			r--; // go down into list r-1
		}
		return u;
	}

	/**
	 * Create a new iterator in which the next value in the iteration is u.next.x
	 * 
	 * @param u
	 * @return
	 */
	protected Iterator<String> iterator(Node u) {
		class SkiplistIterator implements Iterator<String> {
			Node u, prev;

			public SkiplistIterator(Node u) {
				this.u = u;
				prev = null;
			}

			public boolean hasNext() {
				return u.next[0] != null;
			}

			public String next() {
				prev = u;
				u = u.next[0];
				return u.s;
			}
		}
		return new SkiplistIterator(u);
	}

	public Iterator<String> iterator() {
		return iterator(dummy);
	}

	public Iterator<String> iterator(String s) {
		return iterator(findPredNode(s));
	}

	/**
	 * Returns an array of the heights of the nodes in the skiplist. Normally such a
	 * method that exposes internal implementation details would not be public, but
	 * this is used for testing purposes. Please do not change or remove this
	 * otherwise the autograder might fail.
	 * 
	 * @return
	 */
	public int[] getHeights() {
		int[] heights = new int[n + 1];
		Node u = dummy;
		int i = 0;
		while (u != null) {
			heights[i++] = u.height();
			u = u.next[0];
		}
		return heights;
	}

	/**
	 * Returns the node at index i in the skiplist. Normally such a method that
	 * exposes internal implementation details would not be public, but this is used
	 * for testing purposes. Please do not change or remove this otherwise the
	 * autograder might fail.
	 *
	 * @param i
	 * @return
	 */
	public Node getNode(int i) {
		if (i < 0 || i >= n)
			throw new IndexOutOfBoundsException();
		Node u = dummy;
		for (int j = 0; j <= i; j++) {
			u = u.next[0];
		}
		return u;

	}

	// You should not need to modify anything above this line.

	/**
	 * Resizes the node u to have height newHeight, if newHeight > height. Leaves
	 * u's height unchanged, otherwise. Update the height of the skiplist, h, if
	 * necessary.
	 * 
	 * @param u
	 * @param newHeight
	 */
//	public void resize0(Node u, int newHeight) {
//		if (newHeight <= u.height()) {
//			return;
//		}
//
//		Node[] newNode = (Node[]) Array.newInstance(Node.class, newHeight + 1);
//		System.arraycopy(u.next, 0, newNode, 0, u.next.length);
//		u.next = newNode;
//
//		if (newHeight > h) {
//			h = newHeight;
//		}
//	}
//
//	public void resize1(Node u, int newHeight) {
//		if (newHeight <= u.height()) {
//			return;
//		}
//
//		Node[] newNext = new Node[newHeight];
//		System.arraycopy(u.next, 0, newNext, 0, u.next.length);
//		u.next = newNext;
//
//		if (newHeight > h) {
//			h = newHeight;
//		}
//	}

	public void resize(Node u, int newHeight) {
		if (newHeight > u.height()) {
			Node[] newNext = (Node[]) Array.newInstance(Node.class, newHeight + 1);
			System.arraycopy(u.next, 0, newNext, 0, u.next.length);
			u.next = newNext;

			if (newHeight > this.h) {
				this.h = newHeight;
			}
		}
	}

	// helper to bring up a node
	private void promoteNode(Node target) {
		if (target == null) {
			return; // can't be null, though this is probably redundant
		}

		int newLevel = target.height() + 1;
		// make sure not over max level
		if (newLevel > 32) {
			newLevel = 32;
		}

		// resize
		if (newLevel > h) {
			h = newLevel;
//			dummy.next = Arrays.copyOf(dummy.next, 33);
//			dummy.next[newLevel] = null;
		}

		target.next = Arrays.copyOf(target.next, newLevel + 1);

		// update links
		Node prev = dummy;
		for (int i = h; i >= 0; i--) {
			while (prev.next[i] != null && prev.next[i].s.compareTo(target.s) < 0) {
				prev = prev.next[i];
			}
			if (i == newLevel) {
				target.next[i] = prev.next[i];
				prev.next[i] = target;
				break; // dont need to update lower levels for promoted node
			}
		}
	}

	/**
	 * Adds String s to the set, if s is not already in the set. Returns true if s
	 * is newly added to the set. Throws an IllegalArgumentException is s is empty.
	 * 
	 * @param s
	 */
//		Node w = new Node(s, 0);
//		w.next[0] = dummy.next[0];
//		dummy.next[0] = w;
//		n++;
//		return true;
	/**
	 * public boolean addfailed(String s) { if (s == null || s.isEmpty()) throw new
	 * IllegalArgumentException("String s cannot be empty");
	 * 
	 * Node current = dummy; int r = h; Node[] stack = (Node[])
	 * Array.newInstance(Node.class, h + 1); int lvl = h;
	 * 
	 * while (r >= 0) { int counter = 0; while (current.next[r] != null &&
	 * current.next[r].s.compareTo(s) < 0) { current = current.next[r]; // go right
	 * in list r if (current != null && current.s.equals(s)) { return false; }
	 * counter++; if (counter >= 2) { lvl = r + 1; if (lvl > h) { resize(dummy,
	 * lvl); h = lvl; // Update the max height of the skiplist }
	 * 
	 * // stack[r] = current; // remember the node at this level // Node newNode =
	 * new Node(current.s, lvl); // for (int i = 0; i <= lvl; i++) { // if (i <
	 * stack.length) { // newNode.next[i] = stack[i].next[i]; // stack[i].next[i] =
	 * newNode; // } // } // promoteNodeToLevel(current.s, lvl); } } stack[r] =
	 * current; r--; } current = current.next[0];
	 * 
	 * if (lvl > h) { // resize(dummy, lvl); h = lvl; }
	 * 
	 * // insert the new node Node newNode = new Node(s, lvl); for (int i = 0; i <=
	 * lvl; i++) { resize(stack[i], lvl); newNode.next[i] = stack[i].next[i];
	 * stack[i].next[i] = newNode;
	 * 
	 * }
	 * 
	 * // Node newNode = new Node(s, 0); // for (int i = 0; i <= 0; i++) { // if (i
	 * < stack.length) { // newNode.next[i] = stack[i].next[i]; // stack[i].next[i]
	 * = newNode; // } // }
	 * 
	 * n++; return true; }
	 **/

	public boolean add(String s) {
		if (s == null || s.isEmpty()) {
			throw new IllegalArgumentException("String cannot be null or empty");
		}

		Node current = dummy;
		int r = h;
		Node[] stack = (Node[]) Array.newInstance(Node.class, h + 1);
		int comp = -1;

		// iterate and find position
		while (r >= 0) {
			// counter to keep track of how many nodes iterated through of same level
			int counter = 0;
			while (current.next[r] != null && (comp = current.next[r].s.compareTo(s)) < 0) {
				current = current.next[r];
				counter++;
				// if 2 consecutive nodes, add node up.
				if (counter >= 2) {
					promoteNode(current);
					counter = 0;
				}
			}
			// duplication check
			if (current.next[r] != null && comp == 0) {
				return false;
			}
			stack[r] = current;
			r--;
		}

		// insert new node
		Node newNode = new Node(s, 0);
		newNode.next[0] = (stack[0] == null ? dummy : stack[0].next[0]); // if stack[i] is null, use dummy
		if (stack[0] == null) {
			dummy.next[0] = newNode;
		} else {
			stack[0].next[0] = newNode;
		}
		n++;
		return true;

//		for (int i = 0; i <= 0; i++) {
//		newNode.next[i] = (stack[i] == null ? dummy : stack[i]).next[i]; // if stack[i] is null, use dummy
//		if (stack[i] == null) {
//			dummy.next[i] = newNode;
//		} else {
//			stack[i].next[i] = newNode;
//		}
//	}
//	for (int i = 0; i <= 0; i++) {
//		if (i < stack.length) {
//			newNode.next[i] = stack[i].next[i];
//			stack[i].next[i] = newNode;
//		}
//	}
	}

	/**
	 * Returns the minimum y >= s such that y is in the set, or null if there is no
	 * such y. Throws an IllegalArgumentException is s is empty.
	 * 
	 * @param s
	 * @return
	 * @throws IllegalArgumentException
	 */
	public String find(String s) throws IllegalArgumentException {
		if (s == null || s.isEmpty()) {
			throw new IllegalArgumentException("String cannot be null or empty");
		}

		Node current = dummy;
		int r = h;
		int comp = -1;
		while (r >= 0) {
			int counter = 0;
			while (current.next[r] != null && (comp = current.next[r].s.compareTo(s)) < 0) {
				current = current.next[r]; // Move to the next node at the current level.
				counter++;
				if (counter >= 2) {
//					if (current != dummy) {
//						if (r > 0) {
//							promoteNode(current);
//						} else {
					promoteNode(current);
//						}
					counter = 0;
//					}
				}
			}
			// iterating to the 2nd node forced it to skip the while loop so this is here
			// though this should be redundant after I fixed the above loop
			// literally just an edge case catch
			if (current.next[r] != null && comp >= 0) {
				if (comp == 0) {
					counter++;
				}
				if (counter >= 2) {
//					if (current != dummy) {
					promoteNode(current.next[r]);
					counter = 0;
//					}
				}
			}
			// current is node just before node we're looking for
			if (current.next[0] != null && current.next[0].s.compareTo(s) >= 0) {
				return current.next[0].s;
			}

			r--;
		}

//		// current is node just before node we're looking for
//		if (current.next[0] != null && current.next[0].s.compareTo(s) >= 0) {
//			return current.next[0].s;
//		}

		// null if can't find
		return null;
	}

	/**
	 * Removes String s from the set, if it is in the set. Returns true if s was
	 * removed from the set. Throws an IllegalArgumentException is s is empty.
	 * 
	 * @param s
	 * @return
	 * @throws IllegalArgumentException
	 */
	public boolean remove(String s) throws IllegalArgumentException {
		if (s == null || s.isEmpty()) {
			throw new IllegalArgumentException("String cannot be null or empty");
		}

		boolean found = false;
		Node current = dummy;
		int r = h;
		Node[] stack = (Node[]) Array.newInstance(Node.class, h + 1);
		int comp = -1;

		// same logic as find and add
		while (r >= 0) {
			int counter = 0;
			while (current.next[r] != null && (comp = current.next[r].s.compareTo(s)) < 0) {
				current = current.next[r];
				counter++;
				if (counter >= 2) {
					// make sure its not dummy, though this should be redundant
					if (current != dummy) {
						promoteNode(current);
						counter = 0;
					}
				}
			}
			// again, this is literally just an edge case catch for when removing 3rd
			// element
			if (current.next[r] != null && comp >= 0) {
				if (counter >= 2) {
					if (current != dummy) {
						promoteNode(current.next[r]);
						counter = 0;
					}
				}
			}
			stack[r] = current;
			r--;
		}

		current = stack[0].next[0]; // node to remove
		if (current != null && current.s.equals(s)) {
			found = true;
			// remove the node by updating links at all levels where it appears
//			for (int i = 0; i <= h && preds[i].next[i] == current; i++) { // was for normal skiplist
			for (int i = 0; i <= stack.length - 1 && stack[i].next[i] == current; i++) {
				stack[i].next[i] = current.next[i];
			}
			n--;
		}

		// adjust the height of the skiplist if the highest levels are empty
		while (h > 0 && dummy.next[h] == null) {
			h--;
		}

		return found;
	}

	/**
	 * Returns true this GeneSet is equal to GeneSet o. Returns false if o is null
	 * or not an instance of GeneSet. Returns false if this and o contain different
	 * elements. Returns false if the heights and/or shortcuts of any nodes is
	 * different.
	 * 
	 * @param o
	 * @return
	 */
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}

		if (!(o instanceof GeneSet)) {
			return false;
		}

		GeneSet other = (GeneSet) o;

		if (this.n != other.n || this.h != other.h) {
			return false;
		}

		for (int i = this.h; i >= 0; i--) {
			Node thisNode = this.dummy.next[i];
			Node otherNode = other.dummy.next[i];

			while (thisNode != null && otherNode != null) {
				if (!thisNode.s.equals(otherNode.s) || thisNode.height() != otherNode.height())
					return false;

				// next
				thisNode = thisNode.next[i];
				otherNode = otherNode.next[i];
			}

			// if one list is longer than other at any level, not equal
			if (thisNode != null || otherNode != null) {
				return false;
			}

		}

		return true;
	}

	// These are for testing purposes. Modify as you wish!

	private static void testResize() {
		System.out.println("\n\nTesting Resize...");
		System.out.println("\n\nThese are limited local tests. Please test more thoroughly.");

		GeneSet set = new GeneSet();
		System.out.println("heights of empty set: " + Arrays.toString(set.getHeights())); // expect [32] which is the
																							// height of the dummy
		set.add("A");
		System.out.println("heights of set with 1 element: " + Arrays.toString(set.getHeights())); // expect [32,0]
		System.out.println(set); // expect |-A
		set.resize(set.getNode(0), 1);
		System.out.println("resize(u,1) should increase height of A to 1: " + Arrays.toString(set.getHeights())); // expect
																													// [32,1]
		System.out.println(set); // expect L1: |-A
									// L0: |-A

		set.resize(set.getNode(0), 1);
		System.out.println("resize(u,1) should not change height of A: " + Arrays.toString(set.getHeights())); // expect
																												// [32,1]
		System.out.println(set); // expect L1: |-A
									// L0: |-A

		set.resize(set.getNode(0), 0);
		System.out.println("resize(u,0) should not change height of A: " + Arrays.toString(set.getHeights())); // expect
																												// [32,1]
		System.out.println(set); // expect L1: |-A
									// L0: |-A

		set.resize(set.getNode(0), 2);
		System.out.println("resize(u,2) should not change height of A: " + Arrays.toString(set.getHeights())); // expect
																												// [32,1]
		System.out.println(set); // expect L1: |-A
									// L0: |-A

		set.resize(set.getNode(0), 3);
		System.out.println("resize(u,2) should not change height of A: " + Arrays.toString(set.getHeights())); // expect
																												// [32,1]
		System.out.println(set); // expect L1: |-A
									// L0: |-A
	}

	private static void testAdd(int n) {
		System.out.println("\n\nTesting Add...");
		System.out.println("\n\nThese are limited local tests. Please test more thoroughly.");

		GeneSet set = new GeneSet();
		int a = (int) 'A';

		System.out.println("\nTesting add in reverse sorted order, should not add any shortcuts");
		for (int i = n - 1; i >= 0; i--) {
			System.out.println("\tadd(" + (char) (a + 2 * i) + ") returns " + set.add("" + (char) (a + 2 * i))
					+ ", size= " + set.size());
			System.out.println(set);
		}

		System.out.println("============================\n============================\n============================"
				+ "\n==BUNCH OF SHORT CUTS HERE==\n"
				+ "============================\n============================\n============================");
//		set.add("" + (char) (a + 2 * n)); // adds in a bunch of shortcuts
		System.out.println(set);
		set.clear();
		System.out.println("============================\n============================\n============================"
				+ "\n==manual TESTInG==\n"
				+ "============================\n============================\n============================");
		set.add("A");
		System.out.println(set);
//		set.add("B");
//		System.out.println(set);
		set.add("C");
		System.out.println(set);
//		set.add("D");
//		System.out.println(set);
		set.add("E");
		System.out.println(set);
		set.add("D");
		System.out.println(set);
//		set.add("F");
//		System.out.println(set);
		set.add("G");
		System.out.println(set);
//		set.add("B");
//		System.out.println(set);
//		set.add("F");
//		System.out.println(set);

//		set.add("H");
//		System.out.println(set);
		set.add("I");
		System.out.println(set);
		set.add("F");
		System.out.println(set);
		set.add("J");
		System.out.println(set);
		set.add("K");
		System.out.println(set);
		set.add("L");
		System.out.println(set);

		set.resize(set.getNode(0), 5);

		System.out.println(set);
		set.clear();

		System.out.println("\nTesting add of " + n + " elements, in sorted order");
		System.out.println(set);
		for (int i = 0; i < n; i++) {
			System.out.println("\tadd(" + (char) (a + 2 * i) + ") returns " + set.add("" + (char) (a + 2 * i))
					+ ", size= " + set.size());
			System.out.println(set);
		}

		System.out.println("\nTesting add of duplicate elements, should return false and not change set or size");
		for (int i = 0; i < n; i++) {
			System.out.println("\tadd(" + (char) (a + 2 * i) + ") returns " + set.add("" + (char) (a + 2 * i))
					+ ", size= " + set.size());
			System.out.println(set);
		}
	}

	private static void testFind(int n) {
		System.out.println("\n\nTesting Find...");
		System.out.println("\n\nThese are limited local tests. Please test more thoroughly.");

		GeneSet set = new GeneSet();
		int a = (int) 'A';

		for (int i = n - 1; i >= 0; i--) {
			set.add("" + (char) (a + 2 * i));
		}
		System.out.println(set);

		System.out.println("\tFinding first element shouldn't add shortcuts and should return the first element: "
				+ set.find("" + (char) (a)));
		System.out.println(set);
		System.out.println(
				"\tFinding element between first and second shouldn't add shortcuts and should return the second element: "
						+ set.find("" + (char) (a + 1)));
		System.out.println(set);
		System.out.println("\tFinding second element should add one shortcut and should return the seconds element: "
				+ set.find("" + (char) (a + 2)));
		System.out.println(set);

		System.out.println(
				"\tFinding an element > everything in the set should return null and add in lots of level-1 shortcuts: "
						+ set.find("" + (char) (a + 2 * n))); // adds in a bunch of shortcuts
		System.out.println(set);

		System.out.println(
				"\tFinding an element > everything in the set *again* should return null and add in level-2 shortcuts: "
						+ set.find("" + (char) (a + 2 * n))); // adds in a bunch of shortcuts
		System.out.println(set);

		set.find("" + (char) (a + 2 * n));
		System.out.println(set);
	}

	private static void testRemove(int n) {
		System.out.println("\n\nTesting Remove...");
		System.out.println("\n\nThese are limited local tests. Please test more thoroughly.");

		GeneSet set = new GeneSet();
		int a = (int) 'A';

		for (int i = n - 1; i >= 0; i--) {
			set.add("" + (char) (a + 2 * i));
		}
		System.out.println(set);

		System.out
				.println("\tRemoving element between first and second shouldn't add shortcuts and should return false: "
						+ set.remove("" + (char) (a + 1)));
		System.out.println(set);

		System.out.println("\tRemoving first element shouldn't add shortcuts and should return true: "
				+ set.remove("" + (char) (a)));
		System.out.println(set);

		System.out.println("\tRemoving third element (remaining) should add one shortcut and should return true: "
				+ set.remove("" + (char) (a + 6)));
		System.out.println(set);

		System.out.println(
				"\tRemoving an element > everything in the set should return false and add in level-1 shortcuts: "
						+ set.remove("" + (char) (a + 2 * n))); // adds in a bunch of shortcuts
		System.out.println(set);

		System.out.println(
				"\tRemoving an element > everything in the set *again* should return false and add in level-2 shortcuts: "
						+ set.remove("" + (char) (a + 2 * n))); // adds in a bunch of shortcuts
		System.out.println(set);

		set.clear();
		// Now let's remove elements that have shortcuts
		for (int i = 0; i < n; i++) {
			set.add("" + (char) (a + 2 * i));
		}
		System.out.println(set);
		System.out.println("\tRemoving middle-ish element should remove it and not mess up find: "
				+ set.remove("" + (char) (a + 2 * (n / 2))));
		System.out.println(set);

		for (int i = 0; i < n; i++) {
			System.out.println("\tFind(" + (char) (a + 2 * i) + ") should return correct element: "
					+ set.find("" + (char) (a + 2 * i)));
			System.out.println(set);
		}

		for (int i = 0; i < n; i++) {
			System.out.println("\tRemove(" + (char) (a + 2 * i) + ") returns: " + set.remove("" + (char) (a + 2 * i)));
			System.out.println(set);
		}
	}

	public static void otherTests() {
		// Other tests, with combinations of add and remove
		GeneSet set = new GeneSet();
		set.add("C");
		set.add("E");
		set.add("I");
		set.add("K");
		set.add("L");
		set.add("L");
		System.out.println(set);
		set.remove("L");
		set.remove("E");
		set.add("E");
		System.out.println(set);
		set.add("K");
		System.out.println(set);

		set.clear();
		set.add("C");
		set.add("E");
		set.add("I");
		set.add("K");
		set.add("L");
		set.add("L");
		System.out.println(set);
		set.remove("L");
		set.remove("E");
		set.add("E");
		System.out.println(set);
		set.add("L");
		System.out.println(set);

		set.add("C");
		set.add("E");
		set.add("I");
		set.add("K");
		set.add("L");
		set.add("L");
		System.out.println(set);
		set.remove("L");
		set.remove("E");
		set.add("E");
		System.out.println(set);
		set.remove("K");
		System.out.println(set);
	}

	private static void testEquals(int n) {
		System.out.println("\n\nTesting Equals...");
		System.out.println("\n\nThese are limited local tests. Please test more thoroughly.");

		GeneSet set1 = new GeneSet();
		GeneSet set2 = new GeneSet();
		int a = (int) 'A';

		System.out.println("set vs. null should be false: " + set1.equals(null));

		System.out.println("set vs. other type should be false: " + set1.equals("bogus-string-type-here"));

		System.out.println("empty sets should be equal: " + set1.equals(set2));

		// Add elements in reverse order to set1 and set 2
		for (int i = n - 1; i >= 0; i--) {
			set1.add("" + (char) (a + 2 * i));
			System.out.println("sets should be not equal after adding " + (char) (a + 2 * i) + " to set1 but not set2: "
					+ set1.equals(set2) + ", " + set2.equals(set1));
			System.out.println("set should always be equal to itself " + set1.equals(set1));
			set2.add("" + (char) (a + 2 * i));
		}
		System.out.println("set1 and set2 should be equal: " + set1.equals(set2));
		System.out.println("regardless of order: " + set2.equals(set1));

		// Now let's clear set2 and add elements in sorted order
		set2.clear();
		for (int i = 0; i < n; i++) {
			set2.add("" + (char) (a + 2 * i));
		}
		System.out.println("set1 and set2 should NOT be equal: " + set1.equals(set2));

		// Now let's remove elements from set1 and set2 and check equality as we go
		for (int i = 0; i < n; i++) {
			set1.remove("" + (char) (a + 2 * i));
			System.out.println("sets should be not equal after removing " + (char) (a + 2 * i)
					+ " from set1 but not set2: " + set1.equals(set2) + ", " + set2.equals(set1));
			set2.remove("" + (char) (a + 2 * i));
		}

		System.out.println("set1 and set2 should be equal: " + set1.equals(set2));
	}

//	// These are a few examples of how to use the InputGenerator to run local tests
//	// You should test more extensively than this.
	public static void main(String[] args) {
		System.out.println("Testing GeneSet via GeneSet.main...");
		System.out.println("These are limited local tests. Please test more thoroughly.");
		System.out.println("You should also try testing via tests/GeneSetTest.java");

		int n = 12;
		// comment any of these out if you're trying to focus on testing one particular
		// problem.
//		testResize();
//		testAdd(n);
		testFind(n);
//		testRemove(n);
//		otherTests();
//		testEquals(n);

	}
}
