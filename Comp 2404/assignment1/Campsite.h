#ifndef CAMPSITE_H
#define CAMPSITE_H

#include <string>
#include "Camper.h"
#include "Category.h"
#include "defs.h"

class Campsite {
public:
    // Constructor
    Campsite();
    Campsite(int siteNumber, cat::Category category, const std::string& description, int maxPeople, double pricePerDay);

    // Add Camper
    bool addCamper(const std::string& name, const std::string& plateNumber, int numPeople, Date checkIn, Date checkOut);

    // Remove Camper
    bool removeCamper(const std::string& name);

    // Print functions
    void print();  // Print all campsite data
    void printCampers();  // Print all campers
    void printCamper(Date& date);  // Print a camper that overlaps with the given date

    int getSiteNumber();
    cat::Category getCategory();
    double getPricePerDay();

private:
    int siteNumber;
    cat::Category category;
    std::string description;
    int maxPeople;
    double pricePerDay;

    Camper campers[MAX_ARRAY];
    int numCampers;
    
    bool checkDateConflict(Date& checkIn);  // Check for date conflicts
    bool checkDateLegal(Date& checkIn, Date& checkOut);
};

#endif
