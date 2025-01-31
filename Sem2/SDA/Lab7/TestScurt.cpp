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


// adauga în multidicționarul curent toate perechile din mdo care nu se află deja în multidicționar.
// returnează numărul de perechi adăugate
//int adaugaInexistente(MultidictionarOrdonat& mdo);

void testCerinta() {

    MDO a = MDO(relatie1);
    a.adauga(11, 11);//
    a.adauga(11, 12);//
    a.adauga(22, 6);
    a.adauga(22, 8);
    a.adauga(7, 17);
    a.adauga(19, 59);
    a.adauga(13, 13);
    
    MDO b = MDO(relatie1);
    b.adauga(11, 11);//
    b.adauga(11, 12);//
    b.adauga(29, 23);
    b.adauga(26, 14);

    assert(b.dim() == 4);
    assert(b.adaugaInexistente(a) == 5);
    assert(b.dim() == 9);

    assert(a.dim() == 7);
    assert(a.adaugaInexistente(b) == 2);
    assert(a.dim() == 9);

    assert(a.dim() == 9);
    assert(a.adaugaInexistente(b) == 0);
    assert(a.dim() == 9);
    
    cout << "Gata teste cerinta!\n\n";
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

