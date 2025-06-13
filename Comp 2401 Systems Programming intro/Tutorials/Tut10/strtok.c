#include <string.h>
#include <stdio.h>
#include <stdlib.h>

int main () {
   char str[] = "This is-a sample-string";
   char str2[] = "This,is,a sample,string";
   char *token;
   
   /* get the first token */
   token = strtok(str, "-");
   
   /* walk through other tokens */
   while( token != NULL ) {
      printf( "%s\n", token );
      token = strtok(NULL, "-");
   }

    return 0;
}
