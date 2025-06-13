# Compiler and flags
CXX = g++
CXXFLAGS = -std=c++11 -Wall -pedantic

# Executable name
EXEC = a1

# Object files
OBJS = main.o Date.o Category.o Camper.o Campsite.o Campground.o Tester.o

# Target to compile and link the entire project
$(EXEC): $(OBJS)
	$(CXX) $(CXXFLAGS) -o $(EXEC) $(OBJS)

# Compile object files

main.o: main.cc Campground.h Date.h Tester.h
	$(CXX) $(CXXFLAGS) -c main.cc

Date.o: Date.cc Date.h
	$(CXX) $(CXXFLAGS) -c Date.cc

Category.o: Category.cc Category.h
	$(CXX) $(CXXFLAGS) -c Category.cc

Camper.o: Camper.cc Camper.h Date.h
	$(CXX) $(CXXFLAGS) -c Camper.cc

Campsite.o: Campsite.cc Campsite.h Camper.h Date.h Category.h defs.h
	$(CXX) $(CXXFLAGS) -c Campsite.cc

Campground.o: Campground.cc Campground.h Campsite.h defs.h
	$(CXX) $(CXXFLAGS) -c Campground.cc


Tester.o: Tester.cc Tester.h
	$(CXX) $(CXXFLAGS) -c Tester.cc

# Clean command to remove compiled object files and the executable
clean:
	rm -f $(OBJS) $(EXEC)

# Phony target to prevent conflicts with files named clean
.PHONY: clean
