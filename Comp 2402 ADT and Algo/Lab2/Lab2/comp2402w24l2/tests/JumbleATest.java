package comp2402w24l2.tests;

// From within the Lab directory (or whichever directory contains the comp2402w24l2 directory)
// Assumes junit-platform-console-standalone-1.10.1.jar
// is in the Lab/lib/ directory.
// This is the command that works on a linux-based system (e.g. mac os)
// javac -cp "./lib/*:." comp2402w24l2/tests/JumbleATest.java
// java -cp "./lib/*:." comp2402w24l2/tests/JumbleATest


import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import comp2402w24l2.JumbleA;
import comp2402w24l2.Jumble;
import input.InputGenerator;
import input.AlternatingCharGenerator;

public class JumbleATest {

    public static Jumble getJumble() {
         return new JumbleA();
    }

    private static char[] bases = {'A', 'U', 'C', 'G'};

    @Test
    public static void allSame(int n) {
        System.out.println("\tTesting jumble() all same character n=" + n + "...");
        Jumble jum = getJumble();
        for( int i=0; i < bases.length; i++ ) {
            StringBuffer sb = new StringBuffer();
            for( int j=0; j < n; j++ ) { sb.append(bases[i]); }
            InputGenerator<Character> gen1 = new AlternatingCharGenerator(sb.toString().toCharArray(), n);
            assertEquals(sb.toString(), jum.jumble(gen1),
                    "All " + bases[i] + "s " + gen1);
        }
    }

    @Test
    public static void alternatingChar(int n) {
        System.out.println("\tTesting jumble() alternating characters, e.g. AUCAUCAUC... n~" + n + "...");
        char[] rew = {'A', 'U', 'C'};
        n = (n/3)*3; // make it a multiple of 3 for simplicity
        InputGenerator<Character> gen = new AlternatingCharGenerator(rew, n, (n < 200));
        Jumble jum = getJumble();
        StringBuffer sb = new StringBuffer();
        for( int i=0; i < n-12; i++ ) { sb.append(rew[i%3]); }
        sb.append("AAUCUAUCAUCC");

        // 6: AUCAUC
        //    AUAUCC
        // 9: AUCAUCAUC
        //    AUAUCAUCC
        // 12: AUCAUCAUCAUC
        //     AAUCUAUCAUCC
        // 15+: (AUC)*AAUCUAUCAUCC

        String output = jum.jumble(gen);
        assertEquals(sb.toString(), output,
                "Alternating characters A, U, C, with n%3=0 " + gen);
    }

    @Test
    public static void rewFFChar() {
        System.out.println("\tTesting jumble() alternating rew, ff, ... n~" + 21 + "...");

        String s = "AAAAUCUUUAUCGGGGGAUGA";
        String t = "AAAGGGGGAUGUUUAUACAUC";
        InputGenerator<Character> gen = new AlternatingCharGenerator(s.toCharArray(), s.length(), (s.length() < 200));
        Jumble jum = getJumble();

        String output = jum.jumble(gen);
        assertEquals(t, output,
                "Some rewind, a ff " + gen);

        s = "AAAAAAAAAAAAAAAAAAUCUUUUUUUUUUUUUUUUUAUCGAUGGG";
        t = "AAAAAAAAAAGAUGUUUUUUUUUUUGGUUUUUUAUCAAAAAAAAUC";
        gen = new AlternatingCharGenerator(s.toCharArray(), s.length(), (s.length() < 200));
        output = jum.jumble(gen);
        assertEquals(t, output,
                "Some rewinds, a ff " + gen);

        s = "AAAAAAAAAAAAAAAAAAUCUUUUUUUUUUUUUUUUUAUCGAUGGGAUCAUGAUAUAU";
        t = "AAAAAAAUGAAAAGAUGUUUUUAUAUAUUUUUUUGGAUCUUUUUUAUCAAAAAAAAUC";
        gen = new AlternatingCharGenerator(s.toCharArray(), s.length(), (s.length() < 200));
        output = jum.jumble(gen);
        assertEquals(t, output,
                "Some rewinds, a ff " + gen);

    }



    public static void main(String[] args) {
        int n=1000;
        char c = 'A';
        try {
            if (args.length >= 1) {
                n = Integer.parseInt(args[0]);
            }
            if (args.length >= 2) {
                c = args[1].charAt(0);
                if( c != 'B') {
                    c = 'B';
                }
            }
        } catch (NumberFormatException e) {
            System.err.println(e);
            System.exit(-2);
        }

        int[] sizes = {12, 100, 1000, 2000};
        long start = System.nanoTime();
        switch (n) {
            case 0:
                System.out.println( "Testing jumble() correctness input all same...");
                for( int i=0; i < sizes.length; i++) {
                    allSame(sizes[i]);
                }
                break;
            case 1:
                System.out.println( "Testing jumble() correctness input alternating...");
                for( int i=0; i < sizes.length; i++) {
                    alternatingChar(sizes[i]);
                }
                break;
            case 2:
                System.out.println( "Testing jumble() correctness edge case empty strand...");
                allSame(0);
                break;
            case 3:
                int size=10000;
                System.out.println( "Testing jumble() O(n^2) time, only rewinds, adding near front, size=" + size + "...");
                System.out.println( "You pass this test if it executes within 2 seconds.");
                System.out.println( "If it's taking forever, try CTRL-C or CTRL-Z to stop it.");
                allSame(size);
                alternatingChar(size);
                break;
            case 4:
                size=600000;
                System.out.println( "Testing jumble() O(n) time, only rewinds, adding near front size=" + size + "...");
                System.out.println( "You pass this test if it executes within 2 seconds.");
                System.out.println( "If it's taking forever, try CTRL-C or CTRL-Z to stop it.");
                allSame(size);
                alternatingChar(size);
                break;
            case 5:
                size=1000000;
                System.out.println( "Testing jumble() O(n) time only rewinds size=" + size + "...");
                System.out.println( "You pass this test if it executes within 2 seconds.");
                System.out.println( "If it's taking forever, try CTRL-C or CTRL-Z to stop it.");
                allSame(size);
                alternatingChar(size);
                break;
            case 6:
                size=1000000;
                System.out.println( "Testing jumble() O(n) space size=" + size + "...");
                allSame(size);
                break;
            case 7: // rewinds and ff
                System.out.println( "Testing jumble() rewinds and ff...");
                rewFFChar();
                break;
            default:
                System.out.println("To run an individual test: java JumbleTest [test number]");
                for( int i=0; i < 8; i++ ) {
                    main(new String[]{"" + i});
                }
                System.exit(0);
        }
        long stop = System.nanoTime();
        System.out.println("Execution time: " + 1e-9 * (stop-start));    }

}
