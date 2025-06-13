p2:	main.o Date.o Calendar.o
	g++ -o p2 main.o Date.o Calendar.o

main.o:	main.cc Date.h
	g++ -c main.cc

Date.o:	Date.cc Date.h
	g++ -c Date.cc

Calendar.o:	Calendar.cc Calendar.h
	g++ -c Calendar.cc

clean:
	rm -f *.o p2
