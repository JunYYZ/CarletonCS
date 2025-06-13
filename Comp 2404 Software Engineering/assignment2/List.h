
#ifndef LIST_H
#define LIST_H

#include <iostream>
#include <string>
#include <iomanip>
#include "defs.h"

using namespace std;

class List
{

public:
	// constructor
	List();

	// destructor
	~List();

	// other
	bool add(int);
	int get(int);
	int remove(int);
	int size();
	bool isFull();

private:
	int numInts;
	int *list;
};
#endif
