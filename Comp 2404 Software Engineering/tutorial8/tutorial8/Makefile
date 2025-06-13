objects = test.o Search.o Episode.o

all: test

test: $(objects)
	g++ -o test $(objects)

test.o: test.cc Search.h Episode.h
	g++ -c test.cc 

Search.o: Search.h Search.cc
	g++ -c Search.cc
	
Episode.o: Episode.cc Episode.h
	g++ -c Episode.cc

clean:
	rm -f test *.o	

