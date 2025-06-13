#include <stdio.h>

typedef struct {
    unsigned char red, green, blue;
} RGB;

typedef union {
    unsigned int hex;
    RGB color;
} ColorUnion;

void displayRGB(ColorUnion color) {
    // Assuming little-endian architecture; adjust if necessary.
    printf("Red: %d, Green: %d, Blue: %d\n", color.color.red, color.color.green, color.color.blue);
}

int main() {
    ColorUnion color;
    printf("Enter hexadecimal value: ");
    scanf("%x", &color.hex);
    displayRGB(color);
    return 0;
}