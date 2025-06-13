#ifndef STACKARRAYS_H
#define STACKARRAYS_H

#include "File.h"
#include "defs.h"

class StackArrays
{
private:
    File objectArray[MAX_ARRAY];
    File *pointerArray[MAX_ARRAY];
    int fileCount;

public:
    // con
    StackArrays();

    // des
    ~StackArrays();

    // get
    File *getObjectArray();
    File **getPointerArray();
    File *getFilePointer(int index);
    File &getFileReference(int index);

    // func
    void addFile(const File &file);
};

#endif
