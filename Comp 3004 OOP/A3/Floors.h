#ifndef FLOORS_H
#define FLOORS_H

#include <QObject>

class Floors : public QObject
{
    Q_OBJECT
public:
    Floors(int floorNumber, QObject* parent=nullptr);
    ~Floors() = default;

    int getFloorNumber() const;
    bool isUpLit() const { return upLit; }
    bool isDownLit() const { return downLit; }

    void pressUp();
    void pressDown();
    void clearButtons();

signals:
    void floorButtonPressed(int floor, bool goingUp);

private:
    int number;
    bool upLit;
    bool downLit;
};

#endif // FLOORS_H
