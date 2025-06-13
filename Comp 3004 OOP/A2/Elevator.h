#ifndef ELEVATOR_H
#define ELEVATOR_H

#include <vector>
#include <string>

enum class ElevatorState {
    IDLE,
    MOVING_UP,
    MOVING_DOWN,
    DOOR_OPENING,
    BOARDING_ALIGHTING,
    DOOR_CLOSING,
    EMERGENCY
};

class Elevator {
public:
    Elevator(int id, int capacity);

    void moveUp();
    void moveDown();
    void openDoors();
    void closeDoors();

    void boardPassengers(int count);
    void disembarkPassengers(int count);

    void pressFloorButton(int floor);
    void triggerHelp();
    void triggerOverload();
    void goToSafeFloor(int floor);

    ElevatorState getState() const;
    int getCurrentFloor() const;
    bool isOverloaded() const;

private:
    int elevatorId;
    int capacity;
    int currentFloor;
    bool overloaded;
    bool doorsOpen;
    ElevatorState state;
    std::vector<int> requestedFloors;
};

#endif // ELEVATOR_H
