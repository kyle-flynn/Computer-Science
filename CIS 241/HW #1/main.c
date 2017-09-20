#include <stdio.h>

void swap(int* var1, int* var2) {

    int* temp = var1;

    *var1 = *var2;
    *var2 = *temp;

}

int main() {

    int i = 5;
    int j = 10;

    swap(&i, &j);
    printf("The value of i is now %d, and the value of j is now %d", i, j);

    return 0;
}