#include "GetHub.h"
#include <iostream>

GetHub::GetHub()
{
    repoList = new RepoList(); // dyam allocate RepoList
}

GetHub::~GetHub()
{
    delete repoList; // delete dynamically allocated RepoList
}

void GetHub::addRepo(const std::string &title, const std::string &owner)
{
    Repo *newRepo = new Repo(title, owner);
    if (!repoList->add(newRepo))
    {
        std::cout << "Error: Repo list is full, cannot add new Repo." << std::endl;
        delete newRepo;
    }
}

void GetHub::deleteRepo(const int& index)
{
    Repo *removedRepo = repoList->remove(index);
    if (removedRepo == nullptr)
    {
        std::cout << "Error: No such Repo at index " << index << "." << std::endl;
    }
    else
    {
        delete removedRepo;
    }
}

int GetHub::getNumRepos() const
{
    return repoList->size();
}

bool GetHub::download(const int& index, Repo **repo)
{
    Repo *foundRepo = repoList->get(index);
    if (foundRepo == nullptr)
    {
        std::cout << "Error: No such Repo at index " << index << "." << std::endl;
        return false;
    }
    *repo = foundRepo;
    return true;
}

// bool GetHub::addFile(int repoIndex, const std::string& name, const std::string& content, Date& date) {
//     Repo* repo = repoList->get(repoIndex);
//     if (repo == nullptr) {
//         std::cout << "Error: No such Repo at index " << repoIndex << "." << std::endl;
//         return false;
//     }
//     if (!repo->addFile(name, content, date)) {
//         std::cout << "Error: Failed to add File to Repo at index " << repoIndex << "." << std::endl;
//         return false;
//     }
//     return true;
// }

bool GetHub::addFile(const std::string &repoTitle, const std::string &name, const std::string &content, const Date &date)
{
    for (int i = 0; i < repoList->size(); ++i)
    {
        Repo *repo = repoList->get(i);
        if (repo != nullptr && repo->getTitle() == repoTitle)
        {
            return repo->addFile(name, content, date);
        }
    }
    std::cout << "Error: No repository with the title \"" << repoTitle << "\" was found." << std::endl;
    return false;
}

void GetHub::deleteFile(const int& repoIndex, const int& fileIndex)
{
    Repo *repo = repoList->get(repoIndex);
    if (repo == nullptr)
    {
        std::cout << "Error: No such Repo at index " << repoIndex << "." << std::endl;
        return;
    }
    if (!repo->removeFile(fileIndex))
    {
        std::cout << "Error: No such File at index " << fileIndex << " in Repo " << repoIndex << "." << std::endl;
    }
}

void GetHub::printRepos() const
{
    std::cout << "GetHub\n========\n";
    for (int i = 0; i < repoList->size(); ++i)
    {
        std::cout << i << ") ";
        repoList->get(i)->print();
    }
}

void GetHub::printRepo(const int& index) const
{
    Repo *repo = repoList->get(index);
    if (repo == nullptr)
    {
        std::cout << "Error: No such Repo at index " << index << "." << std::endl;
    }
    else
    {
        repo->printFiles();
    }
}

void GetHub::printFileContents(const int& repoIndex, const int& fileIndex) const
{
    Repo *repo = repoList->get(repoIndex);
    if (repo == nullptr)
    {
        std::cout << "Error: No such Repo at index " << repoIndex << "." << std::endl;
        return;
    }
    repo->printContents(fileIndex);
}
