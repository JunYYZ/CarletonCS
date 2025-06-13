#ifndef FILE_H
#define FILE_H

#include <string>
#include "Date.h"
using namespace std;

class File {
    private:
        string name;
        string content;
        Date lmod;

    public:
        // constructor
        File(string name, string content, Date& date);

        // comparison function for dates
        bool lessThan(File& other);

        // print functions
        void print();
        void printContents();
};

#endif
