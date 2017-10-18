#include <stdio.h>
#include <string.h>

int main() {
    char str[] = "system", *p;
    p = str;
    printf("\n%c %s %s %d %d\n", *p, str, p + 1, strlen(str), sizeof(str));
    return 0;
}