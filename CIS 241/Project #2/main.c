#include <stdio.h>
#include "LinkedList.h"

void printInfo() {
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
    printf("What would you like to do?\n");
}

int main() {

    printInfo();

    char input;
    scanf(" %c", &input);

    while (input != '8') {
        switch(input) {
            case '1':
//                printInfo();
                break;
            case '2':
//                printInfo();
                break;
            case '3':
//                printInfo();
                break;
            case '4':
//                printInfo();
                break;
            case '5':
//                printInfo();
                break;
            case '6':
//                printInfo();
                break;
            case '7':
//                printInfo();
                break;
            default:
                printf("Invalid command. Try using a number 1-8.\n");
        }
        scanf(" %c", &input);
    }

    printf("Thank you for shopping!");

    return 0;
}