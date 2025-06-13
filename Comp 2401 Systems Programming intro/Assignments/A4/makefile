CC=gcc
CFLAGS = -lm 

all: main TripletSum Airplane

main: A4_main.c
	$(CC) $(CFLAGS) -o main A4_main.c

TripletSum: A4_TripletSum.c
	$(CC) $(CFLAGS) -o TripletSum A4_TripletSum.c 

Airplane: A4_Airplane.c
	$(CC) $(CFLAGS) -o Airplane A4_Airplane.c 

clean:
	rm -f all

.PHONY: all clean