package comp2402w24l2;

// From within the Lab directory (or wherever you put the comp2402w24l2 directory):
// javac comp2402w24l2/JumbleA.java
// java comp2402w24l2/JumbleA

// You will submit this file to the autograder.
// It will not pass many of the tests, if any.
// Your task is to modify the jumble method to pass the tests.

// Do not add any imports.
// (You shouldn't need any, and you'll fail the autograder if you do.)
import input.InputGenerator;
import input.FileCharGenerator;
import input.AlternatingCharGenerator;
import ods.ArrayDeque;
import ods.ArrayQueue;
import ods.ArrayStack;
import ods.RootishArrayStack;

public class JumbleA extends Jumble {

	public String jumble(InputGenerator<Character> gen) {
		StringBuilder sb = new StringBuilder();
		StringBuilder sb2 = new StringBuilder();
		ArrayQueue<StringBuilder> cuts = new ArrayQueue<>();
		char c1 = '\0';
		char c2 = '\0';
		int cursor = 0;
		int count = 0;

		while (gen.hasNext()) {
			char c = gen.next();
			count++;
			if (cursor == sb.length()) {
				sb.append(c);
			} else {
				sb.setCharAt(cursor, c);
			}
			cursor++;
			if (c2 == 'A' && c1 == 'U') {
				if (c == 'C') {
					cursor = Math.max(cursor - count / 2, 0);
					sb2.append(sb.substring(cursor));
					cuts.add(sb2);
					sb2 = new StringBuilder();
				} else if (c == 'G') {
					cursor = Math.min(cursor + count / 4, count);
				}
			}
			c2 = c1;
			c1 = c;
		}
		while (cuts.size() > 0) {
			sb.append(cuts.remove());
		}
		return sb.toString();

	}

}
// int counter = 0; // track how many new inserts
// String gened = new String();
// String temp = new String();
// boolean split = false;

// sb.insert(cursor, c);
// if (cursor == gened.length()) {
// gened += c;
// } else {
// temp = gened.substring(cursor) + temp;
// gened = gened.substring(0, cursor) + c;
// split = true;
// }
// if (cursor == sb.length()) {
// sb.append(c);
// } else {
// temp = sb.substring(cursor) + temp;
// sb.delete(cursor, sb.length());
// sb.append(c);
// split = true;
// }

// counter += 1;
// if (counter >= 3) {

// counter = 0;
// counter = 0;
// if (sb.charAt(cursor - 2) == 'U' && sb.charAt(cursor - 3) == 'A') {
// if (c == 'C') {
// cursor = Math.max(cursor - (sb.length() + temp.length()) / 2, 0);
//// counter = 0;
// } else if (c == 'G') {
// cursor = Math.min(cursor + (sb.length() + temp.length()) / 4, (sb.length() +
// temp.length()));
// if (cursor == sb.length() + temp.length()) {
// sb.append(temp);
// split = false;
// } else if (cursor >= sb.length()) {
// sb.append(temp);
// temp = sb.substring(cursor);
// sb.delete(cursor, sb.length());
// }
//// counter = 0;
// }
// }
// if (gened.charAt(cursor - 2) == 'U' && gened.charAt(cursor - 3) == 'A') {
// if (c == 'C') {
// if (split) {
// cursor = Math.max(cursor - (gened.length() + temp.length()) / 2, 0);
// } else {
// cursor = Math.max(cursor - gened.length() / 2, 0);
// }
// counter = 0;
// } else if (c == 'G') {
// if (split) {
// cursor = Math.min(cursor + (gened.length() + temp.length()) / 4,
// gened.length() + temp.length());
// if (cursor == gened.length() + temp.length()) {
// gened += temp;
// split = false;
// } else {
// gened += temp;
// temp = gened.substring(cursor);
// gened = gened.substring(0, cursor);
// }
// } else {
// cursor = Math.min(cursor + gened.length() / 4, gened.length());
// }
// counter = 0;
// }
// }
// if (c1 == 'U' && c2 == 'A') {
// if (c == 'C') {
// if (split) {
// cursor = Math.max(cursor - (gened.length() + temp.length()) / 2, 0);
// } else {
// cursor = Math.max(cursor - gened.length() / 2, 0);
// }
// counter = 0;
// } else if (c == 'G') {
// if (split) {
// cursor = Math.min(cursor + (gened.length() + temp.length()) / 4,
// gened.length() + temp.length());
// if (cursor == gened.length() + temp.length()) {
// gened += temp;
// split = false;
// } else {
// gened += temp;
// temp = gened.substring(cursor);
// gened = gened.substring(0, cursor);
// }
// } else {
// cursor = Math.min(cursor + gened.length() / 4, gened.length());
// }
// counter = 0;
// }
// }
// }

//

// if (split) {
// return sb.toString() + temp;
// }

// if (split) {
// return gened + temp;
// }
// for (char ch : deque) {
// gened += ch;
// }
// return gened;

// These are a few examples of how to use the InputGenerator to run local tests
// You should test more extensively than this.
// public static void main(String[] args) {
// System.out.println("Testing jumble() via JumbleA.main...");
// System.out.println("You should also try testing via tests/JumbleATest.java");
//
// Jumble jum = new JumbleA();
// InputGenerator<Character> gen = new FileCharGenerator();
//
// // The following tests jumbleA using the chars in the
// // file samples/jumble-sample.txt, up to the first newline.
//// gen = new FileCharGenerator();
//// System.out.println(jum.jumble(gen)); // Expect AUCUAAUCAUCUC
//
// // If you want to test via the command-line, i.e. if you want to input a
// stream
// // of
// // characters of your own devising and then run jumbleA once you hit 'Enter',
// // then
// // uncomment the next three lines.
// // gen = new FileCharGenerator();
// // System.out.println( "Enter a sequence of characters, then hit Enter to run
// // jumble(gen):" );
// // System.out.println( "Your result is: " + jum.jumble(gen) );
//
// System.out.println("\nTesting Rewind Only...");
//
// char[] options = { 'A', 'U', 'C', 'G' };
// gen = new AlternatingCharGenerator(options, 4); // Generates AUCG
// System.out.println(jum.jumble(gen)); // Expect AUGC
//
// gen = new AlternatingCharGenerator(options, 5); // Generates AUCGA
// System.out.println(jum.jumble(gen)); // Expect AUGAC
//
// gen = new AlternatingCharGenerator(options, 6); // Generates AUCGA
// System.out.println(jum.jumble(gen)); // Expect AUGAUC
//
// gen = new AlternatingCharGenerator(options, 7); // Generates AUCGAUC
// System.out.println(jum.jumble(gen)); // Expect AUGAUCC
//
// gen = new AlternatingCharGenerator(options, 8); // Generates AUCGAUCG
// System.out.println(jum.jumble(gen)); // Expect AUGGAUCC
//
// gen = new AlternatingCharGenerator(options, 12);
// System.out.println(jum.jumble(gen)); // Expect AUGGGAUCAUCC
//
// gen = new AlternatingCharGenerator(options, 16);
// System.out.println(jum.jumble(gen)); // Expect GAUGAUCGGAUCAUCC
//
// gen = new AlternatingCharGenerator(options, 20);
// System.out.println(jum.jumble(gen)); // Expect GGAUCAUGAUCGGAUCAUCC
//
// System.out.println("\nTesting FF Only...");
// options = new char[] { 'A', 'U', 'G', 'C' };
// gen = new AlternatingCharGenerator(options, 4); // Generates AUGC
// System.out.println(jum.jumble(gen)); // Expect AUGC
//
// gen = new AlternatingCharGenerator(options, 5); // Generates AUGCA
// System.out.println(jum.jumble(gen)); // Expect AUGCA
//
// gen = new AlternatingCharGenerator(options, 6); // Generates AUGCAU
// System.out.println(jum.jumble(gen)); // Expect AUGCAU
//
// System.out.println("\nTesting Rew/FF Alternating...");
// options = new char[] { 'A', 'U', 'C', 'G', 'A', 'U', 'G', 'C' };
// gen = new AlternatingCharGenerator(options, 8); // Generates AUCGAUGC
// System.out.println(jum.jumble(gen)); // Expect AUGAUGCC
//
// gen = new AlternatingCharGenerator(options, 12); // Generates AUCGAUGCAUCG
// System.out.println(jum.jumble(gen)); // Expect AUGAUGGCCAUC
//
// gen = new AlternatingCharGenerator(options, 13); // Generates AUCGAUGCAUCGA
// System.out.println(jum.jumble(gen)); // Expect AUGAUGGACCAUC
//
// gen = new AlternatingCharGenerator(options, 16); // Generates
// AUCGAUGCAUCGAUGC
// System.out.println(jum.jumble(gen)); // Expect AUGAUGGAUGCCACUC
//
// // Extra test, shows you how to make an array from a String, if helpful.
// String s = "ACCGAACGGUAC";
// s = "A";
// options = s.toCharArray();
// gen = new AlternatingCharGenerator(options, 120000); // Generates
// AUCAUCAUCAUC
// System.out.println(jum.jumble(gen)); // Expect AAUCUAUCAUCC
//
// System.out.println(Math.max(5 - 5 / 2, 0));
//
// }
