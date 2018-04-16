#define NUM_LOOPS 16*1024
#define ARRAY_SIZE 100

int main() {
	char array[ARRAY_SIZE];
	register int i;
	register int solution = 0;

	for (i = 0; i < ARRAY_SIZE*NUM_LOOPS; i++) {
		solution *= array[0];
		solution *= array[8192];
		solution *= array[16384];
		solution *= array[24576];
		solution *= array[32768];
		solution *= array[40960];
		solution *= array[49152];
		solution *= array[57344];
		solution *= array[65536];
	}
	return solution;
}
