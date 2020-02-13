/* Lab #5 Extra Credit Assignment */
/* Author: Kyle Flynn & John C. Doneth */

#include <sys/ipc.h>
#include <sys/shm.h>
#include <stdio.h>
#include <stdlib.h>
#include <sys/stat.h>
#include <unistd.h>
#include <string.h>
#include <pthread.h>

#define QUIT -1
#define SENT 1
#define IDLE 99
#define MAX 255

/* This is the shared memory between processes. */
struct Message
{
	int status;
	int clients;
	char usr[MAX];
	char str[MAX];
	pthread_mutex_t lock;
};

/* This is the process memory, meant to be shared accross threads. */
struct ProcessMemory
{
	int active;
	struct Message *msg;
	char usr[MAX];
};

void *readerLoop(void *argp);
void *writerLoop(void *argp);

int main()
{
	// Allocating space and declaring variables
	struct ProcessMemory mem;
	struct Message *ptr = malloc(sizeof *ptr);
	struct shmid_ds id_ds;

	// These are the parameters for shmget()
	key_t key = ftok("file", 65);
	size_t size = sizeof *ptr;
	int flags = IPC_CREAT | S_IRUSR | S_IWUSR;
	
	// Making/getting our shared memory segment.
	int shmId = shmget(key, size, flags);

	if (shmId < 0)
	{
		printf("ERROR: Could not created shared memory segment.\n");
	}

	// Initializing our shared memory into our Message struct.
	ptr = (struct Message *)shmat(shmId, NULL, 0);
	ptr->clients = ptr->clients + 1;

	// Making sure our process memory also points to our shared memory.
	mem.active = 1;
	mem.msg = ptr;

	if (((void *)ptr) < 0)
	{
		printf("ERROR: Could not attach to shared memory segment.\n");
	}

	shmctl(shmId, IPC_STAT, &id_ds);

	if (id_ds.shm_cpid == getpid())
	{
		pthread_mutex_init(&(mem.msg->lock), NULL);
	}

	// Every instance asks for the client's name, and stores it in process memory.
	printf("Welcome to SimplChat.\n(%d) active clients.\nEnter your name: ", ptr->clients);
	fgets(mem.usr, MAX, stdin);
	mem.usr[strlen(mem.usr) - 1] = '\0';

	pthread_t writer;
	pthread_t reader;

	pthread_create(&writer, NULL, writerLoop, (void *)&mem);
	pthread_create(&reader, NULL, readerLoop, (void *)&mem);

	pthread_join(writer, NULL);
	pthread_join(reader, NULL);

	// When threads join remove user and detach from memory if only client left.
	ptr->clients = ptr->clients - 1;
	if (ptr->clients <= 0)
	{
		printf("No more clients. Removing shared memory...\n");
		pthread_mutex_destroy(&(mem.msg->lock)); 
		shmdt((void *)ptr);
		shmctl(shmId, IPC_RMID, NULL);
	}
	else
	{
		shmdt((void *)ptr);
	}
	return 0;
}

void *readerLoop(void *argp)
{
	struct ProcessMemory *mem = (struct ProcessMemory *)argp;
	struct Message *ptr = (struct Message *)mem->msg;
	int didRead = 0;
	while (mem->active == 1)
	{
		// As long as we are not idle, and a message was sent.
		if (ptr->status < IDLE && ptr->status >= SENT && didRead == 0)
		{
			// Neat little if statement that displays the "prompt" to the user again
			// if they did not send the message.
			printf("\n%s: %s\n", ptr->usr, ptr->str);
			if (strcmp(ptr->usr, mem->usr) != 0)
			{
				printf("Input > ");
			}
			fflush(stdout);

			/* Critical section. Using thread mutex lock. */
			pthread_mutex_lock(&(mem->msg->lock));
			ptr->status = ptr->status + 1;
			didRead = 1;
			pthread_mutex_unlock(&(mem->msg->lock));
		}

		// Reader checks if all clients received the message, and then resets
		// the status to tell the writer it is ready to receive input again.
		if (didRead == 1 && (ptr->status - SENT) >= ptr->clients)
		{
			ptr->status = IDLE;
			didRead = 0;
		}
	}
	printf("Reader thread exiting...\n");
	return NULL;
}

void *writerLoop(void *argp)
{
	struct ProcessMemory *mem = (struct ProcessMemory *)argp;
	struct Message *ptr = (struct Message *)mem->msg;
	char msg[MAX] = {0};
	char quit[] = "quit";
	while (mem->active == 1)
	{
		while (ptr->status == SENT)
		{
			/* Do nothing  */
		}
		sleep(1);

		// Grabbing our input via fgets(). Making sure we get rid of the \n character.
		printf("Input > ");
		fgets(msg, MAX, stdin);
		msg[strlen(msg) - 1] = '\0';

		// If 'quit' was typed, go ahead and tell the process we are no longer active.
		if (strcmp(quit, msg) == 0)
		{
			mem->active = 0;
			break;
		}
		else
		{
			fflush(stdout);
			strcpy(ptr->usr, mem->usr);
			strcpy(ptr->str, msg);
			ptr->status = SENT;
		}
	}
	printf("Writer thread exiting...\n");
	return NULL;
}
