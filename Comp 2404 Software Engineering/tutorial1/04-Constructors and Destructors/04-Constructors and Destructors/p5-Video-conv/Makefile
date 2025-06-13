p5:	main.o Song.o Video.o
	g++ -o p5 main.o Song.o Video.o

main.o:	main.cc Song.h Video.h
	g++ -c main.cc

Song.o:	Song.cc Song.h
	g++ -c Song.cc

Video.o:	Video.cc Video.h Song.h
	g++ -c Video.cc

clean:
	rm -f *.o p5
