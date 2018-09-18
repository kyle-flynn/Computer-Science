//
// Created by Kyle Flynn on 9/10/2018.
//

#ifndef PROJECT_1_FILE_UTILS_H
#define PROJECT_1_FILE_UTILS_H

#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <errno.h>

/** Reads a given filename into the designated character buffer. **/
int read_file( char* filename, char **buffer );

/** Writes to a given filename with the given character buffer and designated file size. **/
int write_file( char* filename, char *buffer, size_t size);

#endif //PROJECT_1_FILE_UTILS_H
