#include <istream>
#include <string>

#include "HeapArrays.h"
#include "StackArrays.h"
#include "defs.h"


bool testStackObjectArray();
bool testStackPointerArray();
bool testHeapObjectArray();
bool testHeapPointerArray();
// bool constTest();

extern const string content[12];
extern const Date dates[12];

int main(){

    int choice = 0;
    cout<<"Enter one of the following options: "<<endl;
    cout<<"0. Test StackArrays::getObjectArray"<<endl;
    cout<<"1. Test StackArrays::getPointerArray"<<endl;
    cout<<"2. Test HeapArrays::getObjectArray"<<endl;
    cout<<"3. Test HeapArrays::getPointerArray"<<endl;
    // cout<<"4. Test const"<<endl;
    cin >> choice;
    bool flag = true;
    switch(choice){
      case 0: flag = testStackObjectArray(); break;
      case 1: flag = testStackPointerArray(); break;
      case 2: flag = testHeapObjectArray(); break;
      case 3: flag = testHeapPointerArray(); break;
      // case 4: flag = constTest(); break;
    }
    
    
    if (flag){
      return 0;
    }else{
      return 1;
    }
  
}



bool testHeapObjectArray(){
  HeapArrays ha;
  string title;
  cout<<"Enter 3 titles (separated by spaces): "<<endl;
  for (int i = 0; i < 3; ++i){
      cin >> title;
      File file(title, content[i], dates[i]);
      ha.addFile(file);
  }
  File* file = ha.getObjectArray();
  for (int i = 0; i < MAX_ARRAY; ++i){
    // ha.getFileReference(i).printContents();
    file[i].printContents();
    if (&file[i]!=&ha.getFileReference(i)){
      cout<<"Wrong object returned by getFileReference"<<endl;
      return false;
    }
  }
  cout<<"testHeapObjectArray passed"<<endl;
  return true;
}

bool testHeapPointerArray(){
  HeapArrays ha;
  string title;
  cout<<"Enter 3 titles (separated by spaces): "<<endl;

  for (int i = 0; i < 3; ++i){
      cin >> title;
      File file(title, content[i], dates[i]);
      ha.addFile(file);
  }
  File** file = ha.getPointerArray();
  for (int i = 0; i < MAX_ARRAY; ++i){
    //ha.getFilePointer(i)->printContents();
    (*file[i]).printContents();
    if (file[i]!=ha.getFilePointer(i)){
      cout<<"Wrong object returned by getFilePointer"<<endl;
      return false;
    }
  }
  cout<<"testHeapPointerArray passed"<<endl;
  return true;
}

bool testStackObjectArray(){
  StackArrays sa;
  string title;
  cout<<"Enter 3 titles (separated by spaces): "<<endl;
  for (int i = 0; i < MAX_ARRAY; ++i){
      cin >> title;
      File file(title, content[i], dates[i]);
      sa.addFile(file);
  }
  File* file = sa.getObjectArray();
  for (int i = 0; i < MAX_ARRAY; ++i){
    //sa.getFileReference(i).printContents();
    file[i].printContents();
    if (&file[i]!=&sa.getFileReference(i)){
      cout<<"Wrong object returned by getFileReference"<<endl;
      return false;
    }
  }
  cout<<"testStackObjectArray passed"<<endl;
  return true;
}

bool testStackPointerArray(){
  StackArrays sa;
  string title;
  cout<<"Enter 3 titles (separated by spaces): "<<endl;
  for (int i = 0; i < MAX_ARRAY; ++i){
      cin >> title;
      File file(title, content[i], dates[i]);
      sa.addFile(file);
  }
  File** file = sa.getPointerArray();
  for (int i = 0; i < MAX_ARRAY; ++i){
    //sa.getFilePointer(i)->printContents();
    (*file[i]).printContents();
    if (file[i]!=sa.getFilePointer(i)){
      cout<<"Wrong object returned by getFilePointer"<<endl;
      return false;
    }
  }
  cout<<"testStackPointerArray passed"<<endl;
  return true;
}

const Date dates[12] = {
  Date(1, 1, 2020),
  Date(2, 2, 2020),
  Date(3, 3, 2020),
  Date(4, 4, 2020),
  Date(5, 5, 2020),
  Date(6, 6, 2020),
  Date(7, 7, 2020),
  Date(8, 8, 2020),
  Date(9, 9, 2020),
  Date(10, 10, 2020),
  Date(11, 11, 2020),
  Date(12, 12, 2020)
};

const string content[12] = {
R"(picture
  ___
 / _ \
| (_) |
 \___/
)",

R"(picture
  ,--.
 /  0 \
|   .  |
 \___/
)",

R"(picture
  ,--.
 /  0 \
|   |  |
 \___/
)",

R"(
  ,--.
 /  0 \
|   |  |
 \__O/
)",

R"(
   /\
  /  \
 |    |
 |----|
 |    |
 |----|
)",

R"(
   /\
  /  \
 | [] |
 |----|
 |    |
 |----|
)",

R"(
 ______
/ 0  0 \
|    ∆   |
 \_____/
)",

R"(
  ____
 /  00\
|    0 |
|______|
)",

R"(
   ,--.
  / 00\
 |  |  |
  \__/
)",

R"(
  ,--,
 /    \
|  ☏   |
 \    /
  '--'
)",

R"(
   ,--.
  / 00\
 |  |  |
  \__/
)",

R"(
  ,--,
 /    \
|  ☏   |
 \    /
  '--'
)"
};