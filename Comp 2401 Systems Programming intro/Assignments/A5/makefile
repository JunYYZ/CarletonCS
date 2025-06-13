CC = gcc
CFLAGS = -Wall -Wextra -lm
OBJ = A5_Recipe.o
TARGET = recipe

# The default target
all: $(TARGET)

$(TARGET): $(OBJ)
	$(CC) $(CFLAGS) -o $@ $^

# Compile the A5_Recipe.c source file
A5_Recipe.o: A5_Recipe.c A5_Recipe.h
	$(CC) $(CFLAGS) -c $<

# Clean target to remove compiled objects and the executable
clean:
	rm -f all

.PHONY: all clean