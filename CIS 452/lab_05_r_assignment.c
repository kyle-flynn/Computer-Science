/* Lab #5 Assignment - Reader  */
/* Author: Kyle Flynn & John C. Doneth */

#include <sys/ipc.h> 
#include <sys/shm.h> 
#include <stdio.h> 
#include <stdlib.h>
#include <sys/stat.h>
#include <unistd.h>
#include "shared.h"

int main() {
	
  // All of our shared memory variables.
  key_t key = ftok("shmfile",65); 
	size_t size = sizeof(struct Message);
	int flags = S_IRUSR | S_IWUSR;	
	struct Message *ptr;
	int shmid = shmget(key, size, flags);
  struct shmid_ds shmInfo;
	if (shmid < 0) {
		printf("READER: Error accessing shared memory segment.\n");
		exit(1);
	}

	// Simply getting our message pointer from memory.
	ptr = (struct Message*) shmat(shmid, NULL, 0);
	if (((void *) ptr) < 0) {
		printf("READER: Error attaching to shared memory segment.\n");
		exit(1);
	}
  
	// Getting the shmid_ds from the segment
  shmctl(shmid, IPC_STAT, &shmInfo);

	// To avoid race conditions, we subtract the creator pid from this program
	// pid. Each unit of 'time', we sleep the thread for 5 microseconds.
  int procTime = getpid() - shmInfo.shm_cpid;
	int time = procTime;

	// Simple variable that determines if reader has gotten the output yet.
	int didRead = 0;
	printf("(%d) Reader now awaiting for writer...\n\n", procTime);
	while (ptr->status != QUIT) {

		// Only executed if output is not complete, and we have not read yet.
		if (ptr->status >= WRITTEN && didRead == 0) {

			// Critical section that avoids race condition.
			while (time > 0) {
			  time = time - 1;
				usleep(5); // usleep sleeps for n microseconds - part of unistd.h
			}
			ptr->status = ptr->status + 1;
			printf("(%d) Output > %s\n", ptr->status, ptr->str);
			didRead = 1;
			time = procTime;
		} else if (ptr->status == WAITING && didRead == 1) {
			didRead = 0;
		}
	}

	// When shared memory says quit, detach.
	shmdt((void *) ptr);
	printf("Reader detached. Exiting.\n");
  return 0; 
} 
