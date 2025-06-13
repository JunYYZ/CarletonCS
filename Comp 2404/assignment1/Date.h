
#ifndef DATE_H
#define DATE_H

#include <iostream>
#include <string>
#include <iomanip>

using namespace std;

class Date
{

public:
	// constructor
	Date();
	Date(int year, int month, int day);
	Date(const Date &);

	// setters
	void setDay(int);
	void setMonth(int);
	void setYear(int);

	void setDate(int, int, int);
	void setDate(Date &);

	// getters
	int getDay();
	int getMonth();
	int getYear();
	string getMonthName();
	string getShortMonthName();

	// other
	bool isLeapYear(int year);

	void print();
	int convertToDays();

	bool equals(const Date &d);	// Check if two dates are equal
	bool lessThan(const Date &d); // Check if one date comes before another

private:
	// functions
	int getDaysInMonth();

	// variables
	int day;
	int month;
	int year;
};
#endif
