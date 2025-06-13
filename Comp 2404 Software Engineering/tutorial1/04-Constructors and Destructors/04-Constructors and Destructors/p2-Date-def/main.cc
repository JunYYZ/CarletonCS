#include <iostream>
#include <string>
#include "Date.h"
#include "Calendar.h"

using namespace std;

int main()
{
  cout << endl;

  Date d1(2022,9,23);
  d1.print();
  cout<<endl;

  Date d2;
  d2.print();
  cout<<endl;

  Date d3(2022, 10);
  d3.print();
  cout<<endl;

  Date d4(2022);
  d4.print();
  cout<<endl;


  return 0;
}
