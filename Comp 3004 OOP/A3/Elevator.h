#ifndef ELEVATOR_H
#define ELEVATOR_H

#include <QObject>
#include <QString>
#include <QVector>

enum class ElevatorState {
    IDLE,
    MOVING,
    BOARDING,
    DOOR_OPENING,
    DOOR_CLOSING,
    EMERGENCY
};

class Elevator : public QObject
{
    Q_OBJECT
public:
    Elevator(int id, int floorCount, QObject* parent=nullptr);
    ~Elevator() override = default;

    void onTimeStep();
    void reset(int floorCount);

    void triggerFire();
    void triggerDoorObstacle();

    void addFloorRequest(int floor);

    bool hasRequests() const;

    QString describe() const;

signals:
    void logMessage(const QString &msg);

private:
    void moveOneFloor();
    void openDoor();
    void closeDoor();

    int  m_id;
    int  m_floorCount;
    int  m_currentFloor;
    ElevatorState m_state;

    bool m_inEmergency;
    bool m_doorObstacleInProgress;
    int  m_obstacleTimer;
    bool m_movingUp;
    int  m_timeCounter;

    // floors to visit
    QVector<int> m_requests;
};

#endif // ELEVATOR_H
