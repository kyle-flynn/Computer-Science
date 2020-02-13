/* Lab $5 Assignment - Shared Code  */
/* Author: Kyle Flynn & John C. Doneth */

#define QUIT -1
#define WAITING 0
#define WRITTEN 1
#define COMPLETE 3
#define MAX 256

struct Message {
  int status;
	char str[MAX];
};
