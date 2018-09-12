#include <stdio.h>
#include <stdlib.h>
#include "file_utils.h"

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

        // Dynamically allocating memory for our buffer, and getting the response code from our read_file method.
        char **buffer = (char**) malloc(sizeof(char**));
        int read_response = read_file(read_loc, buffer);

        if (read_response != EXIT_SUCCESS) {
            printf("There was an error reading the file. Error code " + read_response);
            return read_response;
        }

        // Reversing our string, and getting the response code from our write_file method.
        char* reversed_str = (char*) malloc(sizeof(buffer));
        reverse(*buffer, reversed_str);
        int write_response = write_file(write_loc, reversed_str, sizeof(reversed_str));

        if (write_response != EXIT_SUCCESS) {
            printf("There was an error writing to the file. Error code " + write_response);
            return write_response;
        }
    }

    // If no other exit code, then the program was a success.
    return EXIT_SUCCESS;
}