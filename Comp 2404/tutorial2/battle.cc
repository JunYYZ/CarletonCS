#include "battle.h"
#include <iostream>
using namespace std;

namespace Gondor {
    void fight(Character& fighter, Character& orc) {
        int fighterDamage = fighter.strike() + 1; // fighter advant
        int orcDamage = orc.strike() - 1;         // orc disadvant
        
        if (orcDamage < 0) orcDamage = 0;         // please don't heal player
        
        cout << fighter.getName() << " strikes " << orc.getName() << " for " << fighterDamage << " damage!" << endl;
        orc.takeDamage(fighterDamage);
        cout << orc.getName() << " strikes " << fighter.getName() << " for " << orcDamage << " damage!" << endl;
        fighter.takeDamage(orcDamage);
    }
}

namespace Mordor {
    void fight(Character& fighter, Character& orc) {
        int fighterDamage = fighter.strike() - 1; // fighter disadvant
        int orcDamage = orc.strike() + 1;         // orc advant
        
        if (fighterDamage < 0) fighterDamage = 0; // don't heal the orc please
        
        cout << orc.getName() << " strikes " << fighter.getName() << " for " << orcDamage << " damage!" << endl;
        fighter.takeDamage(orcDamage);
        cout << fighter.getName() << " strikes " << orc.getName() << " for " << fighterDamage << " damage!" << endl;
        orc.takeDamage(fighterDamage);
    }
}
