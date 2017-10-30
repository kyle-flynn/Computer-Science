cmake_minimum_required(VERSION 3.8)
project(Project__2)

set(CMAKE_C_STANDARD 99)

set(SOURCE_FILES main.c LinkedList.h LinkedList.c GroceryStore.h GroceryStore.c)
add_executable(Project__2 ${SOURCE_FILES})