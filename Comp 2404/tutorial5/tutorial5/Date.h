#ifndef DATE_H
#define DATE_H

#include <iostream>
#include <string>
#include <iomanip>

using namespace std;

class Date
{
public:
    // con
    Date();
    Date(int year, int month, int day);
    Date(Date &);
    Date(const Date &);

    void setDay(int);
    void setMonth(int);
    void setYear(int);
    void setDate(int, int, int);
    void setDate(Date &);

    int getDay() const;
    int getMonth() const;
    int getYear() const;
    string getMonthName() const;

    bool lessThan(const Date &d) const;
    bool equals(const Date &d) const;
    void incDate();
    void addDays(int);
    void print() const;

private:
    int getMaxDay() const;

    int day;
    int month;
    int year;
};
#endif
