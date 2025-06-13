#include <stdio.h>
#include <stdlib.h>

long factorial(int n) {
    if (n <= 1) return 1;
    else return n * factorial(n - 1);
}

int main(int argc, char *argv[]) {
    if (argc != 2) {
        printf("Usage: %s <positive integer>\n", argv[0]);
        return 1;
    }

    int num = atoi(argv[1]);
    if (num < 0) {
        printf("Error: Please enter a positive integer.\n");
        return 1;
    }

    printf("Factorial of %d is %ld\n", num, factorial(num));
    return 0;
}