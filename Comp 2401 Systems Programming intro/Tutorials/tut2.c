#include <stdio.h>

void printBitsRecursive(unsigned char c) {
	if (c <= 1) {
		printf("%d", c); // Print LSB
		return;
	}

	printBitsRecursive(c >> 1); // Right shift by 1 and recurse
	printf("%d", c & 1); // Print LSB
}

int main() {
	double fahrenheit, celsius;

	// Input Fahrenheit temperature
	printf("Enter temperature in Fahrenheit: ");
	scanf("%lf", &fahrenheit);

	// Calculate Celsius temperature
	celsius = 5 * (fahrenheit - 32) / 9;

	// Display Celsius temperature rounded to two decimal places
	printf("Temperature in Celsius: %.2lf\n", celsius);

	int n;

	// Input number of terms
	printf("Enter the number of terms in Fibonacci sequence (1 to 15): ");
	scanf("%d", &n);

	if (n < 1 || n > 15) {
		printf("Invalid input. Please enter a number between 1 and 15.\n");
		return 1;
	}

	// Initialize first two terms
	int a = 0, b = 1;
	int count = 0;

	// Display Fibonacci sequence with 5 items per line
	printf("Fibonacci Sequence:\n");
	while (count < n) {
		printf("%d\t", a);
		int next = a + b;
		a = b;
		b = next;
		count++;
	}
	printf("\n");
	unsigned char c;

	// Input character
	printf("Enter an unsigned char: ");
	scanf("%hhu", &c);

	// Print bits
	printf("Binary representation: ");
	printBitsRecursive(c);
	printf("\n");

	return 0;
}

