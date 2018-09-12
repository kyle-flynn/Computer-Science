cmake_minimum_required(VERSION 3.6)
project(Project__1)

set(CMAKE_C_STANDARD 99)

set(SOURCE_FILES reverse.c file_utils.h file_utils.c)
add_executable(Project__1 ${SOURCE_FILES})