//
// Created by Kyle Flynn on 10/25/2017.
//

#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#ifndef PROJECT_2_LINKEDLIST_H
#define PROJECT_2_LINKEDLIST_H

#define N 20
extern struct product {
    char name[N];
    float quantity_value;
    char quantity_unit[N];
    float price_value;
    char price_unit[N];
    struct product* next;
};

typedef struct product product;

struct product* list;
int size;

void add(struct product* p);
void delete(struct product* p);
struct product* get(char name[N]);

#endif //PROJECT_2_LINKEDLIST_H