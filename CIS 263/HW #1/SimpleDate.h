//
// Created by Kyle Flynn on 8/31/2017.
//

#ifndef HW_1_SIMPLEDATE_H
#define HW_1_SIMPLEDATE_H

class SimpleDate {

public:
    SimpleDate (int month, int day, int year);
    SimpleDate daysFromNow (int n);
    int getMonth () const;
    int getDay () const;
    int getYear () const;
    int compareTo (SimpleDate other);
    int dayOfWeek ();
    int ordinalDate ();
    bool isLeapYear ();
    static bool isLeapYear (int year);

private:
    static const int MIN_YEAR;
    static const int NUM_MONTHS;
    static const int DAYS_IN_MONTH[];
    static const int DAYS_THUS_FAR[];

    int month;
    int day;
    int year;

    int daysInYear (int year);
    SimpleDate nextDate ();
    static int daysInMonth (int month, int year);
    static bool isValidDate (int month, int day, int year);

};

#endif //HW_1_SIMPLEDATE_H