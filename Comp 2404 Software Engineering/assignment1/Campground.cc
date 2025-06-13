#include "Campground.h"
#include <iostream>

// Default constructor
Campground::Campground() : numCampsites(0) {}

// Add a new campsite
void Campground::addCampsite(int siteNumber, cat::Category category, const std::string& description, int maxPeople, double pricePerDay) {
    if (numCampsites >= MAX_ARRAY) {
        std::cout << "Cannot add campsite. Maximum number of campsites reached." << std::endl;
        return;
    }
    
    // Check for unique site number
    for (int i = 0; i < numCampsites; ++i) {
        if (campsites[i].getSiteNumber() == siteNumber) {
            std::cout << "Campsite with site number " << siteNumber << " already exists." << std::endl;
            return;
        }
    }

    // Add the new campsite
    campsites[numCampsites++] = Campsite(siteNumber, category, description, maxPeople, pricePerDay);
    std::cout << "Campsite added successfully." << std::endl;
}

// Remove a campsite
void Campground::removeCampsite(int siteNumber) {
    for (int i = 0; i < numCampsites; ++i) {
        if (campsites[i].getSiteNumber() == siteNumber) {
            // Shift the remaining campsites left
            for (int j = i; j < numCampsites - 1; ++j) {
                campsites[j] = campsites[j + 1];
            }
            numCampsites--;
            std::cout << "Campsite " << siteNumber << " removed successfully." << std::endl;
            return;
        }
    }
    std::cout << "Campsite with site number " << siteNumber << " not found." << std::endl;
}

// Add a camper to a specified campsite
void Campground::addCamper(int siteNumber, const std::string& name, const std::string& plateNumber, int numPeople, Date checkIn, Date checkOut) {
    for (int i = 0; i < numCampsites; ++i) {
        if (campsites[i].getSiteNumber() == siteNumber) {
            if (campsites[i].addCamper(name, plateNumber, numPeople, checkIn, checkOut)) {
                std::cout << "Camper added successfully to campsite " << siteNumber << "." << std::endl;
            } else {
                std::cout << "Failed to add camper to campsite " << siteNumber << "." << std::endl;
            }
            return;
        }
    }
    std::cout << "Campsite with site number " << siteNumber << " not found." << std::endl;
}

// Remove a camper from a specified campsite
void Campground::removeCamper(int siteNumber, const std::string& name) {
    for (int i = 0; i < numCampsites; ++i) {
        if (campsites[i].getSiteNumber() == siteNumber) {
            if (campsites[i].removeCamper(name)) {
                std::cout << "Camper " << name << " removed from campsite " << siteNumber << "." << std::endl;
            } else {
                std::cout << "Camper " << name << " not found in campsite " << siteNumber << "." << std::endl;
            }
            return;
        }
    }
    std::cout << "Campsite with site number " << siteNumber << " not found." << std::endl;
}

// Print all campsites
void Campground::printCampsites() {
    if (numCampsites == 0) {
        std::cout << "No campsites available." << std::endl;
        return;
    }
    for (int i = 0; i < numCampsites; ++i) {
        campsites[i].print();
        std::cout << std::endl;
    }
}

// Print all campers at a specific campsite
void Campground::printCampers(int siteNumber) {
    for (int i = 0; i < numCampsites; ++i) {
        if (campsites[i].getSiteNumber() == siteNumber) {
            campsites[i].printCampers();
            return;
        }
    }
    std::cout << "Campsite with site number " << siteNumber << " not found." << std::endl;
}

// Print all campers at any campsite on a given date
void Campground::printCampers(Date& date) {
    bool camperFound = false;
    for (int i = 0; i < numCampsites; ++i) {
        campsites[i].printCamper(date);
        camperFound = true;
    }
    if (!camperFound) {
        std::cout << "No campers found for the given date." << std::endl;
    }
}

// Print all campsites in a given category
void Campground::printCampsitesByCategory(cat::Category category) {
    bool found = false;
    for (int i = 0; i < numCampsites; ++i) {
        if (campsites[i].getCategory() == category) {
            campsites[i].print();
            std::cout << std::endl;
            found = true;
        }
    }
    if (!found) {
        std::cout << "No campsites found in category: " << cat::categoryToString(category) << "." << std::endl;
    }
}
