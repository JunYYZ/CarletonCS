#ifndef CUSTOMER_H
#define CUSTOMER_H

#include <string>
#include <iostream>
#include "defs.h"
#include "User.h"
#include "Drawable.h"

using namespace std;

class Customer : public User, public Drawable
{
public:
    Customer(string, int rating = 5, const Location &l = Location());
    void print();

    void draw(View &);

    static int compRatings(Customer *c1, Customer *c2)
    {
        return c1->getRating() - c2->getRating();
    }

    static int compNames(Customer *c1, Customer *c2)
    {
        return c1->getName() < c2->getName();
    }

    static void resetNextId()
    {
        nextId = 0;
    }

private:
    static const int custLayer;
    static int nextId;
    static const char code;
};

#endif