objects = main.o View.o Control.o GetHub.o Repo.o File.o RepoList.o FileList.o Date.o Client.o
testobjects = test.o View.o TestControl.o Tester.o GetHub.o Repo.o File.o RepoList.o FileList.o Date.o Client.o

all: a3 a3test

debug: $(testobjects)
	g++ -g -o a3test $(testobjects)

a3: $(objects)
	g++ -o a3 $(objects)

a3test: $(testobjects)
	g++ -o a3test $(testobjects)

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

Client.o: Client.h Client.cc
	g++ -c Client.cc
	
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
	rm -f a3 a3test *.o	

