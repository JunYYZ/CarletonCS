#include <stdio.h>
#include <string.h>

void encodeString(char *input)
{
    char encodedS[11];
    int i = 0;
    // iterate through string
    while (input[i] != '\0')
    {
        // logic:
        // calculates encoded character by subtracting 'a'
        // from input character to get a number from 0 to 25,
        // then we add 2 to shift it two letters to the right,
        // then we take that number's mod 26 so we can handle the wrap-around,
        // then we add 'a' or 'A' back to ensure we get a lowercase or uppercase letter
        if (input[i] >= 'a' && input[i] <= 'z')
        {
            encodedS[i] = ((input[i] - 'a' + 2) % 26) + 'a';
        }
        else if (input[i] >= 'A' && input[i] <= 'Z')
        {
            encodedS[i] = ((input[i] - 'A' + 2) % 26) + 'A';
        }
        else
        {
            encodedS[i] = input[i];
        }
        i++;
    }
    encodedS[i] = '\0'; // Null-terminate the encoded string.
    printf("Encoded message: %s\n", encodedS);
}

int main()
{
    char userInput[11]; // since its 10 characters(space counts right?) or 2 words, we need 11 to account for the null terminator
    printf("Enter a string (10 characters or 2 words): ");

    fgets(userInput, sizeof(userInput), stdin); // read input including spaces
    userInput[strcspn(userInput, "\n")] = 0;    // remove newline character

    encodeString(userInput);
    return 0;
}