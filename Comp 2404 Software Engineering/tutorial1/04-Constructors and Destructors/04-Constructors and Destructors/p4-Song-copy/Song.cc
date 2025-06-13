#include <iostream>
#include <string>
using namespace std;
#include "Song.h"

Song::Song(int i, string t, string a)
{
  id     = i;
  title  = t;
  artist = a;
  cout<<"-- Song default ctor:  "<< id <<endl;
}

//we are not plagiarizing, we are "inspired by"
Song::Song(Song& oldSong)
{
  id     = oldSong.id;
  title  = oldSong.title;
  artist = oldSong.artist;
  cout<<"-- Song COPY ctor:  "<< id <<": "<<title <<endl;
}


Song::~Song()
{
  cout<<"-- Song dtor:  "<< id <<endl;
}

void Song::print()
{
  cout<<"\""<< title <<"\" by "<<artist<<endl;
}