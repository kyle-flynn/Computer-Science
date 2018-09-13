#include <stdio.h>
#include <stdlib.h>
#include "file_utils.h"

/**
 * The following helper method returns the size (in bytes) of a given filename.
 *
 * @param {char*} filename - The location of the file.
 */
size_t get_fsize(const char* filename) {
    FILE* file = fopen(filename, "r");

    if (file == NULL) {
        printf("There was an error analyzing the given filename's size.");
        return 0;
    }

    /*
     * Instead of using sys/stat.h, stdio.h provides a seek method that does roughly the same thing. I could've made
     * my life easier and used the provided code, but I wanted to be different and found this all by myself in the C
     * documentation. Yay me.
     */
    fseek(file, 0L, SEEK_END);
    size_t size = (size_t) ftell(file);
    fseek(file, 0L, SEEK_SET);
    return size;
}

/**
 * The following helper method reverses a given string into a destination string.
 *
 * @param {char*} src - The source string.
 * @param {char*} dest - The destination, or result that the reversed string will load in to.
 */
int reverse(const char* src, char* dest) {
    int size = (int) strlen(src);

    /*
     * Create two variables, one counts up and the other counts down. Loop through the src string backwards, and loop
     * through the destination string forwards.
     */
    for (int i = size, j = 0; i >= 0; i--) {

        // Make sure the null byte isn't the first character in the destination string.
        if (src[i] != '\0') {
            dest[j] = src[i];
            j++;
        }
    }

    return EXIT_SUCCESS;
}

/**
 * The main method of the program. Takes an input file to reverse it's contents, and outputs it to another file.
 *
 * @param {int} argc - The amount of commandline arguments.
 * @param {char**} argv - The arguments themselves in a char** representation.
 */
int main(int argc, char** argv) {

    // This program only accepts 2 strings. Make sure the user knows that.
    if (argc != 3) {
        fprintf(stderr, "Invalid number of arguments.");
        return EXIT_FAILURE;
    } else {

        // Creating friendly, local variables of our arguments.
        char* read_loc = argv[1];
        char* write_loc = argv[2];

        // Dynamically allocating memory for our variables based on file size.
        size_t size = get_fsize(read_loc);
        char* buffer = (char*)malloc(size * sizeof(char));
        char* reversed_str = (char*)malloc(size * sizeof(char));
        int read_response = read_file(read_loc, &buffer);
        if (read_response != EXIT_SUCCESS) {
            printf("There was an error reading the file. Error code " + read_response);
            return read_response;
        }

        // Reversing our string, and getting the response code from our write_file method.
        reverse(buffer, reversed_str);
        int write_response = write_file(write_loc, reversed_str, size);

        if (write_response != EXIT_SUCCESS) {
            printf("There was an error writing to the file. Error code " + write_response);
            return write_response;
        }

        free(buffer);
        free(reversed_str);
    }

    // If no other exit code, then the program was a success.
    return EXIT_SUCCESS;
}