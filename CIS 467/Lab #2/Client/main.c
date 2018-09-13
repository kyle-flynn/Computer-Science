#include <stdio.h>
#include <string.h>
#include <unistd.h>
#include <stdlib.h>
#include <sys/socket.h>
#include <netinet/in.h>
#include <arpa/inet.h>

int queryPort() {
    printf("Enter the server port number: ");
    char port[5000] = "8080";
    fgets(port, 5000, stdin);
    return atoi(port);
}

char* queryAddress() {
    printf("Enter the server address: ");
    char* address = (char*) malloc(sizeof(char*));
    fgets(address, 21, stdin);
    address[strlen(address) - 1] = '\0';
    return address;
}

char* queryNewLocation() {
    printf("Enter a location for the new file: ");
    char* location = (char*) malloc(sizeof(char*));
    fgets(location, 21, stdin);
    location[strlen(location) - 1] = '\0';
    return location;
}

int main(int argc, char** argv) {
    int socketFileDescriptor = socket(AF_INET, SOCK_STREAM, 0);

    if (socketFileDescriptor < 0) {
        printf("There was an error creating the socket.\n");
        return 1;
    }

    uint16_t port = (uint16_t) queryPort();
    char* address = queryAddress();

    printf("Attempting to connect to %s:%d\n", address, port);

    struct sockaddr_in serverAddressInfo;

    serverAddressInfo.sin_family = AF_INET;
    serverAddressInfo.sin_port = htons(port);
    serverAddressInfo.sin_addr.s_addr = inet_addr(address);

    int connection = connect(socketFileDescriptor, (struct sockaddr*)& serverAddressInfo, sizeof(serverAddressInfo));

    if (connection < 0) {
        printf("There was en error connecting.\n");
        return 2;
    }

    printf("Enter a file location: ");
    char fileLocation[5000];
    char fileContents[5000];

    fgets(fileLocation, 5000, stdin);

    char* newlineChar = strchr(fileLocation, '\n');
    *newlineChar = '\0';
    send(socketFileDescriptor, fileLocation, strlen(fileLocation) + 1, 0);
    recv(socketFileDescriptor, fileContents, 5000, 0);
    printf("--Server Response--\n%s\n", fileContents);
    close(socketFileDescriptor);

    char* location = queryNewLocation();

    FILE* file = fopen(location, "w");

    if (file == NULL) {
        printf("Error opening file at that desired location. Exiting program.");
        return EXIT_FAILURE;
    }

    fputs(fileContents, file);
    fclose(file);

    return EXIT_SUCCESS;
}