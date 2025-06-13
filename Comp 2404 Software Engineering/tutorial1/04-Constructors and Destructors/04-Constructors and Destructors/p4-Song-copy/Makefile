p4:	main.o Song.o
	g++ -o p4 main.o Song.o

main.o:	main.cc Song.h
	g++ -c main.cc

Song.o:	Song.cc Song.h
	g++ -c Song.cc

clean:
	rm -f *.o p4
