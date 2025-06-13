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
     * The dummy node. We use the convention that dummy.next = first and
     * dummy.prev = last. Do not remove this.
     */
    protected Node dummy;


    /**
     * Number of bases in the strand. Do not remove this.
     */
    protected int n;


    /**
     * Returns whether or not the pair of characters x and y
     * is a valid base pair for the particular subclass.
     * @param s
     * @param y
     */
    public abstract boolean isPair(char x, char y);

    /**
     * Returns a string representation of this strand.
     * Override this with more useful behaviour for debugging, if you wish.
     */
    public String toString() {
        if( dummy == null ) return "null";
        String s = "[";
        Node p = dummy.next; // first node
        while( p != dummy ) {
            s += p.s;
            p = p.next;
            if( p != dummy ) {
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
        add( "" + x );
    }

    public void add(int i, char x) throws IndexOutOfBoundsException, IllegalArgumentException {
        add(i, "" + x);
    }


    /**
     * This represents a (node u, index j) location of a base.
     * For example, base i may be at the j'th index of the String in node u.
     * You don't have to use this inner class, but I found it helpful
     * to be able to store a location in a single object.
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
     * @param bases is an array of characters that are allowable
     *              for this strand
     */
    public NAStrand(char[] bases) {
        // TODO(student): Implement this
    }

    /**
     * Constructor
     * @param bases is an array of characters that are allowable
     *              for this strand
     * @param s is a string of characters that will be added to the strand
     *          into a single (non-dummy) node
     */
    public NAStrand(char[] bases, String s) {
        // TODO(student): Implement this
    }

    /**
     * Adds the base s to the end of the strand as a single node.
     * Throws an IllegalArgumentException if any of the first four
     * bases of s are not valid.
     * @param s
     */
    public void add(String s) throws IllegalArgumentException {
        // TODO(student): Implement this
    }


    /**
     * Gets the base at position i.
     * Throw an IndexOutOfBoundsException exception if i is out of bounds.
     * Throw a NoSuchElementException if the base at position i is invalid.
     * @param i
     * @return
     */
    public char get(int i) throws IndexOutOfBoundsException, NoSuchElementException {
        // TODO(student): Implement this
        return 'x';
    }


    /**
     * Returns the number of bases in the strand.
     */
    public int size() {
        // TODO(student): Implement this
        return -1;
    }


    /**
     * Clears the NAStrand so that it has no bases.
     */
    public void clear() {
        // TODO(student): Implement this
    }


    /**
     * Sets the base at position i to x, returns the base that was previously at i.
     * Throws an IndexOutOfBoundsException if i is out of bounds.
     * Throws an IllegalArgumentException if the base x is not valid.
     * Throws a NoSuchElementException if the base originally at position i is invalid.
     * @param i
     * @param x
     * @return
     */
    public char set(int i, char x) throws IndexOutOfBoundsException, IllegalArgumentException, NoSuchElementException {
        // TODO(student): Implement this
        return 'x';
    }


    /**
     * Adds the bases of s as a new node in the strand at index i.
     * Throws an IndexOutOfBoundsException if i is out of bounds.
     * Throws an IllegalArgumentException if any of the first 4 bases of s are not valid.
     * @param i
     * @param s
     */
    public void add(int i, String s) throws IndexOutOfBoundsException, IllegalArgumentException {
        // TODO(student): Implement this
    }


    /**
     * Removes the base at position i.
     * Throws an IndexOutOfBoundsException if i is out of bounds.
     * Throws a NoSuchElementException if the base originally at position i is invalid.
     * @param i
     * @return
     */
    public char remove(int i) throws IndexOutOfBoundsException, NoSuchElementException {
        // TODO(student): Implement this
        return 'x';
    }


    /**
     * Splices a String s in a new node right after each instance of pattern p
     * found in the strand.
     * Throws an IllegalArgumentException if the string p or string s is empty.
     * Throws an IllegalArgumentException if any of the first four bases of s are not valid.
     * @param p
     * @param s
     */
    public void specialSplice(String p, String s) throws IllegalArgumentException {
        // TODO(student): Implement this
    }



    /**
     * Reverses the order of the bases in the strand from index i to index k-1
     *  (inclusive)
     * Throws an IndexOutOfBoundsException if either i or k is out of bounds
     * Throws an IndexOutOfBoundsException if i >= k
     * @param i
     * @param k
     */
    public void reverse(int i, int k) throws IndexOutOfBoundsException  {
       // TODO(student): Implement this
    }








    // These are a few examples of how to use main to run local tests
    // You should test more extensively than this.
    public static void main(String[] args) {
        //lab1Tests(); // Uncomment this to run your lab1 tests, minus spliceIn
        lab2Tests();   // Limited local tests for lab 2
    }

    public static void lab2Tests() {
        System.out.println("Testing NASTrand() via NAStrand.main...");
        System.out.println("You should also try testing via tests/NAStrandTest.java");

        NAStrand r;

        System.out.println( "\nLab 2: testing String constructor...");
        r = new RNAStrand("ACGUACGU");
        System.out.println(r); // Expect [ACGUACGU]


        System.out.println( "\nLab 2: testing empty constructor...");
        r = new RNAStrand();
        System.out.println(r); // Expect []


        System.out.println( "\nLab 2: testing add(s)...");
        r.add(0,"AA");
        r.add("UU");
        System.out.println(r); // Expect [AA, UU]
        r.add("GG");
        System.out.println(r); // Expect [AA, UU, GG]


        System.out.println( "\nLab 2: testing the use of Iterator<String>...");
        Iterator<String> it = r.iterator();
        while( it.hasNext() ) { // not perfect, you get the point
            System.out.print( it.next() );
            System.out.print( ", " );
        }
        System.out.println();


        // get
        System.out.println( "\nLab 2: testing get(i)...");
        System.out.print(r.get(0));
        System.out.print(r.get(1));
        System.out.print(r.get(2));
        System.out.print(r.get(3));
        System.out.print(r.get(4));
        System.out.print(r.get(5));
        System.out.println();
        String s = "ACGU";
        r = new RNAStrand(s);
        for( int i=0; i < s.length(); i++ ) {
            if( r.get(i) != s.charAt(i) ) System.out.println( "failed get(" + i + ") test" );
        }


        // size
        System.out.println( "\nLab 2: size tests..." );
        System.out.println(r.size()); // Expect 4
        r = new RNAStrand();
        System.out.println(r.size()); // Expect 0

        // clear
        System.out.println( "\nLab 2: clear tests..." );
        r.clear();
        System.out.println(r);
        it = r.iterator();
        System.out.println( it.hasNext() ); // should be false after you clear


        // set
        System.out.println( "\nLab 2: testing set(i,s)...");
        r = new RNAStrand("AC");
        r.add("GU");
        System.out.println( r.set(0, 'U') + " should be A" );
        System.out.println( r.set(2, 'C') + " should be G" );
        System.out.println( r ); // Expect [UC,CU]


        System.out.println( "\nLab 2: testing add(i,s)...");
        r.clear();
        r.add(0,"UU"); // [UU]
        r.add(0, "AA"); // [AA,UU]
        System.out.println(r); // Expect [AA, UU]
        r.add(2, "GG");
        System.out.println(r); // Expect [AA, GG, UU]
        r.add(1, "CC");
        System.out.println(r); // Expect [A, CC, A, GG, UU]
        r.add(7, "AA");
        System.out.println(r); // Expect [A, CC, A, GG, U, AA, U]
        r.add( 10, "CC");
        System.out.println(r); // Expect [A, CC, A, GG, U, AA, U, CC]
        r = new RNAStrand("ACGUACGU");
        System.out.println(r); // Expect [ACGUACGU]
        r.add(0, "GU");
        System.out.println( r ); // Expect [GU, ACGUACGU]
        r.add( 1, "CA");
        System.out.println( r ); // Expect [G, CA, U, ACGUACGU]
        r.add( 8, "AU");
        System.out.println(r); // Expect [G, CA, U, ACGU, AU, ACGU]
        r.add( 14, "AU");
        System.out.println(r); // Expect [G, CA, U, ACGU, AU, ACGU, AU]


        // remove
        System.out.println( "\nRemove tests..." );
        System.out.println(r);
        r.remove(5);
        System.out.println(r);
        while( r.size() > 0 ) {
            System.out.print( r.remove(0) );
            //System.out.println( r );
        }
        System.out.println();


        // specialSplice
        System.out.println( "\nLab 2: specialSplice tests..." );
        r = new RNAStrand("ACGUACGU");
        System.out.println( "r: " + r + " before specialSplice( GU, AACCA )");
        r.specialSplice("GU", "AACCA");
        System.out.println( r ); // Expect [AC, AACCA, AC, AACCA]
        System.out.println( "specialSplice(AA,AA)");
        r.specialSplice("AA", "AA" );
        System.out.println( r ); // Expect [AC, AA, CCA, AC, AA, CCA]
        r.clear();
        r.add( "CGAA" );
        r.specialSplice( "AA", "AAAAB" );
        System.out.println( r ); // Expect [CG, AAAAB]
        r.specialSplice( "CG", "GGGG");
        System.out.println( r ); // Expect [GGGG, AAAAB]
        r.specialSplice( "AAAB", "UUUU");
        System.out.println( r ); // Expect [GGGG, A, UUUU]
        r.specialSplice("U", "UU");
        System.out.println( r ); // Expect [GGGG, A, UU, UU, UU, UU]



        System.out.println( "\nLab 2: reverse tests..." );
        r.clear();
        // First try reverses all contained within one node
        r = new RNAStrand("ACGUACGU" );
        System.out.println( r );
        for( int i=0; i < r.size(); i++ ) {
            r.reverse(i, r.size());
            System.out.print( r + ", ");
        }
        System.out.println();

        // Now reverse that spans over multiple nodes
        r.clear();
        r.add( "CAG" );
        r.add( "U" );
        r.add( "G" );
        System.out.println( r ); // [CAG,U,G]
        r.reverse(0, 3);
        System.out.println( r ); // [GAC,U,G]
        r.reverse(0, 4);
        System.out.println( r ); // [U,CAG,G]
        r.reverse(1, 5);
        System.out.println( r ); // [U,G,GAC]
        r.add("UA");
        r.add("CC");
        r.add("UU");
        System.out.println( r ); // [U,G,GAC,UA,CC,UU]
        r.reverse(3, 9);
        System.out.println( r ); // [U,G,GCC,AU,CA,UU]
        r.reverse(0, 10);
        System.out.println( r ); // [U,AC,UA,CCG,G,UU]

    }
    public static void lab1Tests() {
        NAStrand r = new RNAStrand();
        NAStrand d = new DNAStrand();

        System.out.println(r);
        System.out.println(d);

        // isPair
        System.out.println(r.isPair('A', 'U'));
        System.out.println(d.isPair('A', 'U'));
        System.out.println(r.isPair('A', 'T'));
        System.out.println(d.isPair('A', 'T'));

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
        r.add(0,'A');
        r.add('U');
        r.add('C');
        r.add(3, 'G');
        System.out.println(r);
        d.add(0,'A');
        d.add('T');
        d.add('C');
        d.add(3,'G');
        System.out.println(d);

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

        for( int i=0; i < 10; i++ ) {
            r.add(r.getBases()[i%4]);
            d.add(d.getBases()[i%4]);
        }
        System.out.println(r);
        System.out.println(d);

        // set
        System.out.println( r.set(0, 'U') );
        System.out.println( r.set(1, 'G') );
        System.out.println( d.set(0, 'T') );
        System.out.println( d.set(1, 'G') );

        // remove
        System.out.println( r.remove(0) );
        System.out.println( r.remove(1) );
        System.out.println( d.remove(0) );
        System.out.println( d.remove(1) );
        System.out.println(r);
        System.out.println(d);

        // reverse
        for( int i=0; i < r.size(); i++ ) {
            r.reverse(i, r.size());
            System.out.println( r );
        }

        for( int i=0; i < d.size(); i++ ) {
            d.reverse(i, d.size());
            System.out.println( d );
        }

    }

}
