#include "RepoList.h"
#include <iostream>

RepoList::RepoList() : numRepos(0) {}

RepoList::~RepoList()
{
    for (int i = 0; i < numRepos; ++i)
    {
        delete repos[i];
    }
}

bool RepoList::add(Repo *repo)
{
    if (numRepos >= MAX_ARRAY)
        return false;
    repos[numRepos] = repo;
    numRepos++;
    return true;
}

Repo *RepoList::get(const int& index)
{
    if (index < 0 || index >= numRepos)
        return nullptr;
    return repos[index];
}

Repo *RepoList::get(const std::string &title)
{
    for (int i = 0; i < numRepos; ++i)
    {
        if (repos[i]->getTitle() == title)
        {
            return repos[i];
        }
    }
    return nullptr;
}

Repo *RepoList::remove(const int& index)
{
    if (index < 0 || index >= numRepos)
        return nullptr;
    Repo *removedRepo = repos[index];
    for (int i = index; i < numRepos - 1; ++i)
    {
        repos[i] = repos[i + 1];
    }
    numRepos--;
    return removedRepo;
}

Repo *RepoList::remove(const std::string &title)
{
    for (int i = 0; i < numRepos; ++i)
    {
        if (repos[i]->getTitle() == title)
        {
            return remove(i);
        }
    }
    return nullptr;
}

int RepoList::size()
{
    return numRepos;
}
