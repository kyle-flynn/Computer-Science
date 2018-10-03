#include <sys/socket.h>
#include <netpacket/packet.h>
#include <net/ethernet.h>
#include <netinet/ip.h>
#include <stdio.h>
#include <errno.h>
#include <sys/types.h>
#include <net/if.h>

int main(int argc, char** argv) {

	int sockfd = socket(AF_PACKET, SOCK_RAW, htons(ETH_P_ALL));	

	if (sockfd < 0) {
    printf("There was an error creating the socket [%d].\n", sockfd);
		return -1;
	}

  struct sockaddr_ll serv_addr, cli_addr;
	serv_addr.sll_family = AF_PACKET;
	serv_addr.sll_protocol = htons(ETH_P_ALL);

  if (argc < 1) {
		serv_addr.sll_ifindex = if_nametoindex("eth0");
	} else {
		serv_addr.sll_ifindex = if_nametoindex(argv[1]);
	}

	int sock_bind = bind(sockfd, (struct sockaddr*)&serv_addr, (socklen_t) sizeof(serv_addr));

  if (sock_bind < 0) {
	  printf("There was an error binding the socket to the address and port.\n");
		return -1;
	}
  
  while (1) {
    int length = sizeof(cli_addr);
		char message[4098];
    int res = recvfrom(sockfd, message, 4098, 0, (struct sockaddr*)&cli_addr, &length);
    if (res < 0) {
      printf("Error receiving packet.\n");
		} else {
      
      if (cli_addr.sll_pkttype != PACKET_OUTGOING) {
        struct ether_header* pck_headr = (struct ether_header*) message;
				struct iphdr* ip_headr = (struct iphdr*) (message + sizeof(pck_headr));
				struct sockaddr_in src, dest;
			  src.sin_addr.s_addr = ip_headr->saddr;
				dest.sin_addr.s_addr = ip_headr->daddr;

				printf("\n---------- INCOMING PACKET HEADER ----------\n");
				printf("Destination: %s\n", inet_ntoa(src.sin_addr));
				printf("Source: %s\n", inet_ntoa(dest.sin_addr));
				printf("Type: %d\n", ntohs(pck_headr->ether_type));

        if (ntohs(pck_headr->ether_type) == ETH_P_IP) {
          printf("*THIS IS AN IPV4 PACKET*");
				}

				printf("\n--------------------------------------------\n");
			}

		}
	}

  return 0;
}
