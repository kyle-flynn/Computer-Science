#include <iostream>
#include <vector>
#include "hakimi_havel.h"

using namespace std;

int main() {
    hakimi_havel hh;
    vector<int> sequence = {4,3,3,2,2,2};
    bool isValid = hh.run(&sequence);
    cout << "Sequence validity is " << isValid << endl;
    return 0;
}