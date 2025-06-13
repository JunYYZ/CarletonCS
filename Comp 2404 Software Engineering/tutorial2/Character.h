#ifndef CHARACTER_H
#define CHARACTER_H

#include <string>
using namespace std;

class Character {
private:
    string name;
    int maxHealth;
    int currentHealth;
    int damage;

public:
    Character(const string&, int maxHealth, int damage);
    
    string getName() const;
    void takeDamage(int damage);
    int strike() const;
    void print() const;
};

#endif // CHARACTER_H
