#ifndef LIST_H
#define LIST_H

#include <iostream>
#include <cstdlib>
#include "defs.h"

// Define a templated List class
template <typename T>
class List
{
public:
	// Constructor with comparison function pointer
	List(int (*compFunc)(T, T));

	// Destructor
	~List();

	// Methods
	void add(T element);	// Add element in sorted order
	T remove(int index);	// Remove element at index
	T get(int index) const; // Get element at index
	int getSize() const;	// Get the number of elements
	bool isFull() const;	// Check if the list is full
	void clear();			// Clear all elements in the list

private:
	int numElements;	  // Number of elements in the list
	T *elements;		  // Array of elements
	int (*compare)(T, T); // Comparison function pointer
};

// Include the implementation file to support templates
// Constructor with comparison function pointer
template <typename T>
List<T>::List(int (*compFunc)(T, T)) : numElements(0), compare(compFunc)
{
	elements = new T[MAX_ARRAY];
}

// Destructor
template <typename T>
List<T>::~List()
{
	delete[] elements;
}

// Add function with sorted insertion
template <typename T>
void List<T>::add(T element)
{
	if (isFull())
	{
		std::cerr << "List is full" << std::endl;
		exit(1);
	}
	int i = numElements - 1;
	while (i >= 0 && compare(elements[i], element) > 0)
	{
		elements[i + 1] = elements[i];
		--i;
	}
	elements[i + 1] = element;
	++numElements;
}

// Remove element at index and shift elements
template <typename T>
T List<T>::remove(int index)
{
	if (index < 0 || index >= numElements)
	{
		std::cerr << "List index out of bounds" << std::endl;
		exit(1);
	}
	T temp = elements[index];
	for (int i = index; i < numElements - 1; ++i)
	{
		elements[i] = elements[i + 1];
	}
	--numElements;
	return temp;
}

// Get element at index
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

// Get the number of elements
template <typename T>
int List<T>::getSize() const
{
	return numElements;
}

// Check if the list is full
template <typename T>
bool List<T>::isFull() const
{
	return numElements >= MAX_ARRAY;
}

// Clear all elements in the list
template <typename T>
void List<T>::clear()
{
	numElements = 0;
}

// Explicit template instantiations
template class List<int>; // example
// Add additional explicit instantiations if needed for Customer* and Driver*

#endif // LIST_H
