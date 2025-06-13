#include "Customer.h"
#include <iostream>

const char Customer::code = 'C';
int Customer::nextId = 0;
const int Customer::custLayer = 3;

Customer::Customer(const std::string &name, int rating, const Location &loc)
    : User(code, ++nextId, name, rating, loc), Drawable(custLayer) {}

void Customer::draw(View &view)
{
    view.drawCustomer(location.getX(), location.getY(), name[0]);
}

int Customer::compNames(Customer *c1, Customer *c2)
{
    return c1->getName().compare(c2->getName());
}

void Customer::resetNextId()
{
    nextId = 0;
}

void Customer::print() const
{
    User::print();
    std::cout << "Customer-specific information." << std::endl;
}
