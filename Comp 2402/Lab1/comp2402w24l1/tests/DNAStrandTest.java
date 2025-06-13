package comp2402w24l1.tests;

// From within the Lab directory (or whichever directory contains the comp2402w24l1 directory)
// Assumes junit-platform-console-standalone-1.10.1.jar
// is in the Lab/lib/ directory.
// This is the command that works on a linux-based system (e.g. mac os)
// javac -cp "./lib/*:." comp2402w24l1/tests/DNAStrandTest.java
// java -cp "./lib/*:." comp2402w24l1/tests/DNAStrandTest

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import comp2402w24l1.DNAStrand;
import comp2402w24l1.NAStrand;


public class DNAStrandTest {

    private static char[] bases = {'A', 'C', 'G', 'T'};

    @Test
    public static void test_creation() {
        DNAStrand actual = new DNAStrand(); // not much to test here
    }

    @Test
    public static void isPair() {
        DNAStrand dnaStrand = new DNAStrand();
        assertTrue(dnaStrand.isPair('A', 'T'), "A and T should be a pair");
        assertTrue(dnaStrand.isPair('T', 'A'), "T and A should be a pair");
        assertTrue(dnaStrand.isPair('C', 'G'), "C and G should be a pair");
        assertTrue(dnaStrand.isPair('G', 'C'), "G and C should be a pair");
        for( int i=0; i < bases.length; i++ ) {
            assertFalse(dnaStrand.isPair(bases[i], bases[i]), bases[i] + " and " + bases[i] + " should not be a pair");
        }
        assertFalse(dnaStrand.isPair('A', 'C'), "A and C should not be a pair");
        assertFalse(dnaStrand.isPair('C', 'A'), "C and A should not be a pair");
        assertFalse(dnaStrand.isPair('A', 'G'), "A and G should not be a pair");
        assertFalse(dnaStrand.isPair('G', 'A'), "G and A should not be a pair");
        assertFalse(dnaStrand.isPair('T', 'C'), "T and C should not be a pair");
        assertFalse(dnaStrand.isPair('C', 'T'), "C and T should not be a pair");
        assertFalse(dnaStrand.isPair('U', 'G'), "U and G should not be a pair");
        assertFalse(dnaStrand.isPair('G', 'T'), "G and T should not be a pair");
        assertFalse(dnaStrand.isPair('a', 't'), "a and t should not be a pair");
        assertFalse(dnaStrand.isPair('t', 'a'), "t and a should not be a pair");
        assertFalse(dnaStrand.isPair('c', 'g'), "c and g should not be a pair");
        assertFalse(dnaStrand.isPair('g', 'c'), "g and c should not be a pair");
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
                System.out.println("Testing empty DNA strand constructor...");
                test_creation();
                break;
            case 1:
                System.out.println("Testing DNA isPair() correctness...");
                isPair();
                break;
            default:
                System.out.println("To run an individual test: java DNAStrandTest [test number]");
                for( int i=0; i < 2; i++ ) {
                    main(new String[]{"" + i});
                }
                System.exit(-1);
        }
        long stop = System.nanoTime();
        System.out.println("Execution time: " + 1e-9 * (stop-start) + "\n");
    }
}
