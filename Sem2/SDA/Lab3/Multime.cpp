#include "Multime.h"
#include "IteratorMultime.h"

#include <iostream>

// complexitate : teta(1)
Multime::Multime() {
	primul = NULL;
	ultimul = NULL;
}


bool Multime::adauga(TElem elem) {
	if (primul == NULL) {
		Nod* p = new Nod;
		p->valoare = elem;
		p->urmator = NULL;
		this->primul = this->ultimul = p;
		return true;
	}
	if (cauta(elem)) {
		return false;
	}
	Nod* p = new Nod;
	p->valoare = elem;
	p->urmator = NULL;
	ultimul->urmator = p;
	ultimul = p;
	return true;
}


bool Multime::sterge(TElem elem) {
	if (!cauta(elem))
		return false;

	Nod* p = primul;

	if (p->valoare == elem) {
		primul = primul->urmator;
		return true;
	}

	Nod* aux;
	while (p->urmator != NULL) {
		if (p->urmator->valoare == elem) {
			aux = p->urmator;
			p->urmator = p->urmator->urmator;
			delete aux;
			return true;
		}
		else {
			p = p->urmator;
		}
	}
	return false;
}


bool Multime::cauta(TElem elem) const {
	Nod* p = primul;
	while (p != NULL && p->valoare != elem) {
		p = p->urmator;
	}
	if (p == NULL) {
		return false;
	}
	return true;
}


int Multime::dim() const {
	int n = 0;
	Nod* p = primul;
	while (p != NULL) {
		n++;
		p = p->urmator;
	}
	return n;
}

bool Multime::vida() const {
	return dim() == 0;
}


Multime::~Multime() {
	
	Nod* curent = this->primul;
	while (curent != NULL) {
		Nod* urmatoru = curent->urmator;
		delete curent;
		curent = urmatoru;
	}
	delete curent;

}

IteratorMultime Multime::iterator() const {
	return IteratorMultime(*this);
}