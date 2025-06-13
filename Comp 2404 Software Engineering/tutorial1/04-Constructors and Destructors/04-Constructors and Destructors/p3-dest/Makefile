p3:	main.o Thing.o
	g++ -o p3 main.o Thing.o

main.o:	main.cc Thing.h
	g++ -c main.cc

Thing.o:	Thing.cc Thing.h
	g++ -c Thing.cc

clean:
	rm -f *.o p3
