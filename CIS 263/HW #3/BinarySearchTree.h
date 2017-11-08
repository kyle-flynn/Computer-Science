//
// Created by Hans Dulimarta (Fall 2017).
//

#ifndef BINARYTREES_BINARYSEARCHTREE_H
#define BINARYTREES_BINARYSEARCHTREE_H
#include <memory>
#include <iostream>

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

    const E & findMin() const {
        return findMin(root)->data;
    }

    const E & findMax() const {
        return findMax(root)->data;
    }

    bool contains(E item) const {
        return contains(item, root);
    }

    bool isEmpty() const {
        return isEmpty(root);
    }

    void makeEmpty() {
        makeEmpty(root);
    }

    void remove(E item) {
        remove(item, root);
    }

    /* TODO: add the following public functions (lines 10-19, page 133 of textbook) .
     * findMin(), findMax(), contains(), isEmpty(), makeEmpty(), remove()
     *
     * and write their corresponding private functions in the private section below
     * Remember to use shared_ptr<Node> in place of Node* shown in text book code
     *
     */
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
            out << ptr->data;
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

    void purge (shared_ptr<Node> ptr) {
        if (ptr != nullptr) {
            purge (ptr->left);
            purge (ptr->right);
            ptr.reset();
        }
    }

    shared_ptr<Node> findMin(shared_ptr<Node> root) const {
        if (root == nullptr) {
           return nullptr;
        }

        if (root->left == nullptr) {
            return root;
        }

        return findMin(root->left);
    }

    shared_ptr<Node> findMax(shared_ptr<Node> root) const {
        if (root == nullptr) {
            return nullptr;
        }

        if (root->right == nullptr) {
            return root;
        }

        return findMax(root->right);
    }

    bool contains(E item, shared_ptr<Node> root) const {
        if (root == nullptr) {
            return false;
        } else if (item < root->data ) {
            return contains(item, root->left);
        } else if (item > root->data) {
            return contains(item, root->right);
        } else {
            return true;
        }
    }

    bool isEmpty(shared_ptr<Node> root) const {
        return (root == nullptr);
    }

    void makeEmpty(shared_ptr<Node>& root) {
        if (root != nullptr) {
            makeEmpty(root->left);
            makeEmpty(root->right);
            remove(root->data, root);
        }
    }

    void remove(E item, shared_ptr<Node>& root) {
        if (root == nullptr) {
            return;
        }
        if (item < root->data) {
            remove(item, root->left);
        } else if (item > root->data) {
            remove(item, root->right);
        } else if (root->left != nullptr && root->right != nullptr) {
            root->data = findMin(root->right)->data;
            remove(root->data, root->right);
        } else {
            shared_ptr<Node> oldNode = root;
            root = (root->left != nullptr) ? root->left : root->right;
            oldNode.reset();
        }
    }

};
#endif //BINARYTREES_BINARYSEARCHTREE_H