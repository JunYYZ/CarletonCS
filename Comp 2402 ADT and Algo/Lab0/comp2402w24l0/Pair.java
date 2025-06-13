package comp2402w24l0;

// From the Lab directory (or whichever directory contains the comp2402w24l0 directory):
// javac comp2402w24l0/Pair.java
// java comp2402w24l0/Pair

// You will submit this file to the autograder.
// It will pass all the tests as-is.
// If you make modifications to play around with the Testing file,
// then you should revert them before submitting to the autograder.

// Do not add any imports.
// (You shouldn't need any, and you'll fail the autograder if you do.)


public class Pair {

    /**
     * The first character of the pair represented by this class.
     */
    protected char x;

    /**
     *  The second character of the pair represented by this class.
     */
    protected char y;

    /**
     * Constructor
     * @param x is the first character of the pair
     * @param y is the second character of the pair
     */
    public Pair(char x, char y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Returns a string representation of this pair.
     */
    public String toString() {
        String s = "[" + x + ", " + y + "]";
        //s = "uncomment me to see how PairTest reacts!";
        return s;
    }

    public static void main(String[] args) {
        System.out.println( "Testing toString() via Pair.main...");
        System.out.println( "You should also try testing via test/PairTest.java");
        Pair p = new Pair('A', 'U');
        System.out.println(p); // should print [A, U]
        p = new Pair('A', 'A');
        System.out.println(p); // should print [A, A]
    }

}
