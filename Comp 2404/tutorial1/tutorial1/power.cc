void power(int a, int b, int& c) {
    c = 1;
    for (int i = 0; i < b; ++i) {
        c *= a;
    }
}