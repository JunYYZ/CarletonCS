#ifndef ELEVATORSCHEDULER_H
#define ELEVATORSCHEDULER_H

#include <vector>

class Elevator;

class ElevatorScheduler {
public:
    ElevatorScheduler();
    int assignElevatorRequest(int floor, bool goingUp, 
                              const std::vector<Elevator*>& elevators);
    void dispatcherStopAllRequests(std::vector<Elevator*>& elevators, int safeFloor);

private:
};

#endif // ELEVATORSCHEDULER_H
