#ifndef TESTCONTROL_H
#define TESTCONTROL_H

#include <iostream>
#include <string>
#include <vector>

#include "GetHub.h"
#include "Client.h"
#include "View.h"
#include "Tester.h"

using namespace std;


class TestControl
{
  public:
    
    void launch();
    

  private:
    int repoListTest();
    int getHubRepoTest();
    int getHubFileTest();

    int clientDownloadTest();
    int clientMemoryTest();
  

    //helper function
    void initGetHub(GetHub& getHub);

    View view;
    Tester tester;

    static vector<string> menu;

    // Repo information
    static const string repoTitles[5];
    static const string owners[5];

    // File information
    static const string fileTitles[5][5];
    static const string content[5][5];
    static const Date dates[5][5];


    
};

#endif
