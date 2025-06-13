#ifndef LIST_H
#define LIST_H

#include <iostream>
#include <cstdlib>
#include <string>
#include "defs.h"

template <typename T>
class List
{
public:
	// Constructor and Destructor
	List(int (*cmp)(T, T));
	~List();

	// List Operations
	void clear();
	void add(T t);
	T remove(int index);
	T get(int index) const;
	int getSize() const;
	bool isFull() const;

private:
	int numElements;
	T *elements;
	int (*compare)(T, T);
};

// Template definitions

template <typename T>
List<T>::List(int (*cmp)(T, T)) : compare(cmp), numElements(0)
{
	elements = new T[MAX_ARRAY]; // Allocate the array dynamically
}

template <typename T>
List<T>::~List()
{
	delete[] elements;
}

template <typename T>
void List<T>::add(T t)
{
	if (numElements >= MAX_ARRAY)
	{
		std::cerr << "List is full" << std::endl;
		exit(1);
	}
	int i = numElements - 1;
	while (i >= 0 && compare(elements[i], t) > 0)
	{
		elements[i + 1] = elements[i];
		--i;
	}
	elements[i + 1] = t;
	++numElements;
}

template <typename T>
T List<T>::remove(int index)
{
	T temp = elements[index];
	while (index < numElements - 1)
	{
		elements[index] = elements[index + 1];
		++index;
	}
	--numElements;
	return temp;
}

template <typename T>
T List<T>::get(int index) const
{
	if (index < 0 || index >= numElements)
	{
		std::cerr << "List index out of bounds" << std::endl;
		exit(1);
	}
	return elements[index];
}

template <typename T>
int List<T>::getSize() const
{
	return numElements;
}

template <typename T>
bool List<T>::isFull() const
{
	return numElements >= MAX_ARRAY;
}

template <typename T>
void List<T>::clear()
{
	numElements = 0;
}

#endif // LIST_H
