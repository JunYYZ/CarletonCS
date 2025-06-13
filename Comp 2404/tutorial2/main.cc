#include "Character.h"
#include "battle.h"
#include <iostream>
using namespace std;

int main() {
    string fighterName, orcName;
    int fighterHealth, fighterDamage, orcHealth, orcDamage;

    cout << "Enter fighter name, max health, and damage: ";
    cin >> fighterName >> fighterHealth >> fighterDamage;
    Character fighter(fighterName, fighterHealth, fighterDamage);

    cout << "Enter orc name, max health, and damage: ";
    cin >> orcName >> orcHealth >> orcDamage;
    Character orc(orcName, orcHealth, orcDamage);

    fighter.print();
    orc.print();

    cout << "\n--- Fight in Gondor ---\n";
    Gondor::fight(fighter, orc);
    fighter.print();
    orc.print();

    cout << "\n--- Fight in Mordor ---\n";
    Mordor::fight(fighter, orc);
    fighter.print();
    orc.print();

    return 0;
}
