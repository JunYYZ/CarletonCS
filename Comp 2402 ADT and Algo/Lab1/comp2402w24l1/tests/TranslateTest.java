package comp2402w24l1.tests;

// From within the Lab directory (or whichever directory contains the comp2402w24l1 directory)
// Assumes junit-platform-console-standalone-1.10.1.jar
// is in the Lab/lib/ directory.
// This is the command that works on a linux-based system (e.g. mac os)
// javac -cp "./lib/*:." comp2402w24l1/tests/TranslateTest.java
// java -cp "./lib/*:." comp2402w24l1/tests/TranslateTest


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.runner.JUnitCore;
import java.util.Random;

import comp2402w24l1.RNAStrand;
import comp2402w24l1.Translate;
import input.InputGenerator;
import input.AlternatingCharGenerator;

public class TranslateTest {

    private static char[] bases = {'A', 'U', 'C', 'G'};

    @Test
    public static void allSame(int n) {
        System.out.println("\tTesting translate() all same character n=" + n + "...");
        for( int i=0; i < bases.length; i++ ) {
            char[] all_same = { bases[i] };
            InputGenerator<Character> gen = new AlternatingCharGenerator( all_same, n, (n<100));
            int expected = -1;
            if( bases[i] == 'A' ) {
                expected = 3*n;
            } else if( bases[i] == 'C' ) {
                expected = 2*n;
            }
            if( n == 0 ) expected = 0;
            assertEquals(expected, Translate.translate(gen),
                    "All " + bases[i] + "s with n= " + n + " " + gen);
        }
    }

    @Test
    public static void alternatingChar(int n) {
        System.out.println("\tTesting translate() alternating characters, e.g. AUCGAUCGAUCG n~" + n + "...");
        InputGenerator<Character> gen = new AlternatingCharGenerator(bases, (n/4)*4, (n < 200));
        assertEquals(2*(n/4), Translate.translate(gen),
                "Alternating characters A, U, C, G, with n%4=0 " + gen);

        gen = new AlternatingCharGenerator(bases, (n/4)*4+1, (n < 200));
        assertEquals(2*(n/4)+3, Translate.translate(gen),
                "Alternating characters A, U, C, G, with n%4=1 " + gen);

        gen = new AlternatingCharGenerator(bases, (n/4)*4+2, (n < 200));
        assertEquals(2*(n/4)+2, Translate.translate(gen),
                "Alternating characters A, U, C, G, with n%4=2 " + gen);

        gen = new AlternatingCharGenerator(bases, (n/4)*4+3, (n < 200));
        assertEquals(2*(n/4)+4, Translate.translate(gen),
                "Alternating characters A, U, C, G, with n%4=2 " + gen);

        char[] reordered_bases = {'A', 'U', 'C', 'G', 'G', 'U'};
        gen = new AlternatingCharGenerator(reordered_bases, 6, (n < 200));
        assertEquals(-1, Translate.translate(gen),
                "AUCGGU " + gen);


        reordered_bases[5] = 'G';
        gen = new AlternatingCharGenerator(reordered_bases, 6, (n < 200));
        assertEquals(-1, Translate.translate(gen),
                "AUCGGG " + gen);
    }

    @Test
    public static void constantSpace(int n) {
        System.out.println("\tTesting translate() alternating characters, e.g. CUCUCU n~" + n + "...");
        char[] reordered_bases = {'C', 'U'};

        InputGenerator<Character> gen = new AlternatingCharGenerator(reordered_bases, (n/4)*4, (n < 200));
        assertEquals(0, Translate.translate(gen),
                "Alternating C, U, C, U, with n%4=0 " + gen);
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

        int[] sizes = {8, 100, 1000, 2000};
        long start = System.nanoTime();
        switch (n) {
            case 0:
                System.out.println( "Testing translate() correctness input all same...");
                for( int i=0; i < sizes.length; i++) {
                    allSame(sizes[i]);
                }
                break;
            case 1:
                System.out.println( "Testing translate() correctness input alternating...");
                for( int i=0; i < sizes.length; i++) {
                    alternatingChar(sizes[i]);
                }
                break;
            case 2:
                System.out.println( "Testing translate() correctness edge case empty strand...");
                allSame(0); // empty strand -- should be 0.
                break;
            case 3: // performance time tests n=10,000
                int size=10000;
                System.out.println( "Testing translate() O(n^2) time performance size=" + size + "...");
                System.out.println( "You pass this test if it executes within 2 seconds.");
                System.out.println( "If it's taking forever, try CTRL-C or CTRL-Z to stop it.");
                allSame(size);
                alternatingChar(size);
                break;
            case 4:// performance time tests n=100,000
                size=100000;
                System.out.println( "Testing translate() O(n) time performance size=" + size + "...");
                System.out.println( "You pass this test if it executes within 2 seconds.");
                System.out.println( "If it's taking forever, try CTRL-C or CTRL-Z to stop it.");
                allSame(size);
                alternatingChar(size);
                break;
            case 5:// performance time tests n=1,000,000
                size=1000000;
                System.out.println( "Testing translate() O(n) time performance size=" + size + "...");
                System.out.println( "You pass this test if it executes within 2 seconds.");
                System.out.println( "If it's taking forever, try CTRL-C or CTRL-Z to stop it.");
                allSame(size);
                alternatingChar(size);
                break;
            case 6: // performance space tests
                size=1000000;
                System.out.println( "Testing translate() O(n) space performance size=" + size + "...");
                allSame(size);
                break;
            case 7: // performance space tests -- use -Xmx4m
                System.out.println( "If you didn't already, run this test (7) with -Xmx4m");
                size=1000000;
                System.out.println( "Testing translate() O(p) space performance p~O(1), size=" + size + "...");
                constantSpace(size);
                break;
            default:
                System.out.println("To run an individual test: java TranslateTest [test number]");
                for( int i=0; i < 8; i++ ) {
                    main(new String[]{"" + i});
                }
                System.exit(-1);
        }
        long stop = System.nanoTime();
        System.out.println("Execution time: " + 1e-9 * (stop-start));    }

}
