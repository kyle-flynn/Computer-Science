cmake_minimum_required(VERSION 3.8)
project(Project__2)

set(CMAKE_C_STANDARD 99)

set(SOURCE_FILES main.c LinkedList.c GroceryStore.c LinkedList.h GroceryStore.h)
add_executable(Project__2 ${SOURCE_FILES})