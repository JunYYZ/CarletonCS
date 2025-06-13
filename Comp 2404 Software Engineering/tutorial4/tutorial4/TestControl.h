#ifndef TESTCONTROL_H
#define TESTCONTROL_H

#include <iostream>
#include <string>
#include <random>
#include <unordered_set>
#include <sstream>
#include "Tester.h"
#include "Date.h"

using namespace std;

class TestControl {
		
	public:
		int launch();
	
	private:
		int repoTest();
		int destructorTest();

		

		Tester tester;

		 // Channel information
		 static string repoTitles[5];
		 static string owners[5];

		// Video information
		 static string fileNames[5][5];
		 static string content[5][5];
		 static Date dates[5][5];
	
};
#endif