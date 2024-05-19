#include "IteratorMDO.h"
#include "MDO.h"
#include <iostream>
#include <vector>

#include <exception>
using namespace std;

void MDO::det_recursiv(PNod p, vector<TValoare>& v) const {
	if (p == nullptr)
		return;
	else {
		det_recursiv(p->left, v);
		det_recursiv(p->right, v);
		v.push_back(p->elem.second);
	}
}
// complexitate theta(1)
Nod::Nod(TElem elem, PNod left, PNod right) {
	this->elem = elem;
	this->left = left;
	this->right = right;
}

// complexitate Theta(1)
TElem Nod::element() {
	return this->elem;
}

// complexitate Theta(1)
PNod Nod::stanga() {
	return this->left;
}

// complexitate Theta(1)
PNod Nod::dreapta() {
	return this->right;
}

// complexitate O(n)
PNod MDO::adauga_rec(PNod p, TElem e) {
	if (p == nullptr) {
		p = new Nod(e, nullptr, nullptr);
	}
	else {
		if (r(p->elem.first, e.first))
			p->left = adauga_rec(p->left, e);
		else
			p->right = adauga_rec(p->right, e);
	}
	return p;
}

void MDO::distrug_rec(PNod p) {
	if (p != nullptr) {
		distrug_rec(p->stanga());
		distrug_rec(p->dreapta());
		delete p;
	}
}

// complexitate O(n)
void MDO::cauta_rec(PNod p, TCheie c, vector<TValoare>& v) const {
	if (p == nullptr)
		return;
	else {
		if (p->elem.first == c)
			v.push_back(p->elem.second);
		if (r(p->elem.first, c))
			cauta_rec(p->left, c, v);
		else
			cauta_rec(p->right, c, v);
	}
}

// complexitate O(n)
pair<PNod, PNod> MDO::minim_drept(PNod curent, PNod parinte) {
	if (curent->right == nullptr)
		return make_pair(curent, parinte);
	return minim_drept(curent->right, curent);
}

// complexitate O(n)
void MDO::sterge_rec(PNod curent, TElem elem, PNod parinte) {
	if (curent == nullptr) {
		return;
	}
	else {
		if (curent->elem.first == elem.first && curent->elem.second == elem.second) {
			this->size--;
			// cazul fara fii sau cu un singur fiu
			if (curent->left == nullptr) {
				// daca e radacina
				if (parinte == nullptr) {
					parinte = curent->right;
					this->rad = curent->right;
				}
				else {
					// daca nu e radacina
					if (r(parinte->elem.first, elem.first))
						parinte->left = curent->right;
					else
						parinte->right = curent->right;
				}
			}
			else {
				if (curent->right == nullptr) {
					// daca e radacina
					if (parinte == nullptr) {
						parinte = curent->left;
						this->rad = curent->left;
					}
					else {
						//daca nu e radacina
						if (r(parinte->elem.first, elem.first))
							parinte->left = curent->left;
						else
							parinte->right = curent->left;
					}
				}
				// cazul cu doi fii
				else {
					auto minim = minim_drept(curent->left, curent);
					PNod min = minim.first;
					PNod parinte_min = minim.second;
					curent->elem = min->elem;
					if (r(parinte_min->elem.first, min->elem.first))
						parinte_min->left = min->left;
					else
						parinte_min->right = min->left;
					delete min;
				}
			}

		}

	}
	if (r(curent->elem.first, elem.first))
		sterge_rec(curent->left, elem, curent);
	else
		sterge_rec(curent->right, elem, curent);
}

// complexitate Theta(1)
MDO::MDO(Relatie r) {
	this->r = r;
	this->rad = nullptr;
	this->size = 0;
}

// complexitate O(n)
void MDO::adauga(TCheie c, TValoare v) {
	TElem elem = make_pair(c, v);
	this->size++;
	rad = adauga_rec(rad, elem);
}

// complexitate O(n)
vector<TValoare> MDO::cauta(TCheie c) const {
	vector<TValoare> v;
	PNod radacina = rad;
	cauta_rec(rad, c, v);
	return v;
}

// complexitate O(n)
bool MDO::sterge(TCheie c, TValoare v) {
	int old_size = size;
	TElem elem = make_pair(c, v);
	sterge_rec(rad, elem, nullptr);
	return old_size > size;
}




// complexitate Theta(1)
int MDO::dim() const {
	if (this->vid())
		return 0;
	else
		return this->size;
}

// complexitate Theta(1)
bool MDO::vid() const {
	return this->rad == nullptr;
}

// complexitate Theta(1)
IteratorMDO MDO::iterator() const {
	return IteratorMDO(*this);
}

/// complexitate Theta(n)
MDO::~MDO() {
	distrug_rec(rad);
}

// O(n^2)
int MDO::adaugaInexistente(MDO& mdo) {
	int contor = 0;
	IteratorMDO it = mdo.iterator();
	it.prim();
	
	while (it.valid()) {
		TElem el = it.element();
		vector<TValoare> v = mdo.cauta(el.first);
		vector<TValoare> v2 = cauta(el.first);
		for(int i = 0;i < v.size();i++) {
			if(!(find(v2.begin(),v2.end(),v[i])!=v2.end())){
				adauga(el.first, v[i]);
				contor++;
			}
		}
		it.urmator();
	}
	return contor;
}