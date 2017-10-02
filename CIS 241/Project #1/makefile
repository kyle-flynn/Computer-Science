all: functions.h functions.o main.o
	gcc -o p functions.o main.o

main.o: functions.h main.c
	gcc -c main.c

functions.o: functions.h functions.c
	gcc -c functions.c
test:
	./p e FEATHER plain1 cipher1
	./p d FEATHER cipher1 newplain1
	diff -s plain1 newplain1

clean:
	rm -f *.o main.o p newplain1 cipher1 
