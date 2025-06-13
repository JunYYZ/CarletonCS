
/*************************************************************/

// INCLUDES 
//
#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#include "person.h"

/*************************************************************/

// prototypes 

int fexists(char *fileName);
void readFile(FILE *fid);

/*************************************************************/

 
int main(int argc, char *argv[]) {

  FILE *fid;
  int rc=0;


  // TODO: add work also goes in this function.
  
  // check the number of command line parmeters 
  // if incorrect number print message and exits
  if (argc < 2) {
      printf("usage %s filename \n",argv[0]);
      return(1);
  }

  // check if file exists
  rc = fexists(argv[1]);

  // print message depending whether the file exists


  return 0;
}


/*********************************************************************/
// Return whether the given file exists in the current directory.
// 
// Input:
// fileName - name of fle
//
// Output:
// None
//
// Return:
// 0 if the file does not exist
// 1 if the file exists
//
int fexists(char *fileName)
{
    // add code

    FILE *fp = NULL;
    int rc = 0;

	// open the file

 	// determine the recturn code
 

  // if applicable then close the file

    return(rc);
}




/*********************************************************************/
// Read the content of the file 
// 
// Input:
// fid - file handle 
//
// Output:
// None
//
// Return:
// 0 if the file does not exist
// 1 if the file exists
//
void readFile(FILE *fid)
{
    // add code

	// while not end of file

	// read a values in a line

	// print the values to the screen 

}
