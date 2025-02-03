#include <iostream>
#include "MyLinkedList.h"
#include "secv.h"
#include "paralel.h"
using namespace std;

int main() {
    secvential();
    paralel(2, 2);
    return 0;
}
