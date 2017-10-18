#include <iostream>
#include <vector>
#include <string>
//#include "gale_shapley.h"

using namespace std;

int main() {
    std::string && temp = "Hello World!";
    std::string & str = temp;
    str += " Life sucks.";

    std::cout << temp << std::endl;

    return 0;
}