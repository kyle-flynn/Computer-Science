#include <iostream>
#include <sstream>
#include <string>
#include <vector>

using namespace std;

void perm_helper (const string& start, const string& end) {
    if (end.size() <= 1) {
        cout << start + end << endl;
        return;
    } else {
        for (unsigned int i = 0; i < end.size(); i++) {
            string remain = end.substr(0, i) + end.substr(i+1);
            perm_helper(start + end[i], remain);
        }
    }
}

void permute (const string& input) {
    perm_helper ("", input);
}

//int main () {
//    permute ("POSTED");
//    return 0;
//}
