#include <iostream>

#include "TestScurt.h"
#include "TestExtins.h"


int main(){
    
    test_cea_mai_frecventa_valoare();
    std::cout << "Gata testul pentru cea mai frecventa valoare!\n";

    testAll();
    testAllExtins();
    std::cout<<"Finished Tests!"<<std::endl;
}
