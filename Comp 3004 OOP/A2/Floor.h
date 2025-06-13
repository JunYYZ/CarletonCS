#ifndef FLOOR_H
#define FLOOR_H

#include <string>

class Floor {
public:
    Floor(int number);
    void pressUpButton();
    void pressDownButton();

    bool isUpButtonLit() const;
    bool isDownButtonLit() const;
    int getFloorNumber() const;

private:
    int floorNumber;
    bool upButtonLit;
    bool downButtonLit;
};

#endif // FLOOR_H
