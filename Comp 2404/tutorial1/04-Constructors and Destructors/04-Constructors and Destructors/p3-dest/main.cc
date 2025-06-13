#include <iostream>
#include <cstdlib>
#include <string>
#include "Thing.h"

using namespace std;

void func();

Thing t1(1,"first global");
Thing t2(2,"second global");
Thing t10(10,"third-ish global");

int main()
{
  cout<<endl<<"In main..."<<endl;

  Thing t3(3, "first local in main");
  cout<<"Calling func()"<<endl;
  func();
  cout<<"Back from func()"<<endl;
  

  // cout<<"Calling func() again"<<endl;
  // func();
  // cout<<"Back from func() again"<<endl;


  cout<<"*** emergency exit"<<endl<<endl;
  exit(1);
  //abort();

  cout<<"\"Then he shut up the Things in the box with a hook\nAnd the cat went away with a sad kind of look\""<<endl<<endl;

  return 0;
}

Thing t3(76,"third global");


void func()
{
  cout<<"In func()"<<endl;
  Thing t5(5, "first local in func");
  Thing t6(6, "second local in func");
  cout<<"leaving func()"<<endl;
}

