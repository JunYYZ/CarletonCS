all: test10

test10:	test10.o Student.o Algorithms.o TestClass.o
	g++ -o test10 test10.o Student.o TestClass.o Algorithms.o

test10.o:	test10.cc Student.h 
	g++ -c test10.cc

TestClass.o:	TestClass.cc TestClass.h
	g++ -c TestClass.cc

Algorithms.o:	Algorithms.cc Algorithms.h
	g++ -c Algorithms.cc

Student.o:	Student.cc Student.h
	g++ -c Student.cc

clean:
	rm -f *.o test10
