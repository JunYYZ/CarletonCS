package comp2402w24l3.tests;

// From within the Lab directory (or whichever directory contains the comp2402w24l3 directory)
// Assumes junit-platform-console-standalone-1.10.1.jar
// is in the Lab/lib/ directory.
// This is the command that works on a linux-based system (e.g. mac os)
// javac -cp "./lib/*:." comp2402w24l3/tests/GeneSetTest.java
// java -cp "./lib/*:." comp2402w24l3/tests/GeneSetTest
//
// On a windows machine, this should work:
// javac -cp "./lib/*;." comp2402w24l3/tests/GeneSetTest.java
// java -cp "./lib/*;." comp2402w24l3/tests/GeneSetTest


import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.HashSet;

import comp2402w24l3.GeneSet;

public class GeneSetTest {

    @Test
    public static void test_creation() {
        GeneSet set = new GeneSet();
        assertEquals(0, set.size(), "GeneSet should be empty after construction");
        assertEquals("L0:\t |", set.toString(), "GeneSet should be L0:         | after construction");
        assertEquals(32, set.getHeights()[0], "GeneSet heights should be [32] after construction");
    }


    @Test
    public static void resize() {
        GeneSet set = new GeneSet();
        set.add("C");
        assertEquals(1, set.size(),
                "GeneSet should have size 1 after adding 1 element.");
        assertEquals("L0:\t |-C\n", set.toString(),
                "GeneSet should be L0:         | after construction");
        assertEquals(32, set.getHeights()[0],
                "dummy height should be 32 after construction");
        assertEquals(0, set.getHeights()[1],
                "new element's height should be 0 after construction");

        set.resize( set.getNode(0), 0 );
        assertEquals(0, set.getHeights()[1],
                "new element's height should be 0 after resize to 0");
        assertEquals(32, set.getHeights()[0],
                "dummy height should stay at 32");
        set.resize( set.getNode(0), 1 );
        assertEquals(1, set.getHeights()[1],
                "new element's height should be 1 after resize to 1");
        assertEquals(32, set.getHeights()[0],
                "dummy height should stay at 32");
        assertEquals("L1:\t |-C\nL0:\t |-C\n", set.toString(),
                "GeneSet toString should reflect new height after resize");

        set.resize( set.getNode(0), 0);
        assertEquals(1, set.getHeights()[1],
                "new element's height should be 1 after resize to 0");
        assertEquals(32, set.getHeights()[0],
                "dummy height should stay at 32");
        assertEquals("L1:\t |-C\nL0:\t |-C\n", set.toString(),
                "GeneSet toString should reflect same height after resize down");

        // Let's add a few more nodes
        set.add("B");
        set.add("A");
        assertEquals("L1:\t |-----C\nL0:\t |-A-B-C\n", set.toString(),
                "GeneSet toString should have 3 elements after adding 2 more");

        set.resize( set.getNode(2), 2 );
        assertEquals(2, set.getHeights()[3],
                "element's height should be 2 after resize to 2");
        assertEquals(0, set.getHeights()[2],
                "new element's height should remain 0");
        assertEquals(0, set.getHeights()[1],
                "new element's height should remain 0");
        assertEquals(32, set.getHeights()[0],
                "dummy height should stay at 32");
        assertEquals("L2:\t |-----C\nL1:\t |-----C\nL0:\t |-A-B-C\n", set.toString(),
                "GeneSet toString should reflect new height after resize");
        //System.out.println( set );

        set.resize( set.getNode(0), 1);
        assertEquals(1, set.getHeights()[1],
                "element's height should be 1 after resize to 1");
        assertEquals(2, set.getHeights()[3],
                "element's height should remain 2");
        assertEquals(0, set.getHeights()[2],
                "element's height should remain 0");
        assertEquals(32, set.getHeights()[0],
                "dummy height should stay at 32");
        assertEquals("L2:\t |-----C\nL1:\t |-A---C\nL0:\t |-A-B-C\n", set.toString(),
                "GeneSet toString's height should not change after resizing to something less than h");

        //System.out.println( set );
    }


    @Test
    public static void addExceptions() {
        GeneSet set = new GeneSet();

        set.add("A"); // no exception
        assertThrows(IllegalArgumentException.class, () -> set.add(""),
                "Should throw IllegalArgumentException when adding '' to GeneSet");
    }

    @Test
    public static void addReverse(int n) {
        GeneSet set = new GeneSet();
        int a = (int)'A';

        for (int i = n-1; i >= 0; i--) {
            assertTrue(set.add("" + (char)(a+2*i)),
                    "add(" + (char)(a+2*i) + ") should return true");
            assertFalse(set.add("" + (char)(a+2*i)),
                    "add(" + (char)(a+2*i) + ") should return false");
            assertEquals(n-i, set.size(),
                    "GeneSet size should be " + (n-i) + " after adding " + (n-i) + " elements");
            assertEquals(0, set.getHeights()[1],
                    "new element's height should be 0 after construction");
        }

        set.add("" + (char)(a+2*n)); // adds in a bunch of shortcuts
        assertEquals(n+1, set.size(),
                "GeneSet size should be " + (n+1) + " after adding " + (n+1) + " elements");
        for( int i=0; i < n; i++ ) {
            if( i % 2 == 1 ) {
                assertEquals(1, set.getHeights()[i+1],
                        "some heights should be 1 after add with search path that passes over it");
            } else {
                assertEquals(0, set.getHeights()[i+1],
                        "some heights should remain 0 after add with search path that passes over it");
            }
        }
    }

    @Test
    public static void addForwards(int n) {
        GeneSet set = new GeneSet();
        int a = (int)'A';

        for (int i = 0; i < n; i++) {
            assertTrue(set.add("" + (char)(a+2*i)),
                    "add(" + (char)(a+2*i) + ") should return true");
            assertFalse(set.add("" + (char)(a+2*i)),
                    "add(" + (char)(a+2*i) + ") should return false");
            assertEquals(i+1, set.size(),
                    "GeneSet size should be " + (i+1) + " after adding " + (i+1) + " elements");
            assertEquals(0, set.getHeights()[i+1],
                    "new element's height should be 0 after construction");
        }

        //System.out.println( set );
        //System.out.println( Arrays.toString( set.getHeights() ));

        for( int i=1; i < n; i++ ) {
            int largestPo2 = (int)(Math.log(n)/Math.log(2));
            int largest = (int)Math.pow(2, largestPo2);
            for( int p = largestPo2; p >= 0; p-- ) {
                if( i % largest == 0 ) {
                    assertEquals(p, set.getHeights()[i],
                            "height[" + i + "] should be " + p + " after add with search path that passes over it "
                                    + Arrays.toString(set.getHeights()));
                    break;
                }
                largest = largest / 2;
            }
        }
    }

    @Test
    public static void addPointers() {
        GeneSet set = new GeneSet();
        int[] elts = {197, 193, 189, 195, 187, 196, 194, 195, 181, 188, 183, 186, 181, 184, 186, 180, 196, 189};
        HashSet<Integer> added = new HashSet<>();

        for (int i = 0; i < elts.length; i++) {
            int elt = elts[i];
            assertEquals( added.add(elt), set.add(""+elt),
                    "add("+elt+") ");

            assertEquals( added.size(), set.size(),
                    "GeneSet size should be " + added.size() + " after adding");
            assertFalse( set.add(""+elt),
                    "add("+elt+") the second time should return false");
        }

        assertEquals( added.size(), set.size(),
                "GeneSet size should be " + added.size() + " after adding");
        System.out.println( Arrays.toString( set.getHeights() ));

        int[] heights = set.getHeights();
        int[] ref_heights = {32, 0, 0, 1, 0, 0, 2, 0, 0, 1, 0, 2, 0, 0};
        for( int i=0; i < ref_heights.length; i++ ) {
            assertEquals( ref_heights[i], heights[i],
                    "height[" + i + "] should be " + ref_heights[i] + " after add with search path that passes over it "
                            + Arrays.toString(heights));
        }
    }

    @Test
    public static void findExceptions() {
        GeneSet set = new GeneSet();

        set.find("A"); // no exception
        assertThrows(IllegalArgumentException.class, () -> set.find(""),
                "Should throw IllegalArgumentException when finding '' in GeneSet");
    }

    @Test
    public static void find(int n) {
        GeneSet set = new GeneSet();
        int a = (int)'A';

        for (int i = 0; i < n; i++) {
            assertNull(set.find("" + (char)(a+2*i)),
                    "find(" + (char)(a+2*i) + ") should return null before adding it");
            assertEquals(0, set.size(),
                    "find should not impact size");
        }

        for (int i = n-1; i >= 0; i--) {
            set.add("" + (char)(a+2*i));
        }

        assertEquals( ""+(char)(a), set.find("" + (char)(a)),
                "Finding first element shouldn't add shortcuts and should return the first element");
        assertEquals(0, set.getHeights()[1],
                "first element's height should be 0 after find");

        assertEquals( ""+(char)(a+2), set.find("" + (char)(a+1)),
                "Finding something between first and second element shouldn't add shortcuts and should return the second element");
        assertEquals(0, set.getHeights()[1],
                "first element's height should be 0 after find");
        assertEquals(0, set.getHeights()[2],
                "second element's height should be 0 after find");

        assertEquals( ""+(char)(a+2), set.find("" + (char)(a+2)),
                "Finding the second element should add shortcut and should return the second element");
        assertEquals(0, set.getHeights()[1],
                "first element's height should be 0 after find");
        assertEquals(1, set.getHeights()[2],
                "second element's height should be 1 after find");

        assertNull( set.find("" + (char)(a+2*n)),
                "Finding element > all elements should add lots of level 1 shortcuts and should return null");
        for( int i=1; i <= n; i++ ) {
            if( i % 2 == 1 ) {
                assertEquals(0, set.getHeights()[i],
                        "some heights should be 0 after find with search path that passes over it");
            } else {
                assertEquals(1, set.getHeights()[i],
                        "some heights should remain 1 after find with search path that passes over it");
            }
        }

        assertNull( set.find("" + (char)(a+2*n)),
                "Finding element > all elements *again* should add level 2 shortcuts and should return null");
        for( int i=1; i <= n; i++ ) {
            if( i % 4 == 0 ) {
                assertEquals(2, set.getHeights()[i],
                        "some heights should be 2 after find with search path that passes over it");
            } else if( i % 2 == 1 ) {
                assertEquals(0, set.getHeights()[i],
                        "some heights should remain 0 after find with search path that passes over it");
            } else {
                assertEquals(1, set.getHeights()[i],
                        "some heights should remain 1 after find with search path that passes over it");
            }
        }
    }

    @Test
    public static void removeExceptions() {
        GeneSet set = new GeneSet();

        set.remove("A"); // no exception
        assertThrows(IllegalArgumentException.class, () -> set.remove(""),
                "Should throw IllegalArgumentException when removing '' in GeneSet");
    }



    @Test
    public static void remove(int n) {
        GeneSet set = new GeneSet();
        int a = (int)'A';

        for (int i = n-1; i >= 0; i--) {
            set.add("" + (char)(a+2*i));
        }
        assertEquals(n, set.size(),
                "GeneSet size should be " + n + " after adding " + n + " elements");
        assertEquals(32, set.getHeights()[0],
                "dummy height should be 32 after construction");
        for( int i=1; i <= n; i++ ) {
            assertEquals(0, set.getHeights()[i],
                    "new element's height should be 0 after construction");
        }

        assertFalse( set.remove("" + (char)(a+1)),
                "Removing element between first and second shouldn't add shortcuts and should return false.");
        assertEquals(32, set.getHeights()[0],
                "dummy height should be 32 after construction");
        for( int i=1; i <= n; i++ ) {
            assertEquals(0, set.getHeights()[i],
                    "new element's height should be 0 after construction");
        }
        for (int i = n-1; i >= 0; i--) {
            assertNotNull(set.find("" + (char)(a+2*i)),
                    "Should still be able to find all elements after remove of a non-existent element.");
        } // Note that this test added in a bunch of shortcuts!

        assertTrue( set.remove("" + (char)(a)),
                "Removing first element shouldn't add shortcuts and should return true.");
        for (int i = n-1; i >= 0; i--) {
            assertNotNull(set.find("" + (char)(a+2*i)),
                    "Should still be able to find all elements after remove of a non-existent element.");
        }
        assertEquals(""+(char)(a+2), set.find("" + (char)(a)),
                "Finding the removed element should return the next element");

        for( int i=n-1; i >= 1; i-- ) {
            assertTrue( set.remove("" + (char)(a+2*i)),
                    "Removing all elements should return true");
            assertNull(set.find("" + (char)(a+2*i)),
                    "Should not be able to find removed element");
        }

        assertEquals(0, set.size(),
                "GeneSet size should be 0 after removing all elements");
    }

    @Test
    public static void equals(int n) {
        GeneSet set1 = new GeneSet();
        GeneSet set2 = new GeneSet();
        int a = (int)'A';

        assertFalse( set1.equals(null),
                "set vs. null should be false.");

        assertFalse( set1.equals("bogus"),
                "set vs. other type should be false");

        assertTrue( set1.equals(set2),
                "empty sets should be equal");

        // Add elements in reverse order to set1 and set 2
        for (int i = n-1; i >= 0; i--) {
            set1.add("" + (char)(a+2*i));
            assertFalse( set1.equals(set2), "sets should be not equal after adding "
                    + (char)(a+2*i) + " to set1 but not set2.");
            assertFalse( set2.equals(set1), "sets should be not equal after adding "
                    + (char)(a+2*i) + " to set1 but not set2, order shouldn't matter.");

            assertTrue( set1.equals(set1), "set should always be equal to itself" );
            set2.add("" + (char)(a+2*i));
            assertTrue( set1.equals(set2), "sets should be equal after adding "
                    + (char)(a+2*i) + " to set2.");
            assertTrue( set2.equals(set1), "sets should be equal after adding "
                    + (char)(a+2*i) + " to set2, order shouldn't matter.");
        }

        // Now let's clear set2 and add elements in sorted order
        set2.clear();
        for (int i = 0; i < n; i++) {
            set2.add("" + (char)(a+2*i));
        }
        assertFalse( set1.equals(set2),
                "sets should be not equal if their shortcuts are different.");
        assertFalse( set2.equals(set1),
                "sets should be not equal if their shortcuts are different, order should not matter.");

        // Now let's remove elements from set1 and set2 and check equality as we go
        for (int i = 0; i < n; i++) {
            set1.remove("" + (char)(a+2*i));
            assertFalse( set1.equals(set2),
                    "sets should be not equal after removing "
                            + (char)(a+2*i) + " from set1 but not set2." );
            set2.remove("" + (char)(a+2*i));
        }
        assertTrue( set1.equals(set2),
                "sets should be equal after removing all elements from both sets." );


        // Now let's clear both sets, make two sets with the same structure but
        // different values, make sure equals fails
        set1.clear();
        set2.clear();
        for (int i = 0; i < n; i++) {
            set1.add("" + (char)(a+2*i));
            set2.add("" + (char)(a+2*i+1));
        }
        //System.out.println( set1 );
        //System.out.println( set2 );
        assertFalse( set1.equals(set2),
                "sets should be not equal if their values are different.");
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

        int[] sizes = {6, 18, 100, 1000};
        long start = System.nanoTime();
        switch (n) {
            case 0:
                System.out.println("Testing GeneSet constructors...");
                test_creation();
                break;
            case 1:
                System.out.println( "Testing resize() correctness...");
                resize();
                break;

            case 2:
                System.out.println( "Testing add exceptions...");
                addExceptions();
                break;
            case 3:
                System.out.println( "Testing add(x) correctness when elements added in reverse sorted order...");
                for( int i=0; i < sizes.length; i++) {
                    addReverse(sizes[i]);
                }
                break;
            case 4:
                System.out.println( "Testing add(x) correctness when elements added in sorted order...");
                for( int i=0; i < sizes.length; i++) {
                    addForwards(sizes[i]);
                }
                break;
            case 5:
                System.out.println( "Testing add(x) correctness when elements added in \"random\" order...");
                addPointers();
                break;
            case 6:
                System.out.println( "Testing find exceptions...");
                findExceptions();
                break;
            case 7:
                System.out.println( "Testing find(x) correctness...");
                for( int i=0; i < sizes.length; i++) {
                    find(sizes[i]);
                }
                break;
            case 8:
                System.out.println( "Testing remove exceptions...");
                removeExceptions();
                break;
            case 9:
                System.out.println( "Testing remove(x) correctness...");
                for( int i=0; i < sizes.length; i++) {
                    remove(sizes[i]);
                }
                break;
            case 10:
                System.out.println( "Testing equals(x) correctness...");
                for( int i=0; i < sizes.length; i++) {
                    equals(sizes[i]);
                }
                break;

            default:
                System.out.println("To run an individual test: java GeneSet [test number]");
                for( int i=0; i < 11; i++ ) {
                    main(new String[]{"" + i});
                }
                System.exit(-1);
        }
        long stop = System.nanoTime();
        System.out.println("Execution time: " + 1e-9 * (stop-start) + "\n");    }

}
