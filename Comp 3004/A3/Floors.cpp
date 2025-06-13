#include "Floors.h"
#include <QDebug>

Floors::Floors(int floorNumber, QObject* parent)
    : QObject(parent),
      number(floorNumber),
      upLit(false),
      downLit(false)
{
}

int Floors::getFloorNumber() const
{
    return number;
}

void Floors::pressUp()
{
    upLit = true;
    emit floorButtonPressed(number, true);
    qDebug() << "[Floor" << number << "] Up pressed";
}

void Floors::pressDown()
{
    downLit = true;
    emit floorButtonPressed(number, false);
    qDebug() << "[Floor" << number << "] Down pressed";
}

void Floors::clearButtons()
{
    upLit = false;
    downLit = false;
}
