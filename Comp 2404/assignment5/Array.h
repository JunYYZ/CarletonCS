#ifndef ARRAY_H
#define ARRAY_H

#include <iostream>
#include <string>
#include <iomanip>
#include <cstdlib>
#include "defs.h"

template <typename T>
class Array
{
private:
	int numElements;
	T *elements;

public:
	static const int MAX_SIZE = 256;

	// con
	Array() : numElements(0)
	{
		elements = new T[MAX_SIZE];
	}

	// des
	~Array()
	{
		delete[] elements;
	}

	Array &operator+=(const T &item)
	{
		if (numElements >= MAX_SIZE)
		{
			std::cerr << "Array is full" << std::endl;
			return *this;
		}
		elements[numElements++] = item;
		return *this;
	}

	Array &operator-=(const T &item)
	{
		int index = 0;
		while (index < numElements && elements[index] != item)
		{
			++index;
		}
		if (index == numElements)
			return *this;
		--numElements;
		for (int i = index; i < numElements; ++i)
		{
			elements[i] = elements[i + 1];
		}
		return *this;
	}

	T &operator[](int index)
	{
		if (index < 0 || index >= numElements)
		{
			std::cerr << "Array index out of bounds" << std::endl;
			exit(1);
		}
		return elements[index];
	}

	const T &operator[](int index) const
	{
		if (index < 0 || index >= numElements)
		{
			std::cerr << "Array index out of bounds" << std::endl;
			exit(1);
		}
		return elements[index];
	}

	int getSize() const
	{
		return numElements;
	}

	void clear()
	{
		numElements = 0;
	}

	bool isFull() const
	{
		return numElements >= MAX_SIZE;
	}
};

#endif
