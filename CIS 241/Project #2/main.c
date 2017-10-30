#include <stdio.h>
#include <stdlib.h>
#include "GroceryStore.h"

void printInfo(int b) {
    if (b == 0) {
        system("cls");

        printf("Welcome to the GVSU Super Store created by Kyle Flynn!\n");
        printf("Enter the correct number for the desired action as specified below.\n");
        printf("====================================================================\n");
        printf("1: Add a product to inventory            2: Purchase a product\n");
        printf("3: Check a product's price               4: Show current inventory\n");
        printf("5: Remove a product from inventory       6: Find a product\n");
        printf("7: Inventory                             8: Exit program\n");
        printf("====================================================================\n");
        printf("\n");
    } else {
        printf("What would you like to do?\n");
    }
}

int main() {

    printInfo(0);

    char input = '0';

    while (input != '8') {

        printInfo(1);
        scanf(" %c", &input);

        switch(input) {
            case '1':
                printInfo(0);
                addProduct();
                break;
            case '2':
                printInfo(0);
                purchaseProduct();
                break;
            case '3':
                printInfo(0);
                printPrice();
                break;
            case '4':
                printInfo(0);
                printInventory();
                break;
            case '5':
                printInfo(0);
                removeProduct();
                break;
            case '6':
                printInfo(0);
                break;
            case '7':
                printInfo(0);
                break;
            default:
                printf("Invalid command. Try using a number 1-8.\n");
        }
    }

    printf("Thank you for shopping!");

    return 0;
}