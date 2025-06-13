#include "Building.h"
#include <QDebug>

Building::Building(int numFloors, QObject* parent)
    : QObject(parent),
      safeFloor(1)
{
    for(int i=1; i<=numFloors; i++){
        floors.append(new Floors(i, this));
    }
}

Building::~Building()
{
    // floors auto-deleted
}

Floors* Building::getFloor(int floorNumber)
{
    if(floorNumber < 1 || floorNumber > floors.size()) return nullptr;
    return floors[floorNumber - 1];
}

int Building::getNumFloors() const
{
    return floors.size();
}
