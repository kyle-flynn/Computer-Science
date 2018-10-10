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

char* queryAddress() {
    printf("Enter the server address: ");
    char* address = (char*) malloc(sizeof(char*));
    fgets(address, 21, stdin);
    address[strlen(address) - 1] = '\0';
    return address;
}

char* queryInput() {
    printf("Type something to the server: ");
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

    struct timeval timeout;
    timeout.tv_sec = 0;
    timeout.tv_usec = 0;

    char response[MESSAGE_SIZE];
    char* input;

    while (1) {
        fd_set read_fds;
        FD_ZERO(&read_fds);
        int fdMax = socketFileDescriptor;
        FD_SET(socketFileDescriptor, &read_fds);
        FD_SET(STDIN_FILENO, &read_fds);
        int activity = select(fdMax + 1, &read_fds, NULL, NULL, NULL) == -1;

        if (activity == -1) {
            printf("Unable to modify socket file descriptor.\n");
        }

        if (FD_ISSET(socketFileDescriptor, &read_fds)) {
            printf("SFD is set\n");
        }

        if (FD_ISSET(STDIN_FILENO, &read_fds)) {
            // Deals with sending to the server
            input = queryInput();
            send(socketFileDescriptor, input, strlen(input) + 1, 0);

            if (strcmp("Quit", input) == 0) {
                break;
            }

            // Deals with receiving from the server
            recv(socketFileDescriptor, response, MESSAGE_SIZE, 0);
            printf("Server: %s\n", response);

            if (strcmp("Quit", response) == 0) {
                printf("Server has disconnected.\n");
            }
        }
    }

    close(socketFileDescriptor);

    return EXIT_SUCCESS;
}