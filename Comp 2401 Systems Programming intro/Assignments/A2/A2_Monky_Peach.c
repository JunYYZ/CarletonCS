#include <stdio.h>

int monkeyPeachBusiness(int day)
{
    if (day == 1)
        return 1; // last peach :(
    else
        return (monkeyPeachBusiness(day - 1) + 1) * 2; // recursive formula: +1 to to peach first, then double it
}

int main()
{
    int nDay;
    printf("Enter the day number (N): ");
    scanf("%d", &nDay);
    // error catching. I think it should be impossible for the monkey to travel back in time
    if (nDay < 1)
    {
        printf("monkey travelled back in time?\n");
        return 1;
    }

    printf("This means the monkey must have picked [%d] peaches on the first day\n", monkeyPeachBusiness(nDay));
    return 0;
}
