#ifndef PERSON_HEADER
#define PERSON_HEADER

#define NAME_LENGTH 32
#define NUM_YEARS 5
#define BASE_SALARY 30000

struct personInfo {
  unsigned int id;
  unsigned short age;
  float salary[NUM_YEARS];
  char firstName[NAME_LENGTH];
  char familyName[NAME_LENGTH];
};

typedef struct personInfo personalInfo; 
void populatePerson(struct personInfo *p);
void printPerson(struct personInfo *p);

#endif
