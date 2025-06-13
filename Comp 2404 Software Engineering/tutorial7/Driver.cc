#include "Driver.h"
#include <iostream>
#include <cmath>

const char Driver::code = 'D';
const int Driver::driverLayer = 1;
int Driver::nextId = 0;

Driver::Driver(const std::string &name, int rating, Size vehicleSize, const Location &loc)
    : User(code, ++nextId, name, rating, loc), Drawable(driverLayer), vehicleSize(vehicleSize) {}

bool Driver::match(Size requiredSize, int customerRating) const
{
    return vehicleSize >= requiredSize && abs(rating - customerRating) <= 2;
}

int Driver::getDistance(const Location &dest) const
{
    return std::abs(location.getX() - dest.getX()) + std::abs(location.getY() - dest.getY());
}

void Driver::draw(View &view)
{
    view.drawDriver(location.getX(), location.getY(), name[0]);
}

int Driver::compRatings(Driver *d1, Driver *d2)
{
    return d2->getRating() - d1->getRating();
}

void Driver::resetNextId()
{
    nextId = 0;
}

void Driver::print() const
{
    User::print();
    std::cout << "Driver-specific information." << std::endl;
}
