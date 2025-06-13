#include "FileList.h"

FileList::FileList() : head(nullptr), tail(nullptr), numFiles(0) {}

FileList::FileList(const FileList &other) : head(nullptr), tail(nullptr), numFiles(0)
{
    FileNode *current = other.head;
    while (current)
    {
        add(new File(*current->file));
        current = current->next;
    }
}

FileList::~FileList()
{
    FileNode *current = head;
    while (current)
    {
        FileNode *toDelete = current;
        current = current->next;
        delete toDelete->file;
        delete toDelete;
    }
    head = tail = nullptr;
}

bool FileList::add(File *newFile)
{
    FileNode *newNode = new FileNode(newFile);

    if (!head)
    {
        head = tail = newNode;
    }
    else if (head->file->lessThan(*newFile))
    { // insert at head if it's the smallest
        newNode->next = head;
        head->prev = newNode;
        head = newNode;
    }
    else
    {
        FileNode *current = head;
        while (current->next && current->next->file->lessThan(*newFile))
        {
            current = current->next;
        }

        newNode->next = current->next;
        newNode->prev = current;

        if (current->next)
        {
            current->next->prev = newNode;
        }
        else
        {
            tail = newNode;
        }
        current->next = newNode;
    }
    ++numFiles;
    return true;
}

File *FileList::get(const string &name) const
{
    FileNode *current = head;
    while (current)
    {
        if (current->file->getName() == name)
        {
            return current->file;
        }
        current = current->next;
    }
    return nullptr;
}

File *FileList::get(int index) const
{
    if (index < 0 || index >= numFiles)
        return nullptr;
    FileNode *current = head;
    for (int i = 0; i < index; ++i)
    {
        current = current->next;
    }
    return current->file;
}

File *FileList::remove(int index)
{
    if (index < 0 || index >= numFiles)
        return nullptr;

    FileNode *current = head;
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

    File *removedFile = current->file;
    delete current;
    --numFiles;
    return removedFile;
}

int FileList::size() const
{
    return numFiles;
}

bool FileList::isFull() const
{
    return false;
}

void FileList::print() const
{
    FileNode *current = head;
    while (current)
    {
        current->file->print();
        current = current->next;
    }
}
