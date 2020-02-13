/* Lab #5 Assignment - Writer  */
/* Author: Kyle Flynn & John C. Doneth  */

#include <sys/ipc.h>
#include <sys/shm.h> 
#include <stdio.h> 
#include <stdlib.h>
#include <sys/stat.h>
#include <unistd.h>
#include <string.h>
#include "shared.h"

int main() {

	// Setting up our shared memory variables.
	struct Message *ptr = malloc(sizeof *ptr);	
  key_t key = ftok("shmfile",65);
	size_t size = sizeof *ptr;
	int flags = IPC_CREAT | S_IRUSR | S_IWUSR;
  int shmid = shmget(key, size, flags);
		
	char quitStr[MAX] = "quit\n";
	if (shmid < 0) {
		printf("WRITER: Error creating shared memory segment.\n");
		exit(1);
	}

	// Accessing our shared memory segment.
	ptr = (struct Message*) shmat(shmid, NULL, 0);
	if (((void *) ptr) < 0) {
		printf("WRITER: Error attaching shared memory segment.\n");
		exit(1);
	}

	// Default status when writer starts up.
	ptr->status = WAITING;

    while (ptr->status != QUIT) {
		printf("Input > ");
		fgets(ptr->str, MAX, stdin);
		fflush(stdout);
		if (strcmp(quitStr, ptr->str) == 0) {
			ptr->status = QUIT;
		} else {
			// If writer doesn't want to quit, tell readers we've written data.
			ptr->status = WRITTEN;
			while (ptr->status < COMPLETE) {
				/* Do Nothing while readers are not done. */
			}

			// Once readers are done, reset!
			ptr->status = WAITING;
			sleep(1);
		}
	}

	// When writer wants to quit, detach and kill memory segment.
	shmdt((void *) ptr);
	shmctl(shmid, IPC_RMID, NULL);
	printf("\nCleaned up. Writer exiting.\n");
    return 0; 
}
