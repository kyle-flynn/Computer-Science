//
// Created by Kyle Flynn on 10/25/2017.
//

#include "LinkedList.h"

void add(struct product* p) {
    if (list == NULL || size == 0) {
        list = p;
        list->next = NULL;
        size = 1;
    } else {
        struct product* current;
        current = list;

        while (current->next != NULL) {
            current = current->next;
        }
        current->next = p;
        current->next->next = NULL;
        size = size + 1;
    }
}

void delete(struct product* p) {
    if (size <= 1) {
        list = NULL;
        list->next = NULL;
        size = 0;
    } else {
        struct product* current;
        current = list;

        if (current->name == p->name) {
            list = current->next;
        } else {
            while (current->next != NULL) {
                if (current->next->name == p->name) {
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