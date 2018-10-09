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

int main(int argc, char** argv) {
    int socketFileDescriptor = socket(AF_INET, SOCK_DGRAM, 0);

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

    printf("Enter a message to send to the server: ");
    char message[5000];
    char response[5000];

    fgets(message, 5000, stdin);

    char* newlineChar = strchr(message, '\n');
    *newlineChar = '\0';

    sendto(socketFileDescriptor, message, strlen(message) + 1, 0, (struct sockaddr*)&serverAddressInfo, sizeof(serverAddressInfo));
    recv(socketFileDescriptor, response, 5000, 0);
    printf("--Server Response--\n%s\n", response);
    close(socketFileDescriptor);

    return EXIT_SUCCESS;
}