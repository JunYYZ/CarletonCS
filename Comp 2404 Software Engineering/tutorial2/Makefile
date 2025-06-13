# Variables
ccFLAGS = -Wall -std=c++11

# Targets
all: p1

p1: Character.o battle.o main.o
	g++ $(ccFLAGS) Character.o battle.o main.o -o p1

Character.o: Character.h Character.cc
	g++ $(ccFLAGS) -c Character.cc

battle.o: battle.h battle.cc
	g++ $(ccFLAGS) -c battle.cc

main.o: Character.h battle.h main.cc
	g++ $(ccFLAGS) -c main.cc

clean:
	rm -f *.o p1