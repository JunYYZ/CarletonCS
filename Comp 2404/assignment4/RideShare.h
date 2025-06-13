#ifndef RIDESHARE_H
#define RIDESHARE_H

#include "Driver.h"
#include "Customer.h"
#include "List.h"
#include "Drawable.h"
#include "View.h"
#include <string>

class RideShare
{
public:
    RideShare();
    ~RideShare();

    void addDriver(const std::string &name, Size size, int rating, const Location &location);
    void addCustomer(const std::string &name, int rating, const Location &location);

    Driver *findRide(int customerRating, Size size, const Location &destination) const;
    Customer *getCustomer(const std::string &id) const;

    void printDrivers() const;
    void printCustomers() const;
    void draw(View &view) const;

private:
    List<Driver *> drivers;
    List<Customer *> customers;
    List<Drawable *> drawables;
};

#endif