#include "RepoList.h"
#include <iostream>

RepoList::RepoList() : head(nullptr), tail(nullptr), numRepos(0) {}

RepoList::RepoList(const RepoList &other) : head(nullptr), tail(nullptr), numRepos(0)
{
    RepoNode *current = other.head;
    while (current)
    {
        add(new Repo(*current->repo));
        current = current->next;
    }
}

RepoList::~RepoList()
{
    RepoNode *current = head;
    while (current)
    {
        RepoNode *toDelete = current;
        current = current->next;
        delete toDelete->repo;
        delete toDelete;
    }
}

bool RepoList::add(Repo *newRepo)
{
    RepoNode *newNode = new RepoNode(newRepo);
    // List is empty
    if (!tail)
    {
        head = tail = newNode;
    }
    else
    {
        // Add to the end of the list
        tail->next = newNode;
        newNode->prev = tail;
        tail = newNode;
    }
    ++numRepos;
    return true;
}

Repo *RepoList::get(int index) const
{
    if (index < 0 || index >= numRepos)
        return nullptr;
    RepoNode *current = head;
    for (int i = 0; i < index; ++i)
    {
        current = current->next;
    }
    return current->repo;
}

Repo *RepoList::get(const std::string &title) const
{
    RepoNode *current = head;
    while (current)
    {
        if (current->repo->getTitle() == title)
        {
            return current->repo;
        }
        current = current->next;
    }
    return nullptr;
}

Repo *RepoList::remove(int index)
{
    if (index < 0 || index >= numRepos)
        return nullptr;

    RepoNode *current = head;
    for (int i = 0; i < index; ++i)
    {
        current = current->next;
    }

    if (current->prev)
        current->prev->next = current->next;
    else
        head = current->next;

    if (current->next)
        current->next->prev = current->prev;
    else
        tail = current->prev;

    Repo *removedRepo = current->repo;
    delete current;
    --numRepos;
    return removedRepo;
}

int RepoList::size() const
{
    return numRepos;
}

bool RepoList::isFull() const
{
    return false;
}
