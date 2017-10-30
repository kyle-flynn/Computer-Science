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
    scanf(" %f", &quantity_value);
    printf("How much is the price?\n");
    scanf(" %f", &price_value);

    struct product p;

    strcpy(p.name, name);
    strcpy(p.quantity_unit, quantity_unit);
    strcpy(p.price_unit, price_unit);
    p.quantity_value = quantity_value;
    p.price_value = price_value;

    add(&p);

    printf("Added product %s\n", p.name);
}

void purchaseProduct() {
    printInventory();

    char name[20];
    float quantity;

    printf("What product would you like to purchase?\n");
    scanf(" %s", name);
    struct product* p = get(name);

    if (p != NULL) {
        printf("How much of this product would you like to purchase?\n");
        scanf(" %f", &quantity);

        if (quantity <= p->quantity_value) {
            float newQuantity = p->quantity_value - quantity;
            p->quantity_value = newQuantity;
            printf("Successfully purchased %f %s of %s\n", p->quantity_value, p->quantity_unit, p->name);
        } else {
            printf("You cannot purchase more than available from inventory!");
        }

    } else {
        printf("Could not find that product. Make sure you check the inventory!\n");
    }

}

void printPrice() {
    char name[20];

    printf("What product do you want to check the price of?\n");
    scanf(" %s", name);

    struct product* p = get(name);
    if (p != NULL) {
        printf("The price for %s is %f %s.\n", name, p->price_value, p->price_unit);
    } else {
        printf("Did not find a name for that product.\n");
    }
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

void removeProduct() {
    printInventory();
    printf("Which product would you like to remove?\n");

    char name[20];
    scanf(" %s", name);

    struct product* p = get(name);
    if (p != NULL) {
        printf("Deleting product %s from inventory...\n", p->name);
        delete(p);
    } else {
        printf("Could not find that product. Make sure you check the inventory!\n");
    }
}