#include "User.h"
#include <iostream>

User::User(char typeCode, int idNum, const std::string &name, int rating, const Location &loc)
    : id(std::string(1, typeCode) + std::to_string(idNum)), name(name), rating(rating), location(loc) {}

void User::print() const
{
    std::cout << "User ID: " << id << ", Name: " << name << ", Rating: " << rating
              << ", Location: (" << location.getX() << ", " << location.getY() << ")" << std::endl;
}
