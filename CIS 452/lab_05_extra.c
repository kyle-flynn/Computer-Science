#include <sys/ipc.h>
#include <sys/shm.h>
#include <stdio.h>
#include <stdlib.h>
#include <sys/stat.h>
#include <unistd.h>
#include <string.h>
#include <pthread.h>

#define QUIT -1
#define WAITING 0
#define SENT 1
#define IDLE 99
#define MAX 255

struct Message {
  int status;
	int clients;
	int received;
	char usr[MAX];
	char str[MAX];
};

struct ProcessMemory {
  int active;
	struct Message* msg;
};

void* readerLoop(void* argp);
void* writerLoop(void* argp);

int main() {
	struct ProcessMemory mem;
	struct Message *ptr = malloc(sizeof *ptr);
	key_t key = ftok("file", 65);
	size_t size = sizeof *ptr;
	int flags = IPC_CREAT | S_IRUSR | S_IWUSR;
	int shmId = shmget(key, size, flags);

	if (shmId < 0) {
    printf("ERROR: Could not created shared memory segment.\n");
	}

	ptr = (struct Message*) shmat(shmId, NULL, 0);
	ptr->clients = ptr->clients + 1;
	mem.active = 1;
	mem.msg = ptr;

	if (((void *) ptr) < 0) {
		printf("ERROR: Could not attach to shared memory segment.\n");
	}

	pthread_t writer;
	pthread_t reader;

	pthread_create(&writer, NULL, writerLoop, (void*) &mem);
	pthread_create(&reader, NULL, readerLoop, (void*) &mem);

	pthread_join(writer, NULL);
	pthread_join(reader, NULL);
  
	// When threads join remove user and detach from memory.
	ptr->clients = ptr->clients - 1;
  if (ptr->clients <= 0) {
		printf("No more clients. Removing shared memory...\n");
		shmdt((void *) ptr);
	  shmctl(shmId, IPC_RMID, NULL);
	} else {
    shmdt((void *) ptr);
	}
	return 0;
}

void* readerLoop(void* argp) {
	struct ProcessMemory *mem = (struct ProcessMemory*) argp;
	struct Message *ptr = (struct Message*) mem->msg;
	int didRead = 0;
  while (mem->active == 1) {
		while (ptr->status >= WRITTEN && !didRead) {
			ptr->status = ptr->status + 1;
			didRead = 1;
			printf("\n%s: %s\n", ptr->usr, ptr->str);
			fflush(stdout);
		}
		if (didRead == 1 && (ptr->status - WRITTEN) >=  ptr->clients) {
			ptr->status = WAITING;
			didRead = 0;
		}
	}
	return NULL;
}

void* writerLoop(void* argp) {
	struct ProcessMemory *mem = (struct ProcessMemory*) argp;
	struct Message *ptr = (struct Message*) mem->msg;
	char usr[MAX];
	printf("Welcome to SimplChat.\n(%d) active clients.\nEnter your name: ", ptr->clients);
	scanf("%s", usr);
	printf("\n");
  fflush(stdout);
	char msg[MAX];
	char qit[MAX] = "quit";
	while (mem->active == 1) {
    while (ptr->status == WRITTEN) {
      /* Do nothing  */
		}
		sleep(1);
	  printf("Input > ");
		scanf("%s", msg);
		if (strcmp(msg, qit) == 0) {
      mem->active = 0;
			break;
		} else {
  		fflush(stdout);
	  	strcpy(ptr->usr, usr);
		  strcpy(ptr->str, msg);
		  ptr->status = WRITTEN;
		}
	}
	printf("Writer thread exiting...");
	return NULL;
}
