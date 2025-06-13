#include "File.h"
#include <iostream>
using namespace std;

File::File() : name("Untitled"), content("No content"), lmod(Date(1901, 1, 1)) {}

File::File(string name, string content, const Date &date)
    : name(name), content(content), lmod(date) {}

File::File(const File &other) : name(other.name), lmod(other.lmod)
{
    // Instead of copying the content, set a custom RCMP message
    content = "This content has been replaced by the RCMP for violating copyright infringement rules.";
}

bool File::lessThan(const File &other) const
{
    return lmod.lessThan(other.lmod);
}

bool File::equals(const File &other) const
{
    return name == other.name && content == other.content && lmod.equals(other.lmod);
}

void File::print() const
{
    std::cout << "File: " << name << " | Last Modified: ";
    lmod.print();
    std::cout << std::endl;
}

void File::printContents() const
{
    print();
    cout << "Content: " << content << endl;
}

string File::getName() const
{
    return this->name;
}
