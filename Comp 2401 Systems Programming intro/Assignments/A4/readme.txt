COMP 2401 - Tutorial A2 / Assignment 4

Project Details
	Name: Junheng Zhang
	Student #: 101 224 986
	Date Created: 2024/03/14

Files Included
	A4_TripletSum.c		- Q1
	A4_Airplane.c 		- Q2
	A4_main.c		- Q3
	makefile		- makefile for Q4
	A4.pdf			- The pdf for text questions
	

Compilation and Execution
	To compile and run each of the executable file for the assignment, follow these steps:

	first use the makefile by typing:
	
	make		
	
	In the command line with the correct diretory.
	Typing make in commandline should compile and provide the executables for Q1 - Q3


	Q1
	./TripletSum
	or
	./TripletSum a b c ...		where a,b,c... are at least 3 integers 
					in order from lowest to largest
	
	Q2
	./Airplane

	Q3
	./main


External Libraries Used
		
	stdlib.h - Q1, Q2
		used srand(), rand(), malloc(), free()

	string.h - Q2
		used strncpy(), strcasecmp()

	time.h - Q2
		for srand() and rand()

	sys/wait.h - Q3
		for pid and waitpid()

	unistd.h - Q3
		for execlp()