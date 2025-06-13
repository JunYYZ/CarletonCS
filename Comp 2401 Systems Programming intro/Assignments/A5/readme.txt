COMP 2401 - Tutorial A2 / Assignment 5

Project Details
	Name: Junheng Zhang
	Student #: 101 224 986
	Date Created: 2024/04/04

Files Included
	A5_Printer.c	- Q1
	A5_Recipe.c	- c file for Q2
	A5_Recipe.h	- header file for Q2
	makefile	- makefile for Q2
	A5.pdf		- The pdf for text questions
	

Compilation and Execution
	To compile and run each of the executable file for the assignment, follow these steps:

	Q1
	gcc -o printer A5_Printer.c -lm
	./printer
	
	Q2
	Use the makefile by typing:
	
	make		
	
	In the command line with the correct diretory.
	Typing make in commandline should compile and provide the executable for Q2

	Then to run the executable:
	./recipe


External Libraries Used

	pthread.h - Q1
		used for multithreadding

	stdlib.h - Q1, Q2
		used exit, EXIT_FAILURE, etc

	string.h - Q2
		used strcpy, strcmp, strcasecmp, strlen, etc

	
	
	
	