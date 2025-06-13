#include <stdio.h>

int binaryValue(int num)
{
    int binary = 0;
    int base = 1;

    while (num > 0)
    {
        int rem = num % 2;
        binary += rem * base;
        num /= 2;
        base *= 10;
    }

    return binary;
}

struct test1
{
    int num;
    char a[10];
    char *name;
};

union test2
{
    int a;
    float b;
};

struct test3
{
    float a;
    struct test1 b;
};

int main()
{
    printf("%i\n", sizeof(int[4][5]));

    printf("%i\n", sizeof(float[2][3][4]));

    struct test1 t1;
    printf("%i\n", sizeof(t1));

    printf("%i\n", sizeof(union test2));

    printf("%i\n", sizeof(struct test3));

    // int a = 4;
    // int b = 7;
    // int c = 9;

    // printf("%d\n", a && c);

    // printf("%d\n", a || b);

    // printf("%d\n", a ^ b ^ c);

    // printf("%d\n", a & b | c);

    // printf("%d\n", a & b & c);

    // printf("%d\n", binaryValue(a));
    // printf("%d\n", binaryValue(b));
    // printf("%d\n", binaryValue(c));

    // char y = 28;

    // printf("%d\n", y >> 3);

    // printf("%d\n", y << 2);

    // printf("%d\n", y & 0x0F);

    // printf("%d\n", y & y);

    // printf("%d\n", y >> 2);

    // printf("%d\n", y >> 2);

    return 0;
}
