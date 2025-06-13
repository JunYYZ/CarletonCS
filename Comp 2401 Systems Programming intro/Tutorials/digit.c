#include <stdio.h>
#include <ctype.h>

int main()
{
    char c;

    // Prompt the user to enter a character
    printf("Enter a character: ");

    // Read a character from the user
    scanf("%c", &c);

    // Check if the entered character is not a digit
    if (!isdigit(c))
    {
        // Display a message indicating that the character is not a digit
        printf("%c is not a digit.\n", c);
    }
    else
    {
        // Display a message indicating that the character is a digit
        printf("%c(%d) is a digit.\n", c, c);

        // Suggestion to try converting the character to an integer using (c - '0')
        printf("\nHint for Tutorial 2, Question 3: \n\tThe difference between using %c(%d) and %c(%d).\n", c, c, c, c - '0');
    }

    return 0;
}
