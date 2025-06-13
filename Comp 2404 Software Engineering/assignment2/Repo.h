#ifndef REPO_H
#define REPO_H

#include "FileList.h"
#include <string>

class Repo
{
public:
    Repo(const std::string &title, const std::string &owner);
    ~Repo();

    bool lessThan(const Repo &other);
    bool addFile(const std::string &name, const std::string &content, const Date &date);
    bool removeFile(const int& index);
    int getNumFiles() const;
    void print() const;
    void printFiles() const;
    void printContents(const int& index) const;

    std::string getTitle() const;

private:
    std::string title;
    std::string owner;
    FileList *fileList; // managing collection of File objects
};

#endif
