//
// Created by Kyle Flynn on 9/10/2018.
//

#ifndef PROJECT_1_FILE_UTILS_H
#define PROJECT_1_FILE_UTILS_H

#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <errno.h>

int read_file( char* filename, char **buffer );
int write_file( char* filename, char *buffer, unsigned int size);

#endif //PROJECT_1_FILE_UTILS_H
