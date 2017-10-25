//
// Created by Kyle Flynn on 10/24/2017.
//

#ifndef PROJECT_2_5_LINKEDLIST_H
#define PROJECT_2_5_LINKEDLIST_H

#define N 20

#include <iostream>
#include <string>

using namespace std;

struct Product {
    string name;
    float quantity_value;
    char quantity_unit[N];
    float price_value;
    char price_unit[N];
};

typedef struct Product Product;

template <typename E>
class LinkedList {
public:

    struct Node {
        E data;
        struct Node* next;
    };

    LinkedList() {
        size = 0;
        list = NULL;
    }

    void add(E item) {
        Node* temp = new Node;
        temp->data = item;
        temp->next = NULL;
        if (size == 0 || list == NULL) {
            list = temp;
        } else {
            Node* current = list;
            for (int i = 0; i < size; i++) {
                current = current->next;
            }
        }
        size++;
    }

    void print() {
//        Node* current = list;
//        while (current != NULL) {
//            Product p = current->data;
//            cout << p.name << endl;
//            current = current->next;
//        }
    }

private:
    Node* list;
    int size;
};

#endif //PROJECT_2_5_LINKEDLIST_H