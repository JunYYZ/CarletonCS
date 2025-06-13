objects = User.o Customer.o Driver.o View.o Location.o Drawable.o Tester.o

all: test
	
test: test.cc $(objects)
	g++ -o test test.cc $(objects)
	
Customer.o:	Customer.h Customer.cc
	g++ -c Customer.cc

User.o:	User.h User.cc
	g++ -c User.cc

Driver.o:	Driver.h Driver.cc
	g++ -c Driver.cc

ViewLocation.o:	ViewLocation.h ViewLocation.cc
	g++ -c ViewLocation.cc

View.o:	View.h View.cc
	g++ -c View.cc

Drawable.o:	Drawable.h Drawable.cc
	g++ -c Drawable.cc

Tester.o:	Tester.h Tester.cc
	g++ -c Tester.cc
	
clean:
	rm -f test *.o
