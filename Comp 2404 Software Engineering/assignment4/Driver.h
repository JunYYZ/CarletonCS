#ifndef DRIVER_H
#define DRIVER_H

#include "User.h"
#include "Drawable.h"
#include "defs.h"

class Driver : public User, public Drawable
{
private:
    Size vehicleSize;
    static const char code;
    static const int driverLayer;
    static int nextId;

public:
    Driver(const std::string &name, int rating, Size vehicleSize, const Location &loc);

    bool match(Size requiredSize, int customerRating) const;
    int getDistance(const Location &dest) const;

    void draw(View &view) override;

    static int compRatings(Driver *d1, Driver *d2);
    static void resetNextId();

    void print() const override;
};

#endif // DRIVER_H
