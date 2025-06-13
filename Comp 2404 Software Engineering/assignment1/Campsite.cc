#include "Campsite.h"
#include <iostream>

// Constructor
Campsite::Campsite() {}
Campsite::Campsite(int siteNumber, cat::Category category, const std::string &description, int maxPeople, double pricePerDay)
    : siteNumber(siteNumber), category(category), description(description), maxPeople(maxPeople), pricePerDay(pricePerDay), numCampers(0)
{
    // Validation
    if (pricePerDay <= 0)
        this->pricePerDay = 0.0; // Ensure price is positive
    if (maxPeople < 1)
        this->maxPeople = 1; // Ensure maxPeople is at least 1
}

// Add Camper
bool Campsite::addCamper(const std::string &name, const std::string &plateNumber, int numPeople, Date checkIn, Date checkOut)
{
    if (numCampers >= MAX_ARRAY || numPeople > maxPeople || !checkDateLegal(checkIn, checkOut))
    {
        return false;
    }

    // If no campers, add the new camper at the first position
    if (numCampers == 0)
    {
        campers[numCampers++] = Camper(name, plateNumber, numPeople, checkIn, checkOut);
        return true;
    }

    // Loop through current campers to find where the new camper should be added
    for (int i = 0; i < numCampers; i++)
    {
        Date currentCamperCheckOut = campers[i].getCheckOut();

        // Check if the current camper's check-out date is less than or equal to the new camper's check-in date
        if (checkIn.lessThan(campers[i].getCheckIn()) && (checkOut.lessThan(campers[i].getCheckIn()) || checkOut.equals(campers[i].getCheckIn())))
        {
            // Shift the campers to the right to make space
            for (int j = numCampers; j > i; --j)
            {
                campers[j] = campers[j - 1];
            }

            // Add the new camper
            campers[i + 1] = Camper(name, plateNumber, numPeople, checkIn, checkOut);
            numCampers++;

            return true;
        }
        else if (currentCamperCheckOut.lessThan(checkIn) || currentCamperCheckOut.equals(checkIn))
        {
            // Now, check the next camper's check-in date, if it exists
            if (i + 1 < numCampers && (checkOut.lessThan(campers[i + 1].getCheckIn()) || checkOut.equals(campers[i + 1].getCheckIn())))
            {
                // Shift the campers to the right to make space
                for (int j = numCampers; j > i + 1; --j)
                {
                    campers[j] = campers[j - 1];
                }

                // Add the new camper
                campers[i + 1] = Camper(name, plateNumber, numPeople, checkIn, checkOut);
                numCampers++;

                return true;
            }
            else
            {
                return false;
            }
        }
    }
    // If we reached here, the new camper's check-in date is after all existing campers' check-out dates
    if (campers[numCampers - 1].getCheckOut().lessThan(checkIn) || campers[numCampers - 1].getCheckOut().equals(checkIn))
    {
        campers[numCampers++] = Camper(name, plateNumber, numPeople, checkIn, checkOut);
        return true;
    }

    return false;
}

// Remove Camper
bool Campsite::removeCamper(const std::string &name)
{
    for (int i = 0; i < numCampers; ++i)
    {
        if (campers[i].getName() == name)
        {
            for (int j = i; j < numCampers - 1; ++j)
            {
                campers[j] = campers[j + 1]; // Shift remaining campers left
            }
            numCampers--;
            return true;
        }
    }
    return false; // Camper not found
}

// Print all campsite information
void Campsite::print()
{
    std::cout << "Site Number: " << siteNumber << std::endl;
    std::cout << "Category: " << cat::categoryToString(category) << std::endl;
    std::cout << "Description: " << description << std::endl;
    std::cout << "Max People: " << maxPeople << std::endl;
    std::cout << std::fixed << std::setprecision(2);
    std::cout << "Price per Day: $" << pricePerDay << std::endl;
}

// Print all campers
void Campsite::printCampers()
{
    for (int i = 0; i < numCampers; ++i)
    {
        campers[i].print();
        std::cout << std::endl;
    }
}

// Print camper by date
void Campsite::printCamper(Date &date)
{
    for (int i = 0; i < numCampers; ++i)
    {
        if (campers[i].getCheckIn().lessThan(date) && !date.lessThan(campers[i].getCheckOut()))
        {
            campers[i].print();
            return;
        }
    }
    std::cout << "No campers found for the given date." << std::endl;
}

int Campsite::getSiteNumber()
{
    return this->siteNumber;
}

cat::Category Campsite::getCategory()
{
    return this->category;
}

double Campsite::getPricePerDay()
{
    return this->pricePerDay;
}

// Check for date conflicts (helper function)
bool Campsite::checkDateLegal(Date &checkIn, Date &checkOut)
{
    if (checkIn.lessThan(checkOut))
    {
        return true;
    }

    return false;
}

// Check for date conflicts (helper function)
bool Campsite::checkDateConflict(Date &checkIn)
{
    // for (int i = 0; i < numCampers; ++i)
    // {
    if (checkIn.lessThan(campers[numCampers - 1].getCheckOut()))
    {
        // There's an overlap in dates
        return false;
    }
    // }
    return true; // No conflicts
}