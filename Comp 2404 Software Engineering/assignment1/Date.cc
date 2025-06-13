
#include "Date.h"

Date::Date()
{
	setDate(1901, 1, 1);
}

Date::Date(int y, int m, int d)
{
	setDate(y, m, d);
}

Date::Date(const Date &d)
{
	setDate(d.year, d.month, d.day);
}

// setters
void Date::setDay(int d)
{
	int max = getDaysInMonth();
	if (d > max)
		d = max;
	if (d < 1)
		d = 1;
	day = d;
}

void Date::setMonth(int m)
{
	if (m > 12)
		m = 12;
	if (m < 1)
		m = 1;
	month = m;
}

void Date::setYear(int y)
{
	if (y < 1901)
		y = 1901;
	year = y;
}

void Date::setDate(int y, int m, int d)
{
	setMonth(m);
	setDay(d);
	setYear(y);
}

void Date::setDate(Date &d)
{
	setDate(d.year, d.month, d.day);
}

// getters
int Date::getDay() { return day; }
int Date::getMonth() { return month; }
int Date::getYear() { return year; }

string Date::getMonthName()
{
	string months[12] = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
	return months[month];
}

string Date::getShortMonthName()
{
	string smonths[12] = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
	return smonths[month];
}

// other

bool Date::lessThan(const Date &d)
{
	if (year < d.year)
		return true;
	if (month < d.month)
		return true;
	return day < d.day;
}

bool Date::equals(const Date &d)
{
	return year == d.year && month == d.month && day == d.day;
}

bool Date::isLeapYear(int year)
{
	if (year % 4 != 0)
		return false;
	if (year % 100 != 0)
		return true;
	return year % 400 == 0;
}

int Date::convertToDays()
{
	int total = 0;
	for (int i = 1901; i < year; i++)
	{
		total += 365;
		if (isLeapYear(i))
			total++;
	}
	for (int i = 1; i < month; i++)
	{
		total += getDaysInMonth();
	}
	total += day;
	return total;
}

// int Date::daysUntil(Date& d){
// 	return d.convertToDays() - convertToDays();
// }

void Date::print()
{
	cout << getMonthName() << " " << getDay() << ", " << getYear() << endl;
}

int Date::getDaysInMonth()
{
	switch (getMonth())
	{
	case 4:
	case 6:
	case 9:
	case 11:
		return 30;
	case 2:
		return 28;
	default:
		return 31;
	}
}
