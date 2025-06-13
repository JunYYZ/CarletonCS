#ifndef CAMPGROUND_H
#define CAMPGROUND_H

#include "Campsite.h"
#include "defs.h"

class Campground {
public:
    // Default constructor
    Campground();

    // Functions to add and remove campsites
    void addCampsite(int siteNumber, cat::Category category, const std::string& description, int maxPeople, double pricePerDay);
    void removeCampsite(int siteNumber);

    // Functions to add and remove campers
    void addCamper(int siteNumber, const std::string& name, const std::string& plateNumber, int numPeople, Date checkIn, Date checkOut);
    void removeCamper(int siteNumber, const std::string& name);

    // Print functions
    void printCampsites();
    void printCampers(int siteNumber);
    void printCampers(Date& date);
    void printCampsitesByCategory(cat::Category category);

private:
    Campsite campsites[MAX_ARRAY];
    int numCampsites;
};

#endif
