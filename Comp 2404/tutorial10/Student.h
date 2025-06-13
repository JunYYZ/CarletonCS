#ifndef STUDENT_H
#define STUDENT_H

#include <iostream>
#include <ostream>

using namespace std;

class TestClass;
class Algorithms;

class Student
{
  friend class TestClass;
  friend class Algorithms;
  friend ostream& operator<<(ostream&, const Student&);
  public:
    Student(string="000000000", string="Hey you!",  float=0.0f);
    Student(const Student&);
    
    bool operator==(const Student&) const;
    

  private:
    string  number;
    string  name;
    float   gpa;
};

#endif
