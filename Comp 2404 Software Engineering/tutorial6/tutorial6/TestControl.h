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
		int deepCopyTest();

		

		Tester tester;

		 // Repo information
		const static string repoTitles[5];
		const static string owners[5];

		// File information
		const static string fileNames[5][5];
		const static string content[5][5];
		const static Date dates[5][5];
	
};
#endif