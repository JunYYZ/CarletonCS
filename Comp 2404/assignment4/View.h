
#ifndef VIEW_H
#define VIEW_H

#include <iostream>
#include <string>
#include <sstream>
#include <vector>
#include "defs.h"
#include "Location.h"

/*
Double dispatching means that the View knows about these classes,
and these classes know about the View. In C++ this is known as a
circular dependency. If we #include the .h files the compiler will
not be able to resolve these classes, since these classes are 
#include-ing the View class. So we must forward reference 
them. All the compiler needs to know is that these are classes. It 
knows a reference is 64 bits so it can allocated the necessary 
memory. 
*/

using namespace std;

class View {
		
	public:
		//constructor
		View();
		~View();
		
		void menu(vector<string>&, int& choice);
		void getName(string& name);
		void getRideInfo(Size& size, Location& l);
		// erase the old map and start fresh
		void refreshMap();
		// print out map to console
		void displayMap();

		/*
		A View object should know how to draw the following things. 
		How these are drawn is up to the individual View class.
		*/
		
		void drawBuilding(int x, int y);
		void drawDriver(int x, int y, char av);
		void drawCustomer(int x, int y, char av);

		// for test purposes, we will return the map
		char getMap(int x, int y) { return map[y][x]; }

		
	
	private:
		// Two dimensional array of characters for the map.
    	// There are extra rows and column because rows and   
    	// columns start and end with incomplete blocks.
		char map[MAX_Y*2+4][MAX_X*4+4];
		// to easily add digits to the map
    	const char digits[10] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};
		

	
};
#endif