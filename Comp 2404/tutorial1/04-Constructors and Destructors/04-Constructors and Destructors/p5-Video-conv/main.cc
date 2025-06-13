#include <iostream>
using namespace std;

#include "Song.h"
#include "Video.h"


void videoArgumentbyValue(Video);
Video returnByValue();


int main()
{
  Song s1(1, "Lounge Act", "Nirvana");
  Song s2(2, "Pain of Infinity", "The Dirty Nil");
  Song s3(3, "Black Sheep", "Metric");
  Song s4(4, "Merry Go Round", "Motley Crue");

  cout << endl << "Return by value ..."<<endl;
  returnByValue();

  cout << endl << "Creating video 1..." << endl;
  Video v1("Everlong", "Foo Fighters", 226);
  v1.print();

  cout << endl << "Creating video 2 from song 2..." << endl;
  Video v2(s2); // this is an explicit call to the conversion ctor
  v2.print();

  cout << endl << "Creating video 3 from song 3..." << endl;
  Video v3 = s3; // initialization is an implicit call to conversion ctor
  v3.print();

  cout << endl << "Calling videoArgumentbyValue()..." << endl;
  videoArgumentbyValue(s4);  // pass by value is an implicit call to conversion ctor

  cout << endl << "Assigning video 1 to video 2..." << endl;
  v2 = v1;
  v2.print(); 

  /*
  * don't forget the 'explicit' keyword
  */

  cout<<"Danger!! This could have unexpected consequences!"<<endl<<endl;

  Song s5 = 5; //implicit conversion from int to Song
  s5.print();
  cout << endl << "End of program" << endl;
  return 0;
}

void videoArgumentbyValue(Video v)
{
  v.print();
}

Video returnByValue(){
  Song s1(1, "Lounge Act", "Nirvana");
  return s1;
}

