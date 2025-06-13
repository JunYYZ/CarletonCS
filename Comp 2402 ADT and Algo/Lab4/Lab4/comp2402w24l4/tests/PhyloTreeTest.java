package comp2402w24l4.tests;

// From within the Lab directory (or whichever directory contains the comp2402w24l4 directory)
// Assumes junit-platform-console-standalone-1.10.1.jar
// is in the Lab/lib/ directory.
// This is the command that works on a linux-based system (e.g. mac os)
// javac -cp "./lib/*:." comp2402w24l4/tests/PhyloTreeTest.java
// java -cp "./lib/*:." comp2402w24l4/tests/PhyloTreeTest
//
// On a windows machine, this should work:
// javac -cp "./lib/*;." comp2402w24l4/tests/PhyloTreeTest.java
// java -cp "./lib/*;." comp2402w24l4/tests/PhyloTreeTest


import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Iterator;
import ods.ArrayStack;
import ods.ArrayDeque;

import comp2402w24l4.PhyloTree;


public class PhyloTreeTest {

    @Test
    public static void testEmptyConstructor() {
        PhyloTree t = new PhyloTree();
        assertEquals(0, t.size(), "PhyloTree should be empty after construction");
        assertEquals("[]", t.toString(), "PhyloTree should be empty after construction");
        Iterator<String> it = t.iterator();
        assertFalse(it.hasNext(), "PhyloTree iterator should be empty after construction");
    }

    @Test
    public static void addChildExceptions() {
        PhyloTree t = new PhyloTree();

        t.addChild(null, null); // no exception
        t.addChild(null, "A");  // no exception // Now A has one child
        t.addChild("A", null ); // no exception // Now A has two children

        assertThrows(IllegalArgumentException.class,
                () -> t.addChild(null, "A"),
                "Should throw IllegalArgumentException when adding a duplicate String");
        assertThrows(IllegalArgumentException.class,
                () -> t.addChild("B", "C"),
                "Should throw IllegalArgumentException when parent doesn't exist");
        t.addChild(null, "B" ); // no exception // Now B has one child
        assertThrows(IllegalArgumentException.class,
                () -> t.addChild("B", "CC"),
                "Should throw IllegalArgumentException when child String is not length k");
        assertThrows(IllegalArgumentException.class,
                () -> t.addChild("A", "B"),
                "Should throw IllegalArgumentException when parent already has 2 children");
    }

    private static PhyloTree addChildLongSkinny(int n, int k) {
        System.out.println( "Testing PhyloTree addChildLongSkinny with n=" + n + " and k=" + k + "..." );
        String tester = "" + (10*n);
        PhyloTree t = new PhyloTree(tester.length()*k);
        ArrayDeque<String> d = new ArrayDeque<>();
        String parent = null;
        for( int i=0; i < n; i++ ) {
            StringBuilder sb = new StringBuilder();
            for( int j = 0; j < k; j++ ) {
                sb.append("" + (10*n+i));
            }
            String child = sb.toString();
            d.add(0, child);
            t.addChild(parent, child);
            parent = sb.toString();
        }
        assertEquals( d.toString(), t.toString(),
                "PhyloTree toString should be " + d + " after construction");

        Iterator<String> it = t.iterator();
        for( int i=0; i < n; i++ ) {
            assertTrue(it.hasNext(), "PhyloTree iterator should have next after construction");
            assertEquals(d.get(i), it.next(), "PhyloTree iterator should have " + d.get(i) + " next after construction");
        }

        assertEquals( n, t.size(), "PhyloTree should have size " + n + " after construction");

        return t;
    }

    private static PhyloTree addChildBalanced(int n, int k) {
        System.out.println( "Testing PhyloTree addChildBalanced with n=" + n + " and k=" + k + "..." );
        String tester = "" + (10*n);
        PhyloTree t = new PhyloTree(tester.length()*k);

        ArrayStack<String> a = new ArrayStack<String>();
        for( int i=0; i < n; i++ ) {
            StringBuilder sb = new StringBuilder();
            for( int j = 0; j < k; j++ ) {
                sb.append("" + (10*n+i));
            }
            a.add(sb.toString());
        }
        for( int i=0; i < n; i++ ) {
            String parent = null;
            if( i > 0 ) parent = a.get( (i-1)/2);
            t.addChild(parent, a.get(i));
        }

        assertEquals(n, t.size(), "PhyloTree should have size " + n + " after construction");
        Iterator<String> it = t.iterator();
        assertEquals( (n>0), it.hasNext(), "PhyloTree iterator should have next after construction");

        // This is a very limited test, as there is no easy way to test
        // that you've put the elements in the right spot in the tree.
        return t;
    }

    @Test
    public static void testArrayConstructor() {
        for( int n=1; n < 10; n++ ) {
            testArrayConstructor(n, 1);
        }
        testArrayConstructor(7, 2);
        testArrayConstructor( 8, 3);
        testArrayConstructor( 9, 4 );
        testArrayConstructor( 10, 3);
        testArrayConstructor( 15, 4);
        testArrayConstructor( 26, 1 );
    }

    @Test
    public static void testArrayConstructorExceptions() {
        ArrayStack<String> a = new ArrayStack<String>();
        a.add("A");
        a.add(null);
        a.add("B");
        new PhyloTree(a); // no exceptions here
        a.add("XYZ"); // left child
        assertThrows(IllegalArgumentException.class, () -> new PhyloTree(a),
                "Should throw IllegalArgumentException when adding Strings of different lengths, left child");

        a.set(3, "C");
        a.set(0, "");
        assertThrows(IllegalArgumentException.class, () -> new PhyloTree(a),
                "Should throw IllegalArgumentException when adding '' to PhyloTree at root");

        a.set(0,"A");
        a.set(3, "");
        assertThrows(IllegalArgumentException.class, () -> new PhyloTree(a),
                "Should throw IllegalArgumentException when adding '' to PhyloTree at left child");

        a.set(3, "C");
        a.add(""); // right child
        assertThrows(IllegalArgumentException.class, () -> new PhyloTree(a),
                "Should throw IllegalArgumentException when adding '' to PhyloTree at right child");
        a.set(4, "XYZ");
        assertThrows(IllegalArgumentException.class, () -> new PhyloTree(a),
                "Should throw IllegalArgumentException when adding Strings of different lengths");

        a.set(4, "C");
        assertThrows(IllegalArgumentException.class, () -> new PhyloTree(a), // right child
                "Should throw IllegalArgumentException when adding duplicate Strings");

        a.set(3, "B"); // left child
        assertThrows(IllegalArgumentException.class, () -> new PhyloTree(a),
                "Should throw IllegalArgumentException when adding duplicate Strings");

        a.clear();
        a.add("");
        a.add("");
        a.add("");
        assertThrows(IllegalArgumentException.class, () -> new PhyloTree(a),
                "Should throw IllegalArgumentException when adding '' to PhyloTree at root");

    }

    public static void testArrayConstructor(int n, int k) {
        System.out.println( "Testing PhyloTree array constructor with n=" + n + " and k=" + k + "..." );
        ArrayStack<String> a = new ArrayStack<String>();
        for( int i=0; i < n; i++ ) {
            StringBuilder sb = new StringBuilder();
            for( int j = 0; j < k; j++ ) {
                sb.append((char)('a'+i));
            }
            a.add(sb.toString());
        }
        PhyloTree t = new PhyloTree(a); // should not throw exceptions
        assertEquals(n, t.size(), "PhyloTree should have size " + n + " after construction");

        Iterator<String> it = t.iterator();
        assertEquals( (n>0), it.hasNext(), "PhyloTree iterator should have next after construction");

        int i = 0;
        while( it.hasNext() ) {
            String next = it.next();
            i++;
        }
        assertEquals( n, i, "PhyloTree iterator should have " + n + " nexts after construction" );

        // Hard-code some toStrings for basic, basic testing
        if( n == 1 && k==1 ) {
            assertEquals("[a]", t.toString(), "PhyloTree toString should be [a] after construction");
        } else if( n == 2 && k==1 ) {
            assertEquals("[b, a]", t.toString(), "PhyloTree toString should be [b, a] after construction");
        } else if( n == 3 && k==1 ) {
            assertEquals("[b, a, c]", t.toString(), "PhyloTree toString should be [b, a, c] after construction");
        } else if( n == 4 && k==1 ) {
            assertEquals("[d, b, a, c]", t.toString(), "PhyloTree toString should be [d, b, a, c] after construction");
        } else if ( n == 8 && k == 3 ) {
            assertEquals("[hhh, ddd, bbb, eee, aaa, fff, ccc, ggg]", t.toString(), "PhyloTree toString should be [hhh,ddd,bbb,eee,aaa,fff,ccc,ggg] after construction");
        }
    }

    public static void testConstructorPerformance(int n, int k) {
        System.out.println( "Testing PhyloTree constructor with n=" + n + " and k=" + k + "..." );
        ArrayStack<String> a = new ArrayStack<String>();
        for( int i=0; i < n; i++ ) {
            StringBuilder sb = new StringBuilder();
            for( int j = 0; j < k; j++ ) {
                sb.append("" + (10*n+i) );
            }
            a.add(sb.toString());
        }
        PhyloTree t = new PhyloTree(a);
        assertEquals(n, t.size(), "PhyloTree should have size " + n + " after construction");

        Iterator<String> it = t.iterator();
        assertEquals( (n>0), it.hasNext(), "PhyloTree iterator should have next after construction");

        int i = 0;
        while( it.hasNext() ) {
            String next = it.next();
            i++;
        }
        assertEquals( n, i, "PhyloTree iterator should have " + n + " nexts after construction" );

    }


    private static void testLCASameNode(int n, int k) {
        System.out.println( "Testing PhyloTree LCA with n=" + n + " and k=" + k + "..." );
        ArrayStack<String> a = new ArrayStack<String>();
        for( int i=0; i < n; i++ ) {
            StringBuilder sb = new StringBuilder();
            for( int j = 0; j < k; j++ ) {
                sb.append("" + (10*n+i) );
            }
            a.add(sb.toString());
        }
        PhyloTree t = new PhyloTree(a);
        assertEquals(n, t.size(), "PhyloTree should have size " + n + " after construction");
        //System.out.println(t.prettyPrint());

        for( int i=0; i < a.size(); i++ ) {
            assertEquals(a.get(i), t.LCA(a.get(i), a.get(i)),
                    "LCA(" + a.get(i) + "," + a.get(i) + ") = " + t.LCA(a.get(i), a.get(i)));
        }
    }
    private static void testLCARoot(int n, int k) {
        System.out.println( "Testing PhyloTree LCA with n=" + n + " and k=" + k + "..." );
        ArrayStack<String> a = new ArrayStack<String>();
        for( int i=0; i < n; i++ ) {
            StringBuilder sb = new StringBuilder();
            for( int j = 0; j < k; j++ ) {
                sb.append("" + (10*n+i) );
            }
            a.add(sb.toString());
        }
        PhyloTree t = new PhyloTree(a);
        assertEquals(n, t.size(), "PhyloTree should have size " + n + " after construction");
        //System.out.println(t.prettyPrint());

        for( int i=0; i < a.size(); i++ ) {
            assertEquals(a.get(0), t.LCA(a.get(0), a.get(i)),
                    "LCA(" + a.get(0) + " (root)," + a.get(i) + ") = " + t.LCA(a.get(0), a.get(i)) );
            assertEquals(a.get(0), t.LCA(a.get(i), a.get(0)),
                    "LCA(" + a.get(i) + " (root)," + a.get(0) + ") = " + t.LCA(a.get(i), a.get(0)) );
        }
    }

    private static void testLCASiblings(int n, int k) {
        System.out.println( "Testing PhyloTree LCA siblings with n=" + n + " and k=" + k + "..." );
        ArrayStack<String> a = new ArrayStack<String>();
        for( int i=0; i < n; i++ ) {
            StringBuilder sb = new StringBuilder();
            for( int j = 0; j < k; j++ ) {
                sb.append("" + (10*n+i) );
            }
            a.add(sb.toString());
        }
        PhyloTree t = new PhyloTree(a);
        assertEquals(n, t.size(), "PhyloTree should have size " + n + " after construction");
        //System.out.println(t.prettyPrint());

        for( int i=1; i < a.size()-1; i+=2 ) {
            assertEquals(a.get((i-1)/2), t.LCA(a.get(i), a.get(i+1)),
                    "LCA(" + a.get(i) + " ," + a.get(i+1) + ") = " + t.LCA(a.get(i), a.get(i+1)) );
            assertEquals(a.get((i-1)/2), t.LCA(a.get(i+1), a.get(i)),
                    "LCA(" + a.get(i+1) + " ," + a.get(i) + ") = " + t.LCA(a.get(i+1), a.get(i)) );

        }
    }

    private static void testLCAParent(int n, int k) {
        System.out.println( "Testing PhyloTree LCA parent with n=" + n + " and k=" + k + "..." );
        ArrayStack<String> a = new ArrayStack<String>();
        for( int i=0; i < n; i++ ) {
            StringBuilder sb = new StringBuilder();
            for( int j = 0; j < k; j++ ) {
                sb.append("" + (10*n+i) );
            }
            a.add(sb.toString());
        }
        PhyloTree t = new PhyloTree(a);
        assertEquals(n, t.size(), "PhyloTree should have size " + n + " after construction");
        //System.out.println(t.prettyPrint());

        for( int i=0; 2*i+2 < a.size(); i++ ) {
            assertEquals(a.get(i), t.LCA(a.get(i), a.get(2*i+1)),
                    "LCA(" + a.get(i) + "," + a.get(2*i+1) + ") = " + t.LCA(a.get(i), a.get(2*i+1)) ); //+ "\n" + t.prettyPrint() );
            assertEquals(a.get(i), t.LCA(a.get(2*i+1), a.get(i)),
                    "LCA(" + a.get(2*i+1) + "," + a.get(i) + ") = " + t.LCA(a.get(2*i+1), a.get(i)) ); // + "\n" + t.prettyPrint()  );
            assertEquals(a.get(i), t.LCA(a.get(i), a.get(2*i+2)),
                    "LCA(" + a.get(i) + "," + a.get(2*i+2) + ") = " + t.LCA(a.get(i), a.get(2*i+2)) ); //+ "\n" + t.prettyPrint()  );
            assertEquals(a.get(i), t.LCA(a.get(2*i+2), a.get(i)),
                    "LCA(" + a.get(2*i+2) + "," + a.get(i) + ") = " + t.LCA(a.get(2*i+2), a.get(i)) ); // + "\n" + t.prettyPrint() );
        }
    }

    private static void testLCAVarious(int n, int k) {
        System.out.println( "Testing PhyloTree LCA parent with n=" + n + " and k=" + k + "..." );
        ArrayStack<String> a = new ArrayStack<String>();
        for( int i=0; i < n; i++ ) {
            StringBuilder sb = new StringBuilder();
            for( int j = 0; j < k; j++ ) {
                sb.append("" + (10*n+i) );
            }
            a.add(sb.toString());
        }
        PhyloTree t = new PhyloTree(a);
        assertEquals(n, t.size(), "PhyloTree should have size " + n + " after construction");
        //System.out.println(t.prettyPrint());

        int p = 4;
        while( p-2 < a.size() ) {
            assertEquals(a.get(0), t.LCA(a.get(1), a.get(p-2)),
                    "LCA(" + a.get(1) + "," + a.get(p-2) + ") = " + t.LCA(a.get(1), a.get(p-2)) );
            assertEquals(a.get(0), t.LCA(a.get(p/2-1), a.get(p-2)),
                    "LCA(" + a.get(p/2-1) + "," + a.get(p-2) + ") = " + t.LCA(a.get(p/2-1), a.get(p-2)) );
            p *= 2;

        }
    }

    private static void testLCAExceptions() {
        System.out.println( "Testing PhyloTree LCA Exceptions..." );
        ArrayStack<String> a = new ArrayStack<String>();
        for( int i=0; i < 10; i++ ) {
            StringBuilder sb = new StringBuilder();
            for( int j = 0; j < 3; j++ ) {
                sb.append("" + (100+i) );
            }
            a.add(sb.toString());
        }
        PhyloTree t = new PhyloTree(a);
        assertEquals(10, t.size(), "PhyloTree should have size " + 10 + " after construction");

        //System.out.println( t.prettyPrint() );
        assertThrows(IllegalArgumentException.class, () -> t.LCA(null, a.get(0)),
                "Should throw IllegalArgumentException when finding LCA with first param null");
        assertThrows(IllegalArgumentException.class, () -> t.LCA(a.get(0), null),
                "Should throw IllegalArgumentException when finding LCA with second param null");
        assertThrows(IllegalArgumentException.class, () -> t.LCA(null, null),
                "Should throw IllegalArgumentException when finding LCA with both null");
        assertThrows(IllegalArgumentException.class, () -> t.LCA("A", "100100100"),
                "Should throw IllegalArgumentException when finding LCA with first param non-existent nodes");
        assertThrows(IllegalArgumentException.class, () -> t.LCA("100100100", "A"),
                "Should throw IllegalArgumentException when finding LCA with second param non-existent nodes");
        assertThrows(IllegalArgumentException.class, () -> t.LCA("A", "B"),
                "Should throw IllegalArgumentException when finding LCA with both params non-existent nodes");

    }

    @Test
    private static void testFixUp(int n, int k) {
        PhyloTree t = addChildLongSkinny(n, k);
        //System.out.println(t.prettyPrint());

        StringBuilder root = new StringBuilder();
        StringBuilder leaf = new StringBuilder();
        ArrayStack<String> a = new ArrayStack<String>();
        for( int i=n-1; i >= 0; i-- ) {
            StringBuilder sb = new StringBuilder();
            for( int j = 0; j < k; j++ ) {
                sb.append("" + (10*n+i) );
            }
            a.add( sb.toString() );
        }
        for(int j = 0; j < k; j++ ) {
            root.append("" + (10*n));
            leaf.append("" + ((10*n)+(n-1)));
        }
        String before = t.toString();
        t.fixUp(root.toString() ); // nothing should happen when fixUp root
        String after = t.toString();
        assertEquals(before, after, "Expect no change after fixup(root)");

        t.fixUp(leaf.toString() );
        //System.out.println(t.prettyPrint());
        //System.out.println( t );
        assertEquals( a.toString(), t.toString(), "Expect " + a + " after fixup(leaf)" );
        //sSystem.out.println( a.toString() );

        assertThrows(IllegalArgumentException.class, () -> t.fixUp("A"),
                "Should throw IllegalArgumentException when fixing up non-existent node");

        assertThrows(IllegalArgumentException.class, () -> t.fixUp(""),
                "Should throw IllegalArgumentException when fixing up empty node");

        assertThrows(IllegalArgumentException.class, () -> t.fixUp(null),
                "Should throw IllegalArgumentException when fixing up null node");

    }

    @Test
    private static void testFixUpExceptions() {
        PhyloTree t = addChildLongSkinny(10, 2);

        assertThrows(IllegalArgumentException.class, () -> t.fixUp("A"),
                "Should throw IllegalArgumentException when fixing up non-existent node");

        assertThrows(IllegalArgumentException.class, () -> t.fixUp(""),
                "Should throw IllegalArgumentException when fixing up empty node");

        assertThrows(IllegalArgumentException.class, () -> t.fixUp(null),
                "Should throw IllegalArgumentException when fixing up null node");

    }

    @Test
    private static void testComputeSetsVarious() {
        ArrayStack<String> a = new ArrayStack<String>();
        a.add("AAA");
        PhyloTree t = new PhyloTree(a);
        //System.out.println(t.prettyPrint());
        t.computeSets(0);   // should be [A] for the only node
        assertEquals("[AAA: [A]]", t.allSets().toString(),
                "Expect [AAA: [A]] for index 0: " + t.allSets());
        t.computeSets(1);   // should be [A] for the only node
        assertEquals("[AAA: [A]]", t.allSets().toString(),
                "Expect [AAA: [A]] for index 1: " + t.allSets());
        t.computeSets(2);   // should be [A] for the only node
        assertEquals("[AAA: [A]]", t.allSets().toString(),
                "Expect [AAA: [A]] for index 2: " + t.allSets());


        a.add("ACA"); // Now there are two nodes, ACA is the leaf, AAA the root
        t = new PhyloTree(a);
        t.computeSets(0);
        assertEquals("[ACA: [A], AAA: [A]]", t.allSets().toString(),
                "Expect [ACA: [A], AAA: [A]] for index 0: " + t.allSets());
        t.computeSets(1);
        assertEquals("[ACA: [C], AAA: [C]]", t.allSets().toString(),
                "Expect [ACA: [C], AAA: [C]] for index 1: " + t.allSets());
        t.computeSets(2);
        assertEquals("[ACA: [A], AAA: [A]]", t.allSets().toString(),
                "Expect [ACA: [A], AAA: [A]] for index 2: " + t.allSets());

        a.add("AGA");
        a.add("ATA");
        t = new PhyloTree(a);
        //System.out.println(t.prettyPrint());
        t.computeSets(0);
        assertEquals("[ATA: [A], ACA: [A], AAA: [A], AGA: [A]]", t.allSets().toString(),
                "Expect [ATA: [A], ACA: [A], AAA: [A], AGA: [A]] since all index 0's are As: " + t.allSets());
        t.computeSets(1);
        assertEquals("[ATA: [T], ACA: [T], AAA: [G, T], AGA: [G]]", t.allSets().toString(),
                "Expect [ATA: [T], ACA: [T], AAA: [G, T], AGA: [G]] for index 1: " + t.allSets());
        a.add("ATC");
        t = new PhyloTree(a);
        //System.out.println(t.prettyPrint());
        t.computeSets(2);
        assertEquals("[ATA: [A], ACA: [A, C], ATC: [C], AAA: [A], AGA: [A]]", t.allSets().toString(),
                "Expect [ATA: [A], ACA: [A, C], ATC: [C], AAA: [A], AGA: [A]] for index 2: " + t.allSets());
        //System.out.println( "Expect [ATA: [A], ACA: [A, C], ATC: [C], AAA: [A], AGA: [A]] for index 2: " + t.allSets() ); // left child but no right

        a.clear();
        a.add(null);
        a.add(null);
        a.add(null);
        a.add("A");
        a.add("C");
        a.add("G");
        a.add("T");
        t = new PhyloTree(a);
        //System.out.println(t.prettyPrint());
        t.computeSets(0);
        assertEquals("[A: [A], null: [A, C], C: [C], null: [A, C, G, T], G: [G], null: [G, T], T: [T]]", t.allSets().toString(),
                "Expect [A: [A], null: [A, C], C: [C], null: [A, C, G, T], G: [G], null: [G, T], T: [T]]: " + t.allSets());
    }

    public static void testComputeSetsExceptions() {
        PhyloTree t = new PhyloTree();
        t.addChild(null, "A");
        assertThrows(IllegalArgumentException.class, () -> t.computeSets(1),
                "Should throw IllegalArgumentException when computeSet with index > k");
        assertThrows(IllegalArgumentException.class, () -> t.computeSets(-1),
                "Should throw IllegalArgumentException when computeSet with index < 0");
        t.addChild("A", null);
        assertThrows(IllegalArgumentException.class, () -> t.computeSets(0),
                "Should throw IllegalArgumentException when computeSet has null leaf nodes");
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

        int[] sizes = {6, 18, 100, 1000, 10000, 100000, 1000000};
        long start = System.nanoTime();
        switch (n) {
            case 0:
                System.out.println("Testing PhyloTree empty constructor...");
                testEmptyConstructor();
                break;
            case 1:
                System.out.println( "Testing addChild exceptions...");
                addChildExceptions();
                break;
            case 2:
                System.out.println( "Testing addChild correctness long skinny small k...");
                for( int i=0; i < sizes.length; i++) {
                    addChildLongSkinny(sizes[i], 1);
                }
                break;
            case 3:
                System.out.println( "Testing addChild correctness balanced small k...");
                for( int i=0; i < sizes.length; i++) {
                    addChildBalanced(sizes[i], 1);
                }
                break;
            case 4:
                System.out.println( "Testing addChild correctness long skinny...");
                for( int i=0; i < sizes.length; i++) {
                    addChildLongSkinny(sizes[i], 2);
                }
                break;
            case 5:
                System.out.println( "Testing addChild correctness balanced...");
                for( int i=0; i < sizes.length; i++) {
                    addChildBalanced(sizes[i], 3);
                }
                break;
            case 6:
                System.out.println( "Testing PhyloTree ArrayStack constructor...");
                testArrayConstructor();
                break;
            case 7:
                System.out.println( "Testing PhyloTree ArrayStack constructor exceptions...");
                testArrayConstructorExceptions();
                break;
            case 8:
                System.out.println( "Testing PhyloTree ArrayStack constructor O(n) time performance...");
                System.out.println( "\tYou pass this test if it executes within 2 seconds.");
                System.out.println( "\tIf it's taking forever, try CTRL-C or CTRL-Z to stop it.");
                for( int i=0; i < sizes.length; i++) {
                    testConstructorPerformance(sizes[i], i%4+1);
                }
                break;
            case 9:
                System.out.println( "Testing PhyloTree ArrayStack constructor O(1) call stack...");
                System.out.println( "\tRun this test with -Xss144k to limit the call stack.");
                testConstructorPerformance(4000000, 1);
                break;
            case 10:
                System.out.println( "Testing LCA(x,x) correctness k=1...");
                for( int i=0; i < sizes.length-3; i++) {
                    testLCASameNode(sizes[i], 1);
                }
                break;
            case 11:
                System.out.println( "Testing LCA(root,x) correctness k=1...");
                for( int i=0; i < sizes.length-3; i++) {
                    testLCARoot(sizes[i], 1);
                }
                break;
            case 12:
                System.out.println( "Testing LCA(siblings) correctness k=1...");
                for( int i=0; i < sizes.length-3; i++) {
                    testLCASiblings(sizes[i], 1);
                }
                break;
            case 13:
                System.out.println( "Testing LCA(parent,x) correctness k=1...");
                for( int i=0; i < sizes.length-3; i++) {
                    testLCAParent(sizes[i], 1);
                }
                break;
            case 14:
                System.out.println( "Testing LCA various k=1...");
                for( int i=0; i < sizes.length-3; i++) {
                    testLCAVarious(sizes[i], 1);
                }
                break;
            case 15:
                System.out.println( "Testing LCA time performance k=1...");
                System.out.println( "\tYou pass this test if it executes within 2 seconds.");
                System.out.println( "\tIf it's taking forever, try CTRL-C or CTRL-Z to stop it.");
                for( int i=sizes.length-3; i < sizes.length; i++) {
                    testLCASameNode(sizes[i], 1);
                    testLCAVarious(sizes[i], 1);
                }
                break;
            case 16:
                System.out.println( "Testing LCA(x,x) correctness k>1...");
                for( int i=0; i < sizes.length-3; i++) {
                    testLCASameNode(sizes[i], 1+i%4);
                }
                break;
            case 17:
                System.out.println( "Testing LCA(root,x) correctness k>1...");
                for( int i=0; i < sizes.length-3; i++) {
                    testLCARoot(sizes[i], 1+i%4);
                }
                break;
            case 18:
                System.out.println( "Testing LCA(siblings) correctness k>1...");
                for( int i=0; i < sizes.length-3; i++) {
                    testLCASiblings(sizes[i], 1+i%4);
                }
                break;
            case 19:
                System.out.println( "Testing LCA(parent,x) correctness k>1...");
                for( int i=0; i < sizes.length-3; i++) {
                    testLCAParent(sizes[i], 1+i%4);
                }
                break;
            case 20:
                System.out.println( "Testing LCA various k>1...");
                for( int i=0; i < sizes.length-3; i++) {
                    testLCAVarious(sizes[i], 1+i%4);
                }
                break;
            case 21:
                System.out.println( "Testing LCA time performance k>1...");
                System.out.println( "\tYou pass this test if it executes within 4 seconds.");
                System.out.println( "\tIf it's taking forever, try CTRL-C or CTRL-Z to stop it.");
                for( int i=sizes.length-3; i < sizes.length; i++) {
                    testLCAParent(sizes[i], 1+i%4);
                    testLCASiblings(sizes[i], 1+i%4);
                }
                break;
            case 22:
                System.out.println( "Testing LCA exceptions...");
                testLCAExceptions();
                break;
            case 23:
                System.out.println( "Testing fixUp correctness k=1...");
                for( int i=0; i < sizes.length-3; i++) {
                    testFixUp(sizes[i], 1);
                }
                break;
            case 24:
                System.out.println( "Testing fixUp exceptions...");
                testFixUpExceptions();
                break;
            case 25:
                System.out.println( "Testing fixUp correctness k>1...");
                for( int i=0; i < sizes.length-3; i++) {
                    testFixUp(sizes[i], 1+i%4);
                }
                break;
            case 26:
                System.out.println( "Testing fixUp time performance k=1...");
                System.out.println( "\tYou pass this test if it executes within 2 seconds.");
                System.out.println( "\tIf it's taking forever, try CTRL-C or CTRL-Z to stop it.");

                for( int i=sizes.length-3; i < sizes.length; i++) {
                    testFixUp(sizes[i], 1);
                }
                break;
            case 27:
                System.out.println( "Testing fixUp time performance k>1...");
                System.out.println( "\tYou pass this test if it executes within 2 seconds.");
                System.out.println( "\tIf it's taking forever, try CTRL-C or CTRL-Z to stop it.");
                for( int i=sizes.length-3; i < sizes.length; i++) {
                    testFixUp(sizes[i], 1+i%4);
                }
                break;
            case 28:
                System.out.println( "Testing computeSets various...");
                testComputeSetsVarious();
                break;
            case 29:
                System.out.println( "Testing computeSets exceptions...");
                testComputeSetsExceptions();
                break;
            default:
                System.out.println("To run an individual test: java PhyloTree [test number]");
                for( int i=0; i < 30; i++ ) {
                    main(new String[]{"" + i});
                }
                System.exit(-1);
        }
        long stop = System.nanoTime();
        System.out.println("Execution time: " + 1e-9 * (stop-start) + "\n");    }

}
