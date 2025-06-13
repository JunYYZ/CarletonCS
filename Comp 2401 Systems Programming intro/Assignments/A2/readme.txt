COMP 2401 - Tutorial A2 / Assignment 2

Project Details
	Name: Junheng Zhang
	Student #: 101 224 986
	Date Created: 2024/02/08

Files Included
	A2_Perfect_Number.c	- Q1
	A2_Monky_Peach.c 	- Q2
	A2_RNG_Calc.c		- Q3
	A2_Message_Coder.c 	- Q4
	A2_Count_UI.c 		- Q5
	A2_HeartRate.c 		- Q6
	A2.pdf			- The pdf for text questions
	

Compilation and Execution
	To compile and run each of the executable file for the assignment, follow these steps:

	Q1
	gcc -o A2 A2_Perfect_Number.c -lm
	./A2
	
	Q2
	gcc -o A2 A2_Monky_Peach.c -lm
	./A2
	
	Q2
	gcc -o A2 A2_RNG_Calc.c -lm
	./A2
	
	Q4
	gcc -o A2 A2_Message_Coder.c -lm
	./A2
	
	Q5
	gcc -o A2 A2_Count_UI.c -lm
	./A2
	
	Q6
	gcc -o A2 A2_HeartRate.c -lm
	./A2

	You have to run each A2 file after you compile it as compiling a new one will overwrite the old	A2.exe. 
	Sorry for not just naming A2_Q1 or something, forgot about needing to compile, will do next time.

External Libraries Used
	Chapter 1 - 3 Notes - All questions
	
	https://www.rapidtables.com/convert/number/binary-to-decimal.html?x=11 - For all questions involving binary
	
	ctype.h	- Q5
		used isalpha(), isspace(), and isdigit()

	string.h - Q4
		used strcspn()
		
	stdlib.h - Q1, Q5
		used srand() and rand() for Q1
		used getline() for Q5

	time.h - Q1
		for srand() and rand()