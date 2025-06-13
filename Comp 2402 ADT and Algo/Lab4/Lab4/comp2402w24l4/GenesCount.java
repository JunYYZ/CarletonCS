package comp2402w24l4;

// From within the Lab directory (or wherever you put the comp2402w24l4 directory):
// javac comp2402w24l4/GenesCount.java
// java comp2402w24l4/GenesCount

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
import java.util.Collections;
import java.util.Comparator;
import java.util.AbstractList;
import java.util.Map;

public class GenesCount {

	public static AbstractList<String> genesCount(InputGenerator<Character> gen, int k) {
		HashMap<String, Integer> freq = new HashMap<>();
		ArrayDeque<Character> window = new ArrayDeque<>();
		StringBuilder cGene = new StringBuilder();

		while (gen.hasNext()) {
			if (window.size() == k) {
				window.remove(0);
				cGene.deleteCharAt(0);
			}
			char c = gen.next();
			window.add(c);
			cGene.append(c);
			if (window.size() == k) {
				String gene = cGene.toString();
				freq.put(gene, freq.getOrDefault(gene, 0) + 1);
			}
		}

		SkiplistSSet<FrequencyGenePair> sortedG = new SkiplistSSet<>();
		for (Map.Entry<String, Integer> entry : freq.entrySet()) {
			sortedG.add(new FrequencyGenePair(entry.getValue(), entry.getKey()));
		}

		AbstractList<String> result = new AbstractList<String>() {
			@Override
			public String get(int index) {
				Iterator<FrequencyGenePair> it = sortedG.iterator();
				while (index-- > 0 && it.hasNext()) {
					it.next();
				}

				return it.hasNext() ? it.next().gene : null;
			}

			@Override
			public int size() {
				return sortedG.size();
			}
		};
		

		return result;
	}

	private static class FrequencyGenePair implements Comparable<FrequencyGenePair> {
		Integer frequency;
		String gene;

		FrequencyGenePair(Integer frequency, String gene) {
			this.frequency = frequency;
			this.gene = gene;
		}

		@Override
		public int compareTo(FrequencyGenePair o) {
			int freqComp = o.frequency.compareTo(this.frequency);
			return (freqComp != 0) ? freqComp : this.gene.compareTo(o.gene);
		}
	}

	// These are a few examples of how to use the InputGenerator to run local tests
	// You should test more extensively than this.
	public static void main(String[] args) {
		System.out.println("Testing genesCount() via GenesCount.main...");
		System.out.println("These are limited local tests; please add more tests of your own!");
		System.out.println("You should also try testing via tests/GenesCountTest.java");

		InputGenerator<Character> gen = new FileCharGenerator();

		// The following tests genes using the chars in the
		// file samples/genes-sample.txt, up to the first newline.
		gen = new FileCharGenerator("Lab4/samples/genesCount-sample.txt");
		System.out.println("Expect \t[ACG, UAC, AUC, CCU, CGA, CUA, GAU, UCC]:\n\t" + genesCount(gen, 3)); // Expect
																											// [ACG,
																											// AUC, CCU,
																											// CGA, CUA,
																											// GAU, UAC,
																											// UCC]

		// If you want to test via the command-line, i.e. if you want to input a stream
		// of
		// characters of your own devising and then run genesCount once you hit 'Enter',
		// then
		// uncomment the next three lines.
		// gen = new FileCharGenerator();
		// System.out.println( "Enter a sequence of characters, then hit Enter to run
		// genesCount(gen, 3):" );
		// System.out.println( "Your result is: " + genesCount(gen, 3) );

		// The following tests use a generate that alternates between the characters
		// listed in the
		// options array. The second argument is the number of characters to generate.
		char[] options = { 'A', 'U', 'C', 'G' };
		gen = new AlternatingCharGenerator(options, 12); // Generates AUCGAUCGAUCG
		System.out.println("Expect [AUCG, CGAU, GAUC, UCGA]: " + genesCount(gen, 4)); // Expect [AUCG, CGAU, GAUC, UCGA]
		gen = new AlternatingCharGenerator(options, 10, true); // Generates AUCGAUCGAU -- uses true to store what was
																// generated
		System.out.println("Expect [AUCGAUCGA, UCGAUCGAU]: " + genesCount(gen, 9)); // Expect [AUCGAUCGA, UCGAUCGAU]
		gen = new AlternatingCharGenerator(options, 11, true); // Generates AUCGAUCGAUC -- uses true to store what was
																// generated
		System.out.println("Expect [AUC, CGA, GAU, UCG]: " + genesCount(gen, 3)); // Expect [AUC, CGA, GAU, UCG]
		gen = new AlternatingCharGenerator(options, 11, true); // Generates AUCGAUCGAUC -- uses true to store what was
																// generated
		System.out.println("Expect [A, C, U, G]: " + genesCount(gen, 1)); // Expect [A, C, U, G]
		gen = new AlternatingCharGenerator(options, 11, true); // Generates AUCGAUCGAUC -- uses true to store what was
																// generated
		System.out.println("Expect []: " + genesCount(gen, 12)); // Expect []
	}

}
