package comp2402w24l1;

// From within the Lab directory (or wherever you put the comp2402w24l1 directory):
// javac comp2402w24l1/DNAStrand.java
// java comp2402w24l1/DNAStrand

// You will submit this file to the autograder.
// It will not pass many of the tests, if any.
// Your task is to modify the class to pass the tests.

// Do not add any imports.
// (You shouldn't need any, and you'll fail the autograder if you do.)

public class DNAStrand extends NAStrand {

	/**
	 * Constructor
	 */
	public DNAStrand() {
		super(new char[] { 'A', 'C', 'G', 'T' });
	}

	/**
	 * Returns whether or not the base pair (x,y) is valid. Valid pairs are 'A' and
	 * 'U', and 'C' and 'G'.
	 *
	 * @param x
	 * @param y
	 */
	@Override
	public boolean isPair(char x, char y) {
		// TODO(Student): Replace with your code.
		return (x == 'A' && y == 'T') || (x == 'T' && y == 'A') || (x == 'C' && y == 'G') || (x == 'G' && y == 'C');
	}

	// These are a few examples of how to use main to run local tests
	// You should test more extensively than this.
	public static void main(String[] args) {
		DNAStrand r = new DNAStrand();
		System.out.println(r);
		System.out.println(r.isPair('A', 'T'));
		System.out.println(r.isPair('T', 'A'));
		System.out.println(r.isPair('C', 'G'));
		System.out.println(r.isPair('G', 'C'));
		System.out.println(r.isPair('A', 'C'));
		System.out.println(r.isPair('C', 'A'));
		System.out.println(r.isPair('A', 'G'));
		System.out.println(r.isPair('G', 'A'));
		System.out.println(r.isPair('T', 'C'));
		System.out.println(r.isPair('C', 'T'));
		System.out.println(r.isPair('U', 'G'));
		System.out.println(r.isPair('G', 'T'));
		System.out.println(r.isPair('A', 'A'));
		System.out.println(r.isPair('C', 'C'));
		System.out.println(r.isPair('G', 'G'));
		System.out.println(r.isPair('T', 'T'));
	}
}
