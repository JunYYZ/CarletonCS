package comp2402w24l1.tests;

// From within the Lab directory (or whichever directory contains the comp2402w24l1 directory)
// Assumes junit-platform-console-standalone-1.10.1.jar
// is in the Lab/lib/ directory.
// This is the command that works on a linux-based system (e.g. mac os)
// javac -cp "./lib/*:." comp2402w24l1/tests/MaxTest.java
// java -cp "./lib/*:." comp2402w24l1/tests/MaxTest
// or, if doing an O(1) space test
// java -Xmx4m -cp "./lib/*:." comp2402w24l1/tests/MaxTest


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.Random;
import org.junit.runner.JUnitCore;

import comp2402w24l1.Max;
import input.InputGenerator;
import input.AlternatingCharGenerator;

public class MaxTest {

    private static char[] bases = {'A', 'U', 'C', 'G'};

    @Test
    public static void allSame(int n) {
        System.out.println("\tTesting max() all same character n=" + n + "...");
        for( int i=0; i < bases.length; i++ ) {
            char[] all_same = { bases[i] };
            InputGenerator<Character> gen = new AlternatingCharGenerator( all_same, n, (n<100));
            assertEquals(n, Max.max(gen),
                    "All same character should return n " + gen);
        }
    }

    @Test
    public static void alternatingChar(int n) {
        System.out.println("\n\tTesting max() alternating characters, e.g. AUCGAUCGAUCG n~" + n + "...");
        InputGenerator<Character> gen = new AlternatingCharGenerator(bases, (n/4)*4, (n < 100));
        assertEquals(n/4, Max.max(gen), "Alternating characters A, U, C, G, with n%4=0 return n/4");
        gen = new AlternatingCharGenerator(bases, (n/4)*4-1, (n < 100));
        assertEquals(n/4, Max.max(gen), "Alternating characters A, U, C, G, with n%4=2 should return n/4");
        gen = new AlternatingCharGenerator(bases, (n/4)*4-2, (n < 100));
        assertEquals(n/4, Max.max(gen), "Alternating characters A, U, C, G, with n%4=2 should return n/4");
        gen = new AlternatingCharGenerator(bases, (n/4)*4-1, (n < 100));
        assertEquals(n/4, Max.max(gen), "Alternating characters A, U, C, G, with n%4=3 should return n/4");
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
                System.out.println( "Testing max() correctness input all same char...");
                for( int i=0; i < sizes.length; i++) {
                    allSame(sizes[i]);
                }
                break;
            case 1:
                System.out.println( "Testing max() correctness input alternating...");
                for( int i=0; i < sizes.length; i++) {
                    alternatingChar(sizes[i]);
                }
                break;
            case 2:
                System.out.println( "Testing max() correctness edge case empty sequence...");
                allSame(0);
                break;
            case 3: // performance time tests n=10,000
                int size=10000;
                System.out.println( "Testing max() O(n^2) time performance size=" + size + "...");
                System.out.println( "You pass this test if it executes within 2 seconds." );
                allSame(size);
                alternatingChar(size);
                break;
            case 4:// performance time tests n=100,000
                size=100000;
                System.out.println( "Testing max() O(n^2) time performance size=" + size + "...");
                System.out.println( "You pass this test if it executes within 2 seconds.");
                allSame(size);
                alternatingChar(size);
                break;
            case 5:// performance time tests n=1,000,000
                size=1000000;
                System.out.println( "Testing max() O(n) time performance size=" + size + "...");
                System.out.println( "You pass this test if it executes within 2 seconds.");
                System.out.println( "If it's taking forever, try CTRL-C or CTRL-Z to stop it.");
                allSame(size);
                alternatingChar(size);
                break;
            case 6: // performance space tests
                size=1000000;
                System.out.println( "Testing max() O(n) space performance size=" + size + "...");
                System.out.println( "If it's taking forever, try CTRL-C or CTRL-Z to stop it.");
                allSame(size); // should be O(n) space
                alternatingChar(size); // should be O(n) space
                break;
            case 7: // performance space tests -- use -Xmx4m
                System.out.println( "If you didn't already, run this test (7) with -Xmx4m");
                size=1000000;
                System.out.println( "Testing max() O(1) space performance size=" + size + "...");
                allSame(size); // should be O(1) space
                alternatingChar(size); // should be O(1) space
                break;
            default:
                System.out.println("To run an individual test: java MaxTest [test number]");
                for( int i=0; i < 8; i++ ) { // This loop runs all tests from 0-7 inclusive
                    main(new String[]{"" + i});
                }

                System.exit(-1);
        }
        long stop = System.nanoTime();
        System.out.println("Execution time: " + 1e-9 * (stop-start));    }

}
