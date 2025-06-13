#include <stdio.h>
#include <string.h>

int main()
{
    char input[256];
    printf("Enter a string: ");
    fgets(input, sizeof(input), stdin);

    char *word = strtok(input, " ");
    char longest[256] = "";

    while (word != NULL)
    {
        if (strlen(word) > strlen(longest))
        {
            strcpy(longest, word);
        }
        word = strtok(NULL, " ");
    }

    longest[strcspn(longest, "\n")] = 0;

    printf("Longest word: %s\n", longest);

    return 0;
}