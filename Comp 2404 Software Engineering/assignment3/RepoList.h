#ifndef REPOLIST_H
#define REPOLIST_H

#include <iostream>
#include <string>
#include <iomanip>
#include "Repo.h"
#include "defs.h"

using namespace std;

struct RepoNode
{
	Repo *repo;
	RepoNode *next;
	RepoNode *prev;
	RepoNode(Repo *r) : repo(r), next(nullptr), prev(nullptr) {}
};

class RepoList
{

public:
	// constructor
	RepoList();
	// copy constructor
	RepoList(const RepoList &);
	// destructor
	~RepoList();

	// other
	bool add(Repo *);
	Repo *get(const string &title) const;
	Repo *get(int index) const;
	Repo *remove(const string &title);
	Repo *remove(int index);
	int size() const;
	bool isFull() const;
	// void print() const;

private:
	int numRepos;
	RepoNode *head;
	RepoNode *tail;
};
#endif
