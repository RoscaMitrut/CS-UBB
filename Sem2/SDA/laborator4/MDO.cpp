#include "IteratorMDO.h"
#include "MDO.h"
#include <iostream>
#include <vector>

#include <exception>
using namespace std;

MDO::MDO(Relatie r) {
	this->cp = INITIAL_CAPACITY;

	this->prim = -1;

	this->rel = r;

	this->elems = new TElem[cp];

	this->urm = new int[cp];

	for (int i = 0; i < this->cp - 1; i++) {
		this->urm[i] = i + 1;
	}
	this->urm[cp - 1] = -1;

	this->primLiber = 0;
}

int MDO::aloca() {
	int i = this->primLiber;
	this->primLiber = this->urm[primLiber];

	return i;
}


void MDO::dealoca(int i) {
	this->urm[i] = this->primLiber;
	this->primLiber = i;
}


void MDO::redim() {
	int newCapacity = 2 * this->cp;

	TElem* newElems = new TElem[newCapacity];

	int* newUrm = new int[newCapacity];

	for (int i = 0; i < this->cp; i++) {
		newElems[i] = this->elems[i];
		newUrm[i] = this->urm[i];
	}

	delete[] this->elems;
	delete[] this->urm;

	this->elems = newElems;
	this->urm = newUrm;

	for (int i = this->cp; i < newCapacity-1; i++) {
		this->urm[i] = i + 1;
	}

	this->urm[newCapacity - 1] = -1;
	this->primLiber = this->cp;

	this->cp = newCapacity;
}

int MDO::creeazaNod(TElem e) {
	if (this->primLiber == -1) {
		redim();
	}
	int i = aloca();
	this->elems[i] = e;
	this->urm[i] = -1;

	return i;
}

void MDO::adauga(TCheie c, TValoare v) {
	int nouNod = creeazaNod({ c,v });

	if (this->prim == -1) {
		this->urm[nouNod] = this->prim;
		this->prim = nouNod;
	}
	else {
		int i = this->prim;
		while ( i != -1 && rel(this->elems[i].first, c)) {
			i = this->urm[i];
		}

		if (i == this->prim) {
			this->urm[nouNod] = this->prim;
			this->prim = nouNod;
		}
		else {
			int nod = this->prim;
			while (this->urm[nod] != i) {
				nod = this->urm[nod];
			}

			if (i == -1) {
				this->urm[nod] = nouNod;
			}
			else {
				this->urm[nouNod] = this->urm[nod];
				this->urm[nod] = nouNod;
			}
		}
	}
}
vector<TValoare> MDO::cauta(TCheie c) const {
	if (vid()) {
		return {};
	}

	vector<TValoare> v;
	int nod = this->prim;

	while (nod != -1) {
		if (elems[nod].first == c) {
			v.push_back(elems[nod].second);
		}
		nod = urm[nod];
	}

	return v;
}


bool MDO::sterge(TCheie c, TValoare v) {
	int nod = this->prim;
	bool gasit = false;
	int p = -1;

	while (nod != -1 && gasit == false) {
		if (elems[nod].first == c && elems[nod].second == v) {
			gasit = true;
			p = nod;
		}

		nod = urm[nod];
	}

	if (gasit == true) {
		if (p == this->prim) {
			prim = urm[prim];
		}
		else {
			int q = this->prim;
			while (urm[q] != p) {
				q = urm[q];
			}
			urm[q] = urm[p];
		}
		dealoca(p);
	}

	return gasit;
}

int MDO::dim() const {
	if (this->prim == -1) {
		return 0;
	}
	else {
		int dim = 1;
		int i = this->prim;
		while (this->urm[i] != -1) {
			i = this->urm[i];
			dim++;
		}

		return dim;
	}
}

bool MDO::vid() const {
	return(this->prim == -1);
	//return(dim() == 0);
}

IteratorMDO MDO::iterator() const {
	return IteratorMDO(*this);
}

MDO::~MDO() {
	delete[] this->elems;
	delete[] this->urm;
}


TValoare MDO::ceaMaiFrecventaValoare() const {
	if (vid()) return NULL_TVALOARE;

	//init frecventa maxima si valoare max cu NULL_TVALOARE
	int frecvMax = NULL_TVALOARE;
	TValoare valoareMax = NULL_TVALOARE;

	int nod = this->prim;

	while (nod != -1) {
		//luam valoarea nodului actual
		TValoare valoare = this->elems[nod].second;
		int frecvCurent = 0;
		int p = this->prim;
		//verificam numarul de aparitii ale valoarii nodului actual
		while (p != -1) {
			if (this->elems[p].second == valoare) 
				frecvCurent++;
			p = this->urm[p];
		}
		//daca frecventa valorii este mai mare decat frecventa maxima
		//schimbam frecventa maxima si valoarea maxima
		if (frecvCurent > frecvMax) {
			frecvMax = frecvCurent;
			valoareMax = valoare;
		}

		nod = this->urm[nod];
	}

	return valoareMax;
}// daca avem frecventa egala la mai multe valori, este returnata prima care apare