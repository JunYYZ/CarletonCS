package comp2402w24l3;

// THERE IS NOTHING TO DO IN THIS FILE! IT'S NEEDED FOR JUMBLEC.JAVA TO COMPILE.

// From within the Lab directory (or wherever you put the comp2402w24l3 directory):
// javac comp2402w24l3/Jumble.java
// java comp2402w24l3/Jumble

// You will submit this file to the autograder.
// It will not pass many of the tests, if any.
// Your task is to modify the jumble method to pass the tests.

// Do not add any imports.
// (You shouldn't need any, and you'll fail the autograder if you do.)
import input.InputGenerator;
import input.FileCharGenerator;
import input.AlternatingCharGenerator;


public abstract class Jumble {

    /** Returns a jumbled version of the input sequence generated by
     * InputGenerator<Character> gen after it has been jumbled as follows:
     * 1. Initially the cursor is at index 0.
     * 2. As characters are generated, add them at the cursor.
     * 3. If an AUC is generated, rewind the cursor floor(n/2) positions, where n is
     * the number of characters generated thus far, or to 0 if this would move the cursor left of 0.
     * 4. If an AUG is generated, fast forward the cursor floor(n/4) positions, where n
     * is the number of characters generated thus far, or to n if this would move the cursor right of n.
     */
    public abstract String jumble(InputGenerator<Character> gen);



}
