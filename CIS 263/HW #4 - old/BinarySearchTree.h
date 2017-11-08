//
// Created by Hans Dulimarta.
//

#ifndef BINARYTREES_BINARYSEARCHTREE_H
#define BINARYTREES_BINARYSEARCHTREE_H
#include <memory>
#include <iostream>
#include <vector>

using namespace std;

template <typename E>     // textbook code E is Comparable
class BinarySearchTree {
public:
    /**
     * Default constructor
     */
    BinarySearchTree() {
    }

    /**
     * Destructor
     */
    ~BinarySearchTree() {
        purge(root);
    }

    void insert (E item) {
        insert (item, root);
    }

    void printTree(ostream& out = cout) const {
        print (out, root);
    }

    bool isEmpty() const {
        return root != nullptr;
    }

    int number_of_nodes() const {
        int count = get_node_count(root);
        return count;
    }

    int number_of_leaves() const {
        int count = get_leaf_count(root);
        return count;
    }

    int number_of_full_nodes() const {
        int count = get_full_node(root);
        return count;
    }

    vector<E> remove_leaves() {
        vector<E> result;
        rem_leaves(result, root);
        return result;
    }

private:
    struct Node {
        E data;
        shared_ptr<Node> left, right;
    };

    shared_ptr<Node> root;

    void print (ostream& out, shared_ptr<Node> ptr) const {
        /* in order traversal */
        if (ptr != nullptr) {
            print (out, ptr->left);
            out << ptr->data << " ";
            print (out, ptr->right);
        }
    }

    void insert (E item, shared_ptr<Node>& ptr) const {
        if (ptr == nullptr) {
            ptr = make_shared<Node>();
            ptr->data = item;
        } else if (item < ptr->data) {
            insert(item, ptr->left);
        } else if (item > ptr->data) {
            insert(item, ptr->right);
        } else {
            // attempt to insert a duplicate item
        }
    }

    void purge (shared_ptr<Node>& ptr) {
        if (ptr != nullptr) {
            purge (ptr->left);
            purge (ptr->right);
            ptr.reset();
        }
    }

    int get_node_count(shared_ptr<Node> ptr) const {
        if (ptr != nullptr) {
            return 1 + get_node_count(ptr->left) + get_node_count(ptr->right);
        } else {
            return 0;
        }
    }

    int get_leaf_count(shared_ptr<Node> ptr) const {
        if (ptr == nullptr) {
            return 0;
        } else if (ptr->left == nullptr && ptr->right == nullptr) {
            return 1;
        } else {
            return get_leaf_count(ptr->left) + get_leaf_count(ptr->right);
        }
    }

    int get_full_node(shared_ptr<Node> ptr) const {
        if (ptr != nullptr) {
            int count = 0;
            if  (ptr->left && ptr->right) {
                count++;
            }
            count += (get_full_node(ptr->left) + get_full_node(ptr->right));
            return count;
        } else {
            return 0;
        }
    }

    void rem_leaves(vector<E>& result, shared_ptr<Node>& ptr) {
        if(ptr->left != nullptr) {
            rem_leaves(result, ptr);
        }

        if(ptr->right != nullptr) {
            rem_leaves(result, ptr);
        }

        if(ptr->left == nullptr && ptr->right == nullptr) {
            vector<E> temp;
            temp[0] = ptr->data;
            result.insert(result.end(), temp.begin(), temp.end());
            ptr.reset();
        }
    }

};
#endif //BINARYTREES_BINARYSEARCHTREE_H