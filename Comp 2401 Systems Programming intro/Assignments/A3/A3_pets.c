#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <time.h>

typedef struct catdata
{
    char *breed;
    char *eyeColor;
} CatRecord;

typedef struct dogdata
{
    char *breed;
    char *tailType;
} DogRecord;

typedef struct petdata
{
    char *name;
    char age;
    char *bodyColor;
    char catOrDog;
    union
    {
        CatRecord cat;
        DogRecord dog;
    };
    // the createRandomPet function does not need malloc or strdup but
    // the getUserPet function does, I wanted to keep track if a pet was
    // created by the user, and when freeing memory, if the pet was created by
    // the user, then I would need to free each individual field of the pet struct
    // if it was generated using the random pet generator,
    // then it would be enough to free the pet struct itself
    short isUserCreated;
} PetInfo;

typedef struct listnode
{
    PetInfo *data;
    struct ListNode *next;
} ListNode;

// helper since this will be called so many times
int randomInt(int min, int max)
{
    return min + rand() % (max - min + 1);
}

// helper to clear input buffer
void clearBuffer()
{
    int c;
    while ((c = getchar()) != '\n' && c != EOF)
    {
    }
}

// create a new pet with random data
PetInfo *createRandomPet()
{
    char *names[] = {"Whiskers", "Mittens", "Shadow", "Mango", "Meowgi", "Buddy", "Bailey", "Cheesebro"};
    char *bodyColors[] = {"Black", "White", "Siamese", "Golden", "Gray"};
    char *catBreeds[] = {"Persian", "Siamese", "Bengal", "Abyssinian", "Ragdoll", "Sphynx"};
    char *catEyeColors[] = {"Yellow", "Green", "Blue", "Amber"};
    char *dogBreeds[] = {"Labrador Retriever", "German Shepherd", "Golden Retriever", "Bulldog", "Beagle", "Poodle", "Dachshund"};
    char *dogTailTypes[] = {"Straight", "Curled", "Docked", "Bobbed"};

    PetInfo *newPet = (PetInfo *)malloc(sizeof(PetInfo));

    if (newPet)
    {
        int n = sizeof(names) / sizeof(names[0]);
        newPet->name = names[randomInt(0, n - 1)];

        newPet->age = (char)randomInt(1, 20);

        n = sizeof(bodyColors) / sizeof(bodyColors[0]);
        newPet->bodyColor = bodyColors[randomInt(0, n - 1)];

        newPet->catOrDog = randomInt(0, 1); // 0 for cat, 1 for dog

        if (newPet->catOrDog == 0)
        { // cat
            n = sizeof(catBreeds) / sizeof(catBreeds[0]);
            newPet->cat.breed = catBreeds[randomInt(0, n - 1)];

            n = sizeof(catEyeColors) / sizeof(catEyeColors[0]);
            newPet->cat.eyeColor = catEyeColors[randomInt(0, n - 1)];
        }
        else
        { // dog
            n = sizeof(dogBreeds) / sizeof(dogBreeds[0]);
            newPet->dog.breed = dogBreeds[randomInt(0, n - 1)];

            n = sizeof(dogTailTypes) / sizeof(dogTailTypes[0]);
            newPet->dog.tailType = dogTailTypes[randomInt(0, n - 1)];
        }

        newPet->isUserCreated = 0;
    }

    return newPet;
}

ListNode *createNode(PetInfo *pet)
{
    ListNode *newNode = (ListNode *)malloc(sizeof(ListNode));
    if (newNode)
    {
        if (!pet)
        {
            printf("Memory allocation failed for new node.\n");
            return NULL;
        }
        newNode->data = pet;
        newNode->next = NULL;
    }
    return newNode;
}

void appendPetInfo(ListNode **list, PetInfo *data)
{
    ListNode *newNode = createNode(data);

    if (!newNode)
    {
        printf("Memory allocation failed for new node.\n");
        return;
    }

    if (*list == NULL)
    {
        *list = newNode;
    }
    else
    {
        ListNode *current = *list;
        while (current->next != NULL)
        {
            current = (ListNode *)current->next;
        }
        current->next = (struct ListNode *)newNode;
    }
}

void displayMenu()
{
    printf("\nPet Records Management Menu:\n");
    printf("Records:\n");
    printf("\t1. Print all the pet records\n");
    printf("\t2. Print all the cat records\n");
    printf("\t3. Print all the dog records\n");
    printf("4. Insert new pet records\n");
    printf("5. Delete pet records based on the name\n");
    printf("6. Quit\n");
    printf("Enter your choice: ");
}

int getMenuChoice()
{
    int choice;
    displayMenu();

    while (scanf("%d", &choice) != 1 || choice < 1 || choice > 6)
    {
        printf("Invalid option. Please enter a number between 1 and 6.\n");
        // clearing input buffer
        while (getchar() != '\n')
            ;
        displayMenu();
    }
    return choice;
}

// helper to print specific pet info, probably unecessary but it's here
void printPetInfo(PetInfo *pet)
{
    if (pet->catOrDog == 0)
    { // Cat
        printf("%-4s  %-10s   %-3d  %-10s  %-10s\t\t  %-10s\n", "Cat", pet->name, pet->age, pet->bodyColor, pet->cat.breed, pet->cat.eyeColor);
    }
    else
    { // Dog
        printf("%-4s  %-10s   %-3d  %-10s  %-10s\t\t  %-10s\n", "Dog", pet->name, pet->age, pet->bodyColor, pet->dog.breed, pet->dog.tailType);
    }

    // printf("Type: %s", pet->catOrDog == 0 ? "Cat" : "Dog");

    // printf(" Name: %s, Age: %d, Color: %s\n", pet->name, pet->age, pet->bodyColor);

    // if (pet->catOrDog == 0)
    // { // Cat
    //     printf("  Breed: %s, Eye Color: %s", pet->cat.breed, pet->cat.eyeColor);
    // }
    // else
    // { // Dog
    //     printf("  Breed: %s, Tail Type: %s", pet->dog.breed, pet->dog.tailType);
    // }
}

void printPets(ListNode *head, char type)
{
    // type is 'c' for cats, 'd' for dogs, 'a' for all
    printf("%-4s  %-10s   %-3s  %-10s  %-10s\t\t  %-10s\n", "Type", "Name", "Age", "Color", "Breed", "Eye Color/Tail Type");
    ListNode *current = head;
    while (current != NULL)
    {
        if (type == 'a' || (type == 'c' && current->data->catOrDog == 0) || (type == 'd' && current->data->catOrDog == 1))
        {
            printPetInfo(current->data);
        }
        current = (ListNode *)current->next;
    }
}

PetInfo *getUserPet()
{
    char buffer[256]; // string buffer for string inputs
    int current;

    PetInfo *newPet = (PetInfo *)malloc(sizeof(PetInfo));

    if (!newPet)
    {
        printf("Memory allocation failed for new pet.\n");
        return NULL;
    }

    do
    {
        printf("Is your pet a cat (0) or a dog (1)?: ");
        scanf("%d", &current);
        if (current != 0 && current != 1)
        {
            printf("Error: Invalid input. Please enter 0 for cat or 1 for dog.\n");
        }
        clearBuffer();
    } while (current != 0 && current != 1);
    newPet->catOrDog = (char)current;

    if (newPet->catOrDog == 0)
    { // Cat
        printf("Enter cat's breed: ");
        scanf("%s", buffer);
        newPet->cat.breed = strdup(buffer);
        clearBuffer();

        printf("Enter cat's eye color: ");
        scanf("%s", buffer);
        newPet->cat.eyeColor = strdup(buffer);
        clearBuffer();
    }
    else
    { // Dog
        printf("Enter dog's breed: ");
        scanf("%s", buffer);
        newPet->dog.breed = strdup(buffer);
        clearBuffer();

        printf("Enter dog's tail type: ");
        scanf("%s", buffer);
        newPet->dog.tailType = strdup(buffer);
        clearBuffer();
    }

    printf("Enter pet's name: ");
    scanf("%s", buffer);
    newPet->name = strdup(buffer); // Duplicate the string to store in the structure
    clearBuffer();

    do
    {
        printf("Enter pet's age: ");
        scanf("%d", &current);
        if (current < 0)
        {
            printf("Error: Age cannot be negative. Please enter a valid age.\n");
        }
        clearBuffer();
    } while (current < 0);
    newPet->age = (char)current;

    printf("Enter pet's body color: ");
    scanf("%s", buffer);
    newPet->bodyColor = strdup(buffer);
    clearBuffer();

    newPet->isUserCreated = 1;

    return newPet;
}

void appendUserPet(ListNode **list)
{
    char more;
    do
    {
        clearBuffer();
        appendPetInfo(list, getUserPet());
        printf("Pet record inserted.\n");

        printf("Do you want to insert more records? (y/n): ");
        scanf(" %c", &more); // Notice the space before %c to consume any newline left in the input buffer
    } while (more == 'y' || more == 'Y');
}

void deletePetRecord(ListNode **list)
{

    char name[100];
    printf("Enter pet name to delete: ");
    scanf("%99s", name); // Use %99s to avoid buffer overflow

    ListNode *current = *list, *prev = NULL;

    int found = 0;
    while (current != NULL)
    {
        if (strcasecmp(current->data->name, name) == 0)
        {

            ListNode *delete = current;
            if (prev == NULL)
            {
                *list = (ListNode *)current->next;
            }
            else
            {
                prev->next = current->next;
            }
            current = (ListNode *)current->next;

            if (found == 0)
            {
                printf("\nDeleting pet: %s\n\n", delete->data->name);
                printf("%-4s  %-10s   %-3s  %-10s  %-10s\t\t  %-10s\n", "Type", "Name", "Age", "Color", "Breed", "Eye Color/Tail Type");
            }
            found = 1;
            printPetInfo(delete->data);
            if (delete->data->isUserCreated)
            {
                free(delete->data->name);
                free(delete->data->bodyColor);
                if (delete->data->catOrDog == 0)
                {
                    free(delete->data->cat.breed);
                    free(delete->data->cat.eyeColor);
                }
                else
                {
                    free(delete->data->dog.breed);
                    free(delete->data->dog.tailType);
                }
            }

            free(delete->data);
            free(delete);
        }
        else
        {
            prev = current;
            current = (ListNode *)current->next;
        }
    }

    if (found == 0)
    {
        printf("No pet found with the name %s.\n", name);
        return;
    }
}

void quit(ListNode *head)
{
    char con;
    printf("Are you sure you want to quit? (y/n): ");
    scanf(" %c", &con); // Notice the space before %c to consume any newline left in the input buffer
    if (con == 'Y' || con == 'y')
    {
        ListNode *current = head;
        ListNode *next;
        while (current != NULL)
        {
            next = (ListNode *)current->next;
            if (current->data->isUserCreated)
            {
                free(current->data->name);
                free(current->data->bodyColor);
                if (current->data->catOrDog == 0)
                {
                    free(current->data->cat.breed);
                    free(current->data->cat.eyeColor);
                }
                else
                {
                    free(current->data->dog.breed);
                    free(current->data->dog.tailType);
                }
            }
            free(current->data);
            free(current);
            current = next;
        }
        printf("Exiting..\n");
        exit(0);
    }
    else
    {
        printf("Returning to the menu.\n");
    }
}

int main(int argc, char *argv[])
{
    srand(time(NULL)); // Seed the random number generator

    ListNode *head = NULL; // Initialize the linked list head to NULL

    int numPets;

    if (argc == 2)
    {
        // If there's a command line argument, use it
        numPets = atoi(argv[1]);
    }
    else
    {
        // Otherwise, ask the user for input
        printf("Commandline argument not provided or invalid, switching to manual inputs.\n");
        printf("Usage: %s <number_of_pets>\n\n", argv[0]);

        printf("Enter the number of pet records: ");
        scanf("%d", &numPets);
    }

    for (int i = 0; i < numPets; i++)
    {
        appendPetInfo(&head, createRandomPet());
    }

    clearBuffer();

    int choice;
    do
    {
        choice = getMenuChoice();
        switch (choice)
        {
        case 1:
            printPets(head, 'a');
            break;
        case 2:
            printPets(head, 'c');
            break;
        case 3:
            printPets(head, 'd');
            break;
        case 4:
            appendUserPet(&head);
            clearBuffer();
            break;
        case 5:
            // Prompt for name and delete the record
            deletePetRecord(&head);
            clearBuffer();
            break;
        case 6:
            // Quitting, confirm and then free memory
            quit(head);
        default:
            printf("Invalid option. Please try again.\n");
        }
    } while (choice != -1);

    quit(head);

    return 0;
}