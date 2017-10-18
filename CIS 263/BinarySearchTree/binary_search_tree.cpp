//
// Created by Kyle Flynn on 10/4/2017.
//

#include "binary_search_tree.h"

binary_search_tree::binary_search_tree() {
    binary_search_tree(null);
}

binary_search_tree::binary_search_tree(leaf_node root) {
    this->root = root;
}