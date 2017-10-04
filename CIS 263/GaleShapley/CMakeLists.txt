cmake_minimum_required(VERSION 3.8)
project(GaleShapley)

set(CMAKE_CXX_STANDARD 11)

set(SOURCE_FILES main.cpp gale_shapley.cpp gale_shapley.h)
add_executable(GaleShapley ${SOURCE_FILES})