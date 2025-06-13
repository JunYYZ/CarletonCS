#ifndef CAMPER_H
#define CAMPER_H

#include <string>
#include "Date.h"
#include <iostream>

using namespace std;

class Camper {
public:
    // Default constructor
    Camper();
    
    // Parameterized constructor
    Camper(string name, string plateNumber, int numPeople, Date checkIn, Date checkOut);
    
    // Print function
    void print();

    // Getters and Setters (optional, can be useful if you need them)
    string getName();
    string getPlateNumber();
    int getNumPeople();
    Date getCheckIn();
    Date getCheckOut();

private:
    string name;
    string plateNumber;
    int numPeople;
    Date checkIn;
    Date checkOut;
};

#endif
