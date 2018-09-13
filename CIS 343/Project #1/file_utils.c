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

    // Grabbing file size
    fseek(file, 0L, SEEK_END);
    size_t size = (size_t) ftell(file);
    fseek(file, 0L, SEEK_SET);

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
int write_file(char* filename, char *buffer, size_t size) {
    FILE* file = fopen(filename, "w");

    // If the file wasn't found, return the designated error code to the caller.
    if (file == NULL) {
        return errno;
    }

    // Very simply write back to the file and close it. The fprintf function will handle \n's and all original bytes.
    fwrite(buffer, sizeof(char), size, file);
    fclose(file);

    return 0;
}