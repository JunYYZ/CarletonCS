#include "RideShare.h"
#include <iostream>
#include <limits>

RideShare::RideShare()
    : drivers(Driver::compRatings), customers(Customer::compNames), drawables(Drawable::compare) {}

RideShare::~RideShare()
{
    for (int i = 0; i < drivers.getSize(); ++i)
    {
        delete drivers.get(i);
    }
    for (int i = 0; i < customers.getSize(); ++i)
    {
        delete customers.get(i);
    }
}

void RideShare::addDriver(const std::string &name, Size size, int rating, const Location &location)
{
    Driver *driver = new Driver(name, rating, size, location);
    drivers.add(driver);
    drawables.add(driver);
}

void RideShare::addCustomer(const std::string &name, int rating, const Location &location)
{
    Customer *customer = new Customer(name, rating, location);
    customers.add(customer);
    drawables.add(customer);
}

Driver *RideShare::findRide(int customerRating, Size size, const Location &destination) const
{
    Driver *bestDriver = nullptr;
    int minDistance = std::numeric_limits<int>::max();

    for (int i = 0; i < drivers.getSize(); ++i)
    {
        Driver *driver = drivers.get(i);
        if (driver->match(size, customerRating))
        {
            int distance = driver->getDistance(destination);
            if (distance < minDistance)
            {
                minDistance = distance;
                bestDriver = driver;
            }
        }
    }

    return bestDriver;
}

Customer *RideShare::getCustomer(const std::string &id) const
{
    for (int i = 0; i < customers.getSize(); ++i)
    {
        if (customers.get(i)->getId() == id)
        {
            return customers.get(i);
        }
    }
    return nullptr;
}

void RideShare::printDrivers() const
{
    for (int i = 0; i < drivers.getSize(); ++i)
    {
        drivers.get(i)->print();
    }
}

void RideShare::printCustomers() const
{
    for (int i = 0; i < customers.getSize(); ++i)
    {
        customers.get(i)->print();
    }
}

void RideShare::draw(View &view) const
{
    for (int i = 0; i < drawables.getSize(); ++i)
    {
        drawables.get(i)->draw(view);
    }
}
