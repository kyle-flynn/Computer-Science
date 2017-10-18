//
// Created by Kyle Flynn on 10/4/2017.
//

#include "leaf_node.h"

#ifndef BINARYSEARCHTREE_BINARY_SEARCH_TREE_H
#define BINARYSEARCHTREE_BINARY_SEARCH_TREE_H

class binary_search_tree {
public:
    binary_search_tree();
    binary_search_tree(leaf_node root);
private:
    leaf_node root;
};


#endif //BINARYSEARCHTREE_BINARY_SEARCH_TREE_H
