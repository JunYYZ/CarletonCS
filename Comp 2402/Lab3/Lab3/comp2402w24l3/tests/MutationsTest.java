package comp2402w24l3.tests;

// From within the Lab directory (or whichever directory contains the comp2402w24l3 directory)
// Assumes junit-platform-console-standalone-1.10.1.jar
// is in the Lab/lib/ directory.
//
// This is the command that works on a linux-based system (e.g. mac os)
// javac -cp "./lib/*:." comp2402w24l3/tests/MutationsTest.java
// java -cp "./lib/*:." comp2402w24l3/tests/MutationsTest
//
// On a windows machine, this should work:
// javac -cp "./lib/*;." comp2402w24l3/tests/MutationsTest.java
// java -cp "./lib/*;." comp2402w24l3/tests/MutationsTest


import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import comp2402w24l3.Mutations;
import input.InputGenerator;
import input.AlternatingCharGenerator;
import input.RandomCharGenerator;

public class MutationsTest {

    private static char[] bases = {'A', 'U', 'C', 'G'};

    @Test
    public static void allSame(int n, int k) {
        System.out.println("\tTesting mutations() all same character n=" + n + ", k=" + k + "...");
        for( int i=0; i < bases.length; i++ ) {
            char[] all_same = { bases[i] };
            InputGenerator<Character> gen = new AlternatingCharGenerator( all_same, n, (n<100));
            if( k <= n )
              assertEquals(0, Mutations.mutations(gen, k), "All same character k <= n should return 0 " + gen);
            else 
              assertEquals(0, Mutations.mutations(gen, k), "All same character k > n should return 0 " + gen);
        }
    }

    @Test
    public static void alternatingChar(int n) {
        System.out.println("\tTesting mutations() alternating characters, e.g. AUCGAUCGAUCG n~" + n + "...");
        InputGenerator<Character> gen = new AlternatingCharGenerator(bases, n, (n < 100));
        assertEquals(3, Mutations.mutations(gen, 4),
                "Alternating characters A, U, C, G, with k=4 should return 3 " + gen);
        gen = new AlternatingCharGenerator(bases, n, (n < 100));
        if( n <= 12 )
            assertEquals(2, Mutations.mutations(gen, 8), // AUCGAUCGAUCG
                    "Alternating characters A, U, C, G, with k=8 should return 2 " + gen);
        else
            assertEquals(3, Mutations.mutations(gen, 8), // AUCGAUCGAUCGAUCG
                "Alternating characters A, U, C, G, with k=8 should return 3 " + gen);
        gen = new AlternatingCharGenerator(bases, n, (n < 100));
        assertEquals(0, Mutations.mutations(gen, n),
                "Alternating characters A, U, C, G, with k=n should return 0 " + gen);
        gen = new AlternatingCharGenerator(bases, n, (n < 100));
        assertEquals(0, Mutations.mutations(gen, n-1),
                "Alternating characters A, U, C, G, with k=n-1 should return 0 " + gen);

    }

    @Test
    public static void empty() {
        System.out.println("\tTesting mutations() empty sequence...");
        InputGenerator<Character> gen = new AlternatingCharGenerator(bases, 0, true);
        gen = new AlternatingCharGenerator(bases, 0, true);
        assertEquals(0, Mutations.mutations(gen, 10),
                "Empty sequence with k>0 should return 0 " + gen);
    }

    public static void randomChar(int n, int k) {
        System.out.println("\tTesting mutations() random input n~" + n + ", k~" + k + "...");
        InputGenerator<Character> gen = new RandomCharGenerator(bases, n, (n < 100));
        // we can't know what the output should be, but it's good for performance tests
        Mutations.mutations(gen, k);
    }

    @Test
    public static void alternatingChar(int n, int k) {
        System.out.println("\tTesting mutations() alternating characters, e.g. AUCGAUCGAUCG n~" + n + "...");
        InputGenerator<Character> gen = new AlternatingCharGenerator(bases, (n/4)*4, (n < 100));
        assertEquals(3, Mutations.mutations(gen, k),
                "Alternating characters A, U, C, G, with k=" + k + " should return 3 " + gen);
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

        int[] sizes = {12, 64, 64*8};
        long start = System.nanoTime();
        switch (n) {
            case 0:
                System.out.println( "Testing mutations() correctness all same character");
                for( int i=0; i < sizes.length; i++) {
                    allSame(sizes[i], sizes[i]);
                }
                for( int i=0; i < sizes.length; i++ ) {
                    allSame(sizes[i], sizes[i]+2);
                }
                break;
            case 1:
                System.out.println( "Testing mutations() correctness input alternating chars...");
                for( int i=0; i < sizes.length; i++) {
                    alternatingChar(sizes[i]);
                }
                break;
            case 2:
                System.out.println( "Testing mutations() correctness edge case empty strand...");
                empty(); // empty strand
                break;
            case 3:
                int size=5000;
                System.out.println( "Testing mutations() O(n^2 k) time performance n~" + size + ", variety of k...");
                System.out.println( "\tYou pass this test if it executes within 2 seconds.");
                System.out.println( "\tIf it's taking forever, try CTRL-C or CTRL-Z to stop it.");
                allSame(size, size);
                alternatingChar(size);
                for( int k=3; k < 2*size; k*=2 ) { // ~log_2(10,000)~=14 iterations
                    allSame(size, k);
                }
                break;
            case 4:
                size=100000;
                System.out.println( "Testing mutations() O(nk^2) time performance n~" + size + ", k < 2,000...");
                System.out.println( "\tYou pass this test if it executes within 2 seconds.");
                System.out.println( "\tIf it's taking forever, try CTRL-C or CTRL-Z to stop it.");
                allSame(size, 1000);
                for( int k=1000; k < 2000; k+=100 ) { // ~10 iterations
                    allSame(size, k);
                }
                break;
            case 5:
                size=13000;
                System.out.println( "Testing mutations() O(nk^2) time performance n~" + size + ", variety of k...");
                System.out.println( "\tYou pass this test if it executes within 2 seconds.");
                System.out.println( "\tIf it's taking forever, try CTRL-C or CTRL-Z to stop it.");
                randomChar(size, size*100);
                for( int k=size/2; k < size; k+=(size/20) ) { // ~10 iterations
                    randomChar(size, k);
                }
                break;
            case 6:
                System.out.println( "If you didn't already, run this test (6) with -Xmx4m");
                size=10000;
                System.out.println( "Testing mutations() O(n) space performance n~" + size + "~k...");
                allSame(size, 20);
                alternatingChar(size, 20);
                break;
            case 7:
                System.out.println( "If you didn't already, run this test (7) with -Xmx4m");
                size=1000000;
                System.out.println( "Testing mutations() O(kd) space performance n~" + size + ", k~10...");
                allSame(size, 20);
                alternatingChar(size, 20);
                break;
            default:
                System.out.println("To run an individual test: java MutationsTest [test number]");
                for( int i=0; i < 8; i++ ) {
                    main(new String[]{"" + i});
                }

                System.exit(-1);
        }
        long stop = System.nanoTime();
        System.out.println("Execution time: " + 1e-9 * (stop-start));    }

}
