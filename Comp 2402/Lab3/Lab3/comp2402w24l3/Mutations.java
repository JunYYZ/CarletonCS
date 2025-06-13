package comp2402w24l3;

// From within the Lab directory (or wherever you put the comp2402w24l3 directory):
// javac comp2402w24l3/Mutations.java
// java comp2402w24l3/Mutations

// You will submit this file to the autograder.
// It will not pass many of the tests, if any.
// Your task is to modify the mutations method to pass the tests.

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

public class Mutations {

	// i no idea what this even is.
	public static int mutations(InputGenerator<Character> gen, int k) {
		SkiplistSSet<String> strands = new SkiplistSSet<>();
		StringBuilder currentStrand = new StringBuilder();
		int mutationCount = 0;
		SkiplistSSet<String> mutations = new SkiplistSSet<>();

		while (gen.hasNext() && currentStrand.length() < k) {
			currentStrand.append(gen.next());
		}

		if (currentStrand.length() < k)
			return 0;

		strands.add(currentStrand.toString());


		while (gen.hasNext()) {

			currentStrand.deleteCharAt(0);
			currentStrand.append(gen.next());

			String strand = currentStrand.toString();
			String closestMutation = strands.find(strand);


			if (closestMutation != null && !closestMutation.equals(strand) && mutations.add(closestMutation)) {
				mutationCount++;
			}

			strands.add(strand);
		}

		return mutationCount;
	}

	// These are a few examples of how to use the InputGenerator to run local tests
	// You should test more extensively than this.
	public static void main(String[] args) {
		System.out.println("Testing mutations() via Mutations.main...");
		System.out.println("You should also try testing via tests/MutationsTest.java");

		InputGenerator<Character> gen = new FileCharGenerator();

		// The following tests mutations using the chars in the
		// file samples/mutations-sample.txt, up to the first newline.
		gen = new FileCharGenerator("Lab3/samples/mutations-sample.txt");
		System.out.println(mutations(gen, 3)); // Expect 3

		// If you want to test via the command-line, i.e. if you want to input a stream
		// of
		// characters of your own devising and then run mutations once you hit 'Enter',
		// then
		// uncomment the next three lines.
		// gen = new FileCharGenerator();
		// System.out.println( "Enter a sequence of characters, then hit Enter to run
		// mutations(gen, 3):" );
		// System.out.println( "Your result is: " + mutations(gen, 3) );

		// The following tests use a generate that alternates between the characters
		// listed in the
		// options array. The second argument is the number of characters to generate.
		char[] options = { 'U', 'G', 'C', 'A' };
		gen = new AlternatingCharGenerator(options, 4); // Generates UGCA
		System.out.println(mutations(gen, 4)); // Expect 0
		gen = new AlternatingCharGenerator(options, 5, true); // Generates UGCAU -- uses true to store what was
																// generated
		System.out.println(mutations(gen, 4)); // Expect 1
		gen = new AlternatingCharGenerator(options, 6, true); // Generates UGCAUG
		System.out.println(mutations(gen, 4)); // Expect 2
		gen = new AlternatingCharGenerator(options, 7, true); // Generates UGCAUGC
		System.out.println(mutations(gen, 4)); // Expect 3
		gen = new AlternatingCharGenerator(options, 12, true); // Generates UGCAUGCAUGCA
		System.out.println(mutations(gen, 1)); // Expect 3

	}

}
