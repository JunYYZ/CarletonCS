#include <stdio.h>

typedef struct {
    int year;
    int month;
    int day;
} Date;

int isLeapYear(int year) {
    return (year % 4 == 0 && year % 100 != 0) || (year % 400 == 0);
}

int getDayOfYear(Date date) {
    int daysInMonth[] = {31, 28 + isLeapYear(date.year), 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
    int dayOfYear = 0;
    for (int i = 0; i < date.month - 1; i++) {
        dayOfYear += daysInMonth[i];
    }
    dayOfYear += date.day;
    return dayOfYear;
}

void promptDate(Date* date) {
    printf("Enter date (month/day/year): ");
    if (scanf("%d/%d/%d", &date->month, &date->day, &date->year) != 3) {
        printf("Error: Invalid input format.\n");
    }
    // Additional validation for month, day, and year can be added here.
}

int main() {
    Date date;
    promptDate(&date);
    printf("Day of the year: %d\n", getDayOfYear(date));
    return 0;
}