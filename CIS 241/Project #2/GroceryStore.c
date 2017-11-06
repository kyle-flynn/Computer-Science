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

    struct product* p = get(name);

    if (p != NULL) {
        printf("Found product %s. How much more would you like to add?\n");
        scanf(" %f", &quantity_value);
        float newQuantity = quantity_value + p->quantity_value;
        p->quantity_value = newQuantity;
        printf("Successfully added %f %s of %s.\n", quantity_value, p->quantity_unit, p->name);
    } else {
        printf("What is the unit of quantity?\n");
        scanf(" %s", quantity_unit);
        printf("What is the price unit?\n");
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
        p.profits = 0;

        add(&p);

        printf("Added product %s\n", p.name);
    }

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

        if (quantity < p->quantity_value) {
            float newQuantity = p->quantity_value - quantity;
            float newProfits = p->profits + (quantity * p->price_value);
            printf("Successfully purchased %f %s of %s\n", quantity, p->quantity_unit, p->name);
            p->quantity_value = newQuantity;
            p->profits = newProfits;
        } else if (quantity == p->quantity_value) {
            printf("Successfully purchased %f %s of %s. The product has been sold out.\n", quantity, p->quantity_unit, p->name);
            delete(p);
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
        printf("Price: %f %s\n", p.price_value, p.price_unit);
        printf("Profits: %f\n\n", p.profits);

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

void printProduct() {
    printf("Which product would you like to know about?\n");

    char name[20];
    scanf(" %s", name);

    struct product* p = get(name);

    if (p != NULL) {
        printf("Product: %s\n", p->name);
        printf("Quantity: %f %s\n", p->quantity_value, p->quantity_unit);
        printf("Price: %f %s\n", p->price_value, p->price_unit);
        printf("Profits: %f\n\n", p->profits);
    } else {
        printf("Could not find that product. Make sure you check the inventory!\n");
    }
}

void loadStore() {
    FILE *f = fopen("store.txt", "r");
    if (f == NULL) {
        printf("Error saving file!\n");
        exit(1);
    }

    char line[20];

    struct product p;

    int i = 0;

    while (fgets(line, sizeof(line), f)) {
        line[strlen(line)-1] = '\0';
        if (i == 0) {
            strcpy(p.name, line);
        } else if (i == 1) {
            strcpy(p.quantity_unit, line);
        } else if (i == 2) {
            strcpy(p.price_unit, line);
        } else if (i == 3) {
            p.quantity_value = strtof(line, NULL);
        } else if (i == 4) {
            p.price_value = strtof(line, NULL);
        } else if (i == 5) {
            p.profits = strtof(line, NULL);
            i = -1;
            add(&p);
        }

        i++;
    }

    fclose(f);
}

void saveStore() {
    FILE *f = fopen("store.txt", "w");
    if (f == NULL) {
        printf("Error saving file!\n");
        exit(1);
    }

    struct product* current = list;

    while (current != NULL) {
        fprintf(f, "%s\n", current->name);
        fprintf(f, "%s\n", current->quantity_unit);
        fprintf(f, "%s\n", current->price_unit);
        fprintf(f, "%f\n", current->quantity_value);
        fprintf(f, "%f\n", current->price_value);
        fprintf(f, "%f\n", current->profits);
        current = current->next;
    }

    fclose(f);
}