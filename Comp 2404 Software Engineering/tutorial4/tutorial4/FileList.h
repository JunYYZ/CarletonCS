#ifndef FILELIST_H
#define FILELIST_H

#include "File.h"
#include "defs.h"

class FileList {
public:
    FileList();
    ~FileList();

    bool add(File* file);
    File* get(int index);
    File* get(const std::string& name);
    File* remove(int index);
    File* remove(const std::string& name);
    int size();

private:
    File* files[MAX_FILES];
    int numFiles;
};

#endif
