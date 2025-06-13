#ifndef SONG_H
#define SONG_H

#include <string>
using namespace std;

class Song
{
  public:
    Song(int=0, string="Unknown", string="Unknown");
    Song(Song&);
    ~Song();
    string getTitle();
    string getArtist();
    void print();

  private:
    int id;
    string title;
    string artist;
};

#endif
