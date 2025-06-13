#ifndef CUSTOMER_H
#define CUSTOMER_H

#include "User.h"
#include "Drawable.h"

class Customer : public User, public Drawable
{
private:
    static const char code;
    static int nextId;
    static const int custLayer;

public:
    Customer(const std::string &name, int rating, const Location &loc);

    void draw(View &view) override;

    static int compNames(Customer *c1, Customer *c2);
    static void resetNextId();

    void print() const override;
};

#endif // CUSTOMER_H
