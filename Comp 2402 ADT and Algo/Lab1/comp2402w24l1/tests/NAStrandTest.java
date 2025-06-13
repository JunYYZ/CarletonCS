package comp2402w24l1.tests;

// From within the Lab directory (or whichever directory contains the comp2402w24l1 directory)
// Assumes junit-platform-console-standalone-1.10.1.jar
// is in the Lab/lib/ directory.
// This is the command that works on a linux-based system (e.g. mac os)
// javac -cp "./lib/*:." comp2402w24l1/tests/NAStrandTest.java
// java -cp "./lib/*:." comp2402w24l1/tests/NAStrandTest


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.runner.JUnitCore;
import java.util.Random;
import java.util.HashSet;

import comp2402w24l1.RNAStrand;
import comp2402w24l1.DNAStrand;

public class NAStrandTest {

    private static char[] bases = {'A', 'C', 'G', 'U'};
    private static char[] dna_bases = {'A', 'C', 'G', 'T'};


    @Test
    public static void test_creation() {
        RNAStrand actual = new RNAStrand(); // Not much to test here
        DNAStrand dna_actual = new DNAStrand();
    }

    @Test
    public static void isBase() {
        RNAStrand rnaStrand = new RNAStrand();
        for(int i=0; i < bases.length; i++ ) {
            assertTrue(rnaStrand.isBase(bases[i]), bases[i] + " should be an RNA base");
        }
        char[] nonBases = {'T', 'a', 'c', 'g', 'u', 't', 'B', 'b', 'Z', 'z', ' ', '1', '!'};
        for(int i=0; i < nonBases.length; i++ ) {
            assertFalse(rnaStrand.isBase(nonBases[i]), nonBases[i] + " should not be an RNA base");
        }
        DNAStrand dnaStrand = new DNAStrand();
        for(int i=0; i < dna_bases.length; i++ ) {
            assertTrue(dnaStrand.isBase(dna_bases[i]), dna_bases[i] + " should be a DNA base");
        }
        char[] dna_nonBases = {'U', 'a', 'c', 'g', 'u', 't', 'B', 'b', 'Z', 'z', ' ', '1', '!'};
        for(int i=0; i < dna_nonBases.length; i++ ) {
            assertFalse(dnaStrand.isBase(dna_nonBases[i]), dna_nonBases[i] + " should not be a DNA base");
        }
    }

    @Test
    public static void isPair() {
        RNAStrand rnaStrand = new RNAStrand();
        assertTrue(rnaStrand.isPair('A', 'U'), "A and U should be an RNA pair");
        assertTrue(rnaStrand.isPair('U', 'A'), "U and A should be an RNA pair");
        assertTrue(rnaStrand.isPair('C', 'G'), "C and G should be an RNA pair");
        assertTrue(rnaStrand.isPair('G', 'C'), "G and C should be an RNA pair");
        for( int i=0; i < bases.length; i++ ) {
            assertFalse(rnaStrand.isPair(bases[i], bases[i]), bases[i] + " and " + bases[i] + " should not be an RNA pair");
        }
        assertFalse(rnaStrand.isPair('A', 'C'), "A and C should not be an RNA pair");
        assertFalse(rnaStrand.isPair('C', 'A'), "C and A should not be an RNA pair");
        assertFalse(rnaStrand.isPair('A', 'G'), "A and G should not be an RNA pair");
        assertFalse(rnaStrand.isPair('G', 'A'), "G and A should not be an RNA pair");
        assertFalse(rnaStrand.isPair('U', 'C'), "U and C should not be an RNA pair");
        assertFalse(rnaStrand.isPair('C', 'U'), "C and U should not be an RNA pair");
        assertFalse(rnaStrand.isPair('U', 'G'), "U and G should not be an RNA pair");
        assertFalse(rnaStrand.isPair('G', 'U'), "G and U should not be an RNA pair");
        assertFalse(rnaStrand.isPair('a', 'u'), "a and u should not be an RNA pair");
        assertFalse(rnaStrand.isPair('u', 'a'), "u and a should not be an RNA pair");
        assertFalse(rnaStrand.isPair('c', 'g'), "c and g should not be an RNA pair");
        assertFalse(rnaStrand.isPair('g', 'c'), "g and c should not be an RNA pair");

        DNAStrand dnaStrand = new DNAStrand();
        assertTrue(dnaStrand.isPair('A', 'T'), "A and T should be a DNA pair");
        assertTrue(dnaStrand.isPair('T', 'A'), "T and A should be a DNA pair");
        assertTrue(dnaStrand.isPair('C', 'G'), "C and G should be a DNA pair");
        assertTrue(dnaStrand.isPair('G', 'C'), "G and C should be a DNA pair");
        for( int i=0; i < bases.length; i++ ) {
            assertFalse(dnaStrand.isPair(bases[i], bases[i]), bases[i] + " and " + bases[i] + " should not be a DNA pair");
        }
        assertFalse(dnaStrand.isPair('A', 'U'), "A and U should not be a DNA pair");
        assertFalse(dnaStrand.isPair('U', 'A'), "U and A should not be a DNA pair");
        assertFalse(dnaStrand.isPair('A', 'C'), "A and C should not be a DNA pair");
        assertFalse(dnaStrand.isPair('C', 'A'), "C and A should not be a DNA pair");
        assertFalse(dnaStrand.isPair('A', 'G'), "A and G should not be a DNA pair");
        assertFalse(dnaStrand.isPair('G', 'A'), "G and A should not be a DNA pair");
        assertFalse(dnaStrand.isPair('T', 'C'), "T and C should not be a DNA pair");
        assertFalse(dnaStrand.isPair('C', 'T'), "C and T should not be a DNA pair");
        assertFalse(dnaStrand.isPair('T', 'G'), "T and G should not be a DNA pair");
        assertFalse(dnaStrand.isPair('G', 'T'), "G and T should not be a DNA pair");
        assertFalse(dnaStrand.isPair('a', 't'), "a and t should not be a DNA pair");
        assertFalse(dnaStrand.isPair('t', 'a'), "t and a should not be a DNA pair");
        assertFalse(dnaStrand.isPair('c', 'g'), "c and g should not be a DNA pair");
        assertFalse(dnaStrand.isPair('g', 'c'), "g and c should not be a DNA pair");
    }


    @Test
    public static void getBases() {
        RNAStrand rnaStrand = new RNAStrand();
        HashSet<Character> rnaBases = new HashSet<Character>();
        for(int i=0; i < bases.length; i++ ) {
            rnaBases.add(bases[i]);
        }
        HashSet<Character> actualRNA = new HashSet<Character>();
        char[] actualBases = rnaStrand.getBases();
        for(int i=0; i < actualBases.length; i++ ) {
            actualRNA.add(rnaStrand.getBases()[i]);
        }
        assertEquals(rnaBases, actualRNA, "RNA bases should be " + bases + " after construction");


        DNAStrand dnaStrand = new DNAStrand();
        HashSet<Character> dnaBases = new HashSet<Character>();
        for(int i=0; i < dna_bases.length; i++ ) {
            dnaBases.add(dna_bases[i]);
        }
        HashSet<Character> actualDNA = new HashSet<Character>();
        char[] actualDNABases = dnaStrand.getBases();
        for(int i=0; i < actualDNABases.length; i++ ) {
            actualDNA.add(dnaStrand.getBases()[i]);
        }
        assertEquals(dnaBases, actualDNA, "DNA bases should be " + dna_bases + " after construction");
    }

    @Test
    public static void add(int n, boolean allValid) {
        RNAStrand rnaStrand = new RNAStrand();
        for(int i = 0; i < n; i++) {
            rnaStrand.add(bases[i%4]);
            if(!allValid) {
                assertThrows(IllegalArgumentException.class, () -> rnaStrand.add('B'),
                        "Should throw IllegalArgumentException when adding 'B' to RNA strand");
            }
            assertEquals(bases[i%4], rnaStrand.get(i),
                    "Last element of RNA should be " + bases[i%4] + " after adding " + (i+1) + " elements");
        }
        DNAStrand dnaStrand = new DNAStrand();
        for(int i = 0; i < n; i++) {
            dnaStrand.add(dna_bases[i%4]);
            if(!allValid) {
                assertThrows(IllegalArgumentException.class, () -> dnaStrand.add('B'),
                        "Should throw IllegalArgumentException when adding 'B' to DNA strand");
            }
            assertEquals(dna_bases[i%4], dnaStrand.get(i),
                    "Last element of DNA should be " + dna_bases[i%4] + " after adding " + (i+1) + " elements");
        }

    }

    @Test
    public static void addAtIndexExceptions() {
        RNAStrand rnaStrand = new RNAStrand();
        assertThrows(IndexOutOfBoundsException.class, () -> rnaStrand.add(-1, 'A'),
                "Should throw IndexOutOfBoundsException when adding at index -1 to RNA strand");
        assertThrows(IndexOutOfBoundsException.class, () -> rnaStrand.add(1, 'A'),
                "Should throw IndexOutOfBoundsException when adding at index 1 to empty RNA strand");
        assertThrows(IllegalArgumentException.class, () -> rnaStrand.add(0, 'B'),
                "Should throw IllegalArgumentException when adding 'B' to RNA strand");
        DNAStrand dnaStrand = new DNAStrand();
        assertThrows(IndexOutOfBoundsException.class, () -> dnaStrand.add(-1, 'A'),
                "Should throw IndexOutOfBoundsException when adding at index -1 to DNA strand ");
        assertThrows(IndexOutOfBoundsException.class, () -> dnaStrand.add(1, 'A'),
                "Should throw IndexOutOfBoundsException when adding at index 1 to empty DNA strand");
        assertThrows(IllegalArgumentException.class, () -> dnaStrand.add(0, 'B'),
                "Should throw IllegalArgumentException when adding 'B' to DNA strand");
    }

    @Test
    public static void addMiddle(int n) {
        RNAStrand rnaStrand = new RNAStrand();
        for(int i=0; i < n; i++ ) {
            rnaStrand.add(bases[i%4]);
        }
        for(int i = 0; i < n; i++) {
            rnaStrand.add(2*i, bases[i%4]);
            assertEquals(bases[i%4], rnaStrand.get(2*i),
                    "after RNA addMiddle, get( " + 2*i + ") should be " + bases[i%4]);
        }
        DNAStrand dnaStrand = new DNAStrand();
        for(int i=0; i < n; i++ ) {
            dnaStrand.add(dna_bases[i%4]);
        }
        for(int i = 0; i < n; i++) {
            dnaStrand.add(2*i, dna_bases[i%4]);
            assertEquals(dna_bases[i%4], dnaStrand.get(2*i),
                    "after DNA addMiddle, get( " + 2*i + ") should be " + dna_bases[i%4]);
        }
    }


    @Test
    public static void get(int n) {
        RNAStrand rnaStrand = new RNAStrand();
        assertThrows(IndexOutOfBoundsException.class, () -> rnaStrand.get(-1),
                "Should throw IndexOutOfBoundsException when getting at index -1 of RNA strand");
        assertThrows(IndexOutOfBoundsException.class, () -> rnaStrand.get(0),
                "Should throw IndexOutOfBoundsException when getting at index 0 from empty RNA strand");
        for(int i = 0; i < n; i++) {
            rnaStrand.add(bases[i%4]);
        }
        for( int i=0; i < n; i++ ) {
            assertEquals(bases[i%4], rnaStrand.get(i),
                    "RNA get( " + i + ") should be " + bases[i%4]);
        }
        assertThrows(IndexOutOfBoundsException.class, () -> rnaStrand.get(n),
                "Should throw IndexOutOfBoundsException when getting at index size() of RNA strand");
        DNAStrand dnaStrand = new DNAStrand();
        assertThrows(IndexOutOfBoundsException.class, () -> dnaStrand.get(-1),
                "Should throw IndexOutOfBoundsException when getting at index -1 in DNA strand");
        assertThrows(IndexOutOfBoundsException.class, () -> dnaStrand.get(0),
                "Should throw IndexOutOfBoundsException when getting at index 0 from empty DNA strand");
        for(int i = 0; i < n; i++) {
            dnaStrand.add(dna_bases[i%4]);
        }
        for( int i=0; i < n; i++ ) {
            assertEquals(dna_bases[i%4], dnaStrand.get(i),
                    "DNA get( " + i + ") should be " + dna_bases[i%4]);
        }
        assertThrows(IndexOutOfBoundsException.class, () -> dnaStrand.get(n),
                "Should throw IndexOutOfBoundsException when getting at index size() of DNA strand");

    }


    @Test
    public static void size(int n) {
        RNAStrand rnaStrand = new RNAStrand();
        for(int i = 0; i < n; i++) {
            rnaStrand.add(bases[i%4]);
            assertEquals(i + 1, rnaStrand.size(),
                    "RNA Size should be " + (i+1) + " after adding " + (i+1) + " elements");
        }
        DNAStrand dnaStrand = new DNAStrand();
        for(int i = 0; i < n; i++) {
            dnaStrand.add(dna_bases[i%4]);
            assertEquals(i + 1, dnaStrand.size(),
                    "DNA Size should be " + (i+1) + " after adding " + (i+1) + " elements");
        }
    }

    @Test
    public static void clear(int n) {
        RNAStrand rnaStrand = new RNAStrand();
        for(int i = 0; i < n; i++) {
            rnaStrand.add(bases[i%4]);
            rnaStrand.clear();
            assertEquals(0, rnaStrand.size(), "RNA Size should be 0 after clearing strand");
        }
        DNAStrand dnaStrand = new DNAStrand();
        for(int i = 0; i < n; i++) {
            dnaStrand.add(dna_bases[i%4]);
            dnaStrand.clear();
            assertEquals(0, dnaStrand.size(), "DNA Size should be 0 after clearing strand");
        }

    }


    @Test
    public static void setExceptions() {
        RNAStrand rnaStrand = new RNAStrand();
        assertThrows(IndexOutOfBoundsException.class, () -> rnaStrand.set(-1, 'A'),
                "Should throw IndexOutOfBoundsException when setting at index -1 in RNA strand");
        assertThrows(IndexOutOfBoundsException.class, () -> rnaStrand.set(0, 'A'),
                "Should throw IndexOutOfBoundsException when setting at index 0 from empty RNA strand");
        rnaStrand.add('A');
        assertThrows(IllegalArgumentException.class, () -> rnaStrand.set(0, 'B'),
                "Should throw IllegalArgumentException when setting at index 0 to invalid base in RNA strand");
        DNAStrand dnaStrand = new DNAStrand();
        assertThrows(IndexOutOfBoundsException.class, () -> dnaStrand.set(-1, 'A'),
                "Should throw IndexOutOfBoundsException when setting at index -1 in DNA strand");
        assertThrows(IndexOutOfBoundsException.class, () -> dnaStrand.set(0, 'A'),
                "Should throw IndexOutOfBoundsException when setting at index 0 from empty DNA strand");
        dnaStrand.add('A');
        assertThrows(IllegalArgumentException.class, () -> dnaStrand.set(0, 'B'),
                "Should throw IllegalArgumentException when setting at index 0 to invalid base in DNA strand");
    }

    @Test
    public static void set(int n) {
        RNAStrand rnaStrand = new RNAStrand();
        for(int i = 0; i < n; i++) {
            rnaStrand.add('A');
        }
        for( int i=0; i < n; i++ ) {
            assertEquals('A', rnaStrand.set(i, 'C'),
                    "RNA set(" + i + ", 'C') should return previous value 'A'");
            assertEquals('C', rnaStrand.get(i),
                    "RNA get(" + i + ") should return 'C' after set(" + i + ", 'C')");
        }
        rnaStrand.add('A');
        assertThrows(IndexOutOfBoundsException.class, () -> rnaStrand.set(rnaStrand.size(), 'A'),
                "RNA Should throw IndexOutOfBoundsException when setting at index size()");
        assertThrows(IllegalArgumentException.class, () -> rnaStrand.set(rnaStrand.size()-1, 'B'),
                "RNA Should throw IllegalArgumentException when setting at index size()-1 to invalid base");
        DNAStrand dnaStrand = new DNAStrand();
        for(int i = 0; i < n; i++) {
            dnaStrand.add('A');
        }
        for( int i=0; i < n; i++ ) {
            assertEquals('A', dnaStrand.set(i, 'C'),
                    "DNA set(" + i + ", 'C') should return previous value 'A'");
            assertEquals('C', dnaStrand.get(i),
                    "DNA get(" + i + ") should return 'C' after set(" + i + ", 'C')");
        }
        dnaStrand.add('A');
        assertThrows(IndexOutOfBoundsException.class, () -> dnaStrand.set(dnaStrand.size(), 'A'),
                "DNA Should throw IndexOutOfBoundsException when setting at index size()");
        assertThrows(IllegalArgumentException.class, () -> dnaStrand.set(dnaStrand.size()-1, 'B'),
                "DNA Should throw IllegalArgumentException when setting at index size()-1 to invalid base");
    }

    @Test
    public static void removeExceptions() {
        RNAStrand rnaStrand = new RNAStrand();
        assertThrows(IndexOutOfBoundsException.class, () -> rnaStrand.remove(-1),
                "RNA Should throw IndexOutOfBoundsException when removing at index -1");
        assertThrows(IndexOutOfBoundsException.class, () -> rnaStrand.remove(0),
                "RNA Should throw IndexOutOfBoundsException when removing at index 0 from empty strand");
        DNAStrand dnaStrand = new DNAStrand();
        assertThrows(IndexOutOfBoundsException.class, () -> dnaStrand.remove(-1),
                "DNA Should throw IndexOutOfBoundsException when removing at index -1");
        assertThrows(IndexOutOfBoundsException.class, () -> dnaStrand.remove(0),
                "DNA Should throw IndexOutOfBoundsException when removing at index 0 from empty strand");

    }


    @Test
    public static void removeBack(int n) {
        RNAStrand rnaStrand = new RNAStrand();
        for(int i = 0; i < n; i++) {
            rnaStrand.add(bases[i%4]);
        }
        for(int i = 0; i < n; i++) {
            assertEquals(n-i, rnaStrand.size(),
                    "RNA Size should be " + (n-i) + " after removing " + i + " elements from back");
            assertEquals(bases[(n-i-1)%4], rnaStrand.remove(rnaStrand.size()-1),
                    "RNA remove(n-1) should return " + bases[(n-i-1)%4]);
        }

        DNAStrand dnaStrand = new DNAStrand();
        for(int i = 0; i < n; i++) {
            dnaStrand.add(dna_bases[i%4]);
        }
        for(int i = 0; i < n; i++) {
            assertEquals(n-i, dnaStrand.size(),
                    "DNA Size should be " + (n-i) + " after removing " + i + " elements from back");
            assertEquals(dna_bases[(n-i-1)%4], dnaStrand.remove(dnaStrand.size()-1),
                    "DNA remove(n-1) should return " + dna_bases[(n-i-1)%4]);
        }

    }


    @Test
    public static void spliceInExceptions() {
        RNAStrand rnaStrand = new RNAStrand();
        assertThrows(IndexOutOfBoundsException.class, () -> rnaStrand.spliceIn(-1, new RNAStrand()),
                "Should throw IndexOutOfBoundsException when splicing in at index -1");
        assertThrows(IndexOutOfBoundsException.class, () -> rnaStrand.spliceIn(1, new RNAStrand()),
                "Should throw IndexOutOfBoundsException when splicing in at index 1 from empty strand");
        DNAStrand dnaStrand = new DNAStrand();
        assertThrows(IllegalArgumentException.class, () -> rnaStrand.spliceIn(0, dnaStrand),
                "Should throw IllegalArgumentException when splicing in from different type of strand");
    }

    @Test
    public static void spliceInMiddle(int n) {
        spliceAtIndex(0, 0, n/4);
        spliceAtIndex(n, n/2, n/4);
    }

    @Test
    public static void spliceAtIndex(int n, int index, int n_q) {
        RNAStrand rnaStrand = new RNAStrand();
        for(int i = 0; i < n; i++) {
            rnaStrand.add('A');
        }
        RNAStrand rnaStrand2 = new RNAStrand();
        for(int i = 0; i < n_q; i++) {
            rnaStrand2.add('C');
        }
        rnaStrand.spliceIn(index, rnaStrand2);
        assertEquals(n+n_q, rnaStrand.size(), "Size should be " + (n+n_q) + " after spliceIn(" + index + ", q)");
        for(int i = 0; i < index; i++) {
            assertEquals('A', rnaStrand.get(i), "get(" + i + ") should be 'A' after spliceIn(" + index + ", q)");
        }
        for(int i = index; i < n_q+index; i++) {
            assertEquals('C', rnaStrand.get(i), "get(" + i + ") should be 'C' after spliceIn(" + index + ", q)");
        }
        for(int i = n_q+index; i < n+n_q; i++) {
            assertEquals('A', rnaStrand.get(i), "get(" + i + ") should be 'A' after spliceIn(" + index + ", q)");
        }
    }

    @Test
    public static void reverseExceptions() {
        RNAStrand rnaStrand = new RNAStrand();
        rnaStrand.add('A');
        rnaStrand.add('C');
        assertThrows(IndexOutOfBoundsException.class, () -> rnaStrand.reverse(-1, 0),
                "Should throw IndexOutOfBoundsException when reversing starting at index -1");
        assertThrows(IndexOutOfBoundsException.class, () -> rnaStrand.reverse(rnaStrand.size(), rnaStrand.size()),
                "Should throw IndexOutOfBoundsException when reversing with i=n");
        assertThrows(IndexOutOfBoundsException.class, () -> rnaStrand.reverse(0, 0),
                "Should throw IndexOutOfBoundsException when reversing ending at index 0");
        assertThrows(IndexOutOfBoundsException.class, () -> rnaStrand.reverse(0, rnaStrand.size()+1),
                "Should throw IndexOutOfBoundsException when reversing ending at index n+1");
        assertThrows(IndexOutOfBoundsException.class, () -> rnaStrand.reverse(0, 0),
                "Should throw IndexOutOfBoundsException when reversing with i=k");
        assertThrows(IndexOutOfBoundsException.class, () -> rnaStrand.reverse(1, 0),
                "Should throw IndexOutOfBoundsException when reversing with i>k");
        // No exceptions for these
        rnaStrand.add('G');
        rnaStrand.reverse(0, 3);
        rnaStrand.reverse(1, 3);
        rnaStrand.reverse(2, 3);
        rnaStrand.reverse(0, 2);
        rnaStrand.reverse(0, 1);
        rnaStrand.reverse(1, 2);

        DNAStrand dnaStrand = new DNAStrand();
        dnaStrand.add('A');
        dnaStrand.add('C');
        assertThrows(IndexOutOfBoundsException.class, () -> dnaStrand.reverse(-1, 0),
                "DNA Should throw IndexOutOfBoundsException when reversing starting at index -1");
        assertThrows(IndexOutOfBoundsException.class, () -> dnaStrand.reverse(dnaStrand.size(), dnaStrand.size()),
                "DNA Should throw IndexOutOfBoundsException when reversing with i=n");
        assertThrows(IndexOutOfBoundsException.class, () -> dnaStrand.reverse(0, 0),
                "DNA Should throw IndexOutOfBoundsException when reversing ending at index 0");
        assertThrows(IndexOutOfBoundsException.class, () -> dnaStrand.reverse(0, dnaStrand.size()+1),
                "DNA Should throw IndexOutOfBoundsException when reversing ending at index n+1");
        assertThrows(IndexOutOfBoundsException.class, () -> dnaStrand.reverse(0, 0),
                "DNA Should throw IndexOutOfBoundsException when reversing with i=k");
        assertThrows(IndexOutOfBoundsException.class, () -> dnaStrand.reverse(1, 0),
                "DNA Should throw IndexOutOfBoundsException when reversing with i>k");
        // No exceptions for these
        dnaStrand.add('G');
        dnaStrand.reverse(0, 3);
        dnaStrand.reverse(1, 3);
        dnaStrand.reverse(2, 3);
        dnaStrand.reverse(0, 2);
        dnaStrand.reverse(0, 1);
        dnaStrand.reverse(1, 2);
    }

    @Test
    public static void reverse(int n) {
        RNAStrand rnaStrand = new RNAStrand();
        for(int i = 0; i < n; i++) { // O(n) loop
            rnaStrand.add(bases[i%4]);
        }

        for( int p=1; p < n/2; p++ ) { // O(n) outer loop
            int i=p-1;
            char left = rnaStrand.get(i);
            char right = rnaStrand.get(i+p-1);
            rnaStrand.reverse(i,i+p);
            assertEquals(right, rnaStrand.get(i),
                    "RNA get(" + i + ") should be " + right + " after reverse(" + i + ", " + (i+p) + ")");
            assertEquals(left, rnaStrand.get(i+p-1),
                    "RNA get(" + (i+p-1) + ") should be " + left + " after reverse(" + i + ", " + (i+p) + ")");
        }
    }


    public static void main(String[] args) {
        int n=1000;
        try {
            if (args.length == 1) {
                n = Integer.parseInt(args[0]);
            }
        } catch (NumberFormatException e) {
            System.err.println(e);
            System.exit(-2);
        }

        int[] sizes = {0, 10, 100, 1000};
        long start = System.nanoTime();
        switch (n) {
            case 0:
                System.out.println("Testing empty NA strand constructor...");
                test_creation();
                break;
            case 1:
                System.out.println( "Testing isBase() correctness...");
                isBase();
                break;
            case 2:
                System.out.println("Testing isPair() correctness...");
                isPair();
                break;
            case 3:
                System.out.println( "Testing getBases() correctness...");
                getBases();
                break;
            case 4:
                System.out.println( "Testing add(x)/get correctness with valid bases...");
                for( int i=0; i < sizes.length; i++) {
                    add(sizes[i], true); // all valid bases
                }
                break;
            case 5:
                System.out.println( "Testing add(x)/get correctness with some invalid bases...");
                for( int i=0; i< sizes.length; i++) {
                    add(sizes[i], false); // some bases invalid
                }
                break;
            case 6:
                System.out.println( "Testing add(i,x)/get exceptions...");
                addAtIndexExceptions();
                break;
            case 7:
                System.out.println("Testing add(i,x)/get correctness when i ranges from 0 through n");
                for (int i = 0; i < sizes.length; i++) {
                    addMiddle(sizes[i]);
                }
                break;
            case 8:
                System.out.println( "Testing get(i)/add correctness...");
                for( int i=0; i < sizes.length; i++) {
                    get(sizes[i]);
                }
                break;
            case 9:
                System.out.println( "Testing size()/add correctness...");
                for( int i=0; i < sizes.length; i++) {
                    size(sizes[i]);
                }
                break;
            case 10:
                System.out.println( "Testing clear() correctness...");
                for( int i=0; i < sizes.length; i++) {
                    clear(sizes[i]);
                }
                break;
            case 11:
                System.out.println( "Testing set(i, x) exceptions...");
                setExceptions();
                break;
            case 12:
                System.out.println( "Testing set(i, x) correctness...");
                for( int i=0; i < sizes.length; i++ ) {
                    set(sizes[i]);
                }
                break;
            case 13:
                System.out.println( "Testing remove(i) exceptions...");
                removeExceptions();
                break;
            case 14:
                System.out.println( "Testing remove(n-1) correctness...");
                for( int i=0; i < sizes.length; i++) {
                    removeBack(sizes[i]);
                }
                break;
            case 15:
                System.out.println( "Testing spliceIn(i, q) exceptions...");
                spliceInExceptions();
                break;
            case 16:
                System.out.println( "Testing spliceIn(n/2, q) correctness...");
                for( int i=0; i < sizes.length; i++) {
                    spliceInMiddle(sizes[i]);
                }
                break;
            case 17:
                System.out.println("Testing reverse(i, k) exceptions");
                reverseExceptions();
                break;
            case 18:
                System.out.println("Testing reverse(i, k) correctness...");
                for( int i=0; i < sizes.length; i++) {
                    if( sizes[i] <= 100 ) {
                        reverse(sizes[i]);
                    }
                }
                break;
            default:
                System.out.println("To run an individual test: java NAStrandTest [test number]");
                for( int i=0; i < 19; i++ ) {
                    main(new String[]{"" + i});
                }
                System.exit(-1);
        }
        long stop = System.nanoTime();
        System.out.println("Execution time: " + 1e-9 * (stop-start) + "\n");    }

}
