#include <stdio.h>
#include <stdlib.h>
#include <ctype.h>

int main()
{
    char *userInput = NULL;
    size_t len = 0; // for genline

    printf("Enter a line of characters: ");
    getline(&userInput, &len, stdin); // Read the line including spaces.

    int letters = 0, spaces = -1, digits = 0, others = 0; // space is -1 because the enter key is not counted
    for (int i = 0; userInput[i] != '\0'; i++)
    {
        if (isalpha(userInput[i]))
            letters++;
        else if (isspace(userInput[i]))
            spaces++;
        else if (isdigit(userInput[i]))
            digits++;
        else
            others++;
    }
    printf("Letters: %d, Spaces: %d, Digits: %d, Other characters: %d\n", letters, spaces, digits, others);

    free(userInput); // since used getline, need to free the memory
    return 0;
}