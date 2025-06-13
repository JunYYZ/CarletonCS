#include <stdio.h>

int main() {
    printf("Hello, World!\n");

    int height;

    printf("Enter the height of the Christmas tree: ");
    scanf("%d", &height);

    if (height < 2) {
        printf("Looks like we've got a baby tree here not quite ready for Christmas cheer.\n");
        return 0;
    } else if (height > 10) {
        printf("Whoa, that tree is too big for my cozy living room. Let's find one that fits just right.\n");
        return 0;
    }

    for (int i = 0; i < height; i++) {
        //pritn spaces
        for (int j = 0; j < height - i - 1; j++) {
            printf(" ");
        }

        //stars
        for (int j = 0; j < 2 * i + 1; j++) {
            printf("*");
        }
        printf("\n");
    }


    for (int i = 0; i < height - 1; i++) {
        printf(" ");
    }
    printf("|");

    return 0;

}