package comp2402w24l1.tests;

// From within the Lab directory (or whichever directory contains the comp2402w24l1 directory)
// Assumes junit-platform-console-standalone-1.10.1.jar
// is in the Lab/lib/ directory.
// This is the command that works on a linux-based system (e.g. mac os)
// javac -cp "./lib/*:." comp2402w24l1/tests/CountATest.java
// java -cp "./lib/*:." comp2402w24l1/tests/CountATest


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.runner.JUnitCore;
import java.util.Random;

import comp2402w24l1.CountA;
import input.InputGenerator;
import input.AlternatingCharGenerator;

public class CountATest {

    private static char[] bases = {'A', 'U', 'C', 'G'};

    @Test
    public static void allSame(int n, int k) {
        System.out.println("\tTesting countA() all same character n=" + n + ", k=" + k + "...");
        for( int i=0; i < bases.length; i++ ) {
            char[] all_same = { bases[i] };
            InputGenerator<Character> gen = new AlternatingCharGenerator( all_same, n, (n<100));
            assertEquals(0, CountA.countA(gen, k), "All same character should return 0 " + gen);
        }
    }

    @Test
    public static void alternatingChar(int n) {
        System.out.println("\tTesting countA() alternating characters, e.g. AUCGAUCGAUCG n~" + n + "...");
        InputGenerator<Character> gen = new AlternatingCharGenerator(bases, (n/4)*4, (n < 100));
        assertEquals(1, CountA.countA(gen, 4),
                "Alternating characters A, U, C, G, with k=4 should return 1 " + gen);
        gen = new AlternatingCharGenerator(bases, (n/4)*4, (n < 100));
        assertEquals(2, CountA.countA(gen, 8),
                "Alternating characters A, U, C, G, with k=8 should return 2 " + gen);
        gen = new AlternatingCharGenerator(bases, (n/4)*4, (n < 100));
        assertEquals(n/4, CountA.countA(gen, n-1),
                "Alternating characters A, U, C, G, with k=n-3 should return n/4 " + gen);
        gen = new AlternatingCharGenerator(bases, (n/4)*4, (n < 100));
        assertEquals(0, CountA.countA(gen, 0),
                "Alternating characters A, U, C, G, with k=0 should return 0 " + gen);
    }

    @Test
    public static void empty() {
        System.out.println("\tTesting countA() empty sequence...");
        InputGenerator<Character> gen = new AlternatingCharGenerator(bases, 0, true);
        assertEquals(0, CountA.countA(gen, 0),
                "Empty seqeuence with k=0 should return 0 " + gen);
        gen = new AlternatingCharGenerator(bases, 0, true);
        assertEquals(0, CountA.countA(gen, 10),
                "Empty sequence with k>0 should return 0 " + gen);
    }

    @Test
    public static void alternatingChar(int n, int k) {
        System.out.println("\tTesting countA() alternating characters, e.g. AUCGAUCGAUCG n~" + n + "...");
        InputGenerator<Character> gen = new AlternatingCharGenerator(bases, (n/4)*4, (n < 100));
        assertEquals(k/4, CountA.countA(gen, k),
                "Alternating characters A, U, C, G, with k=" + k + " should return 1 " + gen);
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

        int[] sizes = {8, 64, 64*8};
        long start = System.nanoTime();
        switch (n) {
            case 0:
                System.out.println( "Testing countA() correctness edge case no U's, edge case no A's");
                for( int i=0; i < sizes.length; i++) {
                    allSame(sizes[i], sizes[i]);
                }
                for( int i=0; i < sizes.length; i++ ) {
                    allSame(sizes[i], sizes[i]+2);
                }
                break;
            case 1:
                System.out.println( "Testing countA() correctness input alternating chars...");
                for( int i=0; i < sizes.length; i++) {
                    alternatingChar(sizes[i]);
                }
                break;
            case 2:
                System.out.println( "Testing countA() correctness edge case empty strand...");
                empty(); // empty strand
                break;
            case 3: // performance time tests n=10,000
                int size=10000;
                System.out.println( "Testing countA() O(n^2) time performance size=" + size + ", variety of k...");
                System.out.println( "You pass this test if it executes within 2 seconds.");
                System.out.println( "If it's taking forever, try CTRL-C or CTRL-Z to stop it.");
                allSame(size, size);
                alternatingChar(size);
                for( int k=3; k < 2*size; k*=2 ) { // ~log_2(10,000)~=14 iterations
                    allSame(size, k);
                }
                break;
            case 4:// performance time tests n=100,000
                size=100000;
                System.out.println( "Testing countA() O(k^2) time performance size=" + size + ", k < 10,000...");
                System.out.println( "You pass this test if it executes within 2 seconds.");
                System.out.println( "If it's taking forever, try CTRL-C or CTRL-Z to stop it.");
                allSame(size, 10000);
                for( int k=10000; k < 20000; k+=1000 ) { // ~10 iterations
                    allSame(size, k);
                }
                break;
            case 5:// performance time tests n=1,000,000
                size=1000000;
                System.out.println( "Testing countA() O(n) time performance size=" + size + ", variety of k...");
                System.out.println( "You pass this test if it executes within 2 seconds.");
                System.out.println( "If it's taking forever, try CTRL-C or CTRL-Z to stop it.");
                allSame(size, size);
                alternatingChar(size);
                for( int k=3; k < size; k+=size/4 ) {
                    allSame(size, k);
                }
                break;
            case 6: // performance space tests
                size=1000000;
                System.out.println( "Testing countA() O(n) space performance size=" + size + "=k...");
                allSame(size, size);
                alternatingChar(size, size);
                break;
            case 7: // performance space tests -- use -Xmx4m
                System.out.println( "If you didn't already, run this test (10) with -Xmx4m");
                size=1000000;
                System.out.println( "Testing countA() O(k) space performance size=" + size + ", k~1000...");
                allSame(size, 1000);
                alternatingChar(size, 1000);
                break;
            default:
                System.out.println("To run an individual test: java CountATest [test number]");
                for( int i=0; i < 8; i++ ) {
                    main(new String[]{"" + i});
                }

                System.exit(-1);
        }
        long stop = System.nanoTime();
        System.out.println("Execution time: " + 1e-9 * (stop-start));    }

}
