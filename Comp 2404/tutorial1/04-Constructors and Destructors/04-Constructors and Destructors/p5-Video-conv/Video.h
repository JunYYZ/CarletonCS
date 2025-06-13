#ifndef VIDEO_H
#define VIDEO_H

#include "Song.h"

class Video
{
  public:
    Video(string="", string="", int = 180);
    Video(Song);    
    //explicit Video(Song&);    
    ~Video();
    void print();

  private:
    string title;
    string artist;
    int length; //length in seconds
};

#endif
