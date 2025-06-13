#include <istream>
#include <string>

#include "File.h"



/*
* NOTE: If you did not const properly, this will not compile.
*/
int main(){

  cout<<endl<<"Testing const"<<endl<<endl;
  const File f("test", "test", Date(2020,1,1));
  f.printContents();
  f.print();

  const File f2(f);

  const File f3("test2", "test2", Date(2020,1,1));
  f.equals(f2);
  f.equals(f3);
  f.equals(f);
  f.lessThan(f);
  f.lessThan(f2);
  f.lessThan(f3);
  
}

