#include "Passengers.h"

Passengers::Passengers(int id, int startFloor, int destFloor)
    : m_id(id),
      m_currentFloor(startFloor),
      m_destinationFloor(destFloor),
      m_onElevator(false)
{
}

int Passengers::getId() const { return m_id; }
int Passengers::getCurrentFloor() const { return m_currentFloor; }
int Passengers::getDestinationFloor() const { return m_destinationFloor; }
bool Passengers::isOnElevator() const { return m_onElevator; }

void Passengers::setOnElevator(bool on)
{
    m_onElevator = on;
}
