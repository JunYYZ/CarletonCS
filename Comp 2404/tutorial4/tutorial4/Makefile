#observe - Linux is case sensitive. These names should match your filenames exactly

all: test
	
test: test.o TestControl.o FileList.o File.o Repo.o Tester.o Date.o
	g++ -o test test.o TestControl.o FileList.o File.o Repo.o Tester.o Date.o
	
test.o: test.cc File.h Repo.h
	g++ -c test.cc

TestControl.o:   TestControl.cc TestControl.h
	g++ -c TestControl.cc

FileList.o:   FileList.cc FileList.h
	g++ -c FileList.cc
	
Repo.o:	Repo.h Repo.cc 
	g++ -c Repo.cc 

File.o:	File.h File.cc
	g++ -c File.cc

Date.o:		Date.h Date.cc
	g++ -c Date.cc

Tester.o:	Tester.h Tester.cc
	g++ -c Tester.cc
	
clean:
	rm -f test *.o
