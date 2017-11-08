#define CATCH_CONFIG_MAIN
#include "BinarySearchTree.h"
#include "catch.hpp"

#include <algorithm>
#include <vector>
using namespace std;

TEST_CASE("BST of integers")
{
vector<int> data {50, 25, 78, 15, 66, 92, 9, 20, 63, 76, 95, 68};
BinarySearchTree<int> t1;
for (auto x : data)
t1.insert (x);

SECTION ("Node counting") {
REQUIRE (t1.number_of_nodes() == data.size());
REQUIRE (t1.number_of_leaves() == 5);
REQUIRE (t1.number_of_full_nodes() == 4);
}

SECTION ("Remove Leaves") {
int before, after;
int leaves;
vector<int> testvals {20, 15, 66, 78, 50};
int k = 0;
while (!t1.isEmpty()) {
before = t1.number_of_nodes();
leaves = t1.number_of_leaves();
auto r = t1.remove_leaves();
after = t1.number_of_nodes();
REQUIRE (after < before);
REQUIRE (r.size() == leaves);
auto iter = find (r.begin(), r.end(), testvals[k]);
REQUIRE (iter != r.end());
k++;
}
}
}

TEST_CASE("BST of strings")
{
vector<string> data {"Kyle", "Mike", "Oscar", "Ben", "Ali", "Beth", "Cindy", "Deb", "Flora",
                     "Jane", "Jackie", "Paula"};
BinarySearchTree<string> t1;
for (auto x : data)
t1.insert (x);

SECTION ("Node counting") {
REQUIRE (t1.number_of_nodes() == data.size());
REQUIRE (t1.number_of_leaves() == 3);
REQUIRE (t1.number_of_full_nodes() == 2);
}

SECTION ("Remove Leaves") {
int before, after;
int leaves;
vector<string> testvals {"Paula", "Oscar", "Flora", "Deb", "Cindy", "Beth", "Ben", "Kyle"};
int k = 0;
while (!t1.isEmpty()) {
before = t1.number_of_nodes();
leaves = t1.number_of_leaves();
auto r = t1.remove_leaves();
after = t1.number_of_nodes();
REQUIRE (after < before);
REQUIRE (r.size() == leaves);
auto iter = find (r.begin(), r.end(), testvals[k]);
REQUIRE (iter != r.end());
k++;
}
}
}