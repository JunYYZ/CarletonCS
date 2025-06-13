#include "HeapArrays.h"

HeapArrays::HeapArrays() : fileCount(0)
{
    objectArray = new File[MAX_ARRAY];
    pointerArray = new File *[MAX_ARRAY];

    for (int i = 0; i < MAX_ARRAY; ++i)
    {
        pointerArray[i] = nullptr;
    }
}

HeapArrays::~HeapArrays()
{
    for (int i = 0; i < MAX_ARRAY; ++i)
    {
        delete pointerArray[i];
    }

    delete[] objectArray;
    delete[] pointerArray;
}

File *HeapArrays::getObjectArray()
{
    return objectArray;
}

File **HeapArrays::getPointerArray()
{
    return pointerArray;
}

File *HeapArrays::getFilePointer(int index)
{
    if (index >= 0 && index < fileCount)
    {
        return pointerArray[index];
    }
    return nullptr;
}

File &HeapArrays::getFileReference(int index)
{
    return objectArray[index];
}

void HeapArrays::addFile(const File &file)
{
    if (fileCount < MAX_ARRAY)
    {
        objectArray[fileCount] = file;

        pointerArray[fileCount] = new File(file);

        fileCount++;
    }
}
