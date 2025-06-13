package comp2402w24l0.tests;


// From within the Lab directory (or whichever directory contains the comp2402w24l0 directory)
// Assumes junit-platform-console-standalone-1.10.1.jar
// is in the Lab/lib/ directory.
// This is the command that works on a linux-based system (e.g. mac os)
// javac -cp "./lib/*:." comp2402w24l0/tests/ContainsATest.java
// java -cp "./lib/*:." comp2402w24l0/tests/ContainsATest
// or, if doing an O(1) space test
// java -Xmx4m -cp "./lib/*:." comp2402w24l0/tests/ContainsATest


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.Random;
import org.junit.runner.JUnitCore;

import comp2402w24l0.ContainsA;
import input.InputGenerator;
import input.AlternatingCharGenerator;

public class ContainsATest {

    private static char[] bases = {'A', 'U', 'C', 'G'};

    @Test
    public static void allSame(int n) {
        //System.out.println("\nTesting containsA() all same character n=" + n + "...");

        for( int i=0; i < bases.length; i++ ) {
            char[] all_same = { bases[i] };
            InputGenerator<Character> gen = new AlternatingCharGenerator( all_same, n, (n<100));
            assertEquals((bases[i]=='A') && (n > 0), ContainsA.containsA(gen),
                    "All same character should return true only if that character is A " + gen);
        }
    }

    @Test
    public static void alternatingChar(int n) {
        //System.out.println("\nTesting containsA() alternating characters, e.g. AUCGAUCGAUCG n~" + n + "...");
        InputGenerator<Character> gen = new AlternatingCharGenerator(bases, (n/4)*4, (n < 100));
        assertEquals(true, ContainsA.containsA(gen), "Alternating characters A, U, C, G, with n%4=0 return true");
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
                System.out.println( "Testing containsA() correctness (0)...");
                for( int i=0; i < sizes.length; i++) {
                    allSame(sizes[i]);
                }
                break;
            case 1:
                System.out.println( "Testing containsA() correctness (1)...");
                for( int i=0; i < sizes.length; i++) {
                    alternatingChar(sizes[i]);
                }
                break;
            case 2:
                System.out.println( "Testing containsA() correctness edge case empty sequence...");
                allSame(0); // empty strand -- should be true.
                break;
            case 3: // performance time tests n=10,000, p=10,000
                int size=10000;
                System.out.println( "Testing containsA() O(n^2) time performance size=" + size + "...");
                System.out.println( "You pass this test if it executes within 2 seconds." );
                allSame(size);
                alternatingChar(size);
                break;
            case 4:// performance time tests n=100,000, p=100,000
                size=100000;
                System.out.println( "Testing containsA() O(n^2) time performance size=" + size + "...");
                System.out.println( "You pass this test if it executes within 2 seconds.");
                allSame(size);
                alternatingChar(size);
                break;
            case 5:// performance time tests n=1,000,000, p=1,000,000
                size=1000000;
                System.out.println( "Testing containsA() O(n) time performance size=" + size + "...");
                System.out.println( "You pass this test if it executes within 2 seconds.");
                System.out.println( "If it's taking forever, try CTRL-C or CTRL-Z to stop it.");
                allSame(size);
                alternatingChar(size);
                break;
            case 6:// performance time tests n=1,000,000, p=1
                size=1000000;
                System.out.println( "Testing containsA() O(p) time performance size=" + size + "...");
                System.out.println( "You will fail this test if you make no changes to the starter code.");
                System.out.println( "You pass this test if it executes within 2 seconds.");
                System.out.println( "If it's taking forever, try CTRL-C or CTRL-Z to stop it.");
                for( int i=0; i < size; i++ ) {
                    bases = new char[]{'U', 'U', 'U', 'C', 'G', 'A'};
                    alternatingChar(size); // if this is O(n) then this loop is O(n^2) and the test will take too long.
                }
                break;
            case 7: // performance space tests
                size=1000000;
                System.out.println( "Testing containsA() O(n=p) space performance size=" + size + "...");
                allSame(size);
                alternatingChar(size);
                break;
            case 8: // performance space tests -- use -Xmx4m
                System.out.println( "If you didn't already, run this with -Xmx4m");
                size=1000000;
                System.out.println( "Testing containsA() O(1) space performance size=" + size + "...");
                System.out.println( "You will fail this test if you make no changes to the starter code.");
                allSame(size);         // Should be O(1) space to pass test with -Xmx4m
                alternatingChar(size); // should be O(1) space to pass test with -Xmx4m
                break;
            default:
                System.out.println("To run an individual test: java ContainsATest [test number]");
                for( int i=0; i < 8; i++ ) {
                    main(new String[]{"" + i});
                }
        }
        long stop = System.nanoTime();
        System.out.println("Execution time: " + 1e-9 * (stop-start));    }

}
