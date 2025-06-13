package comp2402w24l4;

// From within the Lab directory (or wherever you put the comp2402w24l4 directory):
// javac comp2402w24l4/GenesOrder.java
// java comp2402w24l4/GenesOrder

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

import java.util.AbstractList;
import java.util.ListIterator;
import java.util.Iterator;
import java.util.AbstractSet;
import java.util.HashSet;
import java.util.HashMap;

public class GenesOrder {

	public static AbstractList<String> genesOrder(InputGenerator<Character> gen, int k) {
		RedBlackTree<String> tree = new RedBlackTree<>();
		ArrayDeque<Character> window = new ArrayDeque<>();
		StringBuilder cGene = new StringBuilder();

		while (gen.hasNext()) {
			if (window.size() == k) {
				window.remove(0);
				cGene.deleteCharAt(0);
			}
			Character c = gen.next();
			window.add(c);
			cGene.append(c);

			if (window.size() == k) {
				tree.add(cGene.toString());
			}
		}

		DLList<String> sortedG = new DLList<>();
		Iterator<String> it = tree.iterator();
		while (it.hasNext()) {
			sortedG.add(it.next());
		}

		return sortedG;
	}

	// These are a few examples of how to use the InputGenerator to run local tests
	// You should test more extensively than this.
	public static void main(String[] args) {
		System.out.println("Testing genesOrder() via GenesOrder.main...");
		System.out.println("These are limited local tests; please add more tests of your own!");
		System.out.println("You should also try testing via tests/GenesOrderTest.java");

		InputGenerator<Character> gen = new FileCharGenerator();

		// The following tests genes using the chars in the
		// file samples/genes-sample.txt, up to the first newline.
		gen = new FileCharGenerator("samples/genesOrder-sample.txt");
		System.out.println("Expect \t[ACG, AUC, CCU, CGA, CUA, GAU, UAC, UCC]: \n\t" + genesOrder(gen, 3)); // Expect
																											// [ACG,
																											// AUC, CCU,
																											// CGA, CUA,
																											// GAU, UAC,
																											// UCC]

		// If you want to test via the command-line, i.e. if you want to input a stream
		// of
		// characters of your own devising and then run genesOrder once you hit 'Enter',
		// then
		// uncomment the next three lines.
		// gen = new FileCharGenerator();
		// System.out.println( "Enter a sequence of characters, then hit Enter to run
		// genesOrder(gen, 3):" );
		// System.out.println( "Your result is: " + genesOrder(gen, 3) );

		// The following tests use a generate that alternates between the characters
		// listed in the
		// options array. The second argument is the number of characters to generate.
		char[] options = { 'A', 'U', 'C', 'G' };
		gen = new AlternatingCharGenerator(options, 12); // Generates AUCGAUCGAUCG
		System.out.println("Expect [AUCG, CGAU, GAUC, UCGA]: " + genesOrder(gen, 4)); // Expect [AUCG, CGAU, GAUC, UCGA]
		gen = new AlternatingCharGenerator(options, 10, true); // Generates AUCGAUCGAU -- uses true to store what was
																// generated
		System.out.println("Expect [AUCGAUCGA, UCGAUCGAU]: " + genesOrder(gen, 9)); // Expect [AUCGAUCGA, UCGAUCGAU]
		gen = new AlternatingCharGenerator(options, 11, true); // Generates AUCGAUCGAUC -- uses true to store what was
																// generated
		System.out.println("Expect [AUC, CGA, GAU, UCG]: " + genesOrder(gen, 3)); // Expect [AUC, CGA, GAU, UCG]
		gen = new AlternatingCharGenerator(options, 11, true); // Generates AUCGAUCGAUC -- uses true to store what was
																// generated
		System.out.println("Expect [A, C, G, U]: " + genesOrder(gen, 1)); // Expect [A, C, G, U]
		gen = new AlternatingCharGenerator(options, 11, true); // Generates AUCGAUCGAUC -- uses true to store what was
																// generated
		System.out.println("Expect []: " + genesOrder(gen, 12)); // Expect []
	}

}
