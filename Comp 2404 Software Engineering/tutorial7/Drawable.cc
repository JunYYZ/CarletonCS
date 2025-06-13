#include "Drawable.h"

// Static comparison function to compare layers
int Drawable::compare(Drawable* d1, Drawable* d2) {
    return d1->layer - d2->layer;
}
