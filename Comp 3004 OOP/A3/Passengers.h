#ifndef PASSENGERS_H
#define PASSENGERS_H

#include <QObject>

class Passengers
{
public:
    Passengers(int id, int startFloor, int destFloor);
    ~Passengers() = default;

    int getId() const;
    int getCurrentFloor() const;
    int getDestinationFloor() const;
    bool isOnElevator() const;
    void setOnElevator(bool on);

private:
    int m_id;
    int m_currentFloor;
    int m_destinationFloor;
    bool m_onElevator;
};

#endif // PASSENGERS_H
