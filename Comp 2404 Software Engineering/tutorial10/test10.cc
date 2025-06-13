#include <iostream>
#include <string>
#include <sstream>
using namespace std;
#include "Algorithms.h"
#include "TestClass.h"

#include <vector>
#include <iterator>
#include <algorithm>

int passingStudentsTest();
int numberSortTest();
int nameSortTest();
int highestGpaTest();
int findStudentTest();

void printVector(vector<Student>&);

int main(){
    int choice = 0;
    cin >> choice;
    switch(choice){
      case 0: return passingStudentsTest();
      case 1: return numberSortTest(); 
      case 2: return nameSortTest();
      case 3: return highestGpaTest();
      case 4: return findStudentTest();
    }
    return 0;
  
}




int passingStudentsTest(){
  vector<Student> vec;
  Algorithms algo;
  TestClass::populateAlgorithms(algo);
  algo.getPassingStudents(vec);
  cout<<"Passing Students test..."<<endl;
  printVector(vec);
  if (TestClass::arePassing(vec)){
    cout<<"All Students pass, test passed"<<endl;
    return 0;
  }else{
    cout<<"Some Students did not pass, test failed"<<endl;
    return 1;
  }

}
int numberSortTest(){
  vector<Student> vec;
  Algorithms algo;
  TestClass::populateAlgorithms(algo);
  algo.sortByNumber(vec);
  cout<<"Sort by student number test..."<<endl;
  printVector(vec);
  if (TestClass::isSortedByNumber(vec)){
    cout<<"Students sorted by student number, test passed"<<endl;
    return 0;
  }else{
    cout<<"Students not sorted by student number, test failed"<<endl;
    return 1;
  }
}

int nameSortTest(){
  vector<Student> vec;
  Algorithms algo;
  TestClass::populateAlgorithms(algo);
  algo.sortByName(vec);
  cout<<"Sort by name test..."<<endl;

  printVector(vec);

  if (TestClass::isSortedByName(vec)){
    cout<<"Students sorted by name, test passed"<<endl;
    return 0;
  }else{
    cout<<"Students not sorted by name, test failed"<<endl;
    return 1;
  }
}
int highestGpaTest(){

  Algorithms algo;
  TestClass::populateAlgorithms(algo);
  vector<Student>::iterator stu;
  algo.highestGpa(stu);
  cout<<"Highest gpa test..."<<endl;
  if (TestClass::isHighestGpa(stu)){
    cout<<*stu<<endl;
    cout<<"has highest gpa, test passed"<<endl;
    return 0;
  }else{
    cout<<*stu<<endl;
    cout<<"does not have highest gpa, test failed"<<endl;
    return 1;
  }
}

int findStudentTest(){
    Algorithms algo;
    TestClass::populateAlgorithms(algo);
    vector<Student>::iterator stu;
    string student = TestClass::existingStudent();
    if (algo.findStudent(student, stu)){
      cout <<"Student "<<student<<" found:"<<endl;
      cout <<*stu<<endl;
    }else{
      cout << "Error, Student: "<<student<<" not found "<<endl;
      return 1;
    }

    student = TestClass::nonExistingStudent();
    if (algo.findStudent(student, stu)){
      cout <<"Error: Student "<<student<<" found:"<<endl;
      cout <<*stu<<endl;
      return 1;
    }else{
      cout << "Student: "<<student<<" not found, test passed "<<endl;
      return 0;
    }
}

void printVector(vector<Student>& vec){
    cout <<endl<< "Printing students: "<<endl;
    for  (int i = 0; i < vec.size(); ++i){
      cout << vec[i];
    }
    cout<<endl;
}


