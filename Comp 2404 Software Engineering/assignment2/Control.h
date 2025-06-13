
#ifndef CONTROL_H
#define CONTROL_H

#include <iostream>
#include <string>
#include <vector>

#include "GetHub.h"
#include "View.h"

using namespace std;

class Control
{
public:
  Control();

  void launch();

private:
  // GetHub functions
  void printRepos();
  void printRepo();
  void printFileContents();
  void removeRepo();
  void removeFile();

  // helper function
  void initGetHub();

  GetHub getHub;
  View view;

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