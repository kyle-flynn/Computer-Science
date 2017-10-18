#include <iostream>
#include <vector>

void swap(int & x, int & y) {
    int temp = x;
    x = y;
    y = temp;
}

int main() {
    int x = 3;
    int y = 10;
    std::cout << "Current X is " << x << " and current Y is " << y << std::endl;
    swap(x, y);
    std::cout << "Current X is " << x << " and current Y is " << y << std::endl;

    std::vector<int> test {1, 3, 5, 8, 6};

    return 0;
}