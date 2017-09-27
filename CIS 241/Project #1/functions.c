//
// Created by Kyle Flynn on 9/21/2017.
//

#include <stdio.h>
#include <stdlib.h>
#include "functions.h"

char* key;
char* ascii  = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
char* cipher = "ZYXWVUTSRQPONMLKJIHGFEDCBA";

char* makeSet(char* input) {
    char* output = malloc(strlen(input));
    int output_index = 0;
    for (int i = 0; i < strlen(input); i++) {
        char* result = strchr(output, input[i]);
        int index = (int) (result - output);
        if (index < 0) {
            output[output_index] = input[i];
            output_index++;
        }
    }
    output[strlen(output)] = '\0';
    strcpy(input, output);
    free(output);
    return input;
}

char* makeCipher() {
    char* str = malloc(strlen(key) + strlen(cipher));
    strcpy(str, key);
    strcat(str, cipher);
    return makeSet(str);
}

char* encrypt(char input[]) {

    // Make our cipher
    char* currentCipher = makeCipher();

    char* output = malloc(strlen(input));

    for (int i = 0; i < strlen(input); i++) {
        int asciiVal = input[i];
        if (asciiVal != 32) {
            // 65 - 90 are our ASCII values
            output[i] = currentCipher[asciiVal-65];
        } else {
            output[i] = ' ';
        }
    }
    int sizeDiff = strlen(output)-strlen(input);
    output[strlen(output)-sizeDiff] = '\0';
    return output;
}

char* decrypt(char input[]) {

    // Make our cipher
    char* currentCipher = makeCipher();

    char* output = malloc(strlen(input));

    for (int i = 0; i < strlen(input); i++) {
        int asciiVal = input[i];
        if (asciiVal != 32) {
            char* result = strchr(currentCipher, input[i]);
            int index = (int) (result - currentCipher);
            output[i] = ascii[index];
        } else {
            output[i] = ' ';
        }
    }

    int sizeDiff = strlen(output)-strlen(input);
    output[strlen(output)-sizeDiff] = '\0';
    return output;
}

char* readFile(char file[]) {
    FILE* inFile = fopen(file, "r");
    if (inFile == NULL) {
        printf("Error opening file!\n");
        exit(1);
    }

    char* buffer = malloc(255);
    fgets(buffer, 255, inFile);
    fclose(inFile);
    return buffer;
}

void writeFile(char file[], char text[]) {
    FILE* outFile = fopen(file, "w");
    if (outFile == NULL) {
        printf("Error writing file!\n");
        exit(1);
    }

    fprintf(outFile, text);
    fclose(outFile);
}