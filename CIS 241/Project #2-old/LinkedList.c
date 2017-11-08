//
// Created by Kyle Flynn on 10/25/2017.
//

#include "LinkedList.h"

struct product* createProduct() {
    struct product* temp;
    temp = (product*) malloc(sizeof(struct product));
    temp->next = NULL;
    return temp;
}

void add(struct product* p) {

    product* prod;
    prod = createProduct();

    strcpy(prod->name, p->name);
    strcpy(prod->quantity_unit, p->quantity_unit);
    strcpy(prod->price_unit, p->price_unit);
    prod->quantity_value = p->quantity_value;
    prod->price_value = p->price_value;

    if (list == NULL || size == 0) {
        list = prod;
        list->next = NULL;
        size = 1;
    } else {
        struct product* current;
        current = list;

        while (current->next != NULL) {
            current = current->next;
        }
        current->next = prod;
        current->next->next = NULL;
        size = size + 1;
    }
}

void delete(struct product* p) {
    if (size <= 1) {
        list = createProduct();
        list->next = NULL;
        size = 0;
    } else {
        struct product* current;
        current = list;

        if (strcmp(current->name, p->name) == 0) {
            list = current->next;
        } else {
            while (current->next != NULL) {
                if (strcmp(current->next->name, p->name) == 0) {
                    struct product* removed = current->next;
                    if (removed->next != NULL) {
                        current->next = removed->next;
                    } else {
                        current->next = NULL;
                    }
                    break;
                }
                current = current->next;
            }
        }
        size = size - 1;
    }
}

struct product* get(char name[N]) {
    if (size <= 0) {
        return list;
    } else {
        struct product* current;
        current = list;

        if (strcmp(current->name, name) == 0) {
            return current;
        } else {
            while (current != NULL) {
                if (strcmp(current->name, name) == 0) {
                    return current;
                }
                current = current->next;
            }
        }
    }
    return NULL;
}

struct product* update(struct product* p) {
    struct product* prod = get(p->name);

    if (prod != NULL) {
        prod = p;
        return prod;
    } else {
        return NULL;
    }
}

void print() {

    struct product* current;
    current = list;

    while (current != NULL) {
        product p = *current;
        printf("Product: %s\n", p.name);
        current = current->next;
    }
}