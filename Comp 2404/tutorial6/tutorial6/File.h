
#ifndef FILE_H
#define FILE_H

#include <iostream>
#include <string>
#include <iomanip>
#include "Date.h"

using namespace std;

class File
{

public:
	// constructor
	File(const string &name, const string &content, Date &date);
	File(const string& name, const string& content, const Date& date);

	// copy constructor
	File(File &);

	const string &getName()
	{
		return name;
	}

	bool lessThan(File &e);

	bool equals(File &e);

	// other
	void print();
	void printContents();

private:
	Date lastModified;
	string name;
	string content;
};
#endif
