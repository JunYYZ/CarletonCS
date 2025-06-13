package comp2402w24l1.tests;

// From within the Lab directory (or whichever directory contains the comp2402w24l1 directory)
// Assumes junit-platform-console-standalone-1.10.1.jar
// is in the Lab/lib/ directory.
// This is the command that works on a linux-based system (e.g. mac os)
// javac -cp "./lib/*:." comp2402w24l1/tests/RNAStrandTest.java
// java -cp "./lib/*:." comp2402w24l1/tests/RNAStrandTest

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import comp2402w24l1.RNAStrand;
import comp2402w24l1.NAStrand;


public class RNAStrandTest {

    private static char[] bases = {'A', 'C', 'G', 'U'};

    @Test
    public static void test_creation() {
        RNAStrand actual = new RNAStrand(); // not much to test here
    }

    @Test
    public static void isPair() {
        RNAStrand rnaStrand = new RNAStrand();
        assertTrue(rnaStrand.isPair('A', 'U'), "A and U should be a pair");
        assertTrue(rnaStrand.isPair('U', 'A'), "U and A should be a pair");
        assertTrue(rnaStrand.isPair('C', 'G'), "C and G should be a pair");
        assertTrue(rnaStrand.isPair('G', 'C'), "G and C should be a pair");
        for( int i=0; i < bases.length; i++ ) {
            assertFalse(rnaStrand.isPair(bases[i], bases[i]), bases[i] + " and " + bases[i] + " should not be a pair");
        }
        assertFalse(rnaStrand.isPair('A', 'C'), "A and C should not be a pair");
        assertFalse(rnaStrand.isPair('C', 'A'), "C and A should not be a pair");
        assertFalse(rnaStrand.isPair('A', 'G'), "A and G should not be a pair");
        assertFalse(rnaStrand.isPair('G', 'A'), "G and A should not be a pair");
        assertFalse(rnaStrand.isPair('U', 'C'), "U and C should not be a pair");
        assertFalse(rnaStrand.isPair('C', 'U'), "C and U should not be a pair");
        assertFalse(rnaStrand.isPair('U', 'G'), "U and G should not be a pair");
        assertFalse(rnaStrand.isPair('G', 'U'), "G and U should not be a pair");
        assertFalse(rnaStrand.isPair('a', 'u'), "a and u should not be a pair");
        assertFalse(rnaStrand.isPair('u', 'a'), "u and a should not be a pair");
        assertFalse(rnaStrand.isPair('c', 'g'), "c and g should not be a pair");
        assertFalse(rnaStrand.isPair('g', 'c'), "g and c should not be a pair");
    }


    public static void main(String[] args) {
        int n=1000;
        try {
            if (args.length == 1) {
                n = Integer.parseInt(args[0]);
            }
        } catch (NumberFormatException e) {
            System.err.println(e);
            System.exit(-2);
        }

        long start = System.nanoTime();
        switch (n) {
            case 0:
                System.out.println("Testing empty RNA strand constructor...");
                test_creation();
                break;
            case 1:
                System.out.println("Testing isPair() correctness...");
                isPair();
                break;
            default:
                System.out.println("To run an individual test: java RNAStrandTest [test number]");
                for( int i=0; i < 2; i++ ) {
                    main(new String[]{"" + i});
                }
                System.exit(-1);
        }
        long stop = System.nanoTime();
        System.out.println("Execution time: " + 1e-9 * (stop-start) + "\n");
    }
}
