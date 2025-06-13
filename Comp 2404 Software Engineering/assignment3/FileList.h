
#ifndef FILELIST_H
#define FILELIST_H

#include <iostream>
#include <string>
#include <iomanip>
#include "File.h"
#include "defs.h"

using namespace std;

struct FileNode
{
	File *file;
	FileNode *next;
	FileNode *prev;
	FileNode(File *f) : file(f), next(nullptr), prev(nullptr) {}
};

class FileList
{

public:
	// constructor
	FileList();
	// copy constructor
	FileList(const FileList &other);

	// destructor
	~FileList();

	// other
	bool add(File *newFile);
	File *get(const string &name) const;
	File *get(int index) const;
	File *remove(const string &name);
	File *remove(int index);
	int size() const;
	bool isFull() const;
	void print() const;

private:
	FileNode *head;
	FileNode *tail;
	int numFiles;
};
#endif