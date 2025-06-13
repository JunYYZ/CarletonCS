#include <iostream>
#include <string>
#include <sstream>

#include "Customer.h"
#include "Driver.h"
#include "List.h"
#include "Tester.h"

#define ARR 5

using namespace std;


int driverIdTest();
int customerTest();
int listTest();

int main(){

  
    int choice = 0;
    cout << endl;
    cout << "What test would you like to run?"<< endl;
    cout << "  (0) Driver id test." << endl;
    cout << "  (1) Customer id test." << endl;
    cout << "  (2) List test." << endl;
    cout << endl;

    cin >> choice;
    switch(choice){
      case 0: return driverIdTest();
      case 1: return customerTest(); 
      case 2: return listTest(); 
    }

    return 1;
}

// make some Drivers and check their id number
int driverIdTest(){
    cout<<"This test will print out 5 drivers and verify their id numbers."<<endl<<endl;
    vector<int> keys = {4,0,1,3,2};
    vector<string> ids = {"D5","D1","D2","D4", "D3"}; 
    vector<string> names = {"Kyle", "Elsa", "Bonnie", "Clyde", "Anna"};
    Driver* drivers[ARR];
    for (int i = 0; i < ARR; ++i){
      drivers[i] = new Driver(names[i],5 , large, Location(5,5));
    }

    Tester tester;

    tester.initCapture();
    for (int i = 0; i < ARR; ++i){
        drivers[keys[i]]->print();
    }
    tester.endCapture();

    int error = 0;
    tester.findInOrder({0,1,2,3,4}, ids, error);

    if (error>0){
      cout << "At least one id not found in order, or not found at all" << endl;
      return 1;
    }else{
      cout << "All ids found in correct order" << endl;
    }

    tester.findInOrder(keys, names, error);

    if (error>0){
      cout << "At least one name not found in order, or not found at all" << endl;
      return 1;
    }else{
      cout << "All names found in correct order" << endl;
    }

    return 0;
}

// make some cellPhones and check their id number
int customerTest(){
    cout<<"This test will print out 5 customers and verify their id numbers."<<endl<<endl;
    vector<int> keys = {4,0,1,3,2};
    vector<string> ids = {"C5","C1","C2","C4", "C3"}; 
    vector<string> names = {"Kyle", "Elsa", "Bonnie", "Clyde", "Anna"};
    Customer* customers[ARR];
    for (int i = 0; i < ARR; ++i){
      customers[i] = new Customer(names[i], 5, Location(5,5));
    }

    Tester tester;

    tester.initCapture();
    for (int i = 0; i < ARR; ++i){
        customers[keys[i]]->print();
    }
    tester.endCapture();

    int error = 0;
    tester.findInOrder({0,1,2,3,4}, ids, error);

    if (error>0){
      cout << "At least one id not found in order, or not found at all" << endl;
      return 1;
    }else{
      cout << "All ids found in correct order" << endl;
    }

    tester.findInOrder(keys, names, error);

    if (error>0){
      cout << "At least one name not found in order, or not found at all" << endl;
      return 1;
    }else{
      cout << "All names found in correct order" << endl;
    }

    return 0;
}

int listTest(){
    List<Customer*> list(Customer::compNames);
    vector<string> names = {"Kyle", "Elsa", "Bonnie", "Clyde", "Anna"};
    for (int i = 0; i < ARR; ++i){
        list.add(new Customer(names[i], 5, Location(5,5)));
    }
    cout<<"Testing List class, Customers should be in alphabetical order"<<endl;
    Tester tester;  
    
    tester.initCapture();
    for (int i = 0; i < ARR; ++i){
        list.get(i)->print();
    }
    tester.endCapture();

    int error = 0;
    tester.findInOrder({4,2,3,1,0}, names, error);

    if (error>0){
      cout << "At least one name not found in order, or not found at all" << endl;
      return 1;
    }else{
      cout << "All names found in correct order" << endl;
    }

    for (int i = 0; i < ARR; ++i){
        delete list.remove(0);
    }

    List<Driver*> list2(Driver::compRatings);

    vector<int> ratings = {2,4,3,1,5};

    for (int i = 0; i < ARR; ++i){
        list2.add(new Driver(names[i], ratings[i], small, Location(5,5)));
    }

    cout<<"Testing List class, Drivers should be in order of rating"<<endl;
    tester.initCapture();
    for (int i = 0; i < ARR; ++i){
        list2.get(i)->print();
    }
    tester.endCapture();

    error = 0;
    tester.findInOrder({4, 1, 2, 0, 3}, names, error);

    if (error>0){
      cout << "At least one name not found in order, or not found at all" << endl;
      return 1;
    }else{
      cout << "All names found in correct order" << endl;
    }

    cout<<"Tests passed! "<<endl;

    return 0;
}

