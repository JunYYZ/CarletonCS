package comp2402w24l3;

// From within the Lab directory (or wherever you put the comp2402w24l3 directory):
// javac comp2402w24l3/GenesOrder.java
// java comp2402w24l3/GenesOrder

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

import java.util.AbstractList;
import java.util.ListIterator;
import java.util.Iterator;

public class GenesOrder {

	public static AbstractList<String> genesOrder(InputGenerator<Character> gen, int k) {
		ArrayDeque<String> sequences = new ArrayDeque<>();
		SkiplistSSet<String> uniqueGenes = new SkiplistSSet<>();
		StringBuilder currentGene = new StringBuilder();

		// iterate
		while (gen.hasNext()) {
			Character c = gen.next();
			if (currentGene.length() < k) {
				// build initial substrand of len k
				currentGene.append(c);
			} else {
				// once we have initial substrand k, add more
				if (uniqueGenes.add(currentGene.toString())) {// add current substrand
					sequences.add(currentGene.toString());
				}

				currentGene.deleteCharAt(0); // remove first character
				currentGene.append(c); // append new character
			}
		}
		// add last substrand if it hasn't been added
		if (currentGene.length() == k) {
			uniqueGenes.add(currentGene.toString());
			if (uniqueGenes.size() != sequences.size()) {
				sequences.add(currentGene.toString());
			}
		}

		return sequences;
	}

	// These are a few examples of how to use the InputGenerator to run local tests
	// You should test more extensively than this.
//	public static void main(String[] args) {
//		System.out.println("Testing genesOrder() via GenesOrder.main...");
//		System.out.println("You should also try testing via tests/GenesOrderTest.java");
//
//		InputGenerator<Character> gen = new FileCharGenerator();
//
//		// The following tests genes using the chars in the
//		// file samples/genes-sample.txt, up to the first newline.
//		gen = new FileCharGenerator("Lab3/samples/genesOrder-sample.txt");
//		System.out.println(genesOrder(gen, 2)); // Expect [UAC, ACG, CGA, GAU, AUC, UCC, CCU, CUA]
//
//		// If you want to test via the command-line, i.e. if you want to input a stream
//		// of
//		// characters of your own devising and then run genesOrder once you hit 'Enter',
//		// then
//		// uncomment the next three lines.
////		gen = new FileCharGenerator();
////		System.out.println("Enter a sequence of characters, then hit Enter to run		 genesOrder(gen, 4):");
////		System.out.println("Your result is: " + genesOrder(gen, 3));
//
//		// The following tests use a generate that alternates between the characters
//		// listed in the
//		// options array. The second argument is the number of characters to generate.
//		char[] options = { 'A', 'U', 'C', 'G' };
//		gen = new AlternatingCharGenerator(options, 12); // Generates AUCGAUCGAUCG
//		System.out.println(genesOrder(gen, 4)); // Expect [AUCG, UCGA, CGAU, GAUC]
//
//		gen = new AlternatingCharGenerator(options, 10, true); // Generates AUCGAUCGAU -- uses true to store what was
//																// generated
//		System.out.println(genesOrder(gen, 9)); // Expect [AUCGAUCGA, UCGAUCGAU]
//		gen = new AlternatingCharGenerator(options, 11, true); // Generates AUCGAUCGAUC -- uses true to store what was
//																// generated
//		System.out.println(genesOrder(gen, 3)); // Expect [AUC, UCG, CGA, GAU]
//		gen = new AlternatingCharGenerator(options, 11, true); // Generates AUCGAUCGAUC -- uses true to store what was
//																// generated
//		System.out.println(genesOrder(gen, 1)); // Expect [A, U, C, G]
//		gen = new AlternatingCharGenerator(options, 11, true); // Generates AUCGAUCGAUC -- uses true to store what was
//																// generated
//		System.out.println(genesOrder(gen, 12)); // Expect []
//
//		char[] options2 = { 'A' };
//
//		gen = new AlternatingCharGenerator(options2, 4); // Generates AUCGAUCGAUCG
//		System.out.println(genesOrder(gen, 3)); // Expect [AUCG, UCGA, CGAU, GAUC]
//
//	}

}
