all: LinkedList.h GroceryStore.h LinkedList.o GroceryStore.o  main.o
	gcc -o start_store LinkedList.o GroceryStore.o main.o

main.o: GroceryStore.h main.c
	gcc -c main.c

GroceryStore.o: GroceryStore.h GroceryStore.c
	gcc -c GroceryStore.c

LinkedList.o: LinkedList.h LinkedList.c
	gcc -c LinkedList.c

run: 
	./start_store

clean:
	rm -f *.o main.o start_store

