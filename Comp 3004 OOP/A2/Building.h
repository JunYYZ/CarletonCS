#ifndef BUILDING_H
#define BUILDING_H

#include <vector>

class Floor;
class Elevator;

class Building {
public:
    Building(int numFloors, int numElevators, int safeFloor = 1);
    Floor* getFloor(int floorNumber);
    Elevator* getElevator(int elevatorId);

private:
    std::vector<Floor*> floors;
    std::vector<Elevator*> elevators;
    int safeFloor;
};

#endif // BUILDING_H
