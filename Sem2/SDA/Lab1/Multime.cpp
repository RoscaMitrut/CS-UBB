#include "Multime.h"
#include "IteratorMultime.h"
#include <iostream>

using namespace std;

//o posibila relatie
bool rel(TElem e1, TElem e2) {
	if (e1 <= e2) {
		return true;
	}
	else {
		return false;
	}
}


Multime::Multime() {
	capacitate = 2;
	elems = new TElem[capacitate];
	lungime = 0;
}


bool Multime::adauga(TElem elem) {
	if (lungime == capacitate){
		TElem* newelems = new TElem[capacitate*2];

		for (int i = 0;i < lungime;i++) {
			newelems[i] = elems[i];
		}
		capacitate *= 2;
		delete[] elems;
		elems = newelems;
		
	}

	for (int i = 0;i < lungime;i++) {
		if (elem == elems[i])return false;
		if (rel(elem,elems[i]) == true) {
			for (int j = lungime;j > i;j--) {
				elems[j] = elems[j - 1];
			}
			elems[i] = elem;
			lungime++;
			return true;
		}

	}
	elems[lungime] = elem;
	lungime++;
	return true;
}


bool Multime::sterge(TElem elem) {
	for (int i = 0;i <lungime;i++) {
		if (elems[i] == elem){
			for (int j = i;j < lungime-1;++j) {
				elems[j] = elems[j + 1];
			}
			lungime--;
			return true;
		}
	}
	return false;
}	


bool Multime::cauta(TElem elem) const {
	for (int i = 0;i < lungime;i++) {
		if (elems[i] == elem){
			return true;
		}
	}
	return false;
}


int Multime::dim() const {
	return lungime;
}

TElem Multime::element(int i) const {
	return elems[i];
}//


bool Multime::vida() const {
	if (lungime == 0) return true;
	return false;
}

void Multime::intersectie(const Multime& b) {
	for (int i = lungime-1;i >= 0;i--) {
		int flag = 0;
		for (int j = 0;j < b.lungime;j++) {
			if (elems[i] == b.elems[j]) {
				flag = 1;
			}
		}
		if (flag == 0) {
			sterge(elems[i]);
		}
	}
}

IteratorMultime Multime::iterator() const {
	return IteratorMultime(*this);
}


Multime::~Multime() {
	lungime = 0;
	delete[] elems;
}






