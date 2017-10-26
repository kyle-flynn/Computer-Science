#include <stdio.h>
#include <stdlib.h>

void unpack(unsigned short empId) {

    short jobMask = 0x0001;
    short jobType = empId & jobMask;

    empId = empId >> 1;
    short positionMask = 0x000f;
    short position = empId & positionMask;

    empId = empId >> 3;
    short genderMask = 0x0003;
    short gender = empId & genderMask;

    empId = empId >> 2;
    short gNumMask = 0x03ff;
    short gNum = empId & gNumMask;

    printf("G-Num: %d\n", gNum);
    printf("Gender: ");

    switch (gender) {
        case 0:
            printf("Male\n");
            break;
        case 1:
            printf("Female\n");
            break;
        case 2:
            printf("Not telling\n");
            break;
        default:
            printf("Not telling\n");
    }

    printf("Position: ");

    switch (position) {
        case 0:
            printf("Professor\n");
            break;
        case 1:
            printf("Lecturer\n");
            break;
        case 2:
            printf("Administration\n");
            break;
        case 3:
            printf("IT staff\n");
            break;
        case 4:
            printf("Visitor\n");
            break;
        case 5:
            printf("Student\n");
            break;
        case 6:
            printf("Worker\n");
            break;
        case 7:
            printf("President\n");
            break;
        default:
            printf("Unknown\n");
    }

    printf("JobType: ");

    switch (jobType) {
        case 0:
            printf("Full time\n");
            break;
        case 1:
            printf("Part time\n");
            break;
        default:
            printf("Unemployed");
    }
}

unsigned short encode(short gNum, short gender, short position, short jobType) {
    unsigned short empId = 0;
    // You move over X bits to fit in the next desired bit.
    empId = (empId | gNum) << 2;
    empId = (empId | gender) << 3;
    empId = (empId | position) << 1;
    empId = empId | jobType;
    return empId;
}

int main(int argc, char* argv[]) {

    if (argc < 2) {
        printf("Please don't have crippling autism and use this program correctly.");
    } else if (argc == 2) {
        // Decode shit
        unsigned short encode = atoi(argv[1]);
        unpack(encode);
    } else if (argc == 5) {
        // Encode shit
        short gNum = atoi(argv[1]);
        short gender = atoi(argv[2]);
        short position = (argv[3]);
        short jobType = (argv[4]);
        unsigned short id = 0;
        id = encode(gNum, gender, position, jobType);
        printf("Employee ID: %d\n", id);
    } else {
        printf("Please don't have crippling autism and use this program correctly");
    }

    return 0;
}