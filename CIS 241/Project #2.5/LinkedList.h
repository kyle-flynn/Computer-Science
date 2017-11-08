//
// Created by Kyle Flynn on 10/24/2017.
//

#ifndef PROJECT_2_5_LINKEDLIST_H
#define PROJECT_2_5_LINKEDLIST_H

#define N 20

#include <iostream>
#include <memory>
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
        struct Product product;
        struct shared_ptr<Node> next;
    };

    LinkedList() {
        size = 0;
        list = nullptr;
    }

    void add(struct Product item) {
        if (size == 0 || list == nullptr) {
            list = make_shared<Node>();
            list->product = item;
            list->next = nullptr;
        } else {
            shared_ptr<Node> current = list;
            while (current->next != nullptr) {
                current = current->next;
            }
            current->next = make_shared<Node>();
            current->next->product = item;
            current->next->next = nullptr;
        }
        size++;
    }

    void remove(struct Product item) {
        if (size <= 1) {
            list = nullptr;
            list->next = nullptr;
            size = 0;
        } else {
            shared_ptr<Node> current = list;

            if (current->product.name == item.name) {
                list = current->next;
            } else {
                while (current->next != nullptr) {
                    if (current->next->product.name == item.name) {
                        shared_ptr<Node> removed = current->next;
                        if (removed->next != nullptr) {
                            current->next = removed->next;
                        } else {
                            current->next = nullptr;
                        }
                        break;
                    }
                    current = current->next;
                }
            }

        }
    }

    void print() {
        shared_ptr<Node> current = list;
        while (current != nullptr) {
            struct Product p = current->product;
            cout << "Product: " << p.name << endl;
            current = current->next;
        }
    }

private:
    shared_ptr<Node> list;
    int size;
};

#endif //PROJECT_2_5_LINKEDLIST_H