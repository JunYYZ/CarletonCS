#include <stdio.h>
#include <string.h>
#include <stdlib.h>

typedef struct
{
    char firstName[16];
    char familyName[16];
    int salary;
    int yearsWithCompany;
} Employee;

void printFamilyNames(Employee *employees, int size)
{
    for (int i = 0; i < size; i++)
    {
        printf("%s\n", employees[i].familyName);
    }
}

void cmpEmployee(Employee *emp, char *familyName, int size)
{
    for (int i = 0; i < size; i++)
    {
        if (strcmp(emp[i].familyName, familyName) == 0)
        {
            printf("Match found: %s %s, Salary: %d, Years: %d\n",
                   emp[i].firstName, emp[i].familyName, emp[i].salary, emp[i].yearsWithCompany);
        }
    }
}

int main()
{
    Employee employees[20];
    // Initialize employees with random data...

    printFamilyNames(employees, 20);
    char inputFamilyName[16];
    printf("Enter a family name to search: ");
    scanf("%s", inputFamilyName);
    cmpEmployee(employees, inputFamilyName, 20);

    return 0;
}