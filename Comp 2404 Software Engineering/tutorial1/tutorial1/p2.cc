#include <iostream>

int main() {
    int num1, num2;
    std::cout << "Please enter two integers: ";
    std::cin >> num1 >> num2;
    int product = num1 * num2;
    std::cout << "The product of " << num1 << " and " << num2 << " is " << product << std::endl;
    return 0;
}