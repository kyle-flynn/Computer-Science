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

int main(int argc, char** argv) {
    int socketFileDescriptor = socket(AF_INET, SOCK_DGRAM, 0);

    if (socketFileDescriptor < 0) {
        printf("There was an error creating the socket.\n");
        return 1;
    }

    struct timeval socketTimeout;
    socketTimeout.tv_sec = 5;
    socketTimeout.tv_usec = 0;

    setsockopt(socketFileDescriptor, SOL_SOCKET, SO_RCVTIMEO, &socketTimeout, sizeof(socketTimeout));

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

    while(1) {
        int length = sizeof(clientAddressInfo);
        char clientMessage[5000];
        int responseBytes = recvfrom(socketFileDescriptor, clientMessage, 5000, 0, (struct sockaddr*)&clientAddressInfo, &length);

        if (responseBytes == -1) {
            printf("Socket has timed out while trying to receive connections.\n");
        } else {
            printf("Client sent: %s\n", clientMessage);
        }

        sendto(socketFileDescriptor, clientMessage, strlen(clientMessage) + 1, 0, (struct sockaddr*)&clientAddressInfo,
               sizeof(clientAddressInfo));
    }

    close(socketFileDescriptor);

    return 0;
}