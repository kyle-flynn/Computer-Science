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

    // Remove the lowest value from the BST
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

    // This should print nothing because our binary tree is now empty.
    me.printTree();

    me.insert(10);
    me.insert(20);
    me.printTree();

    empty = me.isEmpty();

    cout << endl;
    cout << "Empty: " << empty << endl;

    return 0;
}