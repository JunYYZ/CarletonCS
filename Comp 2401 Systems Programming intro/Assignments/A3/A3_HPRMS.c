#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <time.h>

// patient structure
typedef struct patientStruct
{
    char firstName[7];             // 6 chars + '\0'
    char familyName[11];           // 10 chars + '\0'
    unsigned int department : 3;   // 0-7, 3 bits
    unsigned int dailyCost : 6;    // 0-63, 6 bits
    unsigned int numberOfDays : 5; // 0-31, 5 bits
    unsigned int severity : 2;     // 0-3, 2 bits
} Patient;

// function to generate patient records with random data
void genPatientRecords(Patient *patients, int count)
{
    const char *firstNames[] = {"Emily", "Liam", "Sophia", "Noah", "Olivia", "Ethan", "Ava", "Lucas"};
    const char *lastNames[] = {"Smith", "Patel", "Tremblay", "Nguyen", "MacDonald", "Singh", "Leblanc", "Chen"};
    int firstNamesCount = sizeof(firstNames) / sizeof(firstNames[0]);
    int lastNamesCount = sizeof(lastNames) / sizeof(lastNames[0]);

    for (int i = 0; i < count; i++)
    {
        strncpy(patients[i].firstName, firstNames[rand() % firstNamesCount], 6);
        patients[i].firstName[6] = '\0'; // proper null at end of string

        strncpy(patients[i].familyName, lastNames[rand() % lastNamesCount], 10);
        patients[i].familyName[10] = '\0'; // proper null at end of string

        patients[i].department = (rand() % 6) + 1;
        patients[i].dailyCost = (rand() % 50) + 1;
        patients[i].numberOfDays = rand() % 31;
        patients[i].severity = rand() % 4;
    }
}

// helper to print menu options
void displayMenu()
{
    printf("\nHospital Patient Records Management System Menu\n\n");
    printf("1. Print all patient records.\n");
    printf("2. Summary of the patient records.\n");
    printf("3. Search for all patients by Family Name.\n");
    printf("4. Size of the structure.\n");
    printf("5. Quit.\n\n");
    printf("Enter your choice: ");
}

// menu function
int getMenuChoice()
{
    int choice;
    displayMenu();
    
    while (scanf("%d", &choice) != 1 || choice < 1 || choice > 5)
    {
        printf("Invalid option. Please enter a number between 1 and 5.\n");
        // clearing input buffer
        while (getchar() != '\n')
            ;
        displayMenu();
    }
    return choice;
}

// function to print all patient records
void printAllPR(Patient *patients, int count)
{
    printf("\nAll Patient Records:\n");
    printf("%-5s %-10s  %-10s   %-11s  %-10s  %-13s  %-8s\n",
           "Index", "First Name", "Family Name", "Department", "Daily Cost", "Number of Days", "Severity");
    for (int i = 0; i < count; i++)
    {
        printf("%-5d %-10s  %-10s    %-11u  %-10u  %-13u   %-8u\n",
               i,
               patients[i].firstName,
               patients[i].familyName,
               patients[i].department,
               patients[i].dailyCost,
               patients[i].numberOfDays,
               patients[i].severity);
        // printf("%d. %s %s, Department: %u, Daily Cost: $%u, Hospitalization Days: %u, Severity: %u\n",
        //    i + 1, patients[i].firstName, patients[i].familyName, patients[i].department,
        //    patients[i].dailyCost, patients[i].numberOfDays, patients[i].severity);
    }
}

void printPRSummary(Patient *patients, int count)
{
    int departmentCounts[6] = {0}; // as departments are 1-6
    int totalDailyCost = 0, totalDays = 0;
    int severityCounts[4] = {0}; // severity levels 0-3
    for (int i = 0; i < count; i++)
    {
        departmentCounts[patients[i].department - 1]++;
        totalDailyCost += patients[i].dailyCost;
        totalDays += patients[i].numberOfDays;
        severityCounts[patients[i].severity]++;
    }
    int maxSeverityCount = 0, maxSeverityLevel = 0;
    for (int i = 0; i < 4; i++)
    {
        if (severityCounts[i] > maxSeverityCount)
        {
            maxSeverityCount = severityCounts[i];
            maxSeverityLevel = i;
        }
    }
    printf("\nSummary of Patient Records:\n");
    printf("Total number of patients: %d\n", count);
    for (int i = 0; i < 6; i++)
    {
        printf("D%d patients: %d\n", i + 1, departmentCounts[i]);
    }
    printf("Average daily cost: $%.2f\n", (float)totalDailyCost / count);
    printf("Average number of hospitalization days: %.2f\n", (float)totalDays / count);
    printf("Most common severity level: %d\n", maxSeverityLevel);
    printf("Number of patients in level %d: %d\n", maxSeverityLevel, maxSeverityCount);
}

void patientSearchFamName(Patient *patients, int count)
{
    char searchName[11];
    printf("Enter family name to search: ");
    scanf("%10s", searchName); // familyName is up to 10 characters + '\0' by our struct
    int found = 0;

    printf("\n"); // new line to look prettier

    for (int i = 0; i < count; i++)
    {
        if (strcasecmp(patients[i].familyName, searchName) == 0) // using string.h for case insensitive comparison
        {
            printf("%s %s, Department: %u, Daily Cost: $%u, Hospitalization Days: %u, Severity: %u\n",
                   patients[i].firstName, patients[i].familyName, patients[i].department,
                   patients[i].dailyCost, patients[i].numberOfDays, patients[i].severity);
            found = 1;
        }
    }
    if (!found)
    {
        printf("\nNo records found for the family name: %s\n", searchName);
    }
}

long patientStructSize()
{
    return (unsigned long)sizeof(Patient);
}

void quit(Patient *patients)
{
    char choice;
    printf("Are you sure you want to quit? (y/n): ");
    scanf(" %c", &choice); // space before %c to catch any preceding whitespace
    if (choice == 'Y' || choice == 'y')
    {
        free(patients); // free memory
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

    int uiPatients;
    if (argc == 2)
    {
        // If there's a command line argument, use it
        uiPatients = atoi(argv[1]);
    }
    else
    {
        // Otherwise, ask the user for input
        printf("Commandline argument not provided or invalid, switching to manual inputs.\n");
        printf("Usage: %s <number_of_patients>\n\n", argv[0]);

        printf("Enter the number of patient records (6-19): ");
        scanf("%d", &uiPatients);
    }

    // Validate the input number of patients
    if (uiPatients < 6 || uiPatients > 19)
    {
        printf("Error: The number of patient records should be between 6 and 19.\n");
        return 1;
    }

    // Allocate memory for the patient records
    Patient *patients = (Patient *)malloc(uiPatients * sizeof(Patient));
    if (patients == NULL)
    {
        printf("Memory allocation failed.\n");
        return 1;
    }

    // Populate the patient records with random data
    genPatientRecords(patients, uiPatients);

    // Main loop for interacting with the menu
    int choice;
    do
    {
        choice = getMenuChoice();
        switch (choice)
        {
        case 1:
            printAllPR(patients, uiPatients);
            break;
        case 2:
            printPRSummary(patients, uiPatients);
            break;
        case 3:
            patientSearchFamName(patients, uiPatients);
            break;
        case 4:
            printf("Size of the patient structure: %lu bytes\n", patientStructSize());
            break;
        case 5:
            quit(patients);
        }
    } while (choice != -1);

    free(patients); // free memory if somehow not use quit to exit loop

    return 0;
}