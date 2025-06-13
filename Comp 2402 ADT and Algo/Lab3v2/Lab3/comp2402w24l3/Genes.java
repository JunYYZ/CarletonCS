package comp2402w24l3;

// From within the Lab directory (or wherever you put the comp2402w24l3 directory):
// javac comp2402w24l3/Genes.java
// java comp2402w24l3/Genes

// You will submit this file to the autograder.
// It will not pass many of the tests, if any.
// Your task is to modify the genes method to pass the tests.

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
import ods.SkiplistList;
import ods.SkiplistSSet;
import ods.BinaryTree;
import ods.BinarySearchTree;
import ods.Treap;
import ods.ScapegoatTree;
import ods.SSet;

import java.util.ListIterator;
import java.util.Iterator;

public class Genes {

    public static int genes(InputGenerator<Character> gen, int k) {
        // TODO(student): Your code goes here
        while( gen.hasNext() ) { // This loops through the entire input
            Character c = gen.next();
        }
        return -1;
    }

    // These are a few examples of how to use the InputGenerator to run local tests
    // You should test more extensively than this.
    public static void main(String[] args ) {
        System.out.println("Testing genes() via Genes.main...");
        System.out.println("You should also try testing via tests/GenesTest.java");

        InputGenerator<Character> gen = new FileCharGenerator();

        // The following tests genes using the chars in the
        // file samples/genes-sample.txt, up to the first newline.
        gen = new FileCharGenerator( "samples/genes-sample.txt" );
        System.out.println( genes(gen, 3) ); // Expect 8 

        // If you want to test via the command-line, i.e. if you want to input a stream of
        // characters of your own devising and then run genes once you hit 'Enter', then
        // uncomment the next three lines.
        // gen = new FileCharGenerator();
        // System.out.println( "Enter a sequence of characters, then hit Enter to run genes(gen, 3):" );
        // System.out.println( "Your result is: " + genes(gen, 3) );

        // The following tests use a generate that alternates between the characters listed in the
        // options array.  The second argument is the number of characters to generate.
        char[] options = {'A','U','C','G'};
        gen = new AlternatingCharGenerator(options, 12); // Generates AUCGAUCGAUCG
        System.out.println( genes(gen, 4) ); // Expect 4
        gen = new AlternatingCharGenerator(options, 10, true); // Generates AUCGAUCGAU -- uses true to store what was generated
        System.out.println( genes(gen, 9) ); // Expect 2 
        gen = new AlternatingCharGenerator(options, 11, true); // Generates AUCGAUCGAUC -- uses true to store what was generated
        System.out.println( genes(gen, 3) ); // Expect 4
        gen = new AlternatingCharGenerator(options, 11, true); // Generates AUCGAUCGAUC -- uses true to store what was generated
        System.out.println( genes(gen, 1) ); // Expect 4 
        gen = new AlternatingCharGenerator(options, 11, true); // Generates AUCGAUCGAUC -- uses true to store what was generated
        System.out.println( genes(gen, 12) ); // Expect 0 
    }

}
