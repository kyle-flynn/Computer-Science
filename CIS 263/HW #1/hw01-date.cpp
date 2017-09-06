//
// Created by Kyle Flynn on 8/31/2017.
//

#include "SimpleDate.h"
#include <iostream>

using namespace std;

int main() {

    SimpleDate dateOne(12, 25, 1995);
    SimpleDate dateTwo(12, 24, 2000);

    cout << dateOne.getMonth() << " " << dateOne.getDay() << " " << dateOne.getYear() << endl;
    cout << dateOne.compareTo(dateTwo) << endl;
    cout << dateOne.dayOfWeek() << endl;
    cout << dateOne.daysFromNow(20).ordinalDate() << endl;
    cout << dateOne.isLeapYear() << endl;

    return 0;
}