objects = main.o View.o Control.o GetHub.o Repo.o File.o RepoList.o FileList.o Date.o
testobjects = test.o View.o TestControl.o Tester.o GetHub.o Repo.o File.o RepoList.o FileList.o Date.o

all: a2 a2test

debug: $(testobjects)
	g++ -g -o a2test $(testobjects)

a2: $(objects)
	g++ -o a2 $(objects)

a2test: $(testobjects)
	g++ -o a2test $(testobjects)

test.o: test.cc TestControl.o
	g++ -c test.cc
	
main.o: main.cc Control.o
	g++ -c main.cc 

View.o: View.h View.cc 
	g++ -c View.cc

TestControl.o: TestControl.h TestControl.cc GetHub.o
	g++ -c TestControl.cc

Tester.o: Tester.h Tester.cc
	g++ -c Tester.cc

Control.o: Control.h Control.cc GetHub.o
	g++ -c Control.cc

GetHub.o: GetHub.h GetHub.cc RepoList.o
	g++ -c GetHub.cc

FileList.o: FileList.h FileList.cc
	g++ -c FileList.cc

RepoList.o: RepoList.cc RepoList.h Repo.o
	g++ -c RepoList.cc

Repo.o: Repo.cc Repo.h File.h
	g++ -c Repo.cc
	
File.o: File.cc File.h Repo.h
	g++ -c File.cc

Date.o: Date.cc Date.h
	g++ -c Date.cc

clean:
	rm -f a2 a2test *.o	

