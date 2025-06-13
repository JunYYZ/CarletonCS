#ifndef FILELIST_H
#define FILELIST_H

#include "File.h"
#include "defs.h"

class FileList
{
public:
    FileList();
    ~FileList();

    bool add(File *file);
    File *get(const int& index);
    File *get(const std::string &name);
    File *remove(const int& index);
    File *remove(const std::string &name);
    int size();

private:
    File *files[MAX_ARRAY];
    int numFiles;
};

#endif
