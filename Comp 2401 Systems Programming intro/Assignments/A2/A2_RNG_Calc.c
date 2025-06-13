#include <stdio.h>
#include <stdlib.h>
#include <time.h>

int main()
{
    int array[4][5], rowSum, maxNum = 0;
    srand(time(NULL)); // default random number generator

    for (int i = 0; i < 4; i++)
    {
        rowSum = 0;
        for (int j = 0; j < 5; j++)
        {
            array[i][j] = 10 + rand() % 41; // rng between 10 and 50 since rand() % 41

            printf("%d ", array[i][j]);

            rowSum += array[i][j]; // sum of row needs to add each vallue in the row

            if (array[i][j] > maxNum) // if the current value is greater than the max, set max to the current value
            {
                maxNum = array[i][j];
            }
        }
        printf("| Row sum: %d\n", rowSum);
    }
    printf("Maximum value in the array: %d\n", maxNum);
    return 0;
}