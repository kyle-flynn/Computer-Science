#include <stdio.h>
#include <stdlib.h>
#include "file_utils.h"

int reverse(const char* src, char* dest) {
    int size = (int) strlen(src);
    for (int i = size, j = 0; i >= 0; i--) {
        if (src[i] != '\0') {
            dest[j] = src[i];
            j++;
        }
    }
    return EXIT_SUCCESS;
}

int main(int argc, char** argv) {

    if (argc < 2) {
        fprintf(stderr, "Invalid number of arguments. ");
        return EXIT_FAILURE;
    } else {
        char* read_loc = argv[1];
        char* write_loc = argv[2];
        char **buffer = (char**) malloc(sizeof(char**));
        int read_response = read_file(read_loc, buffer);

        if (read_response != EXIT_SUCCESS) {
            printf("There was an error reading the file. Error code " + read_response);
            return read_response;
        }

        char* reversed_str = (char*) malloc(sizeof(buffer));
        reverse(*buffer, reversed_str);

        int write_response = write_file(write_loc, reversed_str, sizeof(reversed_str));

        if (write_response != EXIT_SUCCESS) {
            printf("There was an error writing to the file. Error code " + write_response);
            return write_response;
        }

    }

    return EXIT_SUCCESS;
}