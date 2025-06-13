#ifndef CLIENT_H
#define CLIENT_H

#include "Repo.h"
#include "GetHub.h"
#include <iostream>

class Client {
public:
    Client();
    ~Client();

    void cloneRepo(GetHub& getHub, int index);
    void printRepo() const;
    void printFileContents(int index) const;

private:
    Repo* currentRepo;
};

#endif