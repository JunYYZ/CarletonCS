package comp2402w24l2;

// From within the Lab directory (or wherever you put the comp2402w24l2 directory):
// javac comp2402w24l2/Jumble.java
// java comp2402w24l2/Jumble

// You will not need to modify this file, nor submit it to the autograder.
// (Any changes will be ignored by the autograder.)

// Do not add any imports.
// (You shouldn't need any, and you'll fail the autograder if you do.)
import input.InputGenerator;
import input.FileCharGenerator;
import input.AlternatingCharGenerator;


public abstract class Jumble {

    // To be implemented in JumbleA and JumbleB
    public abstract String jumble(InputGenerator<Character> gen);

}
