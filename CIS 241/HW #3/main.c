#include <stdio.h>
#include <stdlib.h>

void unpack(int encode) {
    printf("Decoding...\n");
    unsigned short i = 0;
    printf("");
}

unsigned short encode(short gNum, short gender, short position, short jobType) {
    printf("Encoding...\n");
    unsigned short empId;
    empId = 0;
    empId = (empId|gNum) << 2;
    empId = (empId|gender) << 3;
    empId = (empId|position) << 1;
    empId = empId|jobType;
    return empId;
}

int main(int argc, char* argv[]) {

    if (argc < 2) {
        printf("Please don't have crippling autism and use this program correctly.");
    } else if (argc == 2) {
        // Decode shit
        int encode = *argv[1];
        unpack(encode);
    } else if (argc == 5) {
        // Encode shit
        short gNum = atoi(argv[1]);
        short gender = atoi(argv[2]);
        short position = (argv[3]);
        short jobType = (argv[4]);
        unsigned short id = encode(gNum, gender, position, jobType);
        printf("Employee ID: %d", id);
    } else {
        printf("Please don't have crippling autism and use this program correctly");
    }

    return 0;
}