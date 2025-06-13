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

    class TreapWrapper {
        private Treap<String> treap;
    
        public TreapWrapper() {
            this.treap = new Treap<>();
        }
    
        public void add(String x) {
            treap.add(x);
        }
    
        public String find(String x) {
            Iterator<String> it = treap.iterator();
            while (it.hasNext()) {
                String str = it.next();
                if (str.equals(x)) {
                    return str;
                }
            }
            return null;
        }
    }

    public static AbstractList<String> genesOrder(InputGenerator<Character> gen, int k) {
        Treap<String> sequencesTree = new Treap<>();
        ArrayDeque<String> sequences = new ArrayDeque<>();
        StringBuilder current = new StringBuilder();
    
        while (gen.hasNext()) {
            char c = gen.next();
            current.append(c);
            if (current.length() == k) {
                String substrand = current.toString();
                if (sequencesTree.find(substrand) == null) {
                    sequencesTree.add(substrand);
                    sequences.add(substrand);
                }
                current.deleteCharAt(0); // remove the first base
            }
        }
    
        return sequences;
    }

    // These are a few examples of how to use the InputGenerator to run local tests
    // You should test more extensively than this.
    public static void main(String[] args) {
        System.out.println("Testing genesOrder() via GenesOrder.main...");
        System.out.println("You should also try testing via tests/GenesOrderTest.java");

        InputGenerator<Character> gen = new FileCharGenerator();

        // The following tests genes using the chars in the
        // file samples/genes-sample.txt, up to the first newline.
        // gen = new FileCharGenerator("samples/genesOrder-sample.txt");
        // System.out.println(genesOrder(gen, 3)); // Expect [UAC, ACG, CGA, GAU, AUC, UCC, CCU, CUA]

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
        System.out.println(genesOrder(gen, 4)); // Expect [AUCG, UCGA, CGAU, GAUC]
        gen = new AlternatingCharGenerator(options, 10, true); // Generates AUCGAUCGAU -- uses true to store what was
                                                               // generated
        System.out.println(genesOrder(gen, 9)); // Expect [AUCGAUCGA, UCGAUCGAU]
        gen = new AlternatingCharGenerator(options, 11, true); // Generates AUCGAUCGAUC -- uses true to store what was
                                                               // generated
        System.out.println(genesOrder(gen, 3)); // Expect [AUC, UCG, CGA, GAU]
        gen = new AlternatingCharGenerator(options, 11, true); // Generates AUCGAUCGAUC -- uses true to store what was
                                                               // generated
        System.out.println(genesOrder(gen, 1)); // Expect [A, U, C, G]
        gen = new AlternatingCharGenerator(options, 11, true); // Generates AUCGAUCGAUC -- uses true to store what was
                                                               // generated
        System.out.println(genesOrder(gen, 12)); // Expect []
    }

}
