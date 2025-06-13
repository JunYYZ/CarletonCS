

#include "Calendar.h"

Calendar::Calendar(){
    dates[0] = new Date(2022);
    for (int i = 1; i < 365; ++i){
        dates[i] = new Date;
        dates[i]->setDate(*dates[i-1]);
        dates[i]->incDate();
    }
}

void Calendar::getDate(int index, Date** d){
    if (index < 0 || index > 365){
        *d = NULL;
        return;
    }

    *d = dates[index];
}