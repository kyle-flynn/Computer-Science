#include <pthread.h>
#include <stdio.h>
#include <errno.h>
#include <unistd.h>
#include <stdlib.h>
#include <string.h>

/** 
  do_greeting prints a greeting message
  both input argument (arg) and return value are an untyped pointer
*/
void *do_greeting(void *arg);

int main()
{
    pthread_t thread1;     //thread ID holder
	int status;            //captures any error code

    printf("From PID %d, thread id %lu\n", getpid(), pthread_self());
    // create and start a thread executing the "do_greeting()" function
    if ((status = pthread_create(&thread1, NULL, do_greeting, NULL)) != 0) {
        fprintf(stderr, "Thread create error %d: %s\n", status, strerror(status));
        exit(1);
    }
		sleep(2);
    return 0;
}

void *do_greeting(void *arg)
{
    sleep(1);
    printf("Thread version of Hello, world. PID=%d TID=%lu\n", 
            getpid(), pthread_self());
    return NULL;
}
