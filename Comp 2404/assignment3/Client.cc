#include "Client.h"

Client::Client() : currentRepo(nullptr) {}

Client::~Client()
{
    delete currentRepo;
}

void Client::cloneRepo(GetHub &getHub, int index)
{
    delete currentRepo; // remove any previously stored Repo

    Repo *repoToClone = nullptr;
    if (getHub.download(index, &repoToClone) && repoToClone)
    {
        currentRepo = new Repo(*repoToClone);
    }
    else
    {
        std::cout << "Error: Could not clone Repo at index " << index << std::endl;
        currentRepo = nullptr;
    }
}

void Client::printRepo() const
{
    if (currentRepo)
    {
        currentRepo->printFiles();
    }
    else
    {
        std::cout << "Error: No Repo currently cloned." << std::endl;
    }
}

void Client::printFileContents(int index) const
{
    if (currentRepo)
    {
        currentRepo->printContents(index);
    }
    else
    {
        std::cout << "Error: No Repo currently cloned." << std::endl;
    }
}