#include "Character.h"
#include <iostream>
using namespace std;

Character::Character(const string& name, int maxHealth, int damage)
    : name(name), maxHealth(maxHealth), currentHealth(maxHealth), damage(damage) {}

string Character::getName() const {
    return name;
}

void Character::takeDamage(int damage) {
    currentHealth -= damage;
    if (currentHealth < 0) {
        currentHealth = 0;
    }
}

int Character::strike() const {
    return damage;
}

void Character::print() const {
    cout << name << " has " << currentHealth << " health remaining." << endl;
}
