#include <pthread.h> 
#include <errno.h> 
#include <unistd.h> 
#include <stdio.h> 
#include <stdlib.h> 
#include <string.h> 


void* do_greeting2 (void* arg); 

int main() 
{ 
    pthread_t thr1, thr2;  // thread ID's 
    void *result1, *result2;     // return values 
    int status;
    int count1, count2;

    srand(getpid());

    // create and start two threads; both executing the
    // "do_greeting2" function 
    // pass the threads a pointer to NULL as their argument 
    count1 = 30;
    if ((status = pthread_create (&thr1, NULL, do_greeting2, &count1)) != 0) { 
        fprintf (stderr, "thread create error: %s\n", strerror(status)); 
        exit (1); 
    } 
    count2 = 50;
    if ((status = pthread_create (&thr2, NULL, do_greeting2, &count2)) != 0) { 
        fprintf (stderr, "thread create error: %s\n", strerror(status)); 
        exit (1); 
    }

    // join with the threads (wait for them to terminate);
    // get their return vals 
    if ((status = pthread_join (thr1, &result1)) != 0) { 
        fprintf (stderr, "Join error %s\n", strerror(status));
        exit (1); 
    } 
    if ((status = pthread_join (thr2, &result2)) != 0) { 
        fprintf (stderr, "Join error %s\n", strerror(status));
        exit (1); 
    }

    // threads return what they were passed (i.e. NULL) 
    if (result1 != NULL || result2 != NULL) { 
        fprintf(stderr, "bad result\n");
        exit (1); 
    } 
    return 0; 
}

void* do_greeting2 (void* arg) 
{ 
    long val = rand() % 2;
    int *param = (int *) arg;

    // print out message based on val 
    for (int loop = 0;  loop < *param;  loop++) { 
        //sleep (1);
        if (!val) 
            printf ("(%d) Hello ", loop); 
        else 
            printf ("[%d] World\n", loop); 
    } 
    return NULL; 
}
