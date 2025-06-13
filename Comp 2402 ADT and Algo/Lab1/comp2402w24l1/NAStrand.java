package comp2402w24l1;
// From within the Lab directory (or wherever you put the comp2402w24l1 directory):

// javac comp2402w24l1/NAStrand.java
// java comp2402w24l1/NAStrand
// This is an abstract class, so you cannot compile and run it directly.
// You have to compile and run one of its subclasses, e.g. RNAStrand, DNAStrand.
// Thus you should implement those first before trying to run this one.

// You will submit this file to the autograder.
// It will not pass many of the tests, if any.
// Your task is to modify the class to pass the tests.

// Do not add any imports.
// (You shouldn't need any, and you'll fail the autograder if you do.)

public abstract class NAStrand {

	/**
	 * Array used to store allowable bases. Do not remove this.
	 */
	protected char[] bases;

	/**
	 * Array used to store bases. Do not remove this.
	 */
	protected char[] a;

	/**
	 * Index of first base in our array a. Do not remove this.
	 */
	protected int j;

	/**
	 * Number of bases in the strand. Do not remove this.
	 */
	protected int n;

	/**
	 * Returns whether or not the pair of characters x and y is a valid base pair
	 * for the particular subclass.
	 * 
	 * @param x
	 * @param y
	 */
	public abstract boolean isPair(char x, char y);

	/**
	 * Returns a string representation of this strand. Override this with more
	 * useful behaviour for debugging, if you wish.
	 */
	public String toString() {
		String s = "[";
		for (int i = 0; i < n - 1; i++) {
			s += a[(j + i) % a.length];
			s += ", ";
		}
		if (n > 0) {
			s += a[(j + n - 1) % a.length];
		}
		s += "]";
		return s;
	}

	// You should not need to modify anything above this line.

	/**
	 * Constructor
	 * 
	 * @param bases is an array of characters that are allowable for this strand
	 */
	public NAStrand(char[] bases) {
		this.bases = bases;
		this.a = new char[1];
		this.n = 0;
		this.j = 0;
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
	 * @param x
	 */
	public boolean isBase(char x) {
		for (char base : bases) {
			if (x == base) {
				return true;
			}
		}
		return false;
	}

	// helper; resize array
	private void resizeArray() {
		char[] newArray = new char[Math.max(n * 2, 1)];
		System.arraycopy(a, 0, newArray, 0, n);
		a = newArray;
	}

	private void resizeArrayAdd0() {
		char[] newArray = new char[Math.max(n * 2, 1)];
		System.arraycopy(a, 0, newArray, 1, n);
		a = newArray;
	}

	private void resizeArrayD() {
		char[] newArray = new char[Math.max(n * 2, 1)];
		for (int k = 0; k < n; k++)
			newArray[k] = a[(j + k) % a.length];
		a = newArray;
		j = 0;
	}

	private void resizeArray(int newlen) {
		char[] newArray = new char[newlen];
//		System.arraycopy(a, 0, newArray, 0, n);
		for (int k = 0; k < n; k++)
			newArray[k] = a[(j + k) % a.length];
		a = newArray;
		j = 0;
	}

//	private void resizeArrayC() {
//		int newSize = a.length * 2;
//		char[] newArray = new char[newSize];
//		for (int k = 0; k < n; k++) {
//			newArray[k] = a[(j + k) % a.length];
//		}
//		a = newArray;
//		j = 0; // Reset j as the data is now starting from 0 in the new array
//	}

	/**
	 * Adds the base x to the end of the strand Throws an IllegalArgumentException
	 * if the base is not valid.
	 * 
	 * @param x
	 */
	public void add(char x) throws IllegalArgumentException {
		if (!isBase(x)) {
			throw new IllegalArgumentException("sighsgrin");
		}
		if (n == a.length) {
//			resizeArray();
			resizeArrayD();
		}
		a[n] = x;
		n += 1;
	}

	/**
	 * Adds the base x to the strand at index i. Throws an IndexOutOfBoundsException
	 * if i is out of bounds. Throws an IllegalArgumentException if the base is not
	 * valid.
	 * 
	 * @param i
	 * @param x
	 */

	public void add(int i, char x) throws IndexOutOfBoundsException, IllegalArgumentException {
		if (i < 0 || i > n) {
			throw new IndexOutOfBoundsException("dfgurhjg");
		}
		if (!isBase(x)) {
			throw new IllegalArgumentException("rfgopjirgoma");
		}
		if (n >= a.length) {
			resizeArrayD();
		}

		if (i < n / 2) {
			if (j == 0) {
				j = a.length - 1;
			} else {
				j--;
			}
			for (int k = 0; k <= i - 1; k++) {
				a[(j + k) % a.length] = a[(j + k + 1) % a.length];
			}

		} else {
			for (int k = n; k > i; k--) {
				a[(j + k) % a.length] = a[(j + k - 1) % a.length];
			}
		}
		a[(j + i) % a.length] = x;
		n++;

//		if (n == a.length) {
//			if (i == 0) {
//				resizeArrayAdd0();
//			} else if (i == n) {
//				resizeArray();
//			} else if (!(i == n)) {
//				resizeArray();
//				System.arraycopy(a, i, a, i + 1, n - i);
//			}
//			a[i] = x;
//			n += 1;
//
//		} else {
//			if (i < n) {
//				System.arraycopy(a, i, a, i + 1, n - i);
//			}
//
//			a[i] = x;
//			n += 1;
//		}

//		if (n == a.length) {
//		resizeArray();
//	}
//	System.arraycopy(a, i, a, i + 1, n - i);
//	a[i] = x;
//	n += 1;
	}

	/**
	 * Gets the base at position i. Throw an IndexOutOfBoundsException exception if
	 * i is out of bounds.
	 * 
	 * @param i
	 * @return
	 */
	public char get(int i) throws IndexOutOfBoundsException {
		if (i < 0 || i >= n) {
			throw new IndexOutOfBoundsException("hjkiofyj");
		}
//		return a[i];
		return a[(j + i) % a.length];
	}

	/**
	 * Returns the number of bases in the strand.
	 */
	public int size() {
		// TODO(student): Replace this with your code.
		return n;
	}

	/**
	 * Clears the NAStrand so that it has no bases.
	 */
	public void clear() {
		// TODO(student): Replace this with your code.
		this.a = new char[10];
		n = 0;
		j = 0;
	}

	/**
	 * Sets the base at position i to x. Throws an IndexOutOfBoundsException if i is
	 * out of bounds. Throws an IllegalArgumentException if the base is not valid.
	 * 
	 * @param i
	 * @param x
	 * @return
	 */
	public char set(int i, char x) throws IndexOutOfBoundsException, IllegalArgumentException {
		if (i < 0 || i >= n) {
			throw new IndexOutOfBoundsException("ergiuhrg");
		}
		if (!isBase(x)) {
			throw new IllegalArgumentException("dfghdfthrfs");
		}
//		char prevBase = a[i];
//		a[i] = x;
//		return prevBase;
		char prev = a[(j + i) % a.length];
		a[(j + i) % a.length] = x;
		return prev;
	}

	/**
	 * Removes the base at position i. Throws an IndexOutOfBoundsException if i is
	 * out of bounds.
	 * 
	 * @param i
	 * @return
	 */
	public char remove(int i) throws IndexOutOfBoundsException {
		if (i < 0 || i >= n) {
			throw new IndexOutOfBoundsException();
		}
//		char removed = a[i];
////		for (int k = i; k < n-1; k++) 
////            a[k] = a[k+1];
//		System.arraycopy(a, i + 1, a, i, n - i - 1);
//		if (a.length >= 3 * n) {
//			resizeArray();
//		}
//		n -= 1;
//		return removed;

//		if (i == 0 && n == 1) {
//			n -= 1;
//			return a[i];
//		} else if (i == n) {
//			n -= 1;
//			return a[i];
//		} else { // (i < n)
//			char removed = a[i];
//			System.arraycopy(a, i + 1, a, i, n - i - 1);
//			n -= 1;
//			return removed;
//		}

		char removed = a[(j + i) % a.length];
		a[(j + i) % a.length] = ' ';
		if (i < n / 2) {
			for (int k = i; k > 0; k--) {
				a[(j + k) % a.length] = a[(j + k - 1) % a.length];
			}
			j = (j + 1) % a.length;
		} else {
			for (int k = i; k < n - 1; k++) {
				a[(j + k) % a.length] = a[(j + k + 1) % a.length];
			}
		}
		n--;
		if (n * 3 < a.length) {
			resizeArrayD();
		}
		return removed;

	}

	/**
	 * Splices strand q into this strand at position i so that the strand of q
	 * starts at position i in this strand. Throws an IndexOutOfBoundsException if i
	 * is out of bounds. Throws an IllegalArgumentException if the subclass of
	 * NAStrands do not match.
	 * 
	 * @param i
	 * @param q
	 */
	public void spliceIn(int i, NAStrand q) throws IndexOutOfBoundsException, IllegalArgumentException {
		if (i < 0 || i > n) {
			throw new IndexOutOfBoundsException();
		}
		if (getClass() != q.getClass()) {
			throw new IllegalArgumentException();
		}
//		// old resize until fit
//		while (n + q.size() > a.length) {
//			resizeArray();
//		}

//		// works by forcing resize to handle circular
//		int nplusqlen = n + q.size();
////		if (nplusqlen > a.length) {
//		resizeArray(nplusqlen);
////		}
//
//		// shift add elements
//		System.arraycopy(a, i, a, i + q.size(), n - i);
//		System.arraycopy(q.a, 0, a, i, q.n);
//		n += q.size();
		if (q.size() != 0) {
			int qSize = q.size();
			if (n == 0) { // i love edge cases
				this.a = q.a;
			} else {

				if (n + qSize > a.length) {
					resizeArray(Math.max(a.length * 2, n + qSize));
				}

				// adjust index j because its dumb
				int realIndex = (j + i) % a.length;

				// splicing anywhere else other than the end needs shifting, i think trying to
				// shift leftwards is dead end
				if (i < n) {
					for (int k = n - 1; k >= i; k--) {
						a[(j + k + qSize) % a.length] = a[(j + k) % a.length];
					}
				}
				//rahhhhhhhhhh
//				if (i < n / 2) {
//					for (int k = 0; k <= i - 1; k++) {
//						a[(j + k + qSize) % a.length] = a[(j + k + 1) % a.length];
//					}
//				} else {
//					for (int k = n; k > i; k--) {
//						a[(j + k + qSize) % a.length] = a[(j + k - 1) % a.length];
//					}
//				}

				// copy q to array
				for (int k = 0; k < qSize; k++) {
					a[(realIndex + k) % a.length] = q.a[(q.j + k) % q.a.length];
				}
			}
			n += qSize;
		}
	}

	/**
	 * Reverses the order of the bases in the strand from index i to index k-1
	 * (inclusive) Throws an IndexOutOfBoundsException if either i or k is out of
	 * bounds Throws an IndexOutOfBoundsException if i >= k
	 * 
	 * @param i
	 * @param j
	 */
	public void reverse(int i, int k) throws IndexOutOfBoundsException {
		if (i < 0 || k - 1 >= n || i >= k) {
			throw new IndexOutOfBoundsException();
		}
		while (i < k) {
			char temp = a[i];
			k--; // k-1 inclusive, so subtract first ig
			a[i] = a[k];
			a[k] = temp;
			i++;
		}
	}

	// These are a few examples of how to use main to run local tests
	// You should test more extensively than this.
	public static void main(String[] args) {
		
		NAStrand r = new RNAStrand();
		NAStrand d = new DNAStrand();

		System.out.println(r);
		System.out.println(d);

		// isPair
		System.out.println(r.isPair('A', 'U'));
		System.out.println(d.isPair('A', 'U'));
		System.out.println(r.isPair('A', 'T'));
		System.out.println(d.isPair('A', 'T'));

		System.out.println(r.a[0]);
		// getBases
		System.out.println(r.getBases());
		System.out.println(d.getBases());

		// isBase
		System.out.println(r.isBase('A'));
		System.out.println(d.isBase('A'));
		System.out.println(r.isBase('T'));
		System.out.println(d.isBase('T'));
		System.out.println(r.isBase('X'));
		System.out.println(d.isBase('X'));

		// add
		System.out.println("\n-- testing add --");
		r.add(0, 'A');
		System.out.println(r);
		r.add(0, 'U');
		System.out.println(r);
		r.add(0, 'A');
		System.out.println(r);
		r.add(0, 'U');
		System.out.println(r);
		r.add(4, 'C');
		System.out.println(r);
		r.add(5, 'G');
		System.out.println(r);
		r.add(1, 'G');
		System.out.println(r);
		r.add(1, 'G');
		System.out.println(r);
		r.add(1, 'G');
		System.out.println(r);
		d.add(0, 'A');
		System.out.println(d);
		d.add('T');
		System.out.println(d);
		d.add('C');
		System.out.println(d);
		d.add(3, 'G');
		System.out.println(d);

		System.out.println("\n-- end testing add --\n\n");

		// get
		System.out.println(r.get(0));
		System.out.println(r.get(1));
		System.out.println(r.get(2));
		System.out.println(r.get(3));
		System.out.println(d.get(0));
		System.out.println(d.get(1));
		System.out.println(d.get(2));
		System.out.println(d.get(3));

		// size

		System.out.println(r.size());
		System.out.println(d.size());

		// clear
		r.clear();
		d.clear();
		System.out.println(r);
		System.out.println(d);

		for (int i = 0; i < 10; i++) {
			r.add(r.getBases()[i % 4]);
			d.add(d.getBases()[i % 4]);
		}
		System.out.println(r);
		System.out.println(d);

		// set
		System.out.println(r.set(0, 'U'));
		System.out.println(r.set(1, 'G'));
		System.out.println(d.set(0, 'T'));
		System.out.println(d.set(1, 'G'));

		// remove
		System.out.println("\n=== testing remove ===\n");
		System.out.println(r);
		System.out.println(r.remove(0));
		System.out.println(r);
		System.out.println(r.remove(1));
		System.out.println(r);
		System.out.println(r.remove(r.size() - 1));
		System.out.println(r);
		System.out.println(r.remove(r.size() - 1));
		System.out.println(r);

		System.out.println("\n" + d);
		System.out.println(d.remove(0));
		System.out.println(d);
		System.out.println(d.remove(1));
		System.out.println(d);

		System.out.println("\n=== end testing remove ===\n");

		// spliceIn
		// r.spliceIn(0, d); // ERROR: r and d are different types
		System.out.println("\n === testing splice === \n");
		RNAStrand r2 = new RNAStrand();
		r2.add('A');
		r2.add('U');
		r2.add('C');
		System.out.println("r1: " + r);
		System.out.println("r2: " + r2);
		r.spliceIn(1, r2);
		System.out.println("r0: " + r + "\n");
		
		System.out.println("r1: " + r);
		System.out.println("r2: " + r2);
		r.spliceIn(0, r2);
		System.out.println("r0: " + r + "\n");

		System.out.println("r1: " + r);
		System.out.println("r2: " + r2);
		r.spliceIn(r.size(), r2);
		System.out.println("r0: " + r);

		DNAStrand d2 = new DNAStrand();
		d2.add('A');
		d2.add('T');
		d2.add('C');
		System.out.println("d1: " + d);
		d.spliceIn(0, d2);
		System.out.println("d1: " + d);
		d.spliceIn(d.size(), d2);
		System.out.println("d1: " + d);

		System.out.println("\n === end testing splice === \n");
//		// reverse
//		System.out.println("Reversing");
//		System.out.println(r.a);
//		for (int i = 0; i < r.size(); i++) {
//			r.reverse(i, r.size());
//			System.out.println(r);
//		}
//
//		for (int i = 0; i < d.size(); i++) {
//			d.reverse(i, d.size());
//			System.out.println(d);
//		}
	}
}
