#include <sys/ipc.h> 
#include <sys/shm.h> 
#include <stdio.h> 
#include <stdlib.h>
#include <sys/stat.h>
#include <unistd.h>
#include "shared.h"

int main() { 
    key_t key = ftok("shmfile",65); 
 		size_t size = sizeof(struct Message);
		int flags = IPC_CREAT | S_IRUSR | S_IWUSR;	
		struct Message *ptr;
		int shmid = shmget(key, size, flags);

		if (shmid < 0) {
			printf("READER: Error accessing shared memory segment.\n");
			exit(1);
		}

		ptr = (struct Message*) shmat(shmid, NULL, 0);
		if (((void *) ptr) < 0) {
			printf("READER: Error attaching to shared memory segment.\n");
			exit(1);
		}

		int didRead = 0;
		printf("Reader now awaiting for writer...\n\n");
		while (ptr->status > QUIT) {
			if (ptr->status >= WRITTEN && didRead == 0) {
				ptr->status = ptr->status + 1;
				printf("Output > %s\n", ptr->str);
				didRead = 1;
			} else if (ptr->status == WAITING && didRead == 1) {
				printf("READER: Resetting...\n");
				didRead = 0;
			}
		}

		shmdt((void *) ptr);
		printf("Reader detached. Exiting.\n");
    return 0; 
} 
