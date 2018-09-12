//
// Created by Kyle Flynn on 9/10/2018.
//

#include "file_utils.h"

int read_file(char* filename, char **buffer) {
    FILE* file = fopen(filename, "r");
    if (file == NULL) {
        return errno;
    }

    // I prefer this method instead of importing stat.h
    fseek(file, 0L, SEEK_END);
    size_t size = (size_t) ftell(file);
    fseek(file, 0L, SEEK_SET);

    *buffer = (char*) malloc(size * sizeof(char*));
    fread(*buffer, sizeof(char), size, file);
    fclose(file);

    return 0;
}

int write_file(char* filename, char *buffer, unsigned int size) {
    FILE* file = fopen(filename, "w");
    if (file == NULL) {
        return errno;
    }

    fprintf(file, buffer);
    fclose(file);

    return 0;
}