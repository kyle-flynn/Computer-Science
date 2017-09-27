cmake_minimum_required(VERSION 3.8)
project(Project__1)

set(CMAKE_C_STANDARD 99)

set(SOURCE_FILES main.c functions.c functions.h)
add_executable(Project__1 ${SOURCE_FILES})