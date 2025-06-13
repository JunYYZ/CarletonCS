#include "A5_Recipe.h"
#include <stdio.h>
#include <stdlib.h>
#include <string.h>

// Function to get file from user and check if it exists filename is returned via an output parameter
int userFile(char *filename, size_t len)
{
    printf("Enter the filename of the file containing the recipes: ");
    fgets(filename, len, stdin);

    // remove newline character
    filename[strcspn(filename, "\n")] = 0;

    FILE *file;
    if ((file = fopen(filename, "r")))
    {
        fclose(file);
        printf("File '%s' exists.\n", filename);
        return 1; // file exists
    }

    fprintf(stderr, "Error: File '%s' does not exist.\n", filename);
    return 0; // bad
}

// helper to trim input string, it removes newline character
void trimString(char *str)
{
    size_t len = strlen(str);
    if (len > 0 && str[len - 1] == '\n')
    {
        str[len - 1] = '\0';
    }
}

void allPrint(const char *filename)
{
    FILE *file = fopen(filename, "r");

    char line[256]; // line buffer
    int id = 0;     // pretty much just a line counter

    // skip header
    fgets(line, sizeof(line), file);

    printf("All Receipts:\n");

    // main iterative loop
    while (fgets(line, sizeof(line), file))
    {
        // remove newline at end
        line[strcspn(line, "\n")] = 0;

        // isolate name at 25 characters
        line[25] = '\0';

        // trim trailing whitespaces, but i dont think this is necessary since nothing comes after anyways
        for (int i = 24; i >= 0 && line[i] == ' '; i--)
        {
            line[i] = '\0';
        }

        printf("ID:%d \tName: %s\n", id++, line);
    }

    fclose(file);
}

// helper to add spaces on right side of string until given length
void addSpaces(char *str, int fullLength)
{
    int len = strlen(str);

    if (len < fullLength)
    {
        memset(str + len, ' ', fullLength - len); // sets spaces using memeset
        str[fullLength] = '\0';
    }
}

void nameSearch(const char *filename)
{
    char recipeName[100];

    printf("Enter the name of the recipe you want to search for: ");

    fgets(recipeName, sizeof(recipeName), stdin);

    trimString(recipeName);          // trim newline character
    short ilen = strlen(recipeName); // to keep track of original input length, so we can output properly if not found

    FILE *file = fopen(filename, "r");
    char line[256];
    int found = 0;
    char name[26];
    char ingredients[46];
    char secret[11];

    addSpaces(recipeName, 25);

    // skip header line
    fgets(line, sizeof(line), file);

    while (fgets(line, sizeof(line), file))
    {

        // compare
        if (strncasecmp(line, recipeName, 25) == 0)
        {
            found = 1;

            // get name, ingredients, and secret from line
            strncpy(name, line, 25);
            name[25] = '\0';

            strncpy(ingredients, line + 25, 45);
            ingredients[45] = '\0';

            strncpy(secret, line + 70, 10);
            secret[10] = '\0';

            // print with format
            printf("Matched Recipe:\n");
            printf("\tName: %s\n", name);
            printf("\tRecipe: %s\n", ingredients);
            printf("\tSecret Ingredient: %s\n", secret);
            break;
        }
    }
    fclose(file);
    if (!found)
    {
        recipeName[ilen] = '\0';
        printf("Recipe for '%s' not found.\n", recipeName);
    }
}

// Function to ask user for recipe details and write to file
void addRecipe(const char *filename)
{
    char name[26];        // 25 characters + null terminator
    char ingredients[46]; // 45 characters + null terminator
    char secret[11];      // 10 characters + null terminator

    printf("Enter recipe details:\n");
    printf("\tRecipe name (max 25 characters): ");
    fgets(name, sizeof(name), stdin);
    trimString(name);    // removes the trailing newline character
    addSpaces(name, 25); // add spaces to make 25 characters

    printf("\tIngredients (max 45 characters): ");
    fgets(ingredients, sizeof(ingredients), stdin);
    trimString(ingredients);
    addSpaces(ingredients, 45);

    printf("\tSecret ingredient (max 10 characters): ");
    fgets(secret, sizeof(secret), stdin);
    trimString(secret);
    addSpaces(secret, 10);

    // adding to file
    FILE *file = fopen(filename, "a");

    fprintf(file, "%s%s%s\n", name, ingredients, secret);

    fclose(file);

    printf("Recipe added.\n");
}

void secretSearch(const char *filename)
{
    char secretIngredient[20];

    printf("Enter the secret ingredient you want to search for: ");
    fgets(secretIngredient, sizeof(secretIngredient), stdin);

    trimString(secretIngredient);
    short ilen = strlen(secretIngredient);

    FILE *file = fopen(filename, "r");
    FILE *outputFile;
    char line[256];
    int found = 0;
    char outputFileName[32]; // large enough to hold the secret ingredient plus the file extension

    int counter = 0;

    addSpaces(secretIngredient, 10);

    // skip header line
    fgets(line, sizeof(line), file);

    // main iterator
    while (fgets(line, sizeof(line), file))
    {
        // make line null-terminated, though not sure if necessary thb
        line[sizeof(line) - 1] = '\0';

        // main logic, check if input is same as secret ingredient
        if (strncasecmp(line + 70, secretIngredient, 10) == 0)
        {
            // init file and open only once
            if (found == 0)
            {
                char temp = secretIngredient[ilen];
                secretIngredient[ilen] = '\0';

                // format output filename as "[secretIngredient].txt"
                sprintf(outputFileName, "%s.txt", secretIngredient);

                secretIngredient[ilen] = temp; // very roundabout but its to make the text file not have trailing spaces

                outputFile = fopen(outputFileName, "w");
                if (!outputFile)
                {
                    perror("Error creating output file");
                    fclose(file);
                    EXIT_FAILURE;
                }
                // add header line
                fputs("Name                     Ingredients                                  Secret    \n", outputFile);
            }

            // write the line to the output file
            fputs(line, outputFile);
            found = 1;
            counter += 1;
        }
    }

    // print stuff
    if (found)
    {
        printf("Recipes containing the secret ingredient have been stored in the file '%s'.\n", outputFileName);
        printf("Found %i matched records in file\n", counter);
        fclose(outputFile);
    }
    else
    {
        secretIngredient[ilen] = '\0';
        printf("Secret ingredient '%s' not found.\n", secretIngredient);
    }

    fclose(file);
}

// function to search for ingredient instead of secret ingredient. Almost exact same as secret ingredient
void ingredientSearch(const char *filename)
{
    char ingredient[50];

    printf("Enter the ingredient you want to search for: ");
    fgets(ingredient, sizeof(ingredient), stdin);

    trimString(ingredient);
    short ilen = strlen(ingredient);

    FILE *file = fopen(filename, "r");
    FILE *outputFile;
    char line[256];
    int found = 0;
    char outputFileName[64]; // large enough to hold the secret ingredient plus the file extension

    // skip header line
    fgets(line, sizeof(line), file);

    int counter = 0;

    char ingreds[46]; // copy of the ingredients column
    char *token;
    char *delimiters = "+ "; // delimiters are both the plus sign and space

    // iterator
    while (fgets(line, sizeof(line), file))
    {
        // make line null-terminated, though not sure if necessary thb
        line[sizeof(line) - 1] = '\0';

        // copy ingredients column
        strncpy(ingreds, line + 25, 45);

        // tokenize string and compare each token with word
        token = strtok(ingreds, delimiters);
        while (token != NULL)
        {
            if (strcasecmp(token, ingredient) == 0)
            {
                // init file and open only once
                if (found == 0)
                {
                    char temp = ingredient[ilen];
                    ingredient[ilen] = '\0';

                    // format output filename as "[secretIngredient].txt"
                    sprintf(outputFileName, "%s.txt", ingredient);

                    ingredient[ilen] = temp; // very roundabout but its to make the text file not have trailing spaces

                    outputFile = fopen(outputFileName, "w");
                    if (!outputFile)
                    {
                        perror("Error creating output file");
                        fclose(file);
                        EXIT_FAILURE;
                    }
                    // add header line
                    fputs("Name                     Ingredients                                  Secret    \n", outputFile);
                }

                // write line to the output file
                fputs(line, outputFile);
                found = 1;
                counter += 1;
            }
            token = strtok(NULL, delimiters);
        }
    }

    if (found)
    {
        printf("Recipes containing the ingredient have been stored in the file '%s'.\n", outputFileName);
        printf("Found %i matched records in file\n", counter);
        fclose(outputFile);
    }
    else
    {
        ingredient[ilen] = '\0';
        printf("Ingredient '%s' not found.\n", ingredient);
    }

    fclose(file);
}

void storeUserRecipes(const char *filename)
{
    int startID = 1, endID = 0;
    // loop until a valid start is entered
    while (startID <= 1 || endID < startID)
    {
        printf("Enter the start ID of the recipe you want to store in a new file (must be greater than 1): ");
        scanf("%d", &startID);
        // eat remaining newline character
        while (getchar() != '\n')
            ;

        if (startID <= 0)
        {
            printf("Invalid start ID. The start ID must be greater than 0. Please try again.\n");
        }

        printf("Enter the end ID of the recipe you want to store in a new file: ");
        scanf("%d", &endID);

        // eat remaining newline character
        while (getchar() != '\n')
            ;

        if (endID < startID)
        {
            printf("End ID cannot be less than start ID. Please try again.\n");
        }
    }

    FILE *file = fopen(filename, "r");
    FILE *outputFile = fopen("random.txt", "w");

    if (!file || !outputFile)
    {
        perror("Error opening file");
        if (file)
            fclose(file);
        if (outputFile)
            fclose(outputFile);
        exit(EXIT_FAILURE);
    }

    // add header to new file
    fputs("Name                     Ingredients                                  Secret    \n", outputFile);

    char line[256];

    // skip the header line
    fgets(line, sizeof(line), file);

    int count = 0;
    int id = 2; // since there is no line 0 and line 1 is header

    while (fgets(line, sizeof(line), file))
    {
        if (id >= startID && id <= endID)
        {
            fputs(line, outputFile);
            count += 1;
        }
        id += 1;
    }

    // detemine if endID is out of range
    if (count < (endID - startID + 1))
    {
        printf("The index is out of range.\n");
    }

    printf("Recipes with desired range have been stored in the file 'random.txt'. \nFound %d matched records in the file.\n", count);

    fclose(file);
    fclose(outputFile);
}

// function pointer def
typedef void (*displayFunction)(const char *filename);

void displayMenu(displayFunction *functions, const char *filename)
{
    int choice;
    do
    {
        printf("\nMenu:\n"
               "1) Print All Records\n"
               "2) Search Recipe By Name\n"
               "3) Write Recipe To File\n"
               "4) Search By Secret Ingredient\n"
               "5) Search By Ingredient\n"
               "6) Store User Recipes\n"
               "7) Quit\n\n");

        printf("Enter your choice: ");
        scanf("%d", &choice);
        while (getchar() != '\n')
            ; // eat newline character, actually useful here

        if (choice >= 1 && choice <= 6)
        {
            (*functions[choice - 1])(filename);
        }
        else if (choice == 7)
        {
            printf("\n\nQuitting program\n\n");
        }
        else
        {
            printf("\nInvalid input\n");
        }

    } while (choice != 7);
}

int main()
{
    char filename[256];

    // get filename from user
    int result = userFile(filename, sizeof(filename));
    if (result == 0)
    {
        return EXIT_FAILURE;
    }

    // function pointers array to menu options
    displayFunction functions[] = {
        allPrint,
        nameSearch,
        addRecipe,
        secretSearch,
        ingredientSearch,
        storeUserRecipes};

    displayMenu(functions, filename);

    return 0;
}
