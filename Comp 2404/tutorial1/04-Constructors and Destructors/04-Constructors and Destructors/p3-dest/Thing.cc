
#include "Thing.h"

Thing::Thing(int i, string s)
{
  id = i;
  location = s;
  cout<<"-- ctor:  "<< id <<" "<< location <<endl;
}

Thing::~Thing()
{
  cout<<"-- dtor:  "<< id <<" "<< location <<endl;
}

