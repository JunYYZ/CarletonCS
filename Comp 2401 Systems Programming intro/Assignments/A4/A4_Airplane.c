#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <time.h>

typedef struct
{
    char seatID;
    int row;
    int status;
    char firstName[7];
    char lastName[10];
} CustomerRec;

typedef void (*displayFunction)(CustomerRec *cr, int size);

// generate random names and status
void populateCustomerInfo(CustomerRec *cr, int size)
{

    char seatIDs[] = {'A', 'D', 'G', 'K'};

    char *firstNames[] = {"Emily", "Liam", "Sophia", "Noah", "Olivia", "Ethan", "Ava"};

    char *lastNames[] = {"Smith", "Patel", "Tremblay", "Singh", "Leblanc", "Chen"};

    for (int i = 0; i < size; i++)
    {

        cr[i].status = rand() % 2;
        cr[i].seatID = seatIDs[i % 4];
        cr[i].row = 1 + (i / 4);

        if (cr[i].status == 1)
        { // if assigned, populate names
            strcpy(cr[i].firstName, firstNames[rand() % 7]);
            strcpy(cr[i].lastName, lastNames[rand() % 6]);
        }
        else
        { // if not assigned, leave blank
            strcpy(cr[i].firstName, "");
            strcpy(cr[i].lastName, "");
        }
    }
}

// empty seats
void printEmptySeats(CustomerRec *cr, int size)
{
    printf("\nEmpty Seats:\n");
    for (int i = 0; i < size; i++)
    {
        if (!cr[i].status) //if seat is not assigned
        {
            printf("Seat %d%c\n", cr[i].row, cr[i].seatID);
        }
    }
}

//  assigned seats
void printAssignedSeats(CustomerRec *cr, int size)
{
    printf("\nAssigned Seats:\n");
    for (int i = 0; i < size; i++)
    {
        if (cr[i].status) //if seat is assigned
        {
            printf("Seat %d%c - %s %s\n", cr[i].row, cr[i].seatID, cr[i].firstName, cr[i].lastName);
        }
    }
}

// print all seats
void printSeats(CustomerRec *cr, int size)
{
    printf("\nAll Seats:\n");
    for (int i = 0; i < size; i++)
    {
        if (cr[i].status)
        {
            printf("Seat %d%c - Assigned to %s %s\n", cr[i].row, cr[i].seatID, cr[i].firstName, cr[i].lastName);
        }
        else
        {
            printf("Seat %d%c - Empty\n", cr[i].row, cr[i].seatID);
        }
    }
}

//helper to clear input buffer so typing 5C should not cause any issues
void clearBuffer()
{
    int c;
    while ((c = getchar()) != '\n' && c != EOF)
    {
    }
}

// helper fucntion to find a seat
int findSeatByID(CustomerRec *seats, int size, char *seatID)
{
    int row = seatID[0] - '0'; // convert row part of seatID to an integer
    char seatChar = seatID[1]; // get seat letter part of seatID

    for (int i = 0; i < size; ++i)
    {
        if (seats[i].row == row && seats[i].seatID == seatChar)
        {
            return i;
        }
    }
    return -1;
}

// assign someone to seat
void assignSeat(CustomerRec *cr, int size)
{
    char seatUID[4];

    char firstName[7], lastName[10];

    printf("Enter seat ID to assign (e.g., 1A, 2B, ... 5D): ");
    scanf("%3s", seatUID);

    int seatIndex = findSeatByID(cr, size, seatUID);

    //seat validitity checck ( icant spell)
    if (seatIndex == -1 || cr[seatIndex].status == 1)
    {
        printf("Error: Seat is either invalid or already assigned.\n");
        return;
    }

    printf("Enter first name (max 6 characters): ");
    scanf("%6s", firstName);

    printf("Enter last name (max 9 characters): ");
    scanf("%9s", lastName);

    strcpy(cr[seatIndex].firstName, firstName);
    strcpy(cr[seatIndex].lastName, lastName);

    cr[seatIndex].status = 1;
    printf("Seat %s successfully assigned to %s %s.\n", seatUID, firstName, lastName);
}

// delete a seat assignment
void deleteSeatAssignment(CustomerRec *cr, int size)
{
    char seatUID[4];
    printf("Enter seat ID to delete assignment (e.g., 1A, 2B, ... 5D): ");
    scanf("%3s", seatUID);

    int seatIndex = findSeatByID(cr, size, seatUID);

    if (seatIndex == -1 || cr[seatIndex].status == 0)
    {
        printf("Error: Seat is either invalid or not currently assigned.\n");
        return;
    }

    cr[seatIndex].status = 0; // mark unassigned

    printf("Seat %s assignment successfully deleted.\n", seatUID);
}

// display menu and get user input
void displayMenu(CustomerRec *cr, int size, displayFunction *functions)
{
    int choice;
    do
    {
        printf("\nMenu:\n");
        printf("1. Display the list of empty seats.\n");
        printf("2. Display the list of names with corresponding seats.\n");
        printf("3. Display the list of all seats.\n");
        printf("4. Assign a customer to a seat.\n");
        printf("5. Delete a seat assignment.\n");
        printf("6. Quit\n");
        printf("Enter your choice: ");
        scanf("%d", &choice);

        if (choice >= 1 && choice <= 5)
        {
            (*functions[choice - 1])(cr, size);
            clearBuffer();
        }
    } while (choice != 6);
}

int main()
{
    int numSeats;

    srand(time(NULL));

    printf("Enter the number of seats(betweem 0-21): ");
    scanf("%d", &numSeats);

    // validate the number of seats since i added user input for some reason (it was for testing)
    if (numSeats <= 0)
    {
        printf("Invalid number of seats. Program will exit.\n");
        return 1;
    }

    if (numSeats > 20)
    {
        numSeats = 20;
        printf("Max num of seats is 20. Seats will be set to 20.\n");
    }

    CustomerRec *seats = (CustomerRec *)malloc(numSeats * sizeof(CustomerRec));
    if (seats == NULL)
    {
        printf("Failed to allocate memory for seats. Program will exit.\n");
        return 1;
    }

    // init seating data
    populateCustomerInfo(seats, numSeats);

    // function pointers array to menu options
    displayFunction functions[] = {
        printEmptySeats,
        printAssignedSeats,
        printSeats,
        assignSeat,
        deleteSeatAssignment};

    displayMenu(seats, numSeats, functions);

    printf("Thank you for using the Airlines Seating Reservation System. Goodbye!\n");
    free(seats);

    return 0;
}