
#include "List.h"

List::List()
{
	numInts = 0;
	list = new int[MAX_ARRAY];
}

List::~List()
{
	delete[] list;
}

bool List::add(int p)
{
	if (numInts >= MAX_ARRAY)
		return false;
	for (int i = numInts; i > 0; --i)
	{
		if (p < list[i - 1])
		{
			list[i] = list[i - 1];
		}
		else
		{
			list[i] = p;
			++numInts;
			return true;
		}
	}
	// if we get here, p is the smallest element
	list[0] = p;
	++numInts;
	return true;
}

int List::remove(int index)
{
	if (index < 0 || index >= numInts)
		return -1;
	int goner = list[index];
	for (int i = index; i < numInts - 1; ++i)
	{
		list[i] = list[i + 1];
	}
	--numInts;
	return goner;
}

int List::get(int index)
{
	if (index < 0 || index >= numInts)
		return -1;
	return list[index];
}

bool List::isFull()
{
	return numInts >= MAX_ARRAY;
}

int List::size()
{
	return numInts;
}
