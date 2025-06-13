
#include "View.h"
#include <sstream>
#include "Driver.h"
#include "Customer.h"

using namespace std;

View::View(){
   
}

View::~View(){
   
}

void View::menu(vector<string>& menu, int& choice){
cout << endl;
  cout << "Please make a selection:"<< endl<<endl;
  for (int i = 0; i < menu.size(); ++i){
    cout<<"  ("<<i+1<<") "<<menu[i]<<endl;
  }
  cout << "  (0) Exit" << endl<<endl;

  cout << "Enter your selection: ";
  cin >> choice;
  if (choice == 0)
    return;

  while (choice < 0 || choice > menu.size()) {
    cout << "Enter your selection: ";
    cin >> choice;
  }
}

void View::getName(string& name){
    cout<<"Enter your name: ";
    cin >> name;
    cout << endl;
}

void View::getRideInfo(Size& size, Location& l){
    cout<<"What size of car do you need?"<<endl;
    cout<<"(1) Small"<<endl;
    cout<<"(2) Medium"<<endl;
    cout<<"(3) Large"<<endl;
    int s = 0;
    cin>>s;
    size = (Size)(s-1);
    cout<<"What is your destination? Enter 2 numbers between 1 and 8"<<endl;
    cout<<"For example: 3 4"<<endl;
    cin>>l.x>>l.y;
    cout<<endl;
    if (l.x < 1) l.x = 1;
    if (l.x > MAX_X) l.x = MAX_X;
    if (l.y < 1) l.y = 1;
    if (l.y > MAX_Y) l.y = MAX_Y;
}

//      The map should look like this
//
//      MAX_X = 5, MAX_Y = 4
//
//      Driver A at location 1, 3
//      Customer B at location 1, 3
//      Buildings at every location 1, 1 up to 4, 4

//       I would be very careful of messing with the drawing code.
//
//       __1___2___3___4___5
//       1|  _   _ A _   _  |
//        | |_| |_| |B| |_| |
//       2|  _   _   _   _  |
//        | |_| |_| |_| |_| |
//       3|  _   _   _   _  |
//        | |_| |_| |_| |_| |
//       4|_________________|

void View::refreshMap(){

    // blank out the old map
    for (int i = 0; i < MAX_Y*2+4; ++i){
        for (int j = 0; j < MAX_X*4+4; ++j){
            map[i][j]=' ';
        }
    }

    //make the top row
    for (int i = 0; i < MAX_X*4+2; ++i){
        map[0][i]='_';
    }

    // add x-coordinates to top row
    int f;
    for (int j = 0; j < MAX_X; ++j){
        f = j+1;
        if (f > 9){
            map[0][(j)*4+2]=digits[(f/10)];
        }
        map[0][(j)*4+3]=digits[(f%10)];
    }

    
    // add y-coordinates to first column
    // and borders on first and last columns
    for (int i = 0; i < MAX_Y; ++i){
        f = i+1;
        if (f > 9){
            map[i*2+1][0] = digits[f/10];
        }
        map[i*2+1][1]=digits[f%10];
        map[i*2+1][2]='|';
        map[i*2][2]='|';
        map[i*2+1][MAX_X*4]='|';
        map[i*2][MAX_X*4]='|';
    }

    //make the bottom row
    for (int i = 1; i < MAX_X*4; ++i){
        map[MAX_Y*2-1][i+1]='_';
    }
    map[MAX_Y*2-1][2]='|';
    map[MAX_Y*2-1][MAX_X*4]='|';

    for (int i = 1; i < MAX_Y; ++i){
        for (int j = 1; j < MAX_X; ++j){
            drawBuilding(j, i);
        }
    }

}


void View::drawDriver(int x, int y, char av){
    map[y*2-1][x*4-1]= av;
}

void View::drawCustomer(int x, int y, char av){
    map[y*2][x*4+1]= av;
}

void View::drawBuilding(int x, int y){
    map[y*2][x*4]='|';
    map[y*2-1][x*4+1]='_';
    map[y*2][x*4+1]='_';
    map[y*2][x*4+2]='|';
}

void View::displayMap(){
    cout<<endl;
    for (int i = 0; i < MAX_Y*2+2; ++i){
        for (int j = 0; j < MAX_X*4+4; ++j){
            cout<<map[i][j];
        }
        cout<<endl;
    }
}



