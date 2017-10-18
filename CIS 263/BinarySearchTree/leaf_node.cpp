//
// Created by Kyle Flynn on 10/4/2017.
//

#include "leaf_node.h"

leaf_node::leaf_node() = default {
    leaf_node(nullptr, nullptr, nullptr);
}

leaf_node::leaf_node(int value) {
    leaf_node(nullptr, nullptr, value);
}

leaf_node::leaf_node(leaf_node left, leaf_node right, int value) {
    this->left = left;
    this->right = right;
    this->value = value;
}