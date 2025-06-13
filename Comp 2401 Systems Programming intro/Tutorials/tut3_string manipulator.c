#include <stdio.h>
#include <string.h>

int main()
{
    char str1[100], str2[100], strCopy[100];

    printf("Enter string 1: ");
    fgets(str1, 100, stdin);
    str1[strcspn(str1, "\n")] = 0;

    printf("Enter string 2: ");
    fgets(str2, 100, stdin);
    str2[strcspn(str2, "\n")] = 0;

    // concat
    strcat(str1, str2);
    printf("Concatenated string: %s\n", str1);

    // len
    printf("Length of string 1: %lu\n", strlen(str1) - strlen(str2));
    printf("Length of string 2: %lu\n", strlen(str2));

    // compare
    if (strcmp(str1, str2) == 0)
    {
        printf("The strings are equal.\n");
    }
    else
    {
        printf("The strings are not equal.\n");
    }

    // copy
    strcpy(strCopy, str1);
    printf("Copied string: %s\n", strCopy);

    return 0;
}