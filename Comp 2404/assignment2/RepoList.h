#ifndef REPOLIST_H
#define REPOLIST_H

#include "Repo.h"
#include "defs.h"

class RepoList
{

public:
    RepoList();
    ~RepoList();

    bool add(Repo *repo);
    Repo *get(const int& index);
    Repo *get(const std::string &title);
    Repo *remove(const int& index);
    Repo *remove(const std::string &title);
    int size();

private:
    Repo *repos[MAX_ARRAY];
    int numRepos;
};

#endif
