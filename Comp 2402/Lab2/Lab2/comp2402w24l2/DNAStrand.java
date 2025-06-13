package comp2402w24l2;

// From within the Lab directory (or wherever you put the comp2402w24l2 directory):
// javac comp2402w24l2/DNAStrand.java
// java comp2402w24l2/DNAStrand

// There is nothing for you to do in this file for Lab 2.
// Do not make any modifications, as they will be ignored by the autograder.

public class DNAStrand extends NAStrand {
	/**
	 * Array used to store allowable DNA bases. Do not remove this.
	 */
	private static final char[] DNABases = { 'A', 'C', 'G', 'T' };

	/**
	 * Constructor
	 *
	 */
	public DNAStrand() {
		super(DNABases);
	}

	/**
	 * Constructor
	 *
	 */
	public DNAStrand(String s) {
		super(DNABases, s);
	}

	/**
	 * Returns whether or not the base pair (x,y) is valid. Valid pairs are 'A' and
	 * 'T', and 'C' and 'G'.
	 *
	 * @param x
	 * @param y
	 */
	@Override
	public boolean isPair(char x, char y) {
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
