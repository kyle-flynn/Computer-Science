cmake_minimum_required(VERSION 3.8)
project(HW__1)

set(CMAKE_CXX_STANDARD 11)

set(SOURCE_FILES hw01-permute.cpp SimpleDate.cpp SimpleDate.h hw01-date.cpp)
add_executable(HW__1 ${SOURCE_FILES})