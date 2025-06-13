#include <stdio.h>

int isBitSet(unsigned char c, int bitNum)
{
    return (c >> bitNum) & 1;
}

unsigned char setBitToOne(unsigned char c, int bitNum)
{
    return c | (1 << bitNum);
}

unsigned char clearBit(unsigned char c, int bitNum)
{
    return c & ~(1 << bitNum);
}

int main()
{
    unsigned char value;
    int bitNum;

    value = 0x14; // binary: 00010100
    bitNum = 4;

    printf("Is bit %d in %x set? %s\n", bitNum, value, (isBitSet(value, bitNum) ? "Yes" : "No"));

    value = setBitToOne(value, 5);
    printf("After setting bit 5, value is now: %x\n", value);

    value = clearBit(value, 4);
    printf("After clearing bit 4, value is now: %x\n", value);

    return 0;
}