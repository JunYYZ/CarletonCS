#include <stdio.h>
#include <math.h>

#define PI 3.14159265

int main() {
  double  angle = 90;  // in degrees

  printf("sin(90):  %f\n", sin(PI*angle/180));
  printf("cos(90):  %f\n", cos(PI*angle/180));
  printf("tan(90):  %f\n", tan(PI*angle/180));
  printf("ceil(235.435):  %g\n", ceil(235.435));
  printf("floor(235.435):  %g\n", floor(235.435));
  printf("pow(2, 8):  %g\n", pow(2,8));
  printf("fabs(-15):  %g\n", fabs(-15));
}