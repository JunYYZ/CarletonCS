package comp2402w24l0.tests;

// From within the Lab directory (or whichever directory contains the comp2402w24l0 directory)
// Assumes junit-platform-console-standalone-1.10.1.jar
// is in the Lab/lib/ directory.
// This is the command that works on a linux-based system (e.g. mac os)
// javac -cp "./lib/*:." comp2402w24l0/tests/PairTest.java
// java -cp "./lib/*:." comp2402w24l0/tests/PairTest

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import comp2402w24l0.Pair;

public class PairTest {

    @BeforeEach
    void setUp() {}

    @Test
    void testToString() {
        System.out.println( "Testing toString()...");
        for( int i=0; i < 10; i++ ) {
            Pair p = new Pair((char)('A'+i), (char)('U'-i));
            String expected = "[" + (char)('A'+i) + ", " + (char)('U'-i) + "]";
            assertEquals(expected, p.toString());
            // Note: If you change the expected string to be something else, you can see what
            // happens when an assertion fails (you get an AssertionFailedError.)
        }
    }



    public static void main(String[] args) {
        // Note that the commands to run this are at the top of the file.
        // You need to add the junit-platform-console-standalone-1.10.1.jar to your classpath.
        // This jar file part of the Lab0.zip file, and it should be in the lib/ directory.
        PairTest t = new PairTest();
        t.testToString();
    }
}
