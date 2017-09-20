#include <iostream>
#include <sstream>
#include <string>
#include <vector>

using namespace std;

/* Method that takes two read-only string references and permutes
 * recursively the end string starting at the start string.
 */
void perm_helper (const string& start, const string& end) {
    if (end.size() <= 1) {
        cout << start + end << endl;
        return;
    } else {
        for (unsigned int i = 0; i < end.size(); i++) {

            /* Since our strings are read-only, let's only read their substrings.
             * The remaining string is the characters except the first character
             * of the string based on i. This essentially shuffles around the
             * string that we are trying to permute.
             */
            string remain = end.substr(0, i) + end.substr(i+1);
            perm_helper(start + end[i], remain);
        }
    }
}

void permute (const string& input) {
    perm_helper ("", input);
}

int main () {
    permute ("POSTED");
    return 0;
}
