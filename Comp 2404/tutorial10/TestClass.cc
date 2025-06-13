
#include "TestClass.h"
#include <random>
#include <string>
#include <sstream>

const string TestClass::names[10]={"Zeek", "Alice", "Duncan", "Charlie", "Zack", "Debbie", "Gary", "Tory", "Elsa", "Emma"};
const string TestClass::numbers[10] ={"100111222", "100111999", "100111998", "100111994", "100111448", "100123998", "100341455", "100222222", "100900000", "100700000"};
string TestClass::sortedNames[10]={"Zeek", "Alice", "Duncan", "Charlie", "Zack", "Debbie", "Gary", "Tory", "Elsa", "Emma"};
string TestClass::sortedNumbers[10] ={"100111222", "100111999", "100111998", "100111994", "100111448", "100123998", "100341455", "100222222", "100900000", "100700000"};
string TestClass::passingStudents[6]={"Zeek", "Alice", "Zack", "Gary", "Elsa", "Emma"};
const float TestClass::gpas[10] = {12.0f, 11.0f, 1.5f, 5.0f, 6.6f, 2.4f, 10.6f, 5.0f, 11.6f, 9.4f };

vector<string> TestClass::passing;

void TestClass::populateAlgorithms(Algorithms& a){
    for (int i = 0; i < 10; ++i){
        a.addStudent(numbers[i], names[i], gpas[i]);
    }
    sort(sortedNames, sortedNames + 10);
    sort(sortedNumbers, sortedNumbers + 10);
    for (int i = 0; i < 6; ++i){
        passing.push_back(passingStudents[i]);
    }
}

bool TestClass::isSortedByNumber(vector<Student>& v){
    for (int i=0; i<10; ++i){
        if (v[i].number != sortedNumbers[i]) return false;
    }
    return true;
}
bool TestClass::isSortedByName(vector<Student>& v){
    for (int i=0; i<10; ++i){
        if (v[i].name != sortedNames[i]) return false;
    }
    return true;
}

bool TestClass::isHighestGpa(vector<Student>::iterator& it){
    for (int i=0; i<10; ++i){
        if ((*it).gpa < gpas[i]) return false;
    }
    return true;
}

bool TestClass::arePassing(vector<Student>& v){
    if (v.size()!=6) return false;
    for (int i=0; i<v.size(); ++i){
        if (v[i].gpa < 6.0) return false;
    }
    //there are no failing, but let's make sure all passing are there
    for (int i=0; i<6; ++i){
        bool found = false;
        for (int j=0; j<6; ++j){
            if (v[i].name == passingStudents[j]){
                found = true;
                break;
            }
        }
        if (!found) return false;
    }
    return true;
}

bool TestClass::isSortedByNumber(vector<Student*>& v){
    for (int i=1; i<v.size(); ++i){
        if (v[i-1]->number > v[i]->number) return false;
    }
    return true;
}
bool TestClass::isSortedByName(vector<Student*>& v){
    for (int i=1; i<v.size(); ++i){
        if (v[i-1]->name > v[i]->name) return false;
    }
    return true;
}

bool TestClass::isHighestGpa(vector<Student*>& v, Student* stu){
    for (int i=0; i<v.size(); ++i){
        if (v[i]->gpa > stu->gpa) return false;
    }
    return true;
}

bool TestClass::arePassing(vector<Student*>& v){
    for (int i=0; i<v.size(); ++i){
        if (v[i]->gpa < 6.0) return false;
    }
    return true;
}

string TestClass::existingStudent(){
    std::random_device rd; // obtain a random number from hardware
    std::mt19937 gen(rd()); // seed the generator
    std::uniform_int_distribution<> distr(0, 9); // define the range
    return names[distr(gen)];
}

string TestClass::nonExistingStudent(){
    return "Kanye";
}