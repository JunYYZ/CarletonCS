#ifndef USER_H
#define USER_H

#include <string>
#include "Location.h"

class User {
protected:
    std::string id;
    std::string name;
    int rating;
    Location location;

public:
    User(char typeCode, int idNum, const std::string& name, int rating, const Location& loc);

    std::string getId() const { return id; }
    std::string getName() const { return name; }
    int getRating() const { return rating; }
    Location getLocation() const { return location; }

    void setRating(int newRating) { rating = newRating; }
    void setLocation(const Location& loc) { location = loc; }
    void setLocation(int x, int y) { location = Location(x, y); }

    virtual void print() const;
};

#endif // USER_H
