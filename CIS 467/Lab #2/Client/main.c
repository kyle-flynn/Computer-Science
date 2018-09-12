#include <stdio.h>
#include <string.h>
#include <unistd.h>
#include <stdlib.h>
#include <sys/socket.h>
#include <netinet/in.h>
#include <arpa/inet.h>

int queryPort() {
    printf("Enter the server port number (defaults to 8080): ");
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
    char line[5000];
    char line2[5000];

    fgets(line, 5000, stdin);

    char* newlineChar = strchr(line, '\n');
    *newlineChar = '\0';
    send(socketFileDescriptor, line, strlen(line) + 1, 0);
    recv(socketFileDescriptor, line2, 5000, 0);
    printf("Server Response: %s\n", line2);
    close(socketFileDescriptor);

    return 0;
}