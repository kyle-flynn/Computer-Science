//
// Created by Kyle Flynn on 10/27/2017.
//

#include "LinkedList.h"
#include "GroceryStore.h"

void addProduct() {
    char name[20], quantity_unit[20], price_unit[20];
    float quantity_value, price_value;

    printf("What is the product name?\n");
    scanf(" %s", name);
    printf("What is the unit of quantity?\n");
    scanf(" %s", quantity_unit);
    printf("What is the price value?\n");
    scanf(" %s", price_unit);
    printf("How much quantity is there?\n");
    scanf(" %d", quantity_value);
    printf("How much is the price?\n");
    scanf(" %d", price_value);

    struct product p = {
            .name = name,
            .quantity_unit = quantity_unit,
            .price_unit = price_unit,
            .quantity_value = quantity_value,
            .price_value = price_value
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