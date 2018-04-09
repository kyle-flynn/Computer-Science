#define ARRAY_SIZE 16*1024
#define NUM_LOOPS 100

int main() {
	char array[ARRAY_SIZE];
	register int i;
	register int solution = 0;

	for (i = 0; i < ARRAY_SIZE*NUM_LOOPS; i++) {
		solution *= array[0];
		solution += array[8192+8];
	}
	return solution;
}
