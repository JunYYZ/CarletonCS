#include <stdio.h>

void fillArray(int array[4][4])
{
    printf("Enter 16 integers for a 4x4 array:\n");
    for (int i = 0; i < 4; i++)
    {
        for (int j = 0; j < 4; j++)
        {
            if (scanf("%d", &array[i][j]) != 1)
            {
                printf("Invalid input! Please enter an integer.\n");
                while (getchar() != '\n')
                    ;
            }
        }
    }
}

void displayArray(int array[4][4])
{
    printf("Array elements:\n");
    for (int i = 0; i < 4; i++)
    {
        for (int j = 0; j < 4; j++)
        {
            printf("%d ", array[i][j]);
        }
        printf("\n");
    }
}

void displaySums(int array[4][4])
{
    int firstRowSum = 0, secondColumnSum = 0, mainDiagonalSum = 0;
    for (int i = 0; i < 4; i++)
    {
        firstRowSum += array[0][i];
        secondColumnSum += array[i][1];
        mainDiagonalSum += array[i][i];
    }

    printf("Sum of the first row: %d\n", firstRowSum);
    printf("Sum of the second column: %d\n", secondColumnSum);
    printf("Sum of the main diagonal: %d\n", mainDiagonalSum);
}

int main()
{
    int array[4][4];

    fillArray(array);
    displayArray(array);
    displaySums(array);

    return 0;
}