#include "File.h"
#include <iostream>
using namespace std;

File::File(string name, string content, const Date &date)
    : name(name), content(content), lmod(date) {}

bool File::lessThan(File &other)
{
    return lmod.lessThan(other.lmod);
}

void File::print()
{
    std::cout << "File: " << name << " | Last Modified: ";
    lmod.print();
    std::cout << std::endl;
}

void File::printContents()
{
    print();
    cout << "Content: " << content << endl;
}

string File::getName() const
{
    return this->name;
}