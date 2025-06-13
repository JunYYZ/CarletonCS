#ifndef DRAWABLE_H
#define DRAWABLE_H

#include "View.h"

class Drawable
{
protected:
    int layer;

public:
    Drawable(int layer) : layer(layer) {}
    virtual ~Drawable() = default;

    virtual void draw(View &view) = 0; // pure virtual function
    static int compare(Drawable *d1, Drawable *d2);
};

#endif // DRAWABLE_H
