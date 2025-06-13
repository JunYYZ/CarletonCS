#include <istream>
#include <string>
#include <sstream>

#include "defs.h"
#include "Episode.h"
#include "Array.h"

using namespace std;

int testAddRemove();

int main(){
  
    return testAddRemove();    
}

int testAddRemove(){
    cout<<"Testing add and remove"<<endl;
    string podTitle = "The Podcast";
    string host = "The Host";
    string epTitles[10] = {"Episode 1", "Episode 2", "Episode 3", "Episode 4", "Episode 5", "Episode 6", "Episode 7", "Episode 8", "Episode 9", "Episode 10"};
    string category = "Category";
    string content = "Content";
    string video = "Video";

    Array<Episode*> arr;

    Episode* episodes[10];

    if (arr.getSize()!=0){
      cout<<"Error, getSize of empty array not 0"<<endl;
      return 1;
    }

    cout <<"Adding 10 episodes 7 days apart"<<endl;

    for (int i = 0; i < 10; ++i){
        episodes[i] = new Episode(podTitle, host, epTitles[i], category, content, video);
        arr+=episodes[i];
        cout<<*episodes[i]<<endl;
    }
    
    if (arr.getSize()!=10){
      cout<<"Error, getSize of array should be 10, instead it is "<<arr.getSize()<<endl;
      return 1;
    }else{
      cout<<"getSize is 10, correct"<<endl;
    }

    if (arr.isFull()){
      cout<<"Error, array (should be getSize 30) is full. getSize: "<<arr.getSize()<<endl;
      return 1;
    }else{
      cout<<"Array is not full, correct"<<endl;
    }

    cout<<"Remove every second episode:"<<endl;
    int count = 0;
    for (int i = 0; i < 10; i+=2){
        arr-=episodes[i];
    }

    if (arr.getSize()!=5){
      cout<<"Error, getSize of array should be 5, instead it is "<<arr.getSize()<<endl;
      return 1;
    }else{
      cout<<"getSize is 5, correct"<<endl;
    }

    for (int i = 0; i < 5; ++i){
        cout<<*arr[i]<<endl;
    }

    cout<<"Remove all but 2 episodes:"<<endl;
    for (int i = 0; i < 7; ++i){
        arr-=episodes[i];
    }

    cout<<"Printing 2 episodes:"<<endl;
    stringstream s1, s2;
    for (int i = 0; i < arr.getSize(); ++i){
        cout<<*arr[i]<<endl;
        s1<<*arr[i]<<endl;
    }

    s2 << *episodes[7] <<endl << *episodes[9] <<endl;

    if (s1.str() == s2.str()){
      cout<<"Episodes are correct"<<endl;
    }else{
      cout<<"Episodes are not correct:"<<endl<<s1.str()<<"Should be:"<<endl<<s2.str()<<endl;
      return 1;
    }

    
    cout <<"Deleting 10 episodes"<<endl;

    for (int i = 0; i < 10; ++i){
        delete episodes[i];
    }
    return 0;
}



