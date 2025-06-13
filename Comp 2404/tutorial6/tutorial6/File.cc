#include "File.h"

File::File(const string& name, const string& content,  Date& date): lastModified(date), name(name), content(content) {}

File::File(const string& name, const string& content, const Date& date): lastModified(date), name(name), content(content) {}

File::File( File& e): File( e.name, e.content, e.lastModified){  }

void File::print() {
	cout <<"File:          "<<name<<endl;
	cout <<"Last modified: ";
	lastModified.print(); cout<<endl;
}

bool File::lessThan( File& e) {
    if (name < e.name) {
        return true;
    } else if (name == e.name) {
        return lastModified.lessThan(e.lastModified);
    }
    return false;
}

bool File::equals(File& e){
    return (name == e.name) && (!lastModified.lessThan(e.lastModified) && !e.lastModified.lessThan(lastModified));
}

void File::printContents() {
	print();
	cout <<endl<< content<<endl<<endl;
}
