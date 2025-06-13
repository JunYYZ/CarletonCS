
#include "Date.h"

class Calendar{

    public:
        Calendar();
        void getDate(int, Date**);

    private:
        Date* dates[365];
};