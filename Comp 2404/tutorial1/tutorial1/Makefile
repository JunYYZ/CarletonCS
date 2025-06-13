# Compile p1
p1: p1.cc
	g++ -o p1 p1.cc

# Compile p2
p2: p2.cc
	g++ -o p2 p2.cc

# Compile p3 and link with power.o
p3: p3.cc power.o
	g++ -o p3 p3.cc power.o

# Compile power.cc into object code
power.o: power.cc
	g++ -c power.cc

# Compile all executables
all: p1 p2 p3

# Clean executables and object files
clean:
	rm -f p1 p2 p3 power.o