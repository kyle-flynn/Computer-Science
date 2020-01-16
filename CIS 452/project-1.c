#include <term.h>
#include <curses.h>
#include <unistd.h>
#include <stdio.h>

int main() {
	// This must be called first, because it quite literally sets up the terminal.
	setupterm((char*) 0, 1, (int*)0);

	// Simple statements to get the rows and columns supported by the current terminal state.
	int col = tigetnum("cols");
	int row = tigetnum("lines");

	// Basic output to the screen.
  printf("---> Supported terminal size\n");
	printf("---> ROWS: %d\n", row);
	printf("---> COLS: %d\n", col);
	
	// EXTRA CREDIT: Choose the background color of the terminal.
	// The colors came straight from the terminfo() page.
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

	// putp puts data to the console, in this case it's the background color with a 
	// parameter, using tparm.
	putp(tparm(set_background, color));
	
	// Using unistd.h, simple sleep for 5 seconds.
	sleep(5);
	
	// tigetstr doesn't really execute this, it just gives a pointer to a set of
	// characters that get put into the terminal stream.
	char* clear = tigetstr("clear");
	char* startCa = tigetstr("smcup");

	// Once we use putp here, the terminal enables cursor addressing mode.
	putp(startCa);

	// Move the cursor to its origin
	mvcur(-1, -1, 0, 0);

	// Finally clear the screen
  putp(clear);

  // Start our loop that accepts input in the format of ROW,COLUMN
	int newRow = 0;
	int newCol = 0;
	while (newRow >= 0 && newCol >= 0) {
		printf("Enter desired row and column (separated by comma): ");
		scanf("%d,%d", &newRow, &newCol);
		// Make sure every iteration that row,col is less than the supported size as stated
		// at the start of the program. Anything less than 0 terminates the program.
		if (newRow < row && newCol < col && newRow >= 0 && newCol >= 0) {

			// Simply enough, clear	the screen, then move the cursor.
			putp(clear);
			putp(tparm(cursor_address, newRow, newCol));

			// Reset our variables.
			newRow = 0;
			newCol = 0;
		} else {

			// This block gets executed if the row is invalid.
			// Clear the screen, and let the user know their input wasn't valid.
			putp(clear);
			printf("Invalid location. ");
		}
	}

	// Reset the terminal. Exit cursor addressing mode, and set the background to black.
	char* endCa = tigetstr("rmcup");
	putp(endCa);
  putp(tparm(set_background, 0));
	return 0;
}
