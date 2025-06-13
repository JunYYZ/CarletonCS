#include "StackArrays.h"

StackArrays::StackArrays() : fileCount(0)
{
    for (int i = 0; i < MAX_ARRAY; ++i)
    {
        pointerArray[i] = nullptr;
    }
}

StackArrays::~StackArrays()
{
    for (int i = 0; i < MAX_ARRAY; ++i)
    {
        delete pointerArray[i];
    }
}

File *StackArrays::getObjectArray()
{
    return objectArray;
}

File **StackArrays::getPointerArray()
{
    return pointerArray;
}

File *StackArrays::getFilePointer(int index)
{
    if (index >= 0 && index < fileCount)
    {
        return pointerArray[index];
    }
    return nullptr;
}

File &StackArrays::getFileReference(int index)
{
    return objectArray[index];
}

void StackArrays::addFile(const File &file)
{
    if (fileCount < MAX_ARRAY)
    {
        objectArray[fileCount] = file;

        pointerArray[fileCount] = new File(file);

        fileCount++;
    }
}
