#include "Elevator.h"
#include <QDebug>

Elevator::Elevator(int id, int floorCount, QObject* parent)
    : QObject(parent),
      m_id(id),
      m_floorCount(floorCount),
      m_currentFloor(1),
      m_state(ElevatorState::IDLE),
      m_inEmergency(false),
      m_doorObstacleInProgress(false),
      m_obstacleTimer(0),
      m_movingUp(true),
      m_timeCounter(0)
{
}

void Elevator::reset(int floorCount)
{
    m_floorCount = floorCount;
    m_currentFloor = 1;
    m_state = ElevatorState::IDLE;
    m_inEmergency = false;
    m_doorObstacleInProgress = false;
    m_obstacleTimer = 0;
    m_movingUp = true;
    m_timeCounter = 0;
    m_requests.clear();

    emit logMessage(QString("[Elev%1] Reset => floor=1, IDLE").arg(m_id));
}

void Elevator::onTimeStep()
{
    if(m_inEmergency) {
        emit logMessage(QString("[Elev%1] In EMERGENCY => no normal movement").arg(m_id));
        return;
    }
    if(m_doorObstacleInProgress) {
        if(m_obstacleTimer > 0) {
            m_obstacleTimer--;
            if(m_obstacleTimer == 0) {
                emit logMessage("[door obstacle removed => returning to normal]");
                m_doorObstacleInProgress = false;
                m_state = ElevatorState::IDLE;
            }
        }
        return;
    }

    // remain idle if no requests 
    if(m_requests.isEmpty()) {
        m_state = ElevatorState::IDLE;
        return;
    }

    // move
    if(m_state == ElevatorState::IDLE) {
        m_state = ElevatorState::MOVING;
    }
    if(m_state == ElevatorState::MOVING) {
        moveOneFloor();
    }
    // DOOR_OPENING, etc. stuff thats not in the assignment
}

void Elevator::triggerFire()
{
    m_state = ElevatorState::EMERGENCY;
    m_inEmergency = true;
    m_currentFloor = 1;
    emit logMessage(QString("[Elev%1] Fire => EMERGENCY, floor=1").arg(m_id));
}

void Elevator::triggerDoorObstacle()
{
    if(m_state == ElevatorState::DOOR_CLOSING) {
        m_doorObstacleInProgress = true;
        m_obstacleTimer = 20;
        m_state = ElevatorState::EMERGENCY;
        emit logMessage("[door obstacle => opening door again]");
    }
    else {
        emit logMessage("[door obstacle triggered => not in DOOR_CLOSING]");
    }
}

void Elevator::addFloorRequest(int floor)
{
    if(!m_requests.contains(floor)) {
        m_requests.append(floor);
        emit logMessage(QString("[Elev%1] request => floor %2").arg(m_id).arg(floor));
    }
}

bool Elevator::hasRequests() const
{
    return !m_requests.isEmpty();
}

void Elevator::moveOneFloor()
{
    if(m_requests.isEmpty()) {
        m_state = ElevatorState::IDLE;
        return;
    }
    int target = m_requests.first();
    if(target > m_currentFloor) {
        // move up 1
        m_currentFloor++;
        emit logMessage(QString("[Elev%1] up => floor %2").arg(m_id).arg(m_currentFloor));
    }
    else if(target < m_currentFloor) {
        // move down 1
        m_currentFloor--;
        emit logMessage(QString("[Elev%1] down => floor %2").arg(m_id).arg(m_currentFloor));
    }
    // if arrived
    if(m_currentFloor == target) {
        m_requests.removeFirst();
    }
}

void Elevator::openDoor()
{
    m_state = ElevatorState::DOOR_OPENING;
    emit logMessage(QString("[Elev%1] Doors opening").arg(m_id));
}

void Elevator::closeDoor()
{
    m_state = ElevatorState::DOOR_CLOSING;
    emit logMessage(QString("[Elev%1] Doors closing").arg(m_id));
}

QString Elevator::describe() const
{
    QString st;
    switch(m_state){
    case ElevatorState::IDLE:         st = "IDLE"; break;
    case ElevatorState::MOVING:       st = "MOVING"; break;
    case ElevatorState::BOARDING:     st = "BOARDING"; break;
    case ElevatorState::DOOR_OPENING: st = "DOOR_OPEN"; break;
    case ElevatorState::DOOR_CLOSING: st = "DOOR_CLOSE"; break;
    case ElevatorState::EMERGENCY:    st = "EMERGENCY"; break;
    }
    return QString("Elev%1 | Floor:%2 | State:%3")
            .arg(m_id).arg(m_currentFloor).arg(st);
}
