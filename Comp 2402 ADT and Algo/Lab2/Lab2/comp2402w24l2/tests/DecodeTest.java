package comp2402w24l2.tests;

// From within the Lab directory (or whichever directory contains the comp2402w24l2 directory)
// Assumes junit-platform-console-standalone-1.10.1.jar
// is in the Lab/lib/ directory.
// This is the command that works on a linux-based system (e.g. mac os)
// javac -cp "./lib/*:." comp2402w24l2/tests/DecodeTest.java
// java -cp "./lib/*:." comp2402w24l2/tests/DecodeTest


import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import comp2402w24l2.Decode;
import input.InputGenerator;
import input.AlternatingCharGenerator;

public class DecodeTest {

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
                System.out.println( "Testing decode() correctness edge case empty strand...");
                char[] bases = {'A', 'U', 'C', 'G'};
                InputGenerator<Character> gen = new AlternatingCharGenerator(bases, 0, true);
                assertEquals("", Decode.decode(gen) );
                break;
            case 1:
                System.out.println( "Testing decode() correctness input all A's...");
                bases = new char[]{'A'};
                gen = new AlternatingCharGenerator(bases, 8, true);
                assertEquals("UCUA", Decode.decode(gen) );
                gen = new AlternatingCharGenerator(bases, 10, true);
                assertEquals("GCGA", Decode.decode(gen) );
                gen = new AlternatingCharGenerator(bases, 64, true);
                assertEquals( "CGACCUAACGA", Decode.decode(gen) );
                gen = new AlternatingCharGenerator(bases, 64*64, true);
                assertEquals( "CGACCUAACGACCUAACGACCUAACGACCUAACGACCUAACGACCUAACGACCUAACGACCUAACGACCUAACGACCUAACGACCUAACGA",
                        Decode.decode(gen) );
                break;
            case 2:
                System.out.println( "Testing decode() correctness input all C's...");
                bases = new char[]{'C'};
                gen = new AlternatingCharGenerator(bases, 8, true);
                assertEquals("GCGA", Decode.decode(gen) );
                gen = new AlternatingCharGenerator(bases, 10, true);
                assertEquals("UCUA", Decode.decode(gen) );
                gen = new AlternatingCharGenerator(bases, 64, true);
                assertEquals( "CUACCGAACUA", Decode.decode(gen) );
                gen = new AlternatingCharGenerator(bases, 64*64, true);
                assertEquals( "CUACCGAACUACCGAACUACCGAACUACCGAACUACCGAACUACCGAACUACCGAACUACCGAACUACCGAACUACCGAACUACCGAACUA",
                        Decode.decode(gen) );
                break;
            case 3:
                System.out.println( "Testing decode() correctness input  AUCGAUCG's...");
                bases = new char[]{'A', 'U', 'C', 'G'};
                gen = new AlternatingCharGenerator(bases, 8, true);
                assertEquals("UCUA", Decode.decode(gen) );
                gen = new AlternatingCharGenerator(bases, 10, true);
                assertEquals("GCGA", Decode.decode(gen) );
                gen = new AlternatingCharGenerator(bases, 64, true);
                assertEquals( "CUAACGAACUA", Decode.decode(gen) );
                gen = new AlternatingCharGenerator(bases, 64*64, true);
                assertEquals( "CUAACGAACUAACGAACUAACGAACUAACGAACUAACGAACUAACGAACUAACGAACUAACGAACUAACGAACUAACGAACUAACGAACUA",
                        Decode.decode(gen) );
                break;
            case 4:
                int size=100000;
                System.out.println( "Testing decode() O(n^2) time performance size=" + size + "...");
                System.out.println( "You pass this test if it executes within 2 seconds.");
                System.out.println( "If it's taking forever, try CTRL-C or CTRL-Z to stop it.");
                Decode.decode(new AlternatingCharGenerator(new char[]{'A', 'U', 'C', 'G'}, size, true));
                break;
            case 5:
                size=1000000;
                System.out.println( "Testing decode() O(n^(3/2)) time performance size=" + size + "...");
                System.out.println( "You pass this test if it executes within 2 seconds.");
                System.out.println( "If it's taking forever, try CTRL-C or CTRL-Z to stop it.");
                Decode.decode(new AlternatingCharGenerator(new char[]{'A', 'U', 'C', 'G'}, size, true));
                break;
            case 6:
                size=20000000;
                System.out.println( "Testing decode() O(n) time performance size=" + size + "...");
                System.out.println( "You pass this test if it executes within 2 seconds.");
                System.out.println( "If it's taking forever, try CTRL-C or CTRL-Z to stop it.");
                Decode.decode(new AlternatingCharGenerator(new char[]{'A', 'U', 'C', 'G'}, size, true));
                break;
            case 7: // performance space tests
                System.out.println( "If you didn't already, run this test (7) with -Xmx12m");
                size=1000000;
                System.out.println( "Testing decode() O(n) space performance size=" + size + "...");
                Decode.decode(new AlternatingCharGenerator(new char[]{'A', 'U', 'C', 'G'}, size, true));
                break;
            default:
                System.out.println("To run an individual test: java DecodeTest [test number]");
                for( int i=0; i < 7; i++ ) {
                    main(new String[]{"" + i});
                }

                System.exit(-1);
        }
        long stop = System.nanoTime();
        System.out.println("Execution time: " + 1e-9 * (stop-start));    }

}
