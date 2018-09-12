//
// Created by Kyle Flynn on 9/10/2018.
//

#include "file_utils.h"

/**
 * The following method reads a given filename and loads it into the provided buffer.
 *
 * @param {char*} filename - The location of the file.
 * @param {char**} buffer - The buffer that the file's contents will be dumped to.
 */
int read_file(char* filename, char **buffer) {
    FILE* file = fopen(filename, "r");

    // If the file wasn't found, return the designated error code to the caller.
    if (file == NULL) {
        return errno;
    }

    /*
     * Instead of using sys/stat.h, stdio.h provides a seek method that does roughly the same thing. I could've made
     * my life easier and used the provided code, but I wanted to be different and found this all by myself in the C
     * documentation. Yay me.
     */
    fseek(file, 0L, SEEK_END);
    size_t size = (size_t) ftell(file);
    fseek(file, 0L, SEEK_SET);

    // De-referencing the buffer and setting the content to the file's contents.
    *buffer = (char*) malloc(size * sizeof(char*));
    fread(*buffer, sizeof(char), size, file);
    fclose(file);

    return 0;
}

/**
 * The following methods writes to a given filename with the provided content buffer and size.
 *
 * @param {char*} filename - The location of the file
 * @param {char*} buffer - The content buffer that will be written to the file
 * @param {size_t} size - The desired size of the file
 */
int write_file(char* filename, char *buffer, unsigned int size) {
    FILE* file = fopen(filename, "w");

    // If the file wasn't found, return the designated error code to the caller.
    if (file == NULL) {
        return errno;
    }

    // Very simply write back to the file and close it. The fprintf function will handle \n's and all original bytes.
    fprintf(file, buffer);
    fclose(file);

    return 0;
}