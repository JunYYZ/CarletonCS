#ifndef THING_H
#define THING_H

#include <iostream>
#include <string>

using namespace std;

class Thing
{
  public:
    Thing(int=0, string="");
    ~Thing();

  private:
    int id;
    string location;
};

#endif
