//
// Created by Kyle Flynn on 10/4/2017.
//

#ifndef BINARYSEARCHTREE_LEAF_NODE_H
#define BINARYSEARCHTREE_LEAF_NODE_H

class leaf_node {
public:
    leaf_node();
    leaf_node(int value);
    leaf_node(leaf_node left, leaf_node right, int value);

private:
    leaf_node left;
    leaf_node right;
    int value;
};


#endif //BINARYSEARCHTREE_LEAF_NODE_H
