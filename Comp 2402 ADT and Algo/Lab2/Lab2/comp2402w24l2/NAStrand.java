package comp2402w24l2;

// From within the Lab directory (or wherever you put the comp2402w24l2 directory):
// javac comp2402w24l2/NAStrand.java
// java comp2402w24l2/NAStrand
// This is an abstract class, so you cannot instantiate it directly.
// For Lab 2 you are provided with implementations of 2 subclasses
// RNAStrand and DNAStrand.
// So you can compile and run this program right off the bat, although NAStrand
// doesn't do anything useful yet.

// You will submit this file to the autograder.
// It will not pass many of the tests, if any.
// Your task is to modify the class to pass the tests.

// Do not add any other imports.
// (You shouldn't need any, and you'll fail the autograder if you do.)

import java.util.Iterator;
import java.util.NoSuchElementException;

public abstract class NAStrand {

	/**
	 * Represents a single node in our NAStrand. Do not remove this.
	 */
	class Node {
		String s;
		Node prev, next;
	}

	/**
	 * Array used to store allowable bases. Do not remove this.
	 */
	protected char[] bases;

	/**
	 * The dummy node. We use the convention that dummy.next = first and dummy.prev
	 * = last. Do not remove this.
	 */
	protected Node dummy;

	/**
	 * Number of bases in the strand. Do not remove this.
	 */
	protected int n;

	/**
	 * Returns whether or not the pair of characters x and y is a valid base pair
	 * for the particular subclass.
	 * 
	 * @param s
	 * @param y
	 */
	public abstract boolean isPair(char x, char y);

	/**
	 * Returns a string representation of this strand. Override this with more
	 * useful behaviour for debugging, if you wish.
	 */
	public String toString() {
		if (dummy == null)
			return "null";
		String s = "[";
		Node p = dummy.next; // first node
		while (p != dummy) {
			s += p.s;
			p = p.next;
			if (p != dummy) {
				s += ", ";
			}
		}
		s += "]";
		return s;
	}

	/**
	 * Returns the allowable bases.
	 */
	public char[] getBases() {
		return bases;
	}

	/**
	 * Returns whether or not character x is a valid base for this NAStrand
	 *
	 * @param s
	 */
	public boolean isBase(char x) {
		for (int i = 0; i < bases.length; i++) {
			if (x == bases[i]) {
				return true;
			}
		}
		return false;
	}

	// This just makes our testing lives easier; we already had
	// lots of tests that added characters to the strand, so we
	// can still use those, we just have to convert from char to String
	public void add(char x) throws IllegalArgumentException {
		add("" + x);
	}

	public void add(int i, char x) throws IndexOutOfBoundsException, IllegalArgumentException {
		add(i, "" + x);
	}

	/**
	 * This represents a (node u, index j) location of a base. For example, base i
	 * may be at the j'th index of the String in node u. You don't have to use this
	 * inner class, but I found it helpful to be able to store a location in a
	 * single object.
	 */
	protected class Location {
		Node u;
		int j;

		public Location(Node u, int j) {
			this.u = u;
			this.j = j;
		}
	}

	// DO NOT MODIFY THIS ITERATOR (or you may break the autograder)
	public Iterator<String> iterator() {
		return new StringIterator(this);
	}

	class StringIterator implements Iterator<String> {
		/**
		 * The list we are iterating over
		 */
		NAStrand l;

		/**
		 * The node whose value is returned by next()
		 */
		Node p;

		StringIterator(NAStrand q) {
			l = q;
			p = dummy.next;
		}

		public boolean hasNext() {
			return p != dummy;
		}

		public String next() {
			String t = p.s;
			p = p.next;
			return t;
		}

		public void remove() {
			throw new UnsupportedOperationException();
		}
	}

	// DO NOT MODIFY THE ITERATOR CODE ABOVE (or you may break the autograder)

	// You should not need to modify anything above this line.

	/**
	 * Constructor
	 * 
	 * @param bases is an array of characters that are allowable for this strand
	 * @return
	 */
	// help with setting nodes
	private void setNode(Node node, String s) {
		node.s = s;
		node.prev = this.dummy.prev;
		node.next = this.dummy;
		this.dummy.prev.next = node;
		this.dummy.prev = node;
	}

	public NAStrand(char[] bases) {
		this.bases = bases;
		this.dummy = new Node();
		this.dummy.prev = this.dummy.next = this.dummy; // point to itself
		this.n = 0; // no bases initially
	}

	/**
	 * Constructor
	 * 
	 * @param bases is an array of characters that are allowable for this strand
	 * @param s     is a string of characters that will be added to the strand into
	 *              a single (non-dummy) node
	 */
	public NAStrand(char[] bases, String s) {
		this(bases); // call constructor above to init

		// add first non-dummy node with string s
		Node newNode = new Node();
		setNode(newNode, s);
		this.n = s.length(); // update len
	}

	/**
	 * Adds the base s to the end of the strand as a single node. Throws an
	 * IllegalArgumentException if any of the first four bases of s are not valid.
	 * 
	 * @param s
	 */
	public void add(String s) throws IllegalArgumentException {
		if (s == null || s.isEmpty()) {
			throw new IllegalArgumentException("String s cannot be null or empty");
		}
		for (int i = 0; i < Math.min(s.length(), 4); i++) {
			if (!isBase(s.charAt(i))) {
				throw new IllegalArgumentException("Invalid base found in string s");
			}
		}
		Node newNode = new Node();
		setNode(newNode, s);

		this.n += s.length(); // update number bases
	}

	/**
	 * Gets the base at position i. Throw an IndexOutOfBoundsException exception if
	 * i is out of bounds. Throw a NoSuchElementException if the base at position i
	 * is invalid.
	 * 
	 * @param i
	 * @return
	 */
	public char get(int i) throws IndexOutOfBoundsException, NoSuchElementException {
		if (i < 0 || i >= this.n) {
			throw new IndexOutOfBoundsException("Index i is out of bounds");
		}
		// Node current = this.dummy.next;
		// int eIn = 0; // element index
		// while (current != this.dummy) {
		// int endIn = eIn + current.s.length(); // end element index after current node
		// // ie, if [AAA, AAAA], endIn would be 3, then 7
		// if (i < endIn) {
		// char base = current.s.charAt(i - eIn); // using example and i = 5, 5 < 7, 5 -
		// 3(eIn) = 2
		// if (!isBase(base)) {
		// throw new NoSuchElementException("Invalid base at index " + i);
		// }
		// return base;
		// }
		// eIn = endIn; // set element index to after node is done (ie eIn of beginning
		// e of next node)
		// current = current.next;
		// }
		// throw new IndexOutOfBoundsException("Index i is out of bounds");

		boolean startP = i < this.n / 2;
		Node current = new Node();
		int eIn;
		// sart from 0 if from begin, or 'n' if from end
		if (startP) {
			current = this.dummy.next;
			eIn = 0;
		} else {
			current = this.dummy.prev;
			eIn = this.n;
		}

		while (current != this.dummy) {
			int endIn = startP ? eIn + current.s.length() : eIn - current.s.length();

			if ((startP && i < endIn) || (!startP && i >= endIn)) {
				// decide whether to subtract element index or ending index
				// using example in commented section, and i = 5, 5>=3, 5 - 3(endIn) = 2
				// subtract endIn instead of eIn when going backwards since endIn when going
				// back is first letter of node
				char base = startP ? current.s.charAt(i - eIn) : current.s.charAt(i - endIn);
				if (!isBase(base)) {
					throw new NoSuchElementException("Invalid base at index " + i);
				}
				return base;
			}

			eIn = endIn; // set element index to after node is done (ie eIn of beginning b of next node)
			current = startP ? current.next : current.prev;
		}

		throw new IndexOutOfBoundsException("Index i is out of bounds");
	}

	/**
	 * Returns the number of bases in the strand.
	 */
	public int size() {
		return this.n;
	}

	/**
	 * Clears the NAStrand so that it has no bases.
	 */
	public void clear() {
		this.dummy.next = this.dummy.prev = this.dummy;
		this.n = 0;
	}

	/**
	 * Sets the base at position i to x, returns the base that was previously at i.
	 * Throws an IndexOutOfBoundsException if i is out of bounds. Throws an
	 * IllegalArgumentException if the base x is not valid. Throws a
	 * NoSuchElementException if the base originally at position i is invalid.
	 * 
	 * @param i
	 * @param x
	 * @return
	 */
	public char set(int i, char x) throws IndexOutOfBoundsException, IllegalArgumentException, NoSuchElementException {
		if (i < 0 || i >= this.n) {
			throw new IndexOutOfBoundsException("Index i is out of bounds");
		}
		if (!isBase(x)) {
			throw new IllegalArgumentException("The base x is not valid");
		}

		// // uses same logic to iterate through list as get()
		// Node current = this.dummy.next;
		// int ein = 0;
		// while (current != this.dummy) {
		// int endIn = ein + current.s.length();
		// if (i < endIn) {
		// char oBase = current.s.charAt(i - ein);
		// if (!isBase(oBase)) {
		// throw new NoSuchElementException("Trying to overwrite an invalid element at "
		// + i);
		// }
		// // extra step compared to get to replace base at i with x
		// StringBuilder sb = new StringBuilder(current.s);
		// sb.setCharAt(i - ein, x);
		// current.s = sb.toString();
		// return oBase;
		// }
		// ein = endIn;
		// current = current.next;
		// }
		// throw new IndexOutOfBoundsException("Index i is out of bounds");

		boolean startP = i < this.n / 2;
		Node current = new Node();
		int eIn;
		// sart from 0 if from begin, or 'n' if from end
		if (startP) {
			current = this.dummy.next;
			eIn = 0;
		} else {
			current = this.dummy.prev;
			eIn = this.n;
		}

		// same logic as get, except need to set char
		while (current != this.dummy) {
			int endIn = startP ? eIn + current.s.length() : eIn - current.s.length();

			if ((startP && i < endIn) || (!startP && i >= endIn)) {
				int bIndex = startP ? i - eIn : i - endIn;
				char oBase = current.s.charAt(bIndex);
				if (!isBase(oBase)) {
					throw new NoSuchElementException("Trying to overwrite an invalid element at " + i);
				}

				StringBuilder sb = new StringBuilder(current.s);
				sb.setCharAt(bIndex, x);
				current.s = sb.toString();
				return oBase;
			}

			eIn = endIn;
			current = startP ? current.next : current.prev;
		}
		throw new IndexOutOfBoundsException("Index i is out of bounds");
	}

	private void addNodeBeforeA(Node newN, Node a, String s) {
		newN.s = s;
		newN.prev = a.prev;
		newN.next = a;
		newN.next.prev = newN;
		newN.prev.next = newN;
	}

	/**
	 * Adds the bases of s as a new node in the strand at index i. Throws an
	 * IndexOutOfBoundsException if i is out of bounds. Throws an
	 * IllegalArgumentException if any of the first 4 bases of s are not valid.
	 * 
	 * @param i
	 * @param s
	 */
	public void add(int i, String s) throws IndexOutOfBoundsException, IllegalArgumentException {
		if (i < 0 || i > this.n) {
			throw new IndexOutOfBoundsException("Index i is out of bounds");
		}
		if (s == null || s.isEmpty()) {
			throw new IllegalArgumentException("String s cannot be null or empty");
		}
		for (int j = 0; j < Math.min(s.length(), 4); j++) {
			if (!isBase(s.charAt(j))) {
				throw new IllegalArgumentException("Invalid base found in string s");
			}
		}

		// find pos to insert new node, using slightly altered logic from get and set
		// Node current = this.dummy.next;
		// int eIn = 0;
		// while (current != this.dummy && eIn + current.s.length() <= i) {
		// eIn += current.s.length();
		// current = current.next;
		// }

		if (i == 0) { // if add at start of nodes
			// direclt add new node
			Node newNode = new Node();
			addNodeBeforeA(newNode, this.dummy.next, s);
		} else if (i == n) { // if add at end of nodes
			// direclt add new node
			Node newNode = new Node();
			addNodeBeforeA(newNode, this.dummy, s);
		} else { // add at middle
			boolean startP = i < this.n / 2;
			Node current = new Node();
			int eIn;
			// sart from 0 if from begin, or 'n' if from end
			if (startP) {
				current = this.dummy.next;
				eIn = 0;
			} else {
				current = this.dummy.prev;
				eIn = this.n;
			}

			while (current != this.dummy
					&& ((startP && eIn + current.s.length() <= i) || (!startP && eIn - current.s.length() >= i))) {
				eIn = startP ? eIn + current.s.length() : eIn - current.s.length();
				current = startP ? current.next : current.prev;
			}

			if (startP && eIn == i) {
				// direclt add new node after the current node
				// since this means its eIn = i;
				// if eIn = i = start of node, add before current node
				Node newNode = new Node();
				addNodeBeforeA(newNode, current, s);

			} else if (!startP && eIn == i) {
				// if eIn = i = end of node, add after current node

				Node newNode = new Node();
				addNodeBeforeA(newNode, current.next, s);

			} else {
				String oldS = current.s;
				String part1;
				String part2;
				if (eIn < i) { // aka startP == true
					part1 = oldS.substring(0, i - eIn);
					part2 = oldS.substring(i - eIn);
				} else { // startP == false
					part1 = oldS.substring(0, i - (eIn - oldS.length()));
					part2 = oldS.substring(i - (eIn - oldS.length()));
				}
				current.s = part2; // update current node with first part

				Node newNode = new Node(); // new node for s
				addNodeBeforeA(newNode, current, s);

				Node p1Node = new Node(); // new node for second part
				addNodeBeforeA(p1Node, newNode, part1);
			}

		}
		this.n += s.length();
		// System.out.println(this);

	}

	/**
	 * Removes the base at position i. Throws an IndexOutOfBoundsException if i is
	 * out of bounds. Throws a NoSuchElementException if the base originally at
	 * position i is invalid.
	 * 
	 * @param i
	 * @return
	 */
	public char remove(int i) throws IndexOutOfBoundsException, NoSuchElementException {
		if (i < 0 || i >= this.n) {
			throw new IndexOutOfBoundsException("Index i is out of bounds");
		}
		// Node current = this.dummy.next;
		// int eIn = 0; // element index
		// while (current != this.dummy) {
		// int endIn = eIn + current.s.length(); // end element index after current node
		// // ie, if [AAA, AAAA], endIn would be 3, then 7
		// if (i < endIn) {
		// char base = current.s.charAt(i - eIn); // using example and i = 5, 5 < 7, 5 -
		// 3(eIn) = 2
		// if (!isBase(base)) {
		// throw new NoSuchElementException("Invalid base at index " + i);
		// }
		// return base;
		// }
		// eIn = endIn; // set element index to after node is done (ie eIn of beginning
		// e of next node)
		// current = current.next;
		// }
		// throw new IndexOutOfBoundsException("Index i is out of bounds");
		// use same logic as get and set
		// except instead of changing base at i we delete it
		// also need check for if node is empty after remove
		boolean startP = i < this.n / 2;
		Node current = new Node();
		int eIn;
		// sart from 0 if from begin, or 'n' if from end
		if (startP) {
			current = this.dummy.next;
			eIn = 0;
		} else {
			current = this.dummy.prev;
			eIn = this.n;
		}
		while (current != this.dummy) {
			int endIn = startP ? eIn + current.s.length() : eIn - current.s.length();
			if ((startP && i < endIn) || (!startP && i >= endIn)) {
				int bIndex = startP ? i - eIn : i - endIn;
				char removedBase = current.s.charAt(bIndex);
				// check if base is valid
				if (!isBase(removedBase)) {
					throw new NoSuchElementException("Attempting to remove an invalid base");
				}

				// if valid, remove
				StringBuilder sb = new StringBuilder(current.s);
				sb.deleteCharAt(bIndex);
				current.s = sb.toString();
				// if node empty, delete node
				if (current.s.isEmpty()) {
					current.prev.next = current.next;
					current.next.prev = current.prev;
					// should never be necessary but i find it funny to do
					current.next = null;
					current.prev = null;
				}
				this.n -= 1;
				return removedBase;
			}
			eIn = endIn;
			current = startP ? current.next : current.prev;
		}

		throw new IndexOutOfBoundsException("Index i is out of bounds");
	}

	/**
	 * Splices a String s in a new node right after each instance of pattern p found
	 * in the strand. Throws an IllegalArgumentException if the string p or string s
	 * is empty. Throws an IllegalArgumentException if any of the first four bases
	 * of s are not valid.
	 * 
	 * @param p
	 * @param s
	 */

	public void specialSplice(String p, String s) throws IllegalArgumentException {
		if (p == null || p.isEmpty() || s == null || s.isEmpty()) {
			throw new IllegalArgumentException("Patterns p and s cannot be null or empty.");
		}

		for (int j = 0; j < Math.min(s.length(), 4); j++) {
			if (!isBase(s.charAt(j))) {
				throw new IllegalArgumentException("Invalid base found in string s.");
			}
		}

		// once again uses same logic from get, set, except slightly altered
		Node current = this.dummy.next;
		while (current != this.dummy) {
			if (current.s.contains(p)) {
				// i love edge cases! if curr.s is not same len as p,
				if (current.s.length() != p.length()) {
					// check if at front
					if (current.s.indexOf(p) == 0) {
						String part2 = current.s.substring(current.s.indexOf(p) + p.length());

						current.s = s;

						Node newNode = new Node();
						addNodeBeforeA(newNode, current.next, part2);

					} else if (current.s.indexOf(p) + p.length() == current.s.length()) { // chjec if at back
						String part1 = current.s.substring(0, current.s.indexOf(p));

						current.s = s;

						Node newNode = new Node();
						addNodeBeforeA(newNode, current, part1);
					} else { // otherwise we need to split string
						// its the same logic as add(int, string), modified slightly
						String part1 = current.s.substring(0, current.s.indexOf(p));
						String part2 = current.s.substring(current.s.indexOf(p) + p.length());

						current.s = s;

						Node np1 = new Node();
						addNodeBeforeA(np1, current, part1);

						Node np2 = new Node();
						addNodeBeforeA(np2, current.next, part2);
					}
				} else { // this means p and cur.s is the exact same
					current.s = s;
				}

				// System.out.println(this);
				this.n += s.length() - p.length();
				current = current.next; // go next
			} else {
				current = current.next; // go next except p wasn't in current node
				// System.out.println(this);
			}
		}
	}

	/**
	 * Reverses the order of the bases in the strand from index i to index k-1
	 * (inclusive) Throws an IndexOutOfBoundsException if either i or k is out of
	 * bounds Throws an IndexOutOfBoundsException if i >= k
	 * 
	 * @param i
	 * @param k
	 */

	public void reverse(int i, int k) throws IndexOutOfBoundsException {
		if (i < 0 || k > this.n || i >= k) {
			throw new IndexOutOfBoundsException("Index i or k is invalid");
		}

		// // the logic is based on get but its altered quite a bit
		// // track the start and end of the reversal segment
		// Node startNode = null;
		// Node endNode = null;
		// int eIn = 0; // start index of the current node's string in the overall
		// sequence

		// // find start and end node and their index
		// Node current = dummy.next;
		// int nIndex = 0;
		// while (current != dummy) {
		// int currentLength = current.s.length();
		// if (nIndex + currentLength > i && startNode == null) {
		// startNode = current;
		// eIn = nIndex;
		// }
		// if (nIndex + currentLength >= k) {
		// endNode = current;
		// break;
		// }
		// nIndex += currentLength;
		// current = current.next;
		// }
		// // extract the substring from i to k, reverse it,
		// // then split it back among nodes
		// StringBuilder sb = new StringBuilder();
		// current = startNode;
		// while (current != endNode.next) {
		// sb.append(current.s);
		// current = current.next;
		// }
		// String toReverse = sb.toString().substring(i - eIn, k - eIn);
		// StringBuilder reversed = new StringBuilder(toReverse).reverse();

		// // replace nodes

		// // edge case for when its same node
		// if (startNode == endNode) {
		// String newData = startNode.s.substring(0, i - eIn) + reversed.toString() +
		// startNode.s.substring(k - eIn);
		// startNode.s = newData;
		// } else {// i and k span across multiple nodes
		// // replace start node data
		// String startNodeData = startNode.s.substring(0, i - eIn)
		// + reversed.substring(0, startNode.s.length() - (i - eIn));
		// startNode.s = startNodeData;
		// reversed.delete(0, startNode.s.length() - (i - eIn));

		// // replace end node data
		// String endNodeData = reversed.substring(reversed.length() - (k - eIn -
		// sb.length() + endNode.s.length()))
		// + endNode.s.substring(k - eIn - sb.length() + endNode.s.length());
		// endNode.s = endNodeData;
		// reversed.delete(reversed.length() - (k - eIn - sb.length() +
		// endNode.s.length()), reversed.length());

		// // replace data in other nodes between
		// current = startNode.next;
		// while (current != endNode) {
		// String currentData = reversed.substring(0, current.s.length());
		// current.s = currentData;
		// reversed.delete(0, current.s.length());
		// current = current.next;
		// }
		// }
		Node startNode = null;
		Node endNode = null;
		int eIn = 0;

		Node current = dummy.next;
		int nIndex = 0;
		while (current != dummy) {
			int currentLength = current.s.length();
			if (nIndex + currentLength > i && startNode == null) {
				startNode = current;
				eIn = nIndex;
			}
			if (nIndex + currentLength >= k) {
				endNode = current;
				break;
			}
			nIndex += currentLength;
			current = current.next;
		}

		StringBuilder sb = new StringBuilder();
		current = startNode;
		while (current != endNode.next) {
			sb.append(current.s);
			current = current.next;
		}

		String toReverse = sb.toString().substring(i - eIn, k - eIn);
		StringBuilder reversed = new StringBuilder(toReverse).reverse();

		if (startNode == endNode) {
			StringBuilder newData = new StringBuilder(startNode.s.substring(0, i - eIn));
			newData.append(reversed.toString());
			newData.append(startNode.s.substring(k - eIn));
			startNode.s = newData.toString();
		} else {
			StringBuilder startNodeData = new StringBuilder(startNode.s.substring(0, i - eIn));
			startNodeData.append(reversed.substring(0, startNode.s.length() - (i - eIn)));
			startNode.s = startNodeData.toString();
			reversed.delete(0, startNode.s.length() - (i - eIn));

			StringBuilder endNodeData = new StringBuilder(
					reversed.substring(reversed.length() - (k - eIn - sb.length() + endNode.s.length())));
			endNodeData.append(endNode.s.substring(k - eIn - sb.length() + endNode.s.length()));
			endNode.s = endNodeData.toString();
			reversed.delete(reversed.length() - (k - eIn - sb.length() + endNode.s.length()), reversed.length());

			current = startNode.next;
			while (current != endNode) {
				StringBuilder currentData = new StringBuilder(reversed.substring(0, current.s.length()));
				current.s = currentData.toString();
				reversed.delete(0, current.s.length());
				current = current.next;
			}
		}
	}

	// // These are a few examples of how to use main to run local tests
	// // You should test more extensively than this.
	// public static void main(String[] args) {
	// 	// lab1Tests(); // Uncomment this to run your lab1 tests, minus spliceIn
	// 	// lab2Tests(); // Limited local tests for lab 2
	// }

	// public static void lab2Tests() {
	// 	System.out.println("Testing NASTrand() via NAStrand.main...");
	// 	System.out.println("You should also try testing via tests/NAStrandTest.java");

	// 	NAStrand r;

	// 	System.out.println("\nLab 2: testing String constructor...");
	// 	r = new RNAStrand("ACGUACGU");
	// 	System.out.println(r); // Expect [ACGUACGU]

	// 	System.out.println("\nLab 2: testing empty constructor...");
	// 	r = new RNAStrand();
	// 	System.out.println(r); // Expect []

	// 	// == add ==
	// 	System.out.println("\nLab 2: testing add(s)...");
	// 	r.add(0, "AA");
	// 	r.add("UU");
	// 	System.out.println(r); // Expect [AA, UU]
	// 	r.add("GG");
	// 	System.out.println(r); // Expect [AA, UU, GG]

	// 	System.out.println("\nLab 2: testing the use of Iterator<String>...");
	// 	Iterator<String> it = r.iterator();
	// 	while (it.hasNext()) { // not perfect, you get the point
	// 		System.out.print(it.next());
	// 		System.out.print(", ");
	// 	}
	// 	System.out.println();

	// 	// get
	// 	System.out.println("\nLab 2: testing get(i)...");
	// 	System.out.print(r.get(0));
	// 	System.out.print(r.get(1));
	// 	System.out.print(r.get(2));
	// 	System.out.print(r.get(3));
	// 	System.out.print(r.get(4));
	// 	System.out.print(r.get(5));
	// 	System.out.println();
	// 	String s = "ACGU";
	// 	r = new RNAStrand(s);
	// 	for (int i = 0; i < s.length(); i++) {
	// 		if (r.get(i) != s.charAt(i))
	// 			System.out.println("failed get(" + i + ") test");
	// 	}

	// 	// size
	// 	System.out.println("\nLab 2: size tests...");
	// 	System.out.println(r.size()); // Expect 4
	// 	r = new RNAStrand();
	// 	System.out.println(r.size()); // Expect 0

	// 	// clear
	// 	System.out.println("\nLab 2: clear tests...");
	// 	r.clear();
	// 	System.out.println(r);
	// 	it = r.iterator();
	// 	System.out.println(it.hasNext()); // should be false after you clear

	// 	// set
	// 	System.out.println("\nLab 2: testing set(i,s)...");
	// 	r = new RNAStrand("AC");
	// 	r.add("GU");
	// 	System.out.println(r.set(0, 'U') + " should be A");
	// 	System.out.println(r.set(2, 'C') + " should be G");
	// 	System.out.println(r); // Expect [UC,CU]

	// 	// === add test
	// 	System.out.println("\nLab 2: testing add(i,s)...");
	// 	r.clear();
	// 	r.add(0, "UU"); // [UU]
	// 	r.add(0, "AA"); // [AA,UU]
	// 	System.out.println(r); // Expect [AA, UU]
	// 	r.add(4, "GG");
	// 	System.out.println(r); // Expect [AA, GG, UU]
	// 	r.add(1, "CC");
	// 	System.out.println(r); // Expect [A, CC, A, GG, UU]
	// 	r.add(7, "AA");
	// 	System.out.println(r); // Expect [A, CC, A, GG, U, AA, U]
	// 	r.add(10, "CC");
	// 	System.out.println(r); // Expect [A, CC, A, GG, U, AA, U, CC]
	// 	r = new RNAStrand("ACGUACGU");
	// 	System.out.println(r); // Expect [ACGUACGU]
	// 	r.add(0, "GU");
	// 	System.out.println(r); // Expect [GU, ACGUACGU]
	// 	r.add(1, "CA");
	// 	System.out.println(r); // Expect [G, CA, U, ACGUACGU]
	// 	r.add(8, "AU");
	// 	System.out.println(r); // Expect [G, CA, U, ACGU, AU, ACGU]
	// 	r.add(14, "AU");
	// 	System.out.println(r); // Expect [G, CA, U, ACGU, AU, ACGU, AU]

	// 	// === remove
	// 	System.out.println("\nRemove tests...");
	// 	System.out.println(r);
	// 	r.remove(5);
	// 	System.out.println(r);
	// 	while (r.size() > 0) {
	// 		System.out.print(r.remove(0));
	// 		// System.out.println( r );
	// 	}
	// 	System.out.println();

	// 	// === splice test
	// 	System.out.println("\nLab 2: specialSplice tests...");
	// 	// [ACGUACGU]
	// 	// [AC, AACCA, AC, AACCA]
	// 	// [AC, AA, CCA, AC, AA, CCA]

	// 	// [CG, AAAAB]
	// 	// [GGGG, AAAAB]
	// 	// [GGGG, A, UUUU]
	// 	// [GGGG, A, UU, UU, UU, UU]
	// 	r = new RNAStrand("ACGUACGU");
	// 	System.out.println(r);
	// 	r.specialSplice("GU", "AACCA");
	// 	System.out.println(r);
	// 	r.specialSplice("AA", "AA");
	// 	System.out.println(r);
	// 	System.out.println(r.size());
	// 	r.clear();

	// 	r.add("CGAA");
	// 	r.specialSplice("AA", "AAAAB");
	// 	System.out.println(r);
	// 	r.specialSplice("CG", "GGGG");
	// 	System.out.println(r);
	// 	r.specialSplice("AAAB", "UUUU");
	// 	System.out.println(r);
	// 	r.specialSplice("U", "UU");
	// 	System.out.println(r);
	// 	System.out.println(r.size());

	// 	// === reverse
	// 	System.out.println("\nLab 2: reverse tests...");
	// 	r.clear();

	// 	// First try reverses all contained within one node
	// 	r = new RNAStrand("ACGUACGU");
	// 	System.out.println(r);
	// 	for (int i = 0; i < r.size(); i++) {
	// 		r.reverse(i, r.size());
	// 		System.out.print(r + ", ");
	// 	}
	// 	System.out.println();

	// 	// Now reverse that spans over multiple nodes
	// 	r.clear();
	// 	r.add("CAG");
	// 	r.add("U");
	// 	r.add("G");
	// 	System.out.println(r); // [CAG,U,G]
	// 	r.reverse(0, 3);
	// 	System.out.println(r); // [GAC,U,G]
	// 	r.reverse(0, 4);
	// 	System.out.println(r); // [U,CAG,G]
	// 	r.reverse(1, 5);
	// 	System.out.println(r); // [U,G,GAC]
	// 	r.add("UA");
	// 	r.add("CC");
	// 	r.add("UU");
	// 	System.out.println(r); // [U,G,GAC,UA,CC,UU]
	// 	r.reverse(3, 9);
	// 	System.out.println(r); // [U,G,GCC,AU,CA,UU]
	// 	r.reverse(0, 10);
	// 	System.out.println(r); // [U,AC,UA,CCG,G,UU]

	// }

	// public static void lab1Tests() {
	// 	NAStrand r = new RNAStrand();
	// 	NAStrand d = new DNAStrand();

	// 	System.out.println(r);
	// 	System.out.println(d);

	// 	// isPair
	// 	System.out.println(r.isPair('A', 'U'));
	// 	System.out.println(d.isPair('A', 'U'));
	// 	System.out.println(r.isPair('A', 'T'));
	// 	System.out.println(d.isPair('A', 'T'));

	// 	// getBases
	// 	System.out.println(r.getBases());
	// 	System.out.println(d.getBases());

	// 	// isBase
	// 	System.out.println(r.isBase('A'));
	// 	System.out.println(d.isBase('A'));
	// 	System.out.println(r.isBase('T'));
	// 	System.out.println(d.isBase('T'));
	// 	System.out.println(r.isBase('X'));
	// 	System.out.println(d.isBase('X'));

	// 	// add
	// 	r.add(0, 'A');
	// 	r.add('U');
	// 	r.add('C');
	// 	r.add(3, 'G');
	// 	System.out.println(r);
	// 	d.add(0, 'A');
	// 	d.add('T');
	// 	d.add('C');
	// 	d.add(3, 'G');
	// 	System.out.println(d);

	// 	// get
	// 	System.out.println(r.get(0));
	// 	System.out.println(r.get(1));
	// 	System.out.println(r.get(2));
	// 	System.out.println(r.get(3));
	// 	System.out.println(d.get(0));
	// 	System.out.println(d.get(1));
	// 	System.out.println(d.get(2));
	// 	System.out.println(d.get(3));

	// 	// size
	// 	System.out.println(r.size());
	// 	System.out.println(d.size());

	// 	// clear
	// 	r.clear();
	// 	d.clear();
	// 	System.out.println(r);
	// 	System.out.println(d);

	// 	for (int i = 0; i < 10; i++) {
	// 		r.add(r.getBases()[i % 4]);
	// 		d.add(d.getBases()[i % 4]);
	// 	}
	// 	System.out.println(r);
	// 	System.out.println(d);

	// 	// set
	// 	System.out.println(r.set(0, 'U'));
	// 	System.out.println(r.set(1, 'G'));
	// 	System.out.println(d.set(0, 'T'));
	// 	System.out.println(d.set(1, 'G'));

	// 	// remove
	// 	System.out.println(r.remove(0));
	// 	System.out.println(r.remove(1));
	// 	System.out.println(d.remove(0));
	// 	System.out.println(d.remove(1));
	// 	System.out.println(r);
	// 	System.out.println(d);

	// 	// reverse
	// 	for (int i = 0; i < r.size(); i++) {
	// 		r.reverse(i, r.size());
	// 		System.out.println(r);
	// 	}

	// 	for (int i = 0; i < d.size(); i++) {
	// 		d.reverse(i, d.size());
	// 		System.out.println(d);
	// 	}

	// }

}
