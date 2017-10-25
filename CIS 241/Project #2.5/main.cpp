#include <iostream>

#include "LinkedList.h"

using namespace std;

int main() {

    LinkedList<Product> list;

    Product cheese;
    cheese.name = "Cheese";

    Product milk;
    milk.name = "Milk";

    Product eggs;
    eggs.name = "Eggs";

    list.add(cheese);
    list.add(milk);
    list.add(eggs);

    list.print();

    list.remove(eggs);

    cout << endl;

    list.print();

    return 0;
}