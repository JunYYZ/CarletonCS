#include "Camper.h"
#include <iostream>

using namespace std;

// Default constructor (initializes dummy data)
Camper::Camper() : name("Unknown"), plateNumber("XXXXXXX"), numPeople(1), checkIn(), checkOut() {}

// Parameterized constructor
Camper::Camper(string name, string plateNumber, int numPeople, Date checkIn, Date checkOut)
    : name(name), plateNumber(plateNumber), numPeople(numPeople), checkIn(checkIn), checkOut(checkOut) {
    if (numPeople < 1) {
        this->numPeople = 1;  // Ensure at least 1 person
    }
}

// Print function
void Camper::print() {
    cout << "Camper Name: " << name << endl;
    cout << "Plate Number: " << plateNumber << endl;
    cout << "Number of People: " << numPeople << endl;
    cout << "Check-in Date: "; checkIn.print();
    cout << "Check-out Date: "; checkOut.print();
}

// Optional: Getters
string Camper::getName() {
    return name;
}

string Camper::getPlateNumber() {
    return plateNumber;
}

int Camper::getNumPeople() {
    return numPeople;
}

Date Camper::getCheckIn() {
    return checkIn;
}

Date Camper::getCheckOut() {
    return checkOut;
}
