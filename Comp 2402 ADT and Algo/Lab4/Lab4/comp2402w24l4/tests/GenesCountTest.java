package comp2402w24l4.tests;

// From within the Lab directory (or whichever directory contains the comp2402w24l4 directory)
// Assumes junit-platform-console-standalone-1.10.1.jar
// is in the Lab/lib/ directory.
//
// This is the command that works on a linux-based system (e.g. mac os)
// javac -cp "./lib/*:." comp2402w24l4/tests/GenesCountTest.java
// java -cp "./lib/*:." comp2402w24l4/tests/GenesCountTest
//
// On a windows machine, this should work:
// javac -cp "./lib/*;." comp2402w24l4/tests/GenesCountTest.java
// java -cp "./lib/*;." comp2402w24l4/tests/GenesCountTest


import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import comp2402w24l4.GenesCount;
import input.InputGenerator;
import input.AlternatingCharGenerator;
import input.RandomCharGenerator;

import java.util.Arrays;
import java.util.AbstractList;

public class GenesCountTest {

    private static char[] bases = {'A', 'U', 'C', 'G'};

    @Test
    public static void allSame(int n, int k) {
        System.out.println("\tTesting genesCount() all same character n=" + n + ", k=" + k + "...");
        for( int i=0; i < bases.length; i++ ) {
            char[] all_same = { bases[i] };
            InputGenerator<Character> gen = new AlternatingCharGenerator( all_same, n, (n<100));
            StringBuffer sb = new StringBuffer();
            for( int j=0; j < k; j++ )  sb.append(bases[i]); // python, I miss you!
            if( k <= n ) {
                String[] expected = {sb.toString()};
                assertEquals(Arrays.asList(expected), GenesCount.genesCount(gen, k), "All same character k <= n should be a single element" + gen);
            } else {
                String[] expected = {};
                assertEquals(Arrays.asList(expected), GenesCount.genesCount(gen, k), "All same character k > n should be empty" + gen);
            }
        }
    }

    @Test
    public static void alternatingChar(int n) {
        System.out.println("\tTesting genesCount() alternating characters, e.g. AUCGAUCGAUCG n~" + n + "...");

        InputGenerator<Character> gen = new AlternatingCharGenerator(bases, n, (n < 100));
        String[] exp = {"AUCG", "UCGA", "CGAU", "GAUC"};
        assertEquals(Arrays.asList(exp), GenesCount.genesCount(gen, 4),
                "Alternating characters A, U, C, G, with k=4 " + gen);

        gen = new AlternatingCharGenerator(bases, n, (n < 100));
        exp = new String[]{"AUCGAUCG", "UCGAUCGA", "CGAUCGAU", "GAUCGAUC"};
        assertEquals(Arrays.asList(exp), GenesCount.genesCount(gen, 8),
                "Alternating characters A, U, C, G, with k=8 " + gen);


        gen = new AlternatingCharGenerator(bases, n, true);
        AbstractList<String> actual = GenesCount.genesCount(gen, n);
        exp = new String[]{gen.toString()};
        assertEquals(Arrays.asList(exp), actual,
                "Alternating characters A, U, C, G, with k=n " + gen);

        gen = new AlternatingCharGenerator(bases, n, true);
        actual = GenesCount.genesCount(gen, n-1);
        exp = new String[]{gen.toString().substring(0,n-1), gen.toString().substring(1)};
        assertEquals(Arrays.asList(exp), actual,
                "Alternating characters A, U, C, G, with k=n-1 " + gen);

    }

    @Test
    public static void empty() {
        System.out.println("\tTesting genesCount() empty sequence...");
        InputGenerator<Character> gen = new AlternatingCharGenerator(bases, 0, true);
        gen = new AlternatingCharGenerator(bases, 0, true);
        String[] exp = {};
        assertEquals(Arrays.asList(exp), GenesCount.genesCount(gen, 10),
                "Empty sequence with k>0 should return 0 " + gen);
    }


    public static void randomChar(int n, int k) {
        System.out.println("\tTesting genesCount() random input n~" + n + ", k~" + k + "...");
        InputGenerator<Character> gen = new RandomCharGenerator(bases, n, (n < 100));
        // we can't know what the output should be, but it's good for performance tests
        GenesCount.genesCount(gen, k);
    }

    @Test
    public static void alternatingChar(int n, int k) {
        System.out.println("\tTesting genesCount() alternating characters, e.g. AUCGAUCGAUCG n~" + n + "...");
        StringBuffer sb = new StringBuffer();
        for( int i=0; i < k+3; i++ ) {
            sb.append(bases[i%4]);
        }
        String s = sb.toString();
        String[] exp = {s.substring(0,k), s.substring(2,k+2), s.substring(3), s.substring(1,k+1)};
        InputGenerator<Character> gen = new AlternatingCharGenerator(bases, (n/4)*4, (n < 100));
        assertEquals(Arrays.asList(exp), GenesCount.genesCount(gen, k),
                "Alternating characters A, U, C, G, with k=" + k + " " + gen);
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

        int[] sizes = {13, 65, 64*8+1};
        long start = System.nanoTime();
        switch (n) {
            case 0:
                System.out.println( "Testing genesCount() correctness all same character");
                for( int i=0; i < sizes.length; i++) {
                    allSame(sizes[i], sizes[i]);
                }
                for( int i=0; i < sizes.length; i++ ) {
                    allSame(sizes[i], sizes[i]+2);
                }
                break;
            case 1:
                System.out.println( "Testing genesCount() correctness input alternating chars...");
                for( int i=0; i < sizes.length; i++) {
                    alternatingChar(sizes[i]);
                }
                break;
            case 2:
                System.out.println( "Testing genesCount() correctness edge case empty strand...");
                empty(); // empty strand
                break;
            case 3:
                int size=5001;
                System.out.println( "Testing genesCount() O(n^2 k) time performance n~" + size + ", variety of k...");
                System.out.println( "\tYou pass this test if it executes within 2 seconds.");
                System.out.println( "\tIf it's taking forever, try CTRL-C or CTRL-Z to stop it.");
                allSame(size, size);
                alternatingChar(size);
                for( int k=3; k < 2*size; k*=2 ) { // ~log_2(10,000)~=14 iterations
                    allSame(size, k);
                }
                break;
            case 4:
                size=20001;
                System.out.println( "Testing genesCount() O(nk^2) time performance n~" + size + ", k < 2,000...");
                System.out.println( "\tYou pass this test if it executes within 2 seconds.");
                System.out.println( "\tIf it's taking forever, try CTRL-C or CTRL-Z to stop it.");
                allSame(size, 1000);
                for( int k=1000; k < 2000; k+=100 ) { // ~10 iterations
                    allSame(size, k);
                }
                break;
            case 5:
                size=13000;
                System.out.println( "Testing genesCount() O(nk^2) time performance n~" + size + ", variety of k...");
                System.out.println( "\tYou pass this test if it executes within 2 seconds.");
                System.out.println( "\tIf it's taking forever, try CTRL-C or CTRL-Z to stop it.");
                randomChar(size, size*100);
                for( int k=size/2; k < size; k+=(size/20) ) { // ~10 iterations
                    randomChar(size, k);
                }
                break;
            case 6:
                System.out.println( "If you didn't already, run this test (6) with -Xmx4m");
                size=10020;
                System.out.println( "Testing genesCount() O(n) space performance n~" + size + "~k...");
                allSame(size, 20);
                alternatingChar(size, 20);
                break;
            case 7:
                System.out.println( "If you didn't already, run this test (7) with -Xmx4m");
                size=1000020;
                System.out.println( "Testing genesCount() O(kd) space performance n~" + size + ", k~10...");
                allSame(size, 20);
                alternatingChar(size, 20);
                break;

            default:
                System.out.println("To run an individual test: java genesCountTest [test number]");
                for( int i=0; i < 8; i++ ) {
                    main(new String[]{"" + i});
                }

                System.exit(-1);
        }
        long stop = System.nanoTime();
        System.out.println("Execution time: " + 1e-9 * (stop-start));    }

}
