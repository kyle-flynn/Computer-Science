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

    me.remove(-2);
    int min = me.findMin();
    int max = me.findMax();
    bool isThere = me.contains(-2);
    bool empty = me.isEmpty();

    cout << endl;
    cout << "Min: " << min << endl;
    cout << "Max: " << max << endl;
    cout << "-2: " << isThere << endl;
    cout << "Empty: " << empty << endl;

    me.makeEmpty();

    cout << "makeEmpty()" << endl;

    empty = me.isEmpty();

    cout << "Empty: " << empty << endl;

    me.printTree();

    return 0;
}