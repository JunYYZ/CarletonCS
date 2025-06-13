package comp2402w24l4;

// From within the Lab directory (or wherever you put the comp2402w24l4 directory):
// javac comp2402w24l4/Genes.java
// java comp2402w24l4/Genes

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
import ods.RedBlackTree;
import ods.MultiplicativeHashSet;

import java.util.ListIterator;
import java.util.Iterator;
import java.util.AbstractSet;
import java.util.HashSet;
import java.util.HashMap;

public class Genes {

	public static int genes(InputGenerator<Character> gen, int k) {
		HashSet<Integer> uStrands = new HashSet<>();
		ArrayDeque<Character> window = new ArrayDeque<>();
		int index = 0;
		int chc = 0;

		while (gen.hasNext()) {
			Character c = gen.next();
			if (window.size() == k) {
				Character removed = window.remove(0);
				chc -= removed * (index - k);
			}

			window.add(c);
			chc += c * index;

			if (window.size() == k) {
				uStrands.add(chc);
			}

			index++;
		}

		return uStrands.size();
	}

	// These are a few examples of how to use the InputGenerator to run local tests
	// You should test more extensively than this.
	public static void main(String[] args) {
		System.out.println("Testing genes() via Genes.main...");
		System.out.println("These are limited local tests; please add more tests of your own!");
		System.out.println("You should also try testing via tests/GenesTest.java");

		InputGenerator<Character> gen = new FileCharGenerator();

		// The following tests genes using the chars in the
		// file samples/genes-sample.txt, up to the first newline.
		gen = new FileCharGenerator("Lab4/samples/genes-sample.txt");
		System.out.println("Expect 10: " + genes(gen, 3)); // Expect 10

		// If you want to test via the command-line, i.e. if you want to input a stream
		// of
		// characters of your own devising and then run genes once you hit 'Enter', then
		// uncomment the next three lines.
		// gen = new FileCharGenerator();
		// System.out.println( "Enter a sequence of characters, then hit Enter to run
		// genes(gen, 3):" );
		// System.out.println( "Your result is: " + genes(gen, 3) );

		// The following tests use a generate that alternates between the characters
		// listed in the
		// options array. The second argument is the number of characters to generate.
		char[] options = { 'A', 'U', 'C', 'G' };

		gen = new AlternatingCharGenerator(options, 12); // Generates AUCGAUCGAUCG
		System.out.println("Expect 9: " + genes(gen, 4)); // Expect 9
		gen = new AlternatingCharGenerator(options, 10, true); // Generates AUCGAUCGAU -- uses true to store what was
																// generated
		System.out.println("Expect 2: " + genes(gen, 9)); // Expect 2
		gen = new AlternatingCharGenerator(options, 11, true); // Generates AUCGAUCGAUC -- uses true to store what was
																// generated
		System.out.println("Expect 9: " + genes(gen, 3)); // Expect 9
		gen = new AlternatingCharGenerator(options, 11, true); // Generates AUCGAUCGAUC -- uses true to store what was
																// generated
		System.out.println("Expect 11: " + genes(gen, 1)); // Expect 11
		gen = new AlternatingCharGenerator(options, 11, true); // Generates AUCGAUCGAUC -- uses true to store what was
																// generated
		System.out.println("Expect 0: " + genes(gen, 12)); // Expect 0

		// Don't forget to add your own local tests to test your method more thoroughly!

	}

}
