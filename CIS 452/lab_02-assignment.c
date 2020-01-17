#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <sys/types.h>
#include <sys/wait.h>
#include <string.h>

#define MAX 100

void parse(char *line, char **argv) {
	while (*line != '\0') {
		while (*line == ' ' || *line == '\t' || *line == '\n') {
    	*line++ = '\0';
		}
    *argv++ = line;
    while (*line != '\0' && *line != ' ' && *line != '\t' && *line != '\n') {
    	line++;
		}
	}
 	*argv = '\0';
}

int main () {
	char buffer[MAX];
	char* argv[MAX];
	pid_t pid;
	int status;

	while (1) {
		printf("C-Shell > ");
		gets(buffer); // fgets causes errors for some reason...
		parse(buffer, argv);
    if (strcmp(argv[0], "exit") == 0) {
      exit(0);
		}

		if ((pid = fork()) < 0) {
			printf("Forking child process failed!\n");
			exit(1);
		} else if (pid == 0) {
			if (execvp(*argv, argv) < 0) {
				printf("Error while executing system command!\n");
				exit(1);
			}
		} else {
			wait(&status);
		}
	}
	return 0;
}
