#include <stdio.h>

void perfectNumbers(int n)
{
    // look through numbers 2 to 1000
    for (int num = 2; num <= n; num++)
    {
        int sum = 0;
        // look for factors of num
        for (int i = 1; i < num; i++)
        {
            if (num % i == 0) // if its divisible its a factor
            {
                sum += i; // add factor to sum
            }
        }
        // if all divibles added together is equal to num, num is a perfect number
        if (sum == num)
        {
            printf("%d is a perfect number.\n", num);
        }
    }
}

int main()
{
    perfectNumbers(1000);
    return 0;
}