package comp2402w24l2;

// From within the Lab directory (or wherever you put the comp2402w24l2 directory):
// javac comp2402w24l2/Decode.java
// java comp2402w24l2/Decode

// You will submit this file to the autograder.
// It will not pass many of the tests, if any.
// Your task is to modify the decode method to pass the tests.

// Do not add any imports.
// (You shouldn't need any, and you'll fail the autograder if you do.)
import input.InputGenerator;
import input.FileCharGenerator;
import input.AlternatingCharGenerator;
import ods.ArrayDeque;
import ods.ArrayQueue;
import ods.ArrayStack;
import ods.RootishArrayStack;
import ods.SLList;
import ods.DLList;
import ods.SEList;


public class Decode  {

    char[] bases = {'A','U','C','G'};
    public static String decode(InputGenerator<Character> gen) {
        // TODO(student): Your code goes here
        while( gen.hasNext() ) { // This loops through the entire input
            Character c = gen.next();
        }
        return "bogus";
    }

    // These are a few examples of how to use the InputGenerator to run local tests
    // You should test more extensively than this.
    public static void main(String[] args ) {
        System.out.println("Testing decode() via Decode.main...");
        System.out.println("You should also try testing via tests/DecodeTest.java");

        InputGenerator<Character> gen = new FileCharGenerator();

        // The following tests countA using the chars in the
        // file samples/countA-sample.txt, up to the first newline.
        gen = new FileCharGenerator( "samples/decode-sample.txt" );
        System.out.println( decode(gen) ); // Expect AUCAA

        
        // If you want to test via the command-line, i.e. if you want to input a stream of
        // characters of your own devising and then run countA once you hit 'Enter', then
        // uncomment the next three lines.
        // gen = new FileCharGenerator();
        // System.out.println( "Enter a sequence of characters, then hit Enter to run decode(gen):" );
        // System.out.println( "Your result is: " + decode(gen) );


        char[] options = {'A'};
        gen = new AlternatingCharGenerator(options, 10, true);
        System.out.println( decode(gen) ); // Expect GCGA

        gen = new AlternatingCharGenerator(options, 11, true);
        System.out.println( decode(gen) ); // Expect AGACA

        gen = new AlternatingCharGenerator(options, 12, true);
        System.out.println( decode(gen) ); // Expect AUCCA


        options = new char[]{'C'};
        gen = new AlternatingCharGenerator(options, 10, true);
        System.out.println( decode(gen) ); // Expect UCUA

        gen = new AlternatingCharGenerator(options, 11, true);
        System.out.println( decode(gen) ); // Expect AUACA


        options = new char[]{'A','U','C','G'};
        gen = new AlternatingCharGenerator(options, 12); // Generates AUCGAUCGAUCG
        System.out.println( decode(gen) ); // Expect AGCAA

        gen = new AlternatingCharGenerator(options, 10, true); // Generates AUCGAUCGAU -- uses true to store what was generated
        System.out.println( decode(gen) ); // Expect GCGA
         gen = new AlternatingCharGenerator(options, 11, true); // Generates AUCGAUCGAUC -- uses true to store what was generated
        System.out.println( decode(gen) ); // Expect AGAAA
        gen = new AlternatingCharGenerator(options, 13, true); // Generates AUCGAUCGAUC -- uses true to store what was generated
        System.out.println( decode(gen) ); // Expect AGAAA

    }

}
