all: functions.h functions.o p1.o
	gcc -o p functions.o p1.o

main.o: functions.h p1.c
	gcc -c p1.c

functions.o: functions.h functions.c
	gcc -c functions.c
test:
	./p e FEATHER plain1 cipher1
	./p d FEATHER cipher1 newplain1
	diff -s plain1 newplain1

clean:
	rm -f *.o p1.o p newplain1 cipher1 
