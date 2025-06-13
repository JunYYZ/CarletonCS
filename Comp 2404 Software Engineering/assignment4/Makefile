objects = main.o Controller.o View.o RideShare.o User.o Drawable.o Driver.o Customer.o Location.o

all: a4

a4: $(objects)
	g++ -o a4 $(objects) 

main.o: main.cc Controller.h 
	g++ -c main.cc 


Controller.o: Controller.h Controller.cc
	g++ -c Controller.cc
	
View.o: View.cc View.h
	g++ -c View.cc

RideShare.o: RideShare.cc RideShare.h
	g++ -c RideShare.cc

User.o: User.cc User.h
	g++ -c User.cc

Drawable.o: Drawable.h Drawable.cc
	g++ -c Drawable.cc

Driver.o: Driver.cc Driver.h User.h
	g++ -c Driver.cc 

Customer.o: Customer.cc Customer.h 
	g++ -c Customer.cc

Location.o: Location.cc Location.h
	g++ -c Location.cc
	

clean:
	rm -f a4 a4test *.o