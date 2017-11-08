cmake_minimum_required(VERSION 3.8)
project(HW__4)

set(CMAKE_CXX_STANDARD 11)

set(SOURCE_FILES hw04_01.cpp BinarySearchTree.h)

add_executable(part1 ${SOURCE_FILES})
add_executable(part2 hw04_02.cpp WordSearch.cpp FileSystem.cpp)
