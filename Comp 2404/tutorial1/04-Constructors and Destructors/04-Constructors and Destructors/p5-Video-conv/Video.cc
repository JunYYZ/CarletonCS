#include <iostream>
using namespace std;
#include <string>
#include "Video.h"

Video::Video(string t, string a, int d)
{
  title        = t;
  artist       = a;
  length       = d;
  cout<<"-- Video default ctor:  "<< title <<endl;
}

Video::Video(Song s)
{
  title        = s.getTitle();
  artist       = s.getArtist();
  length       = 180;
  cout<<"-- Video conversion ctor:  "<< title <<endl;
}

Video::~Video()
{
  cout<<"-- Video dtor:  "<< title <<endl;
}

void Video::print()
{
  cout<<"** Video for \""<< title <<"\" by "<< artist << endl;
}

