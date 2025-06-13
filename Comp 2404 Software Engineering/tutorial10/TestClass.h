
#ifndef TESTCLASS_H
#define TESTCLASS_H

#include <vector>
#include "Student.h"
#include "Algorithms.h"


using namespace std;

class TestClass {
		
	public:
		static void populateAlgorithms(Algorithms&);

		static bool isSortedByNumber(vector<Student>&);
		static bool isSortedByName(vector<Student>&);
		static bool isHighestGpa(vector<Student>::iterator& it);
		static bool arePassing(vector<Student>&);

		static bool isSortedByNumber(vector<Student*>&);
		static bool isSortedByName(vector<Student*>&);
		static bool isHighestGpa(vector<Student*>&, Student*);
		static bool arePassing(vector<Student*>&);

		static string existingStudent();
		static string nonExistingStudent();
	
	private:
		static const string names[10];
		static const string numbers[10];
		static string sortedNames[10];
		static string sortedNumbers[10];
		static string passingStudents[6];
		static const float gpas[10];

		static vector<string> passing;
		
	
};
#endif