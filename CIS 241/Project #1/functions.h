//
// Created by Kyle Flynn on 9/21/2017.
//

#include <string.h>

#ifndef PROJECT_1_FUNCTIONS_H
#define PROJECT_1_FUNCTIONS_H

extern char* key;
extern char* ascii;
extern char* cipher;

char* makeCipher();
char* encrypt(char input[]);
char* decrypt(char input[]);
char* readFile(char file[]);
void writeFile(char file[], char text[]);

#endif //PROJECT_1_FUNCTIONS_H