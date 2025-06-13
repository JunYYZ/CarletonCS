package comp2402w24l5;

// From within the Lab directory (or wherever you put the comp2402w24l5 directory):
// javac comp2402w24l5/DecodeA.java
// java comp2402w24l5/DecodeA

// You will submit this file to the autograder.
// It will not pass many of the tests, if any.
// Your task is to modify the countA method to pass the tests.

// Do not add any imports.
// (You shouldn't need any, and you'll fail the autograder if you do.)
import input.InputGenerator;
import input.FileIntGenerator;
import input.AlternatingIntGenerator;

import ods.ArrayDeque;
import ods.ArrayQueue;
import ods.ArrayStack;
import ods.RootishArrayStack;
import ods.SLList;
import ods.DLList;
import ods.SEList;
import ods.SkiplistList;
import ods.SkiplistSSet;
import ods.Treap;
import ods.ScapegoatTree;
import ods.SSet;
import ods.RedBlackTree;

import java.util.ListIterator;
import java.util.Iterator;
import java.util.AbstractSet;
import java.util.Collections;
import java.util.Comparator;

import java.util.ArrayList;
import java.util.Queue;
import java.util.LinkedList;
import java.util.TreeSet;

import java.util.TreeMap;
import java.util.HashSet;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

public class DecodeA {

//	public static int decodeA(InputGenerator<Integer> gen, int k) {
//		int output = 0;
//		int index = 0;
//
//		HashMap<Integer, Integer> recenti = new HashMap<>();
//		LinkedList<Integer> order = new LinkedList<>(); 
//
//		while (gen.hasNext()) {
//			Integer cint = gen.next();
//			if (gen.hasNext()) {
//				index++;
//				if (!recenti.containsKey(cint) && order.size() == k) {
//					Integer oldest = order.poll();
//					recenti.remove(oldest);
//				}
//
//				recenti.put(cint, index);
//
//				order.remove(cint);
//				order.offer(cint);
//
//				int fbint = order.peek();
//
//				output += fbint * index;
//			}
//
//		}
//
//		return output;
//	}

	public static int decodeA(InputGenerator<Integer> gen, int k) {
		int output = 0;
		int index = 0;

		HashMap<Integer, Integer> recenti = new HashMap<>();
		TreeSet<IntIndexPair> sortedByIndex = new TreeSet<>(comparator);

		while (gen.hasNext()) {
			Integer cint = gen.next();
			if (gen.hasNext()) {
				index++;
				if (recenti.containsKey(cint)) {
					sortedByIndex.remove(new IntIndexPair(cint, recenti.get(cint)));
				}
				recenti.put(cint, index);
				sortedByIndex.add(new IntIndexPair(cint, index));

				if (sortedByIndex.size() > k) {
					// remove oldest entry by index
					IntIndexPair oldest = sortedByIndex.first();
					sortedByIndex.remove(oldest);
					recenti.remove(oldest.getValue());
				}

				IntIndexPair farthestBackIntPair = sortedByIndex.first();
				int fbint = farthestBackIntPair.getValue();

				output += fbint * index;
			}

		}

		return output;
	}

	// since treeset is annoying
	static class IntIndexPair {
		int value;
		int index;

		IntIndexPair(int value, int index) {
			this.value = value;
			this.index = index;
		}

		// Getters
		public int getValue() {
			return value;
		}

		public int getIndex() {
			return index;
		}

		@Override
		public boolean equals(Object o) {
			if (this == o) {
				return true;
			}
			if (!(o instanceof IntIndexPair)) {
				return false;
			}

			IntIndexPair that = (IntIndexPair) o;

			return value == that.value;
		}

		@Override
		public int hashCode() {
			return Integer.hashCode(value);
		}
	}

	// comparator for treeset
	static Comparator<IntIndexPair> comparator = (p1, p2) -> {
		int indexCompare = Integer.compare(p1.getIndex(), p2.getIndex());

		if (indexCompare != 0) {
			return indexCompare;
		}

		return Integer.compare(p1.getValue(), p2.getValue());
	};

	// These are a few examples of how to use the InputGenerator to run local tests
	// You should test more extensively than this.
	public static void main(String[] args) {
		System.out.println("Testing decodeA() via DecodeA.main...");
		System.out.println("You should also try testing via tests/DecodeATest.java");

		InputGenerator<Integer> gen = new FileIntGenerator();

		// The following tests decodeA using the ints in the
		// file samples/decodeA-sample.txt, up to the first -1.
		// gen = new FileIntGenerator( "samples/decodeA-sample.txt" );
		// System.out.println( "Expect 6: " + decodeA(gen, 3) ); // Expect 6

		// If you want to test via the command-line, i.e. if you want to input a stream
		// of
		// integers of your own devising and then run decodeA once you input -1 and
		// 'Enter', then
		// uncomment the next three lines.
		// gen = new FileIntGenerator();
		// System.out.println( "Enter a sequence of positive integers, then input -1 and
		// Enter to run decodeA(gen, 3):" );
		// System.out.println( "Your result is: " + decodeA(gen, 3) );

		// The following tests use a generate that alternates between the characters
		// listed in the
		// options array. The second argument is the number of characters to generate.
		int[] options = { 1, 2, 3, 4 };
		gen = new AlternatingIntGenerator(options, 12); // Generates 1, 2, 3, 4, 1, 2, 3, 4, 1, 2, 3, 4
		System.out.println("Expect 166 from decodeA(123412341234, 4): " + decodeA(gen, 4));

		options = new int[] { 1, 2, 1, 4 };
		gen = new AlternatingIntGenerator(options, 10, true); // Generates 1, 2, 1, 4, 1, 2, 1, 4, 1, 2
		System.out.println("Expect 113 from decodeA(214121412,4): " + decodeA(gen, 4));
		gen = new AlternatingIntGenerator(options, 11, true); // Generates 1, 2, 1, 4, 1, 2, 1, 4, 1, 2, 1
		System.out.println("Expect 153 from decodeA(12141214121,3): " + decodeA(gen, 3));
		gen = new AlternatingIntGenerator(options, 11, true); // Generates 1, 2, 1, 4, 1, 2, 1, 4, 1, 2, 1
		System.out.println("Expect 107 from decodeA(12141214121,2): " + decodeA(gen, 2));
		gen = new AlternatingIntGenerator(options, 11, true); // Generates 1, 2, 1, 4, 1, 2, 1, 4, 1, 2, 1
		System.out.println("Expect 109 from decodeA(12141214121,1): " + decodeA(gen, 1));

	}

}
