#ifndef GETHUB_H
#define GETHUB_H

#include "RepoList.h"

class GetHub
{
public:
    GetHub();
    ~GetHub();

    // repo-related functions
    void addRepo(const std::string &title, const std::string &owner);
    void deleteRepo(const int& index);
    int getNumRepos() const;
    bool download(const int& index, Repo **repo);

    // file-related functions
    // bool addFile(int repoIndex, const std::string& name, const std::string& content, Date& date);
    bool addFile(const std::string &repoTitle, const std::string &name, const std::string &content, const Date &date);
    void deleteFile(const int& repoIndex, const int& fileIndex);

    // print functions
    void printRepos() const;
    void printRepo(const int& index) const;
    void printFileContents(const int& repoIndex, const int& fileIndex) const;

private:
    RepoList *repoList; // dynamically allocated RepoList to store Repo objects
};

#endif
