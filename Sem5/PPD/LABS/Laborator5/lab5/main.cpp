#include <iostream>
#include "paralel.h"
using namespace std;

int main() {
    secvential();
    paralel(4, 2);
    //paralel(4, 4);
    //paralel(4, 12);

    return 0;
}
