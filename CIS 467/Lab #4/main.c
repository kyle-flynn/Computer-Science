#include <stdio.h>
#include <string.h>
#include <unistd.h>
#include <sys/socket.h>
#include <arpa/inet.h>
#include <linux/if_packet.h>
#include <linux/if_ether.h>

int main(int argc, char** argv) {
	int sockfd = socket(AF_PACKET, SOCK_RAW, htons(ETH_P_ALL));	
  if (sockfd < 0) {
    printf("There was an error creating the socket [%d].\n", sockfd);
		return -1;
	}

  struct sockaddr_ll sock_addr;
	sock_addr.sll_family = AF_PACKET;
	sock_addr.sll_protocol = htons(ETH_P_ALL);
	sock_addr.sll_ifindex = if_nametoindex("eth0");

	int sock_bind = bind(sockfd, (struct sockaddr*)&sock_addr, (socklen_t) sizeof(sock_addr));

  if (sock_bind < 0) {
	  printf("There was an error binding the socket to the address and port.\n");
		return -1;
	}
  
  while (1) {
    int length = sizeof(sock_addr);
		char message[4098];
    int res = recvfrom(sockfd, message, 4098, 0, (struct sockaddr*)&sock_addr, &length);
    if (res < 0) {
      printf("Error receiving packet.\n");
		}
	}

  return 0;
}
