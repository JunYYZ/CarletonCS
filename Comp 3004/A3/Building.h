#ifndef BUILDING_H
#define BUILDING_H

#include <QObject>
#include <QVector>
#include "Floors.h"

class Building : public QObject
{
    Q_OBJECT
public:
    Building(int numFloors, QObject* parent=nullptr);
    ~Building();

    Floors* getFloor(int floorNumber);
    int getNumFloors() const;
    int getSafeFloor() const { return safeFloor; }

private:
    QVector<Floors*> floors;
    int safeFloor;
};

#endif // BUILDING_H
