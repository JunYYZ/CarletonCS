#ifndef FILE_H
#define FILE_H

#include <string>
#include "Date.h"
using namespace std;

class File
{
private:
    string name;
    string content;
    Date lmod;

public:
    // con
    File();
    File(string name, string content, const Date &date);
    File(const File &other);

    bool lessThan(const File &other) const;
    bool equals(const File &other) const;

    void print() const;
    void printContents() const;
    string getName() const;
};

#endif
