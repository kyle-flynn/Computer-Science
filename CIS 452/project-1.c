#include <term.h>
#include <curses.h>
#include <unistd.h>
#include <stdio.h>

int main() {
	setupterm((char*) 0, 1, (int*)0);

	int col = tigetnum("cols");
	int row = tigetnum("lines");

  printf("---> Supported terminal size\n");
	printf("---> ROWS: %d\n", row);
	printf("---> COLS: %d\n", col);
	
  int color = 0;
	printf("****** COLORS ******\n");
	printf("* BLACK   = 0      *\n");
	printf("* RED     = 1      *\n");
	printf("* GREEN   = 2      *\n");
	printf("* YELLOW  = 3      *\n");
	printf("* BLUE    = 4      *\n");
	printf("* MAGENTA = 5      *\n");
	printf("* CYAN    = 6      *\n");
	printf("* WHITE   = 7      *\n");
	printf("****** COLORS ******\n\n");
	printf("Select a background color for the terminal: ");
	scanf("%d", &color);
	putp(tparm(set_background, color));
	sleep(5);
	char* clear = tigetstr("clear");
	
	char* startCa = tigetstr("smcup");
	putp(startCa);
	int oldRow = 0;
	int oldCol = 0;
	mvcur(-1, -1, 0, 0);
  putp(clear);
	int newRow = 0;
	int newCol = 0;
	while (newRow >= 0 && newCol >= 0) {
		printf("Enter desired row and column (separated by comma): ");
		scanf("%d,%d", &newRow, &newCol);
		if (newRow < row && newCol < col && newRow >= 0 && newCol >= 0) {
			putp(clear);
			putp(tparm(cursor_address, newRow, newCol));
			newRow = 0;
			newCol = 0;
		} else {
			putp(clear);
			printf("Invalid location. ");
		}
	}

	char* endCa = tigetstr("rmcup");
	putp(endCa);
  putp(tparm(set_background, 0));
	return 0;
}
