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

char* readFile(char* location) {
    FILE *f = fopen(location, "r");
    if (f == NULL) {
        printf("File [%s] does not exist.\n", location);
        return "";
    }

    fseek(f, 0L, SEEK_END);
    long int size = ftell(f);
    fseek(f, 0L, SEEK_SET);

    printf("File Size: %li\n", size);

    char* buffer = (char*) malloc(((size_t) size) * sizeof(char*));
    fread(buffer, sizeof(char), (size_t) size, f);

    printf("Buffer: %s\n", buffer);

    fclose(f);
    return buffer;
}

int main(int argc, char** argv) {
    int socketFileDescriptor = socket(AF_INET, SOCK_STREAM, 0);

    if (socketFileDescriptor < 0) {
        printf("There was an error creating the socket.\n");
        return 1;
    }

    uint16_t port = (uint16_t) queryPort();

    printf("Server listening on 127.0.0.1:%d\n", port);

    struct sockaddr_in serverAddressInfo, clientAddressInfo;

    serverAddressInfo.sin_family = AF_INET;
    serverAddressInfo.sin_port = htons(port);
    serverAddressInfo.sin_addr.s_addr = INADDR_ANY;

    int socketBind = bind(socketFileDescriptor, (struct sockaddr*)&serverAddressInfo, sizeof(serverAddressInfo));

    if (socketBind < 0) {
        printf("Bind error.\n");
        return 3;
    }

    listen(socketFileDescriptor, 10);

    while(1) {
        int length = sizeof(clientAddressInfo);
        int clientSocket = accept(socketFileDescriptor, (struct sockaddr*)&clientAddressInfo, &length);
        char line[5000];
        recv(clientSocket, line, 5000, 0);
        printf("Client file request: %s\n", line);

        char* result = readFile(line);
        if (strlen(result) <= 0) {
            char* response = "Error: File does not exist.";
            send(clientSocket, response, strlen(response) + 1, 0);
        } else {
            send(clientSocket, result, strlen(result) + 1, 0);
        }

        close(clientSocket);
    }

    close(socketFileDescriptor);

    return 0;
}