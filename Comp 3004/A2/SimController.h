#ifndef SIMULATIONCONTROLLER_H
#define SIMULATIONCONTROLLER_H

#include <vector>
#include <string>

class Elevator;
class Building;

struct PassengerAction {
    int timeStep;
    int passengerId;
    std::string action; // example: "pressUpButton", "pressFloorButton(4)"
};

struct SafetyEvent {
    int timeStep;
    std::string eventType; // "Fire", "Overload", "DoorObstacle", "Help", "PowerOut"
};

class SimulationController {
public:
    SimulationController();
    void initialize(int numFloors, int numElevators);
    void handleTimeStep(int timeStep);
    void handleSafetyEvent(const SafetyEvent& event);
    void addPassengerAction(const PassengerAction& action);
    void addSafetyEvent(const SafetyEvent& event);

private:
    Building* building;
    std::vector<PassengerAction> passengerActions;
    std::vector<SafetyEvent> safetyEvents;

    void processPassengerActions(int currentTime);
    void processSafetyEvents(int currentTime);
};

#endif // SIMULATIONCONTROLLER_H
