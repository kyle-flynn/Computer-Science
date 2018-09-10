#include <stdio.h>
#include <stdlib.h>
#include "file_utils.h"

int main(int argc, char** argv) {

    if (argc < 2) {
        fprintf(stderr, "Invalid number of arguments. ");
    } else {
        char **test = malloc(sizeof(test) * 10);
        int responseOne = read_file("nope_1.txt", test);
        int responseTwo = write_file("nope_2.txt", *test, 256);
        printf("%d", responseOne + responseTwo);
    }

    return 0;
}