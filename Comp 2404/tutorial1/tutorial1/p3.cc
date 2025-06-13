#include <iostream>

// power function declaration power.cc
void power(int a, int b, int& c);

int main() {
    int base, exponent, result;
    std::cout << "Please enter two integers: ";
    std::cin >> base >> exponent;
    power(base, exponent, result);
    std::cout << base << " to the power of " << exponent << " is " << result << std::endl;
    return 0;
}