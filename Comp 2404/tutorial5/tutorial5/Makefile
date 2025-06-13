
test: test.cc File.o HeapArrays.o StackArrays.o Date.o
	g++ -o test test.cc File.o HeapArrays.o StackArrays.o Date.o

test2: test2.cc File.o Date.o
	g++ -o test2 test2.cc File.o Date.o
	
StackArrays.o:	StackArrays.h StackArrays.cc 
	g++ -c StackArrays.cc 
	
HeapArrays.o:	HeapArrays.h HeapArrays.cc 
	g++ -c HeapArrays.cc 

File.o:	File.h File.cc
	g++ -c File.cc

Date.o:	Date.h Date.cc
	g++ -c Date.cc

	
clean:
	rm -f test test2 *.o
