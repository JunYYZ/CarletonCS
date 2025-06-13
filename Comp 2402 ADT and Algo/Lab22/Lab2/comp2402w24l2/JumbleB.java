package comp2402w24l2;

// From within the Lab directory (or wherever you put the comp2402w24l2 directory):
// javac comp2402w24l2/JumbleB.java
// java comp2402w24l2/JumbleB

// You will submit this file to the autograder.
// It will not pass many of the tests, if any.
// Your task is to modify the jumble method to pass the tests.

// Do not add any imports.
// (You shouldn't need any, and you'll fail the autograder if you do.)
import input.InputGenerator;
import input.FileCharGenerator;
import input.AlternatingCharGenerator;
import ods.SLList;
import ods.DLList;
import ods.SEList;
import java.util.ListIterator;
import java.util.Iterator;

public class JumbleB extends Jumble {

    public String jumble(InputGenerator<Character> gen) {
        // TODO(student): Your code goes here
        // This implementation is incorrect.

        StringBuilder sb = new StringBuilder();
        while( gen.hasNext() ) { // This just loops through the generated input
            Character c = gen.next();
            sb.append(c);
        }
        return "bogus"+sb.toString();
    }
    

    // These are a few examples of how to use the InputGenerator to run local tests
    // You should test more extensively than this.
    public static void main(String[] args ) {
        System.out.println("Testing jumble() via JumbleB.main...");
        System.out.println("You should also try testing via tests/JumbleBTest.java");

        Jumble jum = new JumbleB();
        InputGenerator<Character> gen = new FileCharGenerator();

        // The following tests jumbleB using the chars in the
        // file samples/jumble-sample.txt, up to the first newline.
        gen = new FileCharGenerator( "samples/jumble-sample.txt" );
        System.out.println( jum.jumble(gen) ); // Expect AUCUAAUCAUCUC

        // If you want to test via the command-line, i.e. if you want to input a stream of
        // characters of your own devising and then run jumbleB once you hit 'Enter', then
        // uncomment the next three lines.
        // gen = new FileCharGenerator();
        // System.out.println( "Enter a sequence of characters, then hit Enter to run jumble(gen):" );
        // System.out.println( "Your result is: " + jum.jumble(gen) );


        System.out.println( "\nTesting Rewind Only..." );

        char[] options = {'A','U','C','G'};
        gen = new AlternatingCharGenerator(options, 4); // Generates AUCG
        System.out.println( jum.jumble(gen) ); // Expect AUGC

        gen = new AlternatingCharGenerator(options, 5); // Generates AUCGA
        System.out.println( jum.jumble(gen) ); // Expect AUGAC

        gen = new AlternatingCharGenerator(options, 6); // Generates AUCGA
        System.out.println( jum.jumble(gen) ); // Expect AUGAUC

        gen = new AlternatingCharGenerator(options, 7); // Generates AUCGAUC
        System.out.println( jum.jumble(gen) ); // Expect AUGAUCC

        gen = new AlternatingCharGenerator(options, 8); // Generates AUCGAUCG
        System.out.println( jum.jumble(gen) ); // Expect AUGGAUCC

        gen = new AlternatingCharGenerator(options, 12);
        System.out.println( jum.jumble(gen) ); // Expect AUGGGAUCAUCC

        gen = new AlternatingCharGenerator(options, 16);
        System.out.println( jum.jumble(gen) ); // Expect GAUGAUCGGAUCAUCC

        gen = new AlternatingCharGenerator(options, 20);
        System.out.println( jum.jumble(gen) ); // Expect GGAUCAUGAUCGGAUCAUCC



        System.out.println( "\nTesting FF Only..." );
        options = new char[]{'A','U','G','C'};
        gen = new AlternatingCharGenerator(options, 4); // Generates AUGC
        System.out.println( jum.jumble(gen) ); // Expect AUGC

        gen = new AlternatingCharGenerator(options, 5); // Generates AUGCA
        System.out.println( jum.jumble(gen) ); // Expect AUGCA

        gen = new AlternatingCharGenerator(options, 6); // Generates AUGCA
        System.out.println( jum.jumble(gen) ); // Expect AUGCAU



        System.out.println( "\nTesting Rew/FF Alternating..." );
        options = new char[]{'A','U','C','G','A','U','G','C'};
        gen = new AlternatingCharGenerator(options, 8); // Generates AUCGAUGC
        System.out.println( jum.jumble(gen) ); // Expect AUGAUGCC

        gen = new AlternatingCharGenerator(options, 12); // Generates AUCGAUGCAUCG
        System.out.println( jum.jumble(gen) ); // Expect AUGAUGGCCAUC

        gen = new AlternatingCharGenerator(options, 13); // Generates AUCGAUGCAUCGA
        System.out.println( jum.jumble(gen) ); // Expect                 AUGAUGGACCAUC

        gen = new AlternatingCharGenerator(options, 16); // Generates AUCGAUGCAUCGAUGC
        System.out.println( jum.jumble(gen) ); // Expect                 AUGAUGGAUGCCACUC


        // Extra test, shows you how to make an array from a String, if helpful.
        String s = "ACCGAACGGUAC";
        options = s.toCharArray();
        System.out.println( jum.jumble(gen));

    }


}
