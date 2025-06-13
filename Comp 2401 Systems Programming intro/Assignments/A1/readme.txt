COMP 2401 - Tutorial A2 / Assignment 1

Project Details
	Name: Junheng Zhang
	Student #: 101 224 986
	Date Created: 2024/01/25

Files Included
	A1.c - Source code for all questions

Compilation and Execution
	To compile and run the executable file for the assignment, follow these steps:

	gcc -o A1 A1.c -lm
	./A1

	When running the executable, do note that due to how C and scanf() works, if you make
	more inputs than the required inputs by using spaces, ie. writing 2 1 3 4 5 for a prompt that
	asks for 4 numbers, it will just take 5 as the input for the next function. It will not break the
	code, however.

External Libraries Used
	Chapter 1 and Chapter 2 Notes - All questions
	
	https://www.log2base2.com/c-examples/loop/split-a-number-into-digits-in-c.html - Question 2
		adjusted logic for 3 digits
	
	math.h - Question 6, included in command line compile augment
		used pow()
