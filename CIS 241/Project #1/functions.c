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
    char* output = malloc(strlen(input)+1);
    int output_index = 0;
    int i = 0;
    for (i = 0; i < strlen(input); i++) {
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
    char* str = malloc(strlen(key) + strlen(cipher)+2);
    strcpy(str, key);
    strcat(str, cipher);
    return makeSet(str);
}

char* encrypt(char input[]) {

    // Make our cipher
    char* currentCipher = makeCipher();
        
    char output[strlen(input)-1];
    int i = 0;
    for (i = 0; i < strlen(input); i++) {
        int asciiVal = input[i];
        if (asciiVal != 32 && (asciiVal-65) >= 0) {
            // 65 - 90 are our ASCII values
            output[i] = currentCipher[asciiVal-65];
        } else {
            output[i] = ' ';
        }
    }
    int sizeDiff = strlen(output)-strlen(input);
    output[strlen(output)-sizeDiff] = '\0';
    strcpy(input, output);
    return input;
}

char* decrypt(char input[]) {

    // Make our cipher
    char* currentCipher = makeCipher();
    
    char output[strlen(input)-1];
    int i = 0;
    for (i = 0; i < strlen(input); i++) {
        int asciiVal = input[i];
        if (asciiVal != 32 && (asciiVal-65) >= 0) {
            char* result = strchr(currentCipher, input[i]);
            int index = (int) (result - currentCipher);
            output[i] = ascii[index];
        } else {
            output[i] = ' ';
        }
    }

    int sizeDiff = strlen(output)-strlen(input);
    output[strlen(output)-sizeDiff] = '\0';
    strcpy(input, output);
    return input;
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
