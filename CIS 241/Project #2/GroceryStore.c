//
// Created by Kyle Flynn on 10/27/2017.
//

#include "LinkedList.h"

void addProduct() {
    struct product p = {
            .name = "Milk",
            .quantity_unit = "bottles",
            .price_unit = "dollars-per-bottle",
            .quantity_value = 3,
            .price_value = 5
    };

    add(&p);

    printf("Added product %s\n", p.name);
}

void printInventory() {
    struct product* current;
    current = list;

    printf("Current Inventory:\n");

    while (current != NULL) {
        product p = *current;
        printf("Product: %s\n", p.name);
        printf("Quantity: %f %s\n", p.quantity_value, p.quantity_unit);
        printf("Price: %f %s\n\n", p.price_value, p.price_unit);

        current = current->next;
    }
}