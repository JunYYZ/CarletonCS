// The file contains the main function of the program
// The objective is to use these files to demonstrate usage of the make utility

// Header files
#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#include "person.h"

// Define
#define NUM_PEOPLE 7

int main(int argc, char *argv[])
{
  struct person people[NUM_PEOPLE];
  int i;

  // populate the array
  for (i = 0; i < NUM_PEOPLE; i++)
  {
    populate(&people[i]);
  }

  // print the array
  for (i = 0; i < NUM_PEOPLE; i++)
  {
    print(&people[i]);
  }

  return 0;
}
