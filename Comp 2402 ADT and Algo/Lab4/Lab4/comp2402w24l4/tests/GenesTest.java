package comp2402w24l4.tests;

// From within the Lab directory (or whichever directory contains the comp2402w24l4 directory)
// Assumes junit-platform-console-standalone-1.10.1.jar
// is in the Lab/lib/ directory.
//
// This is the command that works on a linux-based system (e.g. mac os)
// javac -cp "./lib/*:." comp2402w24l4/tests/GenesTest.java
// java -cp "./lib/*:." comp2402w24l4/tests/GenesTest
//
// On a windows machine, this should work:
// javac -cp "./lib/*;." comp2402w24l4/tests/GenesTest.java
// java -cp "./lib/*;." comp2402w24l4/tests/GenesTest


import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import comp2402w24l4.Genes;
import input.InputGenerator;
import input.AlternatingCharGenerator;
import input.RandomCharGenerator;

public class GenesTest {

    private static char[] bases = {'A', 'U', 'C', 'G'};

    @Test
    public static void allSame(int n, int k) {
        System.out.println("\tTesting genes() all same character n=" + n + ", k=" + k + "...");
        for( int i=0; i < bases.length; i++ ) {
            char[] all_same = { bases[i] };
            InputGenerator<Character> gen = new AlternatingCharGenerator( all_same, n, (n<100));
            if( k <= n )
              assertEquals(n-k+1, Genes.genes(gen, k), "All same character k <= n should return " + (n-k+1) + " " + gen);
            else 
              assertEquals(0, Genes.genes(gen, k), "All same character k > n should return 0 " + gen);
        }
    }

    @Test
    public static void alternatingChar(int n) {
        System.out.println("\tTesting genes() alternating characters, e.g. AUCGAUCGAUCG n~" + n + "...");
        InputGenerator<Character> gen = new AlternatingCharGenerator(bases, n, (n < 100));
        assertEquals(n-4+1, Genes.genes(gen, 4),
                "Alternating characters A, U, C, G, with k=4 should return " + (n-4+1) + " " + gen );
        gen = new AlternatingCharGenerator(bases, n, (n < 100));
        assertEquals(n-8+1, Genes.genes(gen, 8),
                "Alternating characters A, U, C, G, with k=8 should return " + (n-8+1) + " " + gen);
        gen = new AlternatingCharGenerator(bases, n, (n < 100));
        assertEquals(1, Genes.genes(gen, n),
                "Alternating characters A, U, C, G, with k=n should return 1 " + gen);
        gen = new AlternatingCharGenerator(bases, n, (n < 100));
        assertEquals(2, Genes.genes(gen, n-1),
                "Alternating characters A, U, C, G, with k=n-1 should return 2 " + gen);

        char[] shifted_bases = {'U', 'C', 'G', 'A'};
        gen = new AlternatingCharGenerator(shifted_bases, n, (n < 100));
        int dupes = 0;
        // hard-coding some dupes in here
        if( n > 4891 ) {
            dupes = 19;
        } else if( n > 335 ) {
            dupes = 2;
        } else if( n > 67 ) {
            dupes = 1;
        }

        assertEquals(n-dupes, Genes.genes(gen, 1),
                    "Alternating characters U, C, G, A, with k=1 should return " + (n-dupes) + " "  + gen);


    }

    @Test
    public static void empty() {
        System.out.println("\tTesting genes() empty sequence...");
        InputGenerator<Character> gen = new AlternatingCharGenerator(bases, 0, true);
        gen = new AlternatingCharGenerator(bases, 0, true);
        assertEquals(0, Genes.genes(gen, 10),
                "Empty sequence with k>0 should return 0 " + gen);
    }

    public static void randomChar(int n, int k) {
        System.out.println("\tTesting genes() random input n~" + n + ", k~" + k + "...");
        InputGenerator<Character> gen = new RandomCharGenerator(bases, n, (n < 100));
        // we can't know what the output should be, but it's good for performance tests
        Genes.genes(gen, k);
    }

    @Test
    public static void alternatingChar(int n, int k) {
        System.out.println("\tTesting genes() alternating characters, e.g. AUCGAUCGAUCG n~" + n + "...");
        InputGenerator<Character> gen = new AlternatingCharGenerator(bases, (n/4)*4, (n < 100));
        assertEquals((n-k+1), Genes.genes(gen, k),
                "Alternating characters A, U, C, G, with k=" + k + " should return " + (n-k+1) + gen);


        char[] shifted_bases = {'U', 'C', 'G', 'A'};
        gen = new AlternatingCharGenerator(shifted_bases, (n/4)*4, (n < 100));
        if( n >= 1000000 ) {
            assertEquals(996268, Genes.genes(gen, 1),
                    "Alternating characters U, C, G, A, with k=1 should return " + 996268 + " " + gen);
        }else if( n >= 100000 ) { // hard-coding for specific value of n, will break for larger n
            assertEquals(99627, Genes.genes(gen, 1),
                    "Alternating characters U, C, G, A, with k=1 should return " + 99627 + " " + gen);
        }
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
                System.out.println( "Testing genes() correctness all same character");
                for( int i=0; i < sizes.length; i++) {
                    allSame(sizes[i], sizes[i]);
                }
                for( int i=0; i < sizes.length; i++ ) {
                    allSame(sizes[i], sizes[i]+2);
                }
                break;
            case 1:
                System.out.println( "Testing genes() correctness input alternating chars...");
                for( int i=0; i < sizes.length; i++) {
                    alternatingChar(sizes[i]);
                }
                break;
            case 2:
                System.out.println( "Testing genes() correctness edge case empty strand...");
                empty(); // empty strand
                break;
            case 3:
                int size=5000;
                System.out.println( "Testing genes() O(n^2) time performance n~" + size + ", variety of k...");
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
                System.out.println( "Testing genes() O(nk^2) time performance n~" + size + ", k < 200...");
                System.out.println( "\tYou pass this test if it executes within 4 seconds.");
                System.out.println( "\tIf it's taking forever, try CTRL-C or CTRL-Z to stop it.");
                allSame(size, 100);
                alternatingChar(size, 100);
                for( int k=100; k < 200; k+=20 ) { // ~5 iterations
                    allSame(size, k);
                }
                break;
            case 5:
                size=13000;
                System.out.println( "Testing genes() O(nk) time performance n~" + size + ", variety of k...");
                System.out.println( "\tYou pass this test if it executes within 2 seconds.");
                System.out.println( "\tIf it's taking forever, try CTRL-C or CTRL-Z to stop it.");
                randomChar(size, size*100);
                for( int k=size/2; k < size; k+=(size/20) ) { // ~10 iterations
                    allSame(size, k);
                }
                break;
            case 6:
                size=1000000;
                System.out.println( "Testing genes() O(n) time performance n~" + size + ", variety of k...");
                System.out.println( "\tYou pass this test if it executes within 2 seconds.");
                System.out.println( "\tIf it's taking forever, try CTRL-C or CTRL-Z to stop it.");
                randomChar(size, size*100);
                for( int k=size/2; k < size; k+=(size/10) ) { // ~5 iterations
                    randomChar(size, k);
                }
                break;
            case 7:
                System.out.println( "If you didn't already, run this test (7) with -Xmx8m");
                size=10000;
                System.out.println( "Testing genes() O(n) space performance n~" + size + "~k...");
                allSame(size, 20);
                alternatingChar(size, 20);
                break;
            case 8:
                System.out.println( "If you didn't already, run this test (8) with -Xmx700m");
                size=1000000;
                System.out.println( "Testing genes() O(k+d) space performance n~" + size + ", k~10...");
                System.out.println( "\tThis test may take >2 seconds.");
                allSame(size, 20);
                alternatingChar(size, 20);
                break;
            default:
                System.out.println("To run an individual test: java GenesTest [test number]");
                System.out.println("To run all tests: java GenesTest");
                System.out.println("These are LIMITED local tests; add more to test more thoroughly.");
                for( int i=0; i < 9; i++ ) {
                    main(new String[]{"" + i});
                }

                System.exit(-1);
        }
        long stop = System.nanoTime();
        System.out.println("Execution time: " + 1e-9 * (stop-start));    }

}
