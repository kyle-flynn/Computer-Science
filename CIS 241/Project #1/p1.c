#include <stdio.h>
#include "functions.h"

int main(int argc, char* argv[]) {

    if (argc <= 4) {
        printf("Invalid arguments. Use the parameter h for help.\n");
    } else {
        char* text;
        char cipherText[26];
        char* input;
        char* output;
        char firstArg = *argv[1];
        key = argv[2];
        switch(firstArg) {
            case 'e':
                // Let's encrypt the message.
                text = readFile(argv[3]);
                input = encrypt(text);
                printf("Encryption: %s\n", input);
                writeFile(argv[4], input);
                break;
            case 'd':
                // Let's decrypt the message.
                text = readFile(argv[3]);
                output = decrypt(text);
                printf("Decryption: %s\n", output);
                writeFile(argv[4], output);
                break;
            default:
                printf("-------------------------------------\n");
                printf("Correct usage of this program is...\np1 e|d  key input_file output_file\n");
                printf("-------------------------------------\n");
        }
    }

    return 0;
}
