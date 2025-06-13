#include "File.h"

// File::File(const string& name, const string& content,  Date& date): lastModified(date), name(name), content(content) {}

File::File(const string &name, const string &content, const Date &date) : lastModified(date), name(name), content(content) {}

File::File(File &e) : File(e.name, e.content, e.lastModified) {}

File::~File() {}

bool File::lessThan(const File &other) const
{
    if (name < other.name)
    {
        return true;
    }
    else if (name == other.name)
    {
        return lastModified.lessThan(other.lastModified);
    }
    else
    {
        return false;
    }
}

bool File::equals(const File &e) const
{
    return (name == e.name) && (lastModified.equals(e.lastModified));
}

void File::print() const
{
    cout << "File:          " << name << "Last modified: " << endl;
    lastModified.print();
    cout << endl;
}

void File::printContents()
{
    print();
    cout << "Content: " << content << endl;
}
