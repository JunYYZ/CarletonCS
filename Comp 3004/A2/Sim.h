#ifndef SIMULATION_H
#define SIMULATION_H

#include <string>
#include <vector>

class SimulationController;

class Simulation {
public:
    Simulation();
    void setUpSimulation(int numFloors, int numElevators);
    void start();
    void pause();
    void resume();
    void stop();
    void advanceTimeStep();
    void logEvent(const std::string& event);

    bool isPaused() const;
    bool isStopped() const;
    int getCurrentTimeStep() const;

private:
    int currentTimeStep;
    bool paused;
    bool stopped;

    SimulationController* controller;
};

#endif // SIMULATION_H
