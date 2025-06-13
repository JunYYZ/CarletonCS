#ifndef ARRAY_H
#define ARRAY_H

#include <iostream>
#include <string>
#include <iomanip>
#include <cstdlib>
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
	void clear();
	void add(string t);
	string remove(int index);
	string get(int index) const;
	int getSize() const;
	bool isFull() const;

private:
	int numElements;
	string *elements;
};

List::List()
{
	elements = new string[MAX_ARRAY];
	numElements = 0;
}

List::~List()
{
	delete[] elements;
}

void List::add(string t)
{
	if (numElements >= MAX_ARRAY)
	{
		cerr << "List is full" << endl;
		exit(1);
	}
	int i = numElements - 1;
	while (i >= 0 && elements[i].compare(t) > 0)
	{
		elements[i + 1] = elements[i];
		--i;
	}
	elements[i + 1] = t;
	++numElements;
}

string List::remove(int index)
{

	string temp = elements[index];

	while (index < numElements)
	{
		elements[index] = elements[index + 1];
		++index;
	}
	return temp;
}

int List::getSize() const
{
	return numElements;
}

bool List::isFull() const
{
	return numElements >= MAX_ARRAY;
}

string List::get(int index) const
{
	if (index < 0 || index >= numElements)
	{
		cerr << "List index out of bounds" << endl;
		exit(1);
	}
	return elements[index];
}

void List::clear()
{
	numElements = 0;
}

#endif