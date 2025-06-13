objects = test9.o Episode.o

all: test9

test9: $(objects)
	g++ -o test9 $(objects)

test9.o: test9.cc Array.h Episode.h 
	g++ -c test9.cc 

Episode.o: Episode.cc Episode.h 
	g++ -c Episode.cc

clean:
	rm -f test9 *.o	

