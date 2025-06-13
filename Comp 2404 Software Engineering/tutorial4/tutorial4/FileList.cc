#include "FileList.h"
#include <iostream>

FileList::FileList() : numFiles(0) {}

FileList::~FileList() {
    for (int i = 0; i < numFiles; ++i) {
        delete files[i];
    }
}

bool FileList::add(File* file) {
    if (numFiles >= MAX_FILES) return false;
    
    // insert in sorted order using File's ;esstnan
    int i = numFiles - 1;
    while (i >= 0 && file->lessThan(*files[i])) {
        files[i + 1] = files[i];
        i--;
    }
    files[i + 1] = file;
    numFiles++;
    return true;
}

File* FileList::get(int index) {
    if (index < 0 || index >= numFiles) return nullptr;
    return files[index];
}

File* FileList::get(const std::string& name) {
    for (int i = 0; i < numFiles; ++i) {
        if (files[i]->getName() == name) {
            return files[i];
        }
    }
    return nullptr;
}

File* FileList::remove(int index) {
    if (index < 0 || index >= numFiles) return nullptr;
    File* removedFile = files[index];
    for (int i = index; i < numFiles - 1; ++i) {
        files[i] = files[i + 1];
    }
    numFiles--;
    return removedFile;
}

File* FileList::remove(const std::string& name) {
    for (int i = 0; i < numFiles; ++i) {
        if (files[i]->getName() == name) {
            return remove(i);
        }
    }
    return nullptr;
}

int FileList::size() {
    return numFiles;
}
