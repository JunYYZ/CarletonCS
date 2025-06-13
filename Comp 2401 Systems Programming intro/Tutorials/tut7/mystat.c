// The file include function to compute basic stats related to person records 

// header files
#include <stdio.h>
#include <stdlib.h>

#include "mystat.h"

//compute the avarge value of of an array of floats.
void average(float *arr, int size, float *average)
{
  int i;
  *average = 0;

  for (i = 0; i < size; i++, arr++) {
    *average += *arr;
  }
  *average /= size;
}
