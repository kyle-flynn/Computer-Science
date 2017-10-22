#include <iostream>
#include "BinarySearchTree.h"

int main() {
    BinarySearchTree<int> me;
    me.insert (20);
    me.insert (30);
    me.insert (-2);
    me.insert (10);
    me.insert (7);
    me.insert (35);
    me.insert (24);
    me.printTree();

    return 0;
}