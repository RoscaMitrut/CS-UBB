#include <assert.h>

#include "MDO.h"
#include "IteratorMDO.h"
#include <iostream>
#include <exception>
#include <vector>

using namespace std;

bool relatie1(TCheie cheie1, TCheie cheie2) {
	if (cheie1 <= cheie2) {
		return true;
	}
	else {
		return false;
	}
}

void testAll(){
	MDO dictOrd = MDO(relatie1);
	assert(dictOrd.dim() == 0);
	assert(dictOrd.vid());
    dictOrd.adauga(1,2);
    dictOrd.adauga(1,3);
    assert(dictOrd.dim() == 2);
    assert(!dictOrd.vid());
    vector<TValoare> v= dictOrd.cauta(1);
    assert(v.size()==2);
    v= dictOrd.cauta(3);
    assert(v.size()==0);
    IteratorMDO it = dictOrd.iterator();
    it.prim();
    while (it.valid()){
    	TElem e = it.element();
    	it.urmator();
    }
    assert(dictOrd.sterge(1, 2) == true);
    assert(dictOrd.sterge(1, 3) == true);
    assert(dictOrd.sterge(2, 1) == false);
    assert(dictOrd.vid());
}

void test_cea_mai_frecventa_valoare() {

    MDO dict = MDO(relatie1);
    assert(dict.vid() == true);
    assert(dict.ceaMaiFrecventaValoare() == NULL_TVALOARE);

    dict.adauga(1, 1);
    dict.adauga(2, 2);
    dict.adauga(1, 3);
    dict.adauga(7, 1);
    assert(dict.dim() == 4);
    assert(dict.ceaMaiFrecventaValoare() == 1);

    dict.sterge(1, 1);
    assert(dict.dim() == 3);
    assert(dict.ceaMaiFrecventaValoare() == 3);

    dict.adauga(12, 7);
    dict.adauga(345, 7);
    dict.adauga(123, 7);
    assert(dict.dim() == 6);
    assert(dict.ceaMaiFrecventaValoare() == 7);
}
