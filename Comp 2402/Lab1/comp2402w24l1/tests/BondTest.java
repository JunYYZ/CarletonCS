package comp2402w24l1.tests;


// From within the Lab directory (or whichever directory contains the comp2402w24l1 directory)
// Assumes junit-platform-console-standalone-1.10.1.jar
// is in the Lab/lib/ directory.
// This is the command that works on a linux-based system (e.g. mac os)
// javac -cp "./lib/*:." comp2402w24l1/tests/BondTest.java
// java -cp "./lib/*:." comp2402w24l1/tests/BondTest
// or, if doing an O(1) space test
// java -Xmx4m -cp "./lib/*:." comp2402w24l1/tests/BondTest


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.runner.JUnitCore;
import java.util.Random;

import comp2402w24l1.Bond;
import input.InputGenerator;
import input.AlternatingCharGenerator;

public class BondTest {

    private static char[] bases = {'A', 'U', 'C', 'G'};

    @Test
    public static void allSame(int n) {
        System.out.println("\tTesting bond() all same character n=" + n + "...");
        if( n == 0 ) {
            assertTrue(Bond.bond(new AlternatingCharGenerator(bases, n, (n < 100))),
                    "All same characters with n=0 should be true"); // Expect property
            return;
        }
        for( int i=0; i < bases.length; i++ ) {
            char[] all_same = { bases[i] };
            InputGenerator<Character> gen = new AlternatingCharGenerator(all_same, n, (n<100));
            assertFalse(Bond.bond(gen), "All same character should be false" + gen); // Expect not property
        }
    }

    @Test
    public static void alternatingChar(int n) {
        System.out.println("\tTesting bond() alternating characters short bonds, e.g. AUCGAUCGAUCG n~" + n + "...");
        InputGenerator<Character> gen = new AlternatingCharGenerator(bases, (n/4)*4, (n<100));
        assertTrue(Bond.bond(gen),
                "Alternating characters A, U, C, G, length div by 4 should return true " + gen); // Expect property
        gen = new AlternatingCharGenerator(bases, (n/4)*4-2, (n<100));
        assertTrue(Bond.bond(gen),
                "Alternating characters A, U, C, G, length div by 2 should return true " + gen); // Expect property
        gen = new AlternatingCharGenerator(bases, (n/4)*4-1, (n<100));
        assertFalse(Bond.bond(gen),
                "Alternating characters A, U, C, G, odd length should return false " + gen); // Expect not property
        char[] reordered_bases = {'U', 'C', 'G', 'A'};
        gen = new AlternatingCharGenerator(reordered_bases, (n/4)*4, (n<100));
        assertFalse(Bond.bond(gen),
                "Alternating characters U, C, G, A length div by 4 should return false " + gen); // Expect not property
    }

    @Test
    public static void nestedBonds(int n) {
        System.out.println("\tTesting bond() nested bonds, e.g. AAUUAAUU n~" + n + "...");
        char[] reordered_bases = {'A', 'A', 'U', 'U'};
        InputGenerator<Character> gen = new AlternatingCharGenerator(reordered_bases, (n/4)*4, (n<100));
        assertTrue(Bond.bond(gen),
                "Nested bonds length div by 4 should return true " + gen); // Expect property
        gen = new AlternatingCharGenerator(reordered_bases, (n/4)*4-2, (n<100));
        assertFalse(Bond.bond(gen),
                "Nested bonds length div by 2 should return false " + gen); // Expect not property
        reordered_bases = new char[]{'A', 'C', 'G', 'A', 'C', 'G', 'U', 'U', 'A', 'U'};
        gen = new AlternatingCharGenerator(reordered_bases, (n/10)*10, (n<100));
        assertTrue(Bond.bond(gen),
                "Nested bonds with property should return true " + gen); // Expect  property
        gen = new AlternatingCharGenerator(reordered_bases, (n/10)*10-2, (n<100));
        assertTrue(Bond.bond(gen),
                "Nested bonds with property should return true " + gen); // Expect property
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

        int[] sizes = {8, 100, 1000};
        long start = System.nanoTime();
        switch (n) {
            case 0:
                System.out.println( "Testing bond() correctness input all same char...");
                for( int i=0; i < sizes.length; i++) {
                    allSame(sizes[i]);
                }
                break;
            case 1:
                System.out.println( "Testing bond() correctness input alternating char...");
                for( int i=0; i < sizes.length; i++) {
                    alternatingChar(sizes[i]);
                }
                break;
            case 2:
                System.out.println( "Testing bond() correctness input nested bonds...");
                for( int i=0; i < sizes.length; i++) {
                    nestedBonds(sizes[i]+2);
                }
                break;
            case 3:
                System.out.println( "Testing bond() correctness edge case empty strand...");
                allSame(0);
                break;
            case 4: // performance time tests n=10,000
                int size=10000;
                System.out.println( "Testing bond() O(n^2) time performance size=" + size + "...");
                System.out.println( "You pass this test if it executes within 2 seconds.");
                System.out.println( "If it's taking forever, try CTRL-C or CTRL-Z to stop it.");
                allSame(size);
                alternatingChar(size);
                nestedBonds(size);
                break;
            case 5:// performance time tests n=100,000
                size=100000;
                System.out.println( "Testing bond() O(n) time performance size=" + size + "...");
                System.out.println( "You pass this test if it executes within 2 seconds.");
                System.out.println( "If it's taking forever, try CTRL-C or CTRL-Z to stop it.");
                allSame(size);
                alternatingChar(size);
                nestedBonds(size);
                break;
            case 6:// performance time tests n=1,000,000
                size=1000000;
                System.out.println( "Testing bond() O(n) time performance size=" + size + "...");
                System.out.println( "You pass this test if it executes within 2 seconds.");
                System.out.println( "If it's taking forever, try CTRL-C or CTRL-Z to stop it.");
                allSame(size);
                alternatingChar(size);
                nestedBonds(size);
                break;
            case 7: // performance space tests O(n)
                size=1000000;
                System.out.println( "Testing bond() O(n) space performance size=" + size + "...");
                allSame(size);
                nestedBonds(size);
                break;
            case 8: // performance space tests O(g) -- use -Xmx4m
                System.out.println( "If you didn't already, run this test (8) with -Xmx4m");
                size=1000000;
                System.out.println( "Testing bond() O(g) space performance size=" + size + "...");
                alternatingChar(size);
                nestedBonds(size);
                break;
            default:
                System.out.println("To run an individual test: java BondTest [test number]");
                for( int i=0; i < 9; i++ ) {
                    main(new String[]{"" + i});
                }

                System.exit(-1);

        }
        long stop = System.nanoTime();
        System.out.println("Execution time: " + 1e-9 * (stop-start));    }

}
