#ifndef TESTCONTROL_H
#define TESTCONTROL_H

#include <iostream>
#include <string>
#include <vector>

#include "GetHub.h"
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

  // helper function
  void initGetHub(GetHub &getHub);

  View view;
  Tester tester;

  static vector<string> menu;

  // Repo information
  static string repoTitles[5];
  static string owners[5];

  // File information
  static string fileTitles[5][5];
  static string content[5][5];
  static Date dates[5][5];
};

#endif
