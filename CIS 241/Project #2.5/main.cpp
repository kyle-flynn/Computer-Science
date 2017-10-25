#include <iostream>

#include "LinkedList.h"

using namespace std;

int main() {

    LinkedList<Product> list;

    Product cheese;
    cheese.name = "Cheese";

    Product milk;
    milk.name = "Milk";

    list.add(cheese);
    list.add(milk);

    list.print();

    return 0;
}