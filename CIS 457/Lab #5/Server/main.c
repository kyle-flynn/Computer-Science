#include <stdio.h>
#include <string.h>
#include <unistd.h>
#include <stdlib.h>
#include <sys/socket.h>
#include <sys/types.h>
#include <netinet/in.h>
#include <arpa/inet.h>

#define MESSAGE_SIZE 2048

int queryPort() {
    printf("Enter the server port number: ");
    char port[5000] = "8080";
    fgets(port, 5000, stdin);
    return atoi(port);
}

char* queryInput() {
    printf("Type something to the client: ");
    char* input = (char*) malloc(sizeof(char));
    fgets(input, MESSAGE_SIZE, stdin);
    input[strlen(input) - 1] = '\0';
    return input;
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

    struct timeval timeout;
    timeout.tv_sec = 0;
    timeout.tv_usec = 0;

    int clientSocket;
    char response[MESSAGE_SIZE];
    char* input;

    int length = sizeof(clientAddressInfo);
    clientSocket = accept(socketFileDescriptor, (struct sockaddr*)&clientAddressInfo, &length);

    while (1) {
        fd_set read_fds;
        FD_ZERO(&read_fds);
        int fdmax = socketFileDescriptor;
        FD_SET(socketFileDescriptor, &read_fds);

        if (select(fdmax+1, &read_fds, NULL,NULL,&timeout) == -1) {
            printf("Unable to modify socket file descriptor.\n");
        }

        // Deals with receiving from the client
        recv(clientSocket, response, MESSAGE_SIZE, 0);
        printf("Client: %s\n", response);

        if (strcmp("Quit", response) != 0) {
            printf("Client has disconnected.\n");
        }

        // Deals with sending to the client
        input = queryInput();
        send(clientSocket, input, strlen(input) + 1, 0);
        free(input);

        if (strcmp("Quit", input) != 0) {
            break;
        }
    }

    close(clientSocket);
    close(socketFileDescriptor);

    return 0;
}