/**
 *
 * a) The recursion terminates when the array size is 1, or if both sides of the
 * array are both a majority.
 * b) If N is odd, then one array is even and the other is odd. The algorithm still performs.
 * c) O(NlogN)
 * d) You can avoid using a second array by just using the address of the vector.
 *
 */

#include <iostream>
#include <vector>
#include <fstream>
#include <algorithm>

using namespace std;

/**
 * The following function simply scans a vector for how many times a number
 * occurs inside of it.
 * @param a input vector consisting of positive integers
 * @param number the number to search for inside of the vector
 * @return the amount of times that the number occurs inside of the vector.
 */
int numFreq(vector<int> a, int number) {
    int count = 0;
    for (int num : a) {
        if (num == number) {
            count++;
        }
    }
    return count;
}

/**
 * The following recursive function returns the majority element in a vector of integers
 * @param a input vector consisting of positive integers
 * @return the majority element or -1 if none is found
 */

int majority (vector<int> a) {

    if (a.size() == 1) {
        return a[0];
    }

    /* Calculate the size of half of the array, and assign a
     padding if it is odd. */
    int mid = a.size() / 2;
    int pad = a.size() % 2 == 0 ? 0 : 1;

    vector<int> left;
    vector<int> right;

    // Re-assign our sub-vectors
    left.assign(a.begin(), a.end()-mid);
    right.assign(a.begin()+mid+pad, a.end());

    int lMaj = majority(left);
    int rMaj = majority(right);

    if (lMaj == rMaj) {
        return lMaj;
    }

    int lCount = numFreq(a, lMaj);
    int rCount = numFreq(a, rMaj);

    if (lCount > mid) {
        return lMaj;
    } else if (rCount > mid) {
        return rMaj;
    } else {
        return -1;
    }

}

int main2(int argc, char* argv[]) {
    //Use clog for your debugging output
    clog << "This program runs from " << argv[0] << endl;
    if (argc < 2) {
        cerr << "Missing filename argument" << endl;
        exit (0);
    }

    /* The first command line argument is the input filename */
    ifstream inputfile {argv[1]};   /* input file stream */
    if (inputfile.good()) {
        int num_test, num_items_per_test;
        vector<int> testVector;

        inputfile >> num_test;  // read the number of test cases

        //Use clog for your debugging
        clog << "Number of test cases: " << num_test << endl;
        for (int k = 0; k < num_test; k++) {
            inputfile >> num_items_per_test;
            clog << "Test " << k << " has " << num_test << " data items" << endl;
            testVector.clear();
            for (int m = 0; m < num_items_per_test; m++) {
                int val;
                inputfile >> val;
                testVector.push_back(val);
            }
            // Use cout for the ACTUAL output
            cout << "Majority: " << majority(testVector) << endl;
        }
    } else {
        cerr << "Can't open data file " << argv[1] << endl;
    }
    return 0;
}