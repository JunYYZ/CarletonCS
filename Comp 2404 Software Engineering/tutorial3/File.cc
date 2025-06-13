#include "File.h"
#include <iostream>
using namespace std;

File::File(string name, string content, Date& date)
    : name(name), content(content), lmod(date) {}

bool File::lessThan(File& other) {
    return lmod.lessThan(other.lmod);
}

void File::print() {
    cout << "File: " << name << " | Last Modified: ";
    lmod.print();
    cout << endl;
}


void File::printContents() {
    print();  // reuse print function for metadata
    cout << "Content: " << content << endl;
}
