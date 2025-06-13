#ifndef HEAPARRAYS_H
#define HEAPARRAYS_H

#include "File.h"
#include "defs.h"

class HeapArrays
{
private:
    File *objectArray;
    File **pointerArray;
    int fileCount;

public:
    // con
    HeapArrays();

    // des
    ~HeapArrays();

    // get
    File *getObjectArray();
    File **getPointerArray();
    File *getFilePointer(int index);
    File &getFileReference(int index);

    // func
    void addFile(const File &file);
};

#endif
