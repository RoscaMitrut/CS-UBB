#include <iostream>
#include "TestExtins.h"
#include "TestScurt.h"

//
#include "Multime.h"
#include "IteratorMultime.h"
#include "assert.h"
//

using namespace std;

int main() {

	testAll();
	testAllExtins();

	Multime m1;
	m1.adauga(51);//
	m1.adauga(72);
	m1.adauga(10);//
	m1.adauga(2341);
	m1.adauga(1234);
	m1.adauga(2346);//

	Multime m2;
	m2.adauga(51);
	m2.adauga(10);
	m2.adauga(2346);

	m1.intersectie(m2);

	assert(m1.element(0) == 10);
	assert(m1.element(1) == 51);
	assert(m2.element(2) == 2346);

	assert(m1.cauta(72) == false);
	assert(m1.cauta(2341) == false);
	assert(m1.cauta(1234) == false);

	assert(m1.dim() == 3);

	cout << "End";
}
