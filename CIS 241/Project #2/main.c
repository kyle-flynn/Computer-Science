#include <stdio.h>
#include "LinkedList.h"

int main() {
    struct product eggs;
    strcpy(eggs.name, "Eggs");
    eggs.quantity_value = 10;

    struct product milk;
    strcpy(milk.name, "Milk");
    milk.quantity_value = 1;

    struct product cheese;
    strcpy(cheese.name, "Cheese");
    cheese.quantity_value = 4;

    add(&eggs);
    add(&milk);
    add(&cheese);

    print();

    delete(&milk);

    printf("\n");

    print();

    return 0;
}