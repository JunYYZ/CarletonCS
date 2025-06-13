#include <stdio.h>
#include <math.h>

void reverseString()
{
    char input[14], reversed[14]; // since max 14 characters

    printf("\nEnter a string (less than 15 characters): ");
    scanf("%s", input);

    short length = 0;

    // because I'm not sure if we're allowed to use strlen(input) or even include string.h
    // int length2 = sizeof(input) / sizeof(char) does not work since the char array is allocated to 14 characters

    while (input[length] != '\0')
    {
        length++;
    }

    short j = 0; // index for reversed[]
    for (int i = length - 1; i >= 0; i--)
    {
        reversed[j] = input[i];
        j++;
    }
    reversed[length] = '\0'; // end string

    printf("Reversed string: %s\n", reversed);
}

void split3DigitNumber()
{
    int number;
    short nComponents[3]; // components of a 3 digit number: {hundreds, tens, ones}

    printf("\nEnter a three digit number: ");

    // loop to check for valid input, even though the code works as expected for invalid input
    // (ie 1100 would give 11,0,0 and 41 gives 0,4,1)
    for (int i = 0; i < 1; i++)
    {
        scanf("%d", &number);
        // valid range and repeat check
        if (number < 100 || number > 999)
        {
            printf("Invalid input, please enter a three(3) digit number\n");
            i--; // decrement to redo input
        }
    }

    // split the numbers
    nComponents[0] = number / 100;        // hundreds
    nComponents[1] = (number % 100) / 10; // tens
    nComponents[2] = number % 10;         // ones

    printf("Hundreds: %d\n", nComponents[0]);
    printf("Tens: %d\n", nComponents[1]);
    printf("Ones: %d\n", nComponents[2]);
}

// void getNumInputLoop(int num[], short size) // i was going to use this but the number generator was too hard and used
//                                             // a different input loop
// {
//     for (int i = 0; i < size; i++) // loop for user input
//     {
//         scanf("%d", &num[i]);
//     }
// }

void spd3() // sum, product, difference of 3 numbers (names are hard)
{
    int nums[3];
    int spd[3]; // sums, product, diffference: {sums, product, difference}

    printf("\nEnter three numbers: ");

    // getNumInputLoop(nums, 3);
    for (int i = 0; i < 3; i++) // loop for user input
    {
        scanf("%d", &nums[i]);
    }

    spd[0] = nums[0] + nums[1] + nums[2]; // sum of numbers
    spd[1] = nums[0] * nums[1] * nums[2]; // product of numbers
    spd[2] = nums[0] - nums[1] - nums[2]; // difference of numbers

    printf("Sum: %d,  Product: %d, Difference: %d\n", spd[0], spd[1], spd[2]);
}

void weirdNumberGenerator()
{
    int nums[4];
    short repEntryIndex;
    printf("\nEnter four numbers (1 to 9): \n");
    for (int i = 0; i < 4; i++)
    {
        scanf("%d", &nums[i]);
        // valid range and repeat check
        if (nums[i] < 1 || nums[i] > 9)
        {
            printf("Invalid input, please enter a number between 1 and 9\n");
            i--; // decrement to redo input
        }
        else // catch duplicate entry
        {
            for (int j = 0; j < i; j++)
            {
                if (nums[i] == nums[j])
                {
                    repEntryIndex = i; // if there is duplicate, note its index
                }
            }
        }
    }

    short count = 0;
    printf("Generated three-digit numbers:\n");

    // combination through the array to generate numbers
    // if there are duplicate numbers in user input there will be duplicate numbers printed
    for (int i = 0; i < 4; i++)
    {
        for (int j = 0; j < 4; j++)
        {
            for (int k = 0; k < 4; k++)
            {
                if (!(i == repEntryIndex || j == repEntryIndex || k == repEntryIndex)) // if hit duplicate entry, ignore it.
                {
                    // print if no repeated digits, also works for catching if there are more than 2 repeated digits
                    if (nums[i] != nums[j] && nums[i] != nums[k] && nums[j] != nums[k])
                    {
                        printf("%d%d%d\t", nums[i], nums[j], nums[k]);
                        count++;
                    }
                }
            }
        }
        printf("\n");
    }
    printf("Total generated numbers: %d\n", count); // prints number of numbers generated
}

void airBaggageCalculator()
{
    // setup "constants"
    double standardRate = 120.0;
    double baggageChargeRate = 0.015;
    double freeLuggageWeight = 20.0;

    double baggageWeight, totalCharges;

    printf("\nEnter baggage weight in kilograms: ");
    scanf("%lf", &baggageWeight);

    if (baggageWeight <= freeLuggageWeight) // if less then standard rate
    {
        totalCharges = standardRate;
        printf("No bagagge charges within free allowance\n");
    }
    else
    {
        // calculate excess charge by calculating excess weight then multiplying by rate
        double excessCharge = (baggageWeight - freeLuggageWeight) * (baggageChargeRate * standardRate);
        printf("Baggage charges: $%.3f\n", excessCharge);
        totalCharges = standardRate + excessCharge;
    }

    printf("Total charges for the flight: $%.3f\n", totalCharges);
}

void narcisissticNumbers()
{
    printf("\nNarcissistic Numbers between 100 and 999:\n");

    for (int num = 100; num <= 999; num++)
    {

        short nComponents[3];
        // split the numbers into its components
        nComponents[0] = num / 100;        // hundreds
        nComponents[1] = (num % 100) / 10; // tens
        nComponents[2] = num % 10;         // ones

        short sum = pow(nComponents[0], 3) + pow(nComponents[1], 3) + pow(nComponents[2], 3);

        // check if number is narcisisst, if it is, should equal orignal number "sum"
        if (num == sum)
        {
            printf("%d\n", num);
        }
    }
}

int main()
{
    weirdNumberGenerator();

    reverseString();

    split3DigitNumber();

    spd3();

    airBaggageCalculator();

    narcisissticNumbers();
}