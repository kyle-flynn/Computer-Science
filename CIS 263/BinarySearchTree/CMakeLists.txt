cmake_minimum_required(VERSION 3.8)
project(BinarySearchTree)

set(CMAKE_CXX_STANDARD 11)

set(SOURCE_FILES main.cpp binary_search_tree.cpp binary_search_tree.h leaf_node.cpp leaf_node.h)
add_executable(BinarySearchTree ${SOURCE_FILES})