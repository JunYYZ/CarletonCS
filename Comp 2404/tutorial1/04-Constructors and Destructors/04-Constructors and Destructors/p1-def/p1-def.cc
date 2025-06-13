#include <iostream>
#include <string>
#include <iomanip>

using namespace std;

void doTheMath(float x=0, float y=0, char op='+');

int main()
{
  doTheMath(2, 2);
  doTheMath(2, 2, '/');
  doTheMath(1, 2, '/');
  doTheMath(10.5f, 20, '*');
  doTheMath();


  return 0;
}

void doTheMath(float x, float y, char op)
{
  float answer;
  if (op == '+') answer = x + y;
  if (op == '-') answer = x - y;
  if (op == '*') answer = x * y;
  if (op == '/') answer = x / y;
  cout <<fixed<<setprecision(2) << x << " " <<op <<" "<< y <<" = " << answer <<endl;;
}

