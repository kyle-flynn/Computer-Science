cmake_minimum_required(VERSION 3.8)
project(Hello_World)

set(CMAKE_CXX_STANDARD 11)

set(SOURCE_FILES main.cpp)
add_executable(Hello_World ${SOURCE_FILES})