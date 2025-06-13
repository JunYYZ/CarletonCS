// recipes.h
#ifndef RECIPES_H
#define RECIPES_H

#include <stddef.h> // for size_t

typedef struct Recipe
{
    int id;
    char name[25];
    char ingredients[45];
    char secret_ingredients[10];
    char buffer;
} Recipe;

// Function declarations

// Prompts the user for a filename and checks if it exists
int userFile(char *filename, size_t len);

// Trims the input string to remove the newline character
void trimString(char *str);

// Prints all recipes from the file
void allPrint(const char *filename);

// Adds spaces to the right side of a string until it reaches a given length
void addSpaces(char *str, int fullLength);

// Searches for a recipe by name and prints its details if found
void nameSearch(const char *filename);

// Asks the user for recipe details and writes the new recipe to the file
void addRecipe(const char *filename);

// Searches for recipes by secret ingredient and writes them to a new file
void secretSearch(const char *filename);

// Searches for recipes by ingredient and writes them to a new file
void ingredientSearch(const char *filename);

// Stores recipes within a specified range of IDs in a new file
void storeUserRecipes(const char *filename);

typedef void (*displayFunction)(const char *filename);

// Displays the menu options to the user
void displayMenu(displayFunction *functions, const char *filename);

#endif // RECIPES_H