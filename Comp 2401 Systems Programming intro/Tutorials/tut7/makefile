CC=gcc
CFLAGS=-I.

# Define all object files
OBJ = main.o person.o mystat.o

# Define header file dependencies for each source file
DEPS = person.h mystat.h

%.o: %.c $(DEPS)
	$(CC) -c -o $@ $< $(CFLAGS)

# Compile the final program
tutorial: $(OBJ)
	$(CC) -o $@ $^ $(CFLAGS)

clean:
	rm -f *.o tutorial