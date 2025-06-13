COMP 2401 - Tutorial A2 / Assignment 3

Project Details
	Name: Junheng Zhang
	Student #: 101 224 986
	Date Created: 2024/02/29

Files Included
	A3_HPRMS.c	- Q1
	A3_pets.c 	- Q2
	A3.pdf		- The pdf for text questions
	

Compilation and Execution
	To compile and run each of the executable file for the assignment, follow these steps:

	Q1
	gcc -o hospital A3_HPRMS.c -lm
	./hospital	
	or
	./hospital x		where x is a number 6 to 19 patient records generated
	
	Q2
	gcc -o pets A3_pets.c -lm
	./pets
	or
	./pets x		where x is the number of pet records generated.
				Note that if you use ./pets x you have to click enter twice for the menu to show up for some reason.


External Libraries Used
		
	stdlib.h - Q1, Q2
		used srand(), rand(), malloc(), free()

	string.h - Q1, Q2
		used strncpy(), strcasecmp(), strdup()

	time.h - Q1, Q2
		for srand() and rand()